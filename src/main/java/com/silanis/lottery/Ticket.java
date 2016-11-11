package com.silanis.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Predicate;

public final class Ticket implements Serializable, Predicate<Prize> {

	private static final long serialVersionUID = -1799984875530920030L;

	private final int ball;
	private String name;
	private Date purchaseDateTime;
	private BigDecimal price = BigDecimal.TEN;

	public Ticket(String name, int ball) {
		super();
		this.name = Preconditions.checkNotNullOrEmpy(name);
		this.ball = Preconditions.checkInRange(ball, 50);
		this.purchaseDateTime = new Date(); 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPurchaseDateTime() {
		return purchaseDateTime;
	}

	public void setPurchaseDateTime(Date purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}

	public int getBall() {
		return ball;
	}	
	
	@Override
	public String toString() {		
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ball;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (ball != other.ball)
			return false;
		return true;
	}
	
	@Override
	public boolean test(Prize prize) {
		return this.ball == (prize.getBall());
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

}
