package pl.lodz.p.it.ssbd2024.ssbd01.mow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.ssbd2024.ssbd01.util.RunAs;

@Service
@RequiredArgsConstructor
public class SessionSchedulerService {

    private final SessionService sessionService;

    @Scheduled(fixedRateString = "${scheduler.task.time-rate}")
    public void executeUpdateTemporarySessions() {
        RunAs.runAsSystem(sessionService::updateTemperatureForUpcomingSessions);
    }

}
