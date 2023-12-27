package sudyar.blps.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sudyar.blps.dto.response.FeedbackResponse;
import sudyar.blps.entity.Feedback;
import sudyar.blps.repo.FeedbackRepository;

@Service
@RequiredArgsConstructor
public class FeedbackService {

	private final FeedbackRepository feedbackRepository;

	public FeedbackResponse getAllForExecutor(String login) {
		return new FeedbackResponse(feedbackRepository.findAllByLoginExecutor(login));
	}

	public FeedbackResponse getAllForEmployer(String login) {
		return new FeedbackResponse(feedbackRepository.findAllByLoginEmployer(login));
	}

	public void add(Feedback feedback) {
		feedbackRepository.save(feedback);
	}

}
