package pl.lodz.p.it.ssbd2024.ssbd01.mow.converter;

import org.springframework.stereotype.Component;
import pl.lodz.p.it.ssbd2024.ssbd01.dto.mow.get.GetEventDTO;
import pl.lodz.p.it.ssbd2024.ssbd01.entity.mow.Event;

@Component
public class EventDTOConverter {

    public GetEventDTO getEventDTO(Event event) {
        return new GetEventDTO(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getIsNotCanceled(),
                event.getStartDate(),
                event.getEndDate()
        );
    }
}