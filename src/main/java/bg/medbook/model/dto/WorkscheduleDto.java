package bg.medbook.model.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkscheduleDto {

    @NotNull
    @Max(7)
    @Min(0)
    private Integer day;

    @NotEmpty
    private String startTime;

    @NotEmpty
    private String endTime;

}
