package com.silanis.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Predicate;

public final class Prize implements Serializable, Predicate<Ticket>{	
	
	private static final long serialVersionUID = 362711019707321592L;
	
	public static Prize create(int ball, int prizeNo, BigDecimal value) {
		return new Prize(ball, prizeNo, value);
	}

	private final int ball;
	private final int prizeNo;
	private final BigDecimal availableAmount;
	private BigDecimal actualValue;	
	
	private Prize(int ball, int prizeNo, BigDecimal availableAmount) {
		super();
		this.ball = Preconditions.checkInRange(ball, 50);;
		this.prizeNo =  Preconditions.checkInRange(prizeNo, 3);
		this.availableAmount = availableAmount;
	}
	
	public int getPrizeNo() {
		return prizeNo;
	}
	
	public int getBall() {
		return ball;
	}
	
	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}	
	
	public BigDecimal getActualValue() {
		return actualValue;
	}
	
	public BigDecimal calculateAndGetActualPrizeValue(PrizeCalculator calculator) {
		this.actualValue = calculator.apply(this);
		return this.actualValue;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ball;
		return result;
	}
	
	@Override
	public String toString() {
		String[] texts = {"1st ball", "2nd ball", "3rd ball"};
		return texts[prizeNo-1];
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prize other = (Prize) obj;
		if (ball != other.ball)
			return false;
		return true;
	}

	@Override
	public boolean test(Ticket ticket) {
		return this.ball == (ticket.getBall());
	}
	
	
	
}
