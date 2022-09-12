package bg.medbook.service;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bg.medbook.model.entity.User;
import bg.medbook.model.enumeration.UserAccountStatus;
import bg.medbook.model.enumeration.UserAccountType;
import bg.medbook.model.repository.UserRepository;

@Service
public class UserService {

    private static final PasswordEncoder passwordEndcoder = new BCryptPasswordEncoder();

    private UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
	this.userRepository = userRepository;
    }

    public Integer create(User user) {
	user.setAccountStatus(
		user.getAccountType() == UserAccountType.DOCTOR ? UserAccountStatus.CREATED : UserAccountStatus.ACTIVE);
	user.setPassword(passwordEndcoder.encode(user.getPassword()));
	return userRepository.save(user).getId();
    }

    public void updateUser(User userNew) {
	User user = userRepository.findById(userNew.getId()).orElseThrow();
	userNew.setAccountStatus(user.getAccountStatus());
	userNew.setPassword(passwordEndcoder.encode(userNew.getPassword()));
	userRepository.save(userNew);
    }

    public Boolean checkExists(@NotEmpty String username) {
	return userRepository.existsByUsername(username);
    }

    public User findByUsername(String username) {

	return userRepository.findByUsername(username).orElseThrow();
    }

}
