package com.silanis.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LotteryService implements Serializable, ILotteryService {

	private static final long serialVersionUID = 4473972407132727615L;
	Map<Integer, Ticket> purchases = new HashMap<>();
	Map<Integer, Prize> prizes = new HashMap<>();
	private Random random;
	List<Integer> balls = new ArrayList<>();
	int nbBalls;
	BigDecimal availableAmount;
	private PrizeCalculator calculator;

	public LotteryService() {
		this(50, BigDecimal.valueOf(200), new PrizeCalculator(), new Random());
	}

	public LotteryService(int nbBalls, BigDecimal availableAmount, PrizeCalculator calculator, Random random) {
		super();
		this.nbBalls = nbBalls;
		this.availableAmount = availableAmount;
		this.random = random;
		this.calculator = calculator;
		this.balls = IntStream.range(1, nbBalls + 1).boxed().collect(Collectors.toList());
	}

	public Map<Integer, Prize> drawnThreeBalls() {
		this.prizes = drawBalls(3);
		return this.prizes;
	}

	void mixing() {
		Collections.shuffle(balls, random);
	}

	Map<Integer, Prize> drawBalls(int nbPeekBalls) {
		AtomicInteger index = new AtomicInteger();
		Map<Integer, Prize> winningBalls = new HashMap<>();
		List<Integer> range = balls.stream().collect(Collectors.toCollection(ArrayList::new));
		Collections.shuffle(range);
		range.subList(0, nbPeekBalls).forEach(ballNo -> {
			Prize prize = Prize.create(ballNo, index.incrementAndGet(), availableAmount);
			prize.calculateAndGetActualPrizeValue(calculator);
			winningBalls.put(ballNo, prize);
		});
		return winningBalls;
	}

	@Override
	public Map<Integer, Winner> peekWinners() {

		if (this.prizes.isEmpty()) {
			this.drawnThreeBalls();
		}

		Map<Integer, Winner> winners = new HashMap<>();
		for (Entry<Integer, Prize> entry : prizes.entrySet()) {
			Ticket ticket;
			if (purchases.containsKey(entry.getKey())) {
				ticket = purchases.get(entry.getKey());
				Winner winner = new Winner(ticket, entry.getValue());
				winners.put(entry.getValue().getPrizeNo(), winner);
			} else {
				Winner winner = new Winner.NoWinner(entry.getValue());
				winners.put(entry.getValue().getPrizeNo(), winner);
			}

		}
		return winners;
	}

	@Override
	public void purchase(String name) {
		Integer availableBall = remainBallsForPurchase().findAny().orElse(null);
		if (availableBall == null) {
			throw new LotteryException("No more tickets for purchasing. Retry.");
		} else {
			purchase(name, availableBall);
		}
	}

	@Override
	public Stream<Integer> remainBallsForPurchase() {
		return this.balls.stream().filter(ball -> !this.purchases.containsKey(ball));
	}

	@Override
	public void purchaseAll() {
		IntStream.range(1, nbBalls + 1).forEach(ballNo -> {
			purchase("test" + ballNo, ballNo);
		});
	}

	@Override
	public void purchase(String name, int ballNo) {
		if (!prizes.isEmpty()) {
			throw new LotteryException(
					String.format("You can't no longer buy ticket. Please type reset the system.", ballNo));
		}

		if (purchases.containsKey(ballNo)) {
			throw new LotteryException(String.format("This ticket %d has been purchased. Retry.", ballNo));
		}
		Ticket ticket = new Ticket(name, ballNo);
		this.purchases.put(ballNo, ticket);
	}
	
	@Override
	public void reset() {
		reset(200);
	}

	@Override
	public void reset(int amout) {
		this.availableAmount = BigDecimal.valueOf(amout);
		this.purchases.clear();
		this.prizes.clear();
		mixing();
	}

}
