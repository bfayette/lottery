package com.silanis.lottery;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

public class PrizeCalculator implements Function<Prize, BigDecimal> {

	private static BigDecimal oneHundred = BigDecimal.TEN.multiply(BigDecimal.TEN);
	private static BigDecimal prizeFirstPercent = BigDecimal.valueOf(75).divide(oneHundred);
	private static BigDecimal prizeSecondPercent = BigDecimal.valueOf(15).divide(oneHundred);
	private static BigDecimal prizeThirdPercent = BigDecimal.valueOf(10).divide(oneHundred);

	@Override
	public BigDecimal apply(Prize prize) {		
		int prizeNo = prize.getPrizeNo();
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal amount = (prize.getAvailableAmount()).divide(BigDecimal.valueOf(2), RoundingMode.HALF_EVEN);
		switch (prizeNo) {
		case 1:
			result = amount.multiply(prizeFirstPercent).setScale(0, RoundingMode.DOWN);
			break;
		case 2:
			result = amount.multiply(prizeSecondPercent).setScale(0, RoundingMode.DOWN);
			break;
		case 3:
			result = amount.multiply(prizeThirdPercent).setScale(0, RoundingMode.DOWN);
			break;
		}
		return result;
	}
}
