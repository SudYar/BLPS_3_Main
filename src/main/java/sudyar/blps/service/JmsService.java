package sudyar.blps.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.Queue;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import sudyar.blps.entity.Notice;
import sudyar.blps.etc.Note;

@Service
@RequiredArgsConstructor
public class JmsService {

	private final JmsTemplate jmsTemplate;
	private final Queue queue;

	public void sendNotice(Notice notice) {

		Note note = new Note(notice.getToUser(), notice.getFromUser(), notice.getDescription());
		try {
			ObjectMapper mapper = new ObjectMapper();
			String noteAsJson = mapper.writeValueAsString(note);

			jmsTemplate.convertAndSend(queue, noteAsJson);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
