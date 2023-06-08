package sudyar.blps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sudyar.blps.entity.Notice;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponse {
    private List<Notice> notices;
}
