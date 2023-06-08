package sudyar.blps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sudyar.blps.entity.Ordering;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdersResponse {
    private List<Ordering> orders;
}
