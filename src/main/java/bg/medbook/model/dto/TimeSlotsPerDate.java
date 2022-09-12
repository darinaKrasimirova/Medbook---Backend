package bg.medbook.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class TimeSlotsPerDate {

    private String date;

    private List<TimeSlot> slots;
}
