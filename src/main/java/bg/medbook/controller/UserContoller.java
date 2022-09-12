package bg.medbook.controller;

import java.util.Collections;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bg.medbook.JWTUtil;
import bg.medbook.model.dto.UserDto;
import bg.medbook.model.mapper.UserMapper;
import bg.medbook.service.UserService;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/users")
@Validated
public class UserContoller {

    private UserMapper mapper = UserMapper.INSTANCE;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    @PostMapping("/login")
    public Map<String, Object> loginHandler(@RequestBody LoginCredentials credentials) {
	try {
	    UsernamePasswordAuthenticationToken authInputToken = new UsernamePasswordAuthenticationToken(
		    credentials.getUsername(), credentials.getPassword());
	    authManager.authenticate(authInputToken);
	    String token = jwtUtil.generateToken(credentials.getUsername());
	    return Collections.singletonMap("jwt-token", token);
	} catch (AuthenticationException authExc) {
	    throw new RuntimeException("Invalid Login Credentials");
	}
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
	return ResponseEntity.ok(mapper.entityToDto(userService.findByUsername(username)));
    }

    @GetMapping("/exist")
    public ResponseEntity<Boolean> checkExists(@RequestParam("username") @NotEmpty String username) {
	return ResponseEntity.ok(userService.checkExists(username));
    }

    @PostMapping
    public ResponseEntity<Integer> createUser(@RequestBody @Valid UserDto user) {
	Integer id = userService.create(mapper.dtoToEntity(user));
	return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody @Valid UserDto user) {
	userService.updateUser(mapper.dtoToEntity(user));
    }

    @ExceptionHandler({ ConstraintViolationException.class, MissingServletRequestParameterException.class })
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected String handleConstraintViolationException(Exception objException) {
	return objException.getMessage();
    }

    @Getter
    @Setter
    static class LoginCredentials {
	private String username;
	private String password;
    }

}
