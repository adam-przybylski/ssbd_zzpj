package pl.lodz.p.it.ssbd2024.ssbd01.mow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mok.Account;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mow.*;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mok.OptLockException;
import pl.lodz.p.it.ssbd2024.ssbd01.exception.mow.*;
import pl.lodz.p.it.ssbd2024.ssbd01.mow.repository.*;
import pl.lodz.p.it.ssbd2024.ssbd01.util.ETagBuilder;
import pl.lodz.p.it.ssbd2024.ssbd01.util.messages.ExceptionMessages;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final EventRepository eventRepository;
    private final SessionRepository sessionRepository;
    private final TicketRepository ticketRepository;
    private final SpeakerRepository speakerRepository;
    private final RoomRepository roomRepository;

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class},
            timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void updateSession(UUID id, String etag, Session session, UUID speakerId, UUID roomId)
            throws
            SessionNotFoundException,
            OptLockException,
            SessionStartDateInPast,
            SessionStartDateAfterEndDateException,
            SessionsExistOutsideRangeException, RoomNotFoundException, SpeakerNotFoundException, SpeakerIsBusyException, RoomSeatsExceededException,
            RoomIsBusyException {
        Session pSession = sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException(ExceptionMessages.SESSION_NOT_FOUND));
        if (!ETagBuilder.isETagValid(etag, String.valueOf(pSession.getVersion()))) {
            throw new OptLockException(ExceptionMessages.OPTIMISTIC_LOCK_EXCEPTION);
        }
        if (session.getStartTime().isAfter(session.getEndTime())) {
            throw new SessionStartDateAfterEndDateException(ExceptionMessages.SESSION_START_AFTER_END);
        }

        if (session.getStartTime().isBefore(LocalDateTime.now())) {
            throw new SessionStartDateInPast(ExceptionMessages.SESSION_START_IN_PAST);
        }

        Event pEvent = pSession.getEvent();
        if (session.getStartTime().isBefore(pEvent.getStartDate()) || session.getEndTime().isAfter(pEvent.getEndDate())) {
            throw new SessionsExistOutsideRangeException(ExceptionMessages.SESSIONS_OUTSIDE_RANGE);
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException(ExceptionMessages.ROOM_NOT_FOUND));

        Speaker speaker = speakerRepository.findById(speakerId)
                .orElseThrow(() -> new SpeakerNotFoundException(ExceptionMessages.SPEAKER_NOT_FOUND));

        List<Session> speakerOverlappingSessions = sessionRepository.findSpeakerSessionsInRange(
                speakerId,
                session.getStartTime(),
                session.getEndTime());

        if (!speakerOverlappingSessions.isEmpty()) {
            if (speakerOverlappingSessions.size() > 1) {
                throw new SpeakerIsBusyException(ExceptionMessages.SPEAKER_IS_BUSY);
            }
            if (!speakerOverlappingSessions.getFirst().getId().equals(id)) {
                throw new SpeakerIsBusyException(ExceptionMessages.SPEAKER_IS_BUSY);
            }
        }

        if (session.getMaxSeats() > room.getMaxCapacity()) {
            throw new RoomSeatsExceededException(ExceptionMessages.ROOM_SEATS_EXCEEDED);
        }

        List<Session> overlappingSessions = sessionRepository.findSessionsInsideRangeAtRoom(
                roomId,
                session.getStartTime(),
                session.getEndTime());

        if (!overlappingSessions.isEmpty()) {
            if (overlappingSessions.size() > 1) {
                throw new RoomIsBusyException(ExceptionMessages.ROOM_BUSY);
            }
            if (!overlappingSessions.getFirst().getId().equals(id)) {
                throw new RoomIsBusyException(ExceptionMessages.ROOM_BUSY);
            }
        }


        pSession.setSpeaker(speaker);
        pSession.setRoom(room);
        pSession.setName(session.getName());
        pSession.setDescription(session.getDescription());
        pSession.setStartTime(session.getStartTime());
        pSession.setEndTime(session.getEndTime());
        pSession.setMaxSeats(session.getMaxSeats());


        sessionRepository.saveAndFlush(pSession);
    }


    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class},
            timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public String createSession(Session session, UUID eventId, UUID speakerId, UUID roomId)
            throws SessionStartDateAfterEndDateException,
            SessionStartDateInPast,
            EventNotFoundException,
            RoomNotFoundException,
            SpeakerNotFoundException,
            RoomSeatsExceededException,
            RoomIsBusyException,
            SpeakerIsBusyException, SessionsExistOutsideRangeException {
        if (session.getStartTime().isBefore(LocalDateTime.now())) {
            throw new SessionStartDateInPast(ExceptionMessages.SESSION_START_IN_PAST);
        }
        if (session.getStartTime().isAfter(session.getEndTime())) {
            throw new SessionStartDateAfterEndDateException(ExceptionMessages.SESSION_START_AFTER_END);
        }

        var event = eventRepository
                .findByIdAndIsNotCanceledTrue(eventId)
                .orElseThrow(() -> new EventNotFoundException(ExceptionMessages.EVENT_NOT_FOUND));
        session.setEvent(event);
        var speaker = speakerRepository
                .findById(speakerId)
                .orElseThrow(() -> new SpeakerNotFoundException(ExceptionMessages.SPEAKER_NOT_FOUND));
        session.setSpeaker(speaker);
        var room = roomRepository
                .findByIdAndIsActiveTrue(roomId)
                .orElseThrow(() -> new RoomNotFoundException(ExceptionMessages.ROOM_NOT_FOUND));

        List<Session> speakerOverlappingSessions = sessionRepository.findSpeakerSessionsInRange(
                speakerId,
                session.getStartTime(),
                session.getEndTime());

        if (!speakerOverlappingSessions.isEmpty()) {
            throw new SpeakerIsBusyException(ExceptionMessages.SPEAKER_IS_BUSY);
        }

        if (session.getMaxSeats() > room.getMaxCapacity()) {
            throw new RoomSeatsExceededException(ExceptionMessages.ROOM_SEATS_EXCEEDED);
        }

        if (session.getStartTime().isBefore(event.getStartDate()) || session.getEndTime().isAfter(event.getEndDate())) {
            throw new SessionsExistOutsideRangeException(ExceptionMessages.SESSIONS_OUTSIDE_RANGE);
        }

        List<Session> overlappingSessions = sessionRepository.findSessionsInsideRangeAtRoom(
                roomId,
                session.getStartTime(),
                session.getEndTime());

        if (!overlappingSessions.isEmpty()) {
            throw new RoomIsBusyException(ExceptionMessages.ROOM_BUSY);
        }

        session.setRoom(room);

        sessionRepository.saveAndFlush(session);
        return session.getId().toString();
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class},
            timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public void cancelSession(UUID id, String etag)
            throws SessionNotFoundException, OptLockException, SessionAlreadyCanceledException {
        Session session = sessionRepository.findById(id).orElseThrow(() -> new SessionNotFoundException(ExceptionMessages.SESSION_NOT_FOUND));
        if (!ETagBuilder.isETagValid(etag, String.valueOf(session.getVersion()))) {
            throw new OptLockException(ExceptionMessages.OPTIMISTIC_LOCK_EXCEPTION);
        }

        if (!session.getIsActive()) {
            throw new SessionAlreadyCanceledException(ExceptionMessages.SESSION_ALREADY_CANCELLED);
        }

        session.setIsActive(false);
        sessionRepository.saveAndFlush(session);
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class},
            timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public List<Account> getSessionParticipants(UUID id) {
        List<Ticket> tickets = ticketRepository.findBySession_Id(id);

        return tickets.stream().map(Ticket::getAccount).toList();
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class},
            timeoutString = "${transaction.timeout}")
    @PreAuthorize("permitAll()")
    public Session getSession(UUID id) throws SessionNotFoundException {
        return sessionRepository
                .findById(id)
                .orElseThrow(() -> new SessionNotFoundException(ExceptionMessages.SESSION_NOT_FOUND));
    }

    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class},
            timeoutString = "${transaction.timeout}")
    @PreAuthorize("permitAll()")
    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }


    @Transactional(
            propagation = Propagation.REQUIRES_NEW,
            rollbackFor = {Exception.class},
            timeoutString = "${transaction.timeout}")
    @PreAuthorize("hasRole('ROLE_SYSTEM')")
    public void updateTemperatureForUpcomingSessions() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nowPlus14Days = now.plusDays(14);

        List<Session> sessions = sessionRepository.findAllSessionsStartingIn14DaysOrLess(now, nowPlus14Days);

        double latitude = 51.7450;
        double longitude = 19.4522;

        String url = String.format("https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&hourly=temperature_2m");

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response;
        try {
            response = restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching temperature data: " + e.getMessage());
        }

        if (response == null) {
            throw new RuntimeException("Error while fetching temperature data: response is null");
        }

        Map<String, Object> hourly = (Map<String, Object>) response.get("hourly");
        List<Double> temperatures = (List<Double>) hourly.get("temperature_2m");

        for (Session session : sessions) {
            int hour = session.getStartTime().getHour();
            Double temperature = temperatures.get(hour);
            session.setTemperature(temperature);
            sessionRepository.saveAndFlush(session);
        }
    }

}
