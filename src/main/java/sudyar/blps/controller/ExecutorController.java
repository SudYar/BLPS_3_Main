package sudyar.blps.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sudyar.blps.dto.response.InfoResponse;
import sudyar.blps.entity.Ordering;
import sudyar.blps.entity.Status;
import sudyar.blps.service.FeedbackService;
import sudyar.blps.service.NoticeService;
import sudyar.blps.service.OrderService;
import sudyar.blps.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/executor")
@RequiredArgsConstructor
public class ExecutorController {

	private final OrderService orderService;
	private final UserService userService;
	private final NoticeService noticeService;
	private final FeedbackService feedbackService;

	@GetMapping
	public InfoResponse showInfo() {
		String ORDERS_INFO = "Получить вcе заказы: /executor/orders";
		return new InfoResponse(ORDERS_INFO, 1);
	}

	@GetMapping("/orders")
	public ResponseEntity<?> getOrders() {
		return ResponseEntity.ok(orderService.getAll());
	}


	@GetMapping("/getNotice")
	public ResponseEntity<?> getNotice() {
		return ResponseEntity.ok(noticeService.getAll(SecurityContextHolder.getContext().getAuthentication().getName()));
	}

	@PostMapping("/chooseOrder")
	public InfoResponse chooseOrder(@RequestBody int idOrdering) {
		Optional<Ordering> optionalOrdering = orderService.getById(idOrdering);

		if (optionalOrdering.isPresent()) {
			Ordering order = optionalOrdering.get();
			if (noticeService.existByStatusAndOrdering(order, Status.IN_WORK))
				return new InfoResponse("Данный заказ уже кто-то взял", 1);

			final String MESSAGE_ORDER_HAVE = "Вы взялись за заказ: ";
			final String MESSAGE_ALREADY_HAVE = "Вы уже и так взялись за заказ: ";

			final String MESSAGE_READY = "Я готов взяться за заказ";
			String executor = SecurityContextHolder.getContext().getAuthentication().getName();
			// Костыль с 3 раундами участия в конкурсе
			if (!noticeService.existChosenOrdering(order.getOwnerLogin(), executor, order, Status.READY)) { // Если ещё не отправлял запрос, что готов выполнять, то отправляем
				noticeService.createNotice(order.getOwnerLogin(), executor, MESSAGE_READY, order, Status.READY);
				return new InfoResponse(MESSAGE_ORDER_HAVE + order, 0);
			} else if (!noticeService.existChosenOrdering(executor, order.getOwnerLogin(), order, Status.START2)) { //Запрос Ready уже был. Если не было старта второго раунда, то пока что ждем
				return new InfoResponse(MESSAGE_ALREADY_HAVE + order, 1);
			} else if (!noticeService.existChosenOrdering(order.getOwnerLogin(), executor, order, Status.READY2)) { // Если ещё не отправлял запрос, что готов выполнять, то отправляем
				noticeService.createNotice(order.getOwnerLogin(), executor, MESSAGE_READY, order, Status.READY2);
				return new InfoResponse(MESSAGE_ORDER_HAVE + order, 0);
			} else if (!noticeService.existChosenOrdering(executor, order.getOwnerLogin(), order, Status.START3)) { //Запрос Ready уже был. Если не было старта второго раунда, то пока что ждем
				return new InfoResponse(MESSAGE_ALREADY_HAVE + order, 1);
			} else if (!noticeService.existChosenOrdering(order.getOwnerLogin(), executor, order, Status.READY3)) { // Если ещё не отправлял запрос, что готов выполнять, то отправляем
				noticeService.createNotice(order.getOwnerLogin(), executor, MESSAGE_READY, order, Status.READY3);
				return new InfoResponse(MESSAGE_ORDER_HAVE + order, 0);
			} else //Запрос Ready уже был. Если не было старта второго раунда, то пока что ждем
				return new InfoResponse(MESSAGE_ALREADY_HAVE + order, 1);
		} else return new InfoResponse("Заказа с id '" + idOrdering + "' нет", 1);
	}

	@PostMapping("/finishOrder")
	public InfoResponse finishOrder(@RequestBody int idOrdering) {
		Optional<Ordering> optionalOrdering = orderService.getById(idOrdering);

		final String SUCCESS = "Заказ выполнен";
		final String NOT_SUCCESS = "Данный заказ ещё не взят в работу";
		if (optionalOrdering.isPresent()) {
			Ordering order = optionalOrdering.get();
			String executor = SecurityContextHolder.getContext().getAuthentication().getName();
			if (noticeService.existChosenOrdering(executor, order.getOwnerLogin(), order, Status.IN_WORK)) {
				noticeService.deleteNotices(order, Status.IN_WORK);
				noticeService.createNotice(order.getOwnerLogin(), executor, SUCCESS, order, Status.FINISH);
				return new InfoResponse(SUCCESS, 0);
			} else return new InfoResponse(NOT_SUCCESS, 1);

		} else return new InfoResponse("Заказа с id '" + idOrdering + "' нет", 1);
	}

	@GetMapping("/myFeedbacks")
	public ResponseEntity<?> myFeedbacks() {
		String login = SecurityContextHolder.getContext().getAuthentication().getName();
		return ResponseEntity.ok(feedbackService.getAllForExecutor(login));
	}
}