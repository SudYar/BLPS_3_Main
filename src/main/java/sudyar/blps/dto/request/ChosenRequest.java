package sudyar.blps.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChosenRequest {

	private int idOrder;

	private String executor;
}
