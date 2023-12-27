package sudyar.blps.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sudyar.blps.dto.response.AuthResponse;
import sudyar.blps.etc.AuthUser;
import sudyar.blps.etc.AuthUserWithRole;
import sudyar.blps.service.AuthService;

@RestController
@RequestMapping("auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@PostMapping("sign-in")
	ResponseEntity<AuthResponse> signIn(@RequestBody AuthUser user) throws BadCredentialsException {
		return ResponseEntity.ok(authService.signIn(user));
	}

	@PostMapping("sign-up")
	ResponseEntity<AuthResponse> signUp(@RequestBody AuthUserWithRole newUser) {
		return authService.signUp(newUser);
	}
}