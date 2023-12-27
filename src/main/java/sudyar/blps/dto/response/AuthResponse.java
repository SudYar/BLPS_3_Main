package sudyar.blps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse implements Serializable {

	private static final long serialVersionUID = 3625187491L;
	private String jwt;
	private String errorMessage;
}
