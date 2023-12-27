package sudyar.blps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseResponse {

	private final String status;
	private final Integer code;
}