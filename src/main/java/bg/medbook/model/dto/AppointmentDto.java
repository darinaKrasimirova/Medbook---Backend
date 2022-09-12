package bg.medbook.model.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AppointmentDto {

    @NotNull
    private UserDto patient;

    @NotNull
    private DoctorDto doctor;

    @NotNull
    private WorkplaceDto workplace;

    @NotEmpty
    private String date;

    @NotNull
    @NotEmpty
    private String time;

    private String comment;

}
