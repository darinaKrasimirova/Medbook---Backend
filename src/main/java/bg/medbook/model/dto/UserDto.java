package bg.medbook.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {

    private Integer id;

    @NotEmpty
    @Size(max = 100)
    private String username;

    @NotEmpty
    @Email(regexp = "(.*)@(.*)\\.(.*)")
    @Size(max = 100)
    private String email;

    @NotEmpty
    private String password;

    private String name;

    @Size(max = 20)
    private String phoneNumber;

    @NotEmpty
    private String accountType;

    private String accountStatus;

}
