package com.silanis.lottery;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Stream;

public interface ILotteryService {

	void reset();

	void purchase(String userName, int ballNo);

	public Map<Integer, Winner> peekWinners();

	Map<Integer, Prize> drawnThreeBalls();

	void purchase(String string);

	void purchaseAll();

	Stream<Integer> remainBallsForPurchase();

	void reset(int amout);
}