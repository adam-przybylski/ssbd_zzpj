package pl.lodz.p.it.ssbd2024.ssbd01.mow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.Account;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mow.Event;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mow.Session;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mow.Ticket;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.OptLockException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mow.*;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mow.TicketNotFoundException;
import pl.lodz.p.it.ssbd2024.ssbd01.mow.repository.EventRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.mow.repository.SessionRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.mow.repository.TicketRepository;
import pl.lodz.p.it.ssbd2024.ssbd01.util.ETagBuilder;
import pl.lodz.p.it.ssbd2024.ssbd01.util.PageUtils;
import pl.lodz.p.it.ssbd2024.ssbd01.util.messages.ExceptionMessages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static pl.lodz.p.it.ssbd2024.ssbd01.util.Utils.isSessionActive;

@Service
@RequiredArgsConstructor
public class MeEventService {

    private final SessionRepository sessionRepository;
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}, timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_PARTICIPANT')")
    public void signUpForSession(UUID sessionId)
            throws SessionNotFoundException, AlreadySignUpException, MaxSeatsOfSessionReachedException,
            SessionNotActiveException {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new SessionNotFoundException(ExceptionMessages.SESSION_NOT_FOUND));
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!isSessionActive(session)) {
            throw new SessionNotActiveException(ExceptionMessages.SESSION_NOT_ACTIVE);
        }
        if (ticketRepository.findBySession(session).size() >= session.getMaxSeats()) {
            throw new MaxSeatsOfSessionReachedException(ExceptionMessages.MAX_SEATS_REACHED);
        }
        Optional<Ticket> accountTicket = ticketRepository.findBySessionAndAccount(session, account);
        if (accountTicket.isPresent() && !accountTicket.get().getIsNotCancelled()) {
            accountTicket.get().setIsNotCancelled(true);
            accountTicket.get().setReservationTime(LocalDateTime.now());
            ticketRepository.saveAndFlush(accountTicket.get());
        } else if (accountTicket.isPresent()) {
            throw new AlreadySignUpException(ExceptionMessages.ALREADY_SIGNED_UP);
        }
        Ticket ticket = new Ticket(account, session);
        ticketRepository.saveAndFlush(ticket);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}, timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_PARTICIPANT')")
    public Ticket getSession(UUID id) throws TicketNotFoundException {
        return ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(ExceptionMessages.TICKET_NOT_FOUND));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}, timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_PARTICIPANT')")
    public Page<Ticket> getMyFutureAndPresentSessions(PageUtils pageUtils) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = pageUtils.buildPageable();
        return ticketRepository.findAllByAccountIdAndEndTimeAfterNow(account.getId(), LocalDateTime.now(), pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}, timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_PARTICIPANT')")
    public Page<Ticket> getMyHistoricalSessions(PageUtils pageUtils) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = pageUtils.buildPageable();
        return ticketRepository.findAllByAccountIdAndEndTimeBeforeNow(account.getId(), LocalDateTime.now(), pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}, timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_PARTICIPANT')")
    public Page<Event> getMyHistoricalEvents(PageUtils pageUtils) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = pageUtils.buildPageable();
        return eventRepository.findAllByEndDateBeforeAndSessions_Tickets_Account(LocalDate.now(),
                account, pageable);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class}, timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_PARTICIPANT')")
    public void signOutFromSession(UUID id, String eTag) throws TicketNotFoundException, TicketAlreadyCancelledException, OptLockException {
        Ticket ticket = ticketRepository.findById(id).orElseThrow(() -> new TicketNotFoundException(ExceptionMessages.TICKET_NOT_FOUND));

        if (!ETagBuilder.isETagValid(eTag, String.valueOf(ticket.getVersion()))) {
            throw new OptLockException(ExceptionMessages.OPTIMISTIC_LOCK_EXCEPTION);
        }

        if (!ticket.getIsNotCancelled()) {
            throw new TicketAlreadyCancelledException(ExceptionMessages.TICKET_ALREADY_CANCELLED);
        }

        ticket.setIsNotCancelled(false);
        ticketRepository.saveAndFlush(ticket);
    }
}
