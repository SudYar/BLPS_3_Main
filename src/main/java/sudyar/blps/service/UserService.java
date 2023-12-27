package sudyar.blps.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sudyar.blps.entity.User;
import sudyar.blps.repo.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User getUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	public boolean exitsUserLogin(String login) {
		return userRepository.existsByLogin(login);
	}
}
