package sudyar.blps.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sudyar.blps.entity.User;
import sudyar.blps.repo.UserRepository;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {


	private final UserRepository userRepository;

	private final String USER_NOT_FOUND = "User not found.";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository
				.findByLogin(username);
		if (user == null) throw new UsernameNotFoundException(USER_NOT_FOUND);
		final var roleName = user.getRole().name();

		return new org.springframework.security.core.userdetails.User(
				user.getLogin(),
				user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(roleName))
		);
	}
}
