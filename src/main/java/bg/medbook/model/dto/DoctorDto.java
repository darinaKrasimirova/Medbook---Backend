package bg.medbook.model.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import bg.medbook.model.entity.MedicalField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DoctorDto {

    private Integer id;

    private Integer userId;

    @NotEmpty
    private String username;

    private String name;

    private String phoneNumber;

    private String personalDescription;

    private String educationDescription;

    @Past
    private LocalDate practiceStart;

    private String practiceDescription;

    @NotNull
    private MedicalField medicalField;

    private String image;

    private String servicesDescription;

    private List<WorkplaceDto> workplaces;
}
