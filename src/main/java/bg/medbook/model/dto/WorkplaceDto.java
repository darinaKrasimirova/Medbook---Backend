package bg.medbook.model.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import bg.medbook.model.entity.City;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WorkplaceDto {

    private Integer id;

    @NotNull
    private City city;

    @NotEmpty
    private String address;

    private String name;

    private List<WorkscheduleDto> workschedule;
}
