package sudyar.blps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class InfoResponse {

	private final String info;
	private final Integer code;

}
