package sudyar.blps.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.JaasAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sudyar.blps.dto.response.AuthResponse;
import sudyar.blps.entity.User;
import sudyar.blps.etc.AuthUser;
import sudyar.blps.etc.AuthUserWithRole;
import sudyar.blps.repo.UserRepository;
import sudyar.blps.security.JwtTokenProvider;


@Service
@RequiredArgsConstructor
public class AuthService {

	private final String BEARER_START = "Bearer";
	private final String LOGIN_HAS_TAKEN = "This login is already taken";
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtUtils;
	private final UserRepository userRepository;
	private final PasswordEncoder encoder;


	public AuthResponse signIn(AuthUser user) throws BadCredentialsException {
		Authentication authentication = authenticationManager.authenticate(
				new JaasAuthenticationToken(user.getLogin(), user.getPassword(), null)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateToken(authentication);
		AuthResponse response = new AuthResponse();
		response.setJwt(BEARER_START + " " + jwt);

		return response;
	}

	public ResponseEntity<AuthResponse> signUp(AuthUserWithRole newUser) {
		final var response = new AuthResponse();
		if (userRepository.existsByLogin(newUser.getLogin())) {
			response.setErrorMessage(LOGIN_HAS_TAKEN);
			return ResponseEntity
					.badRequest()
					.body(response);
		}


		// Create new user's account
		final var user = new User();
		user.setLogin(newUser.getLogin());
		user.setPassword(encoder.encode(newUser.getPassword()));
		user.setRole(newUser.getRole());
		userRepository.save(user);


		// Sign in
		final var authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(newUser.getLogin(), newUser.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		final var jwt = jwtUtils.generateToken(authentication);
		response.setJwt(BEARER_START + " "  + jwt);

		return ResponseEntity.ok(response);
	}

}
