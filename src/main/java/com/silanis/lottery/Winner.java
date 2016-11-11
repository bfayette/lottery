package com.silanis.lottery;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Winner implements Serializable{
	
	private static final long serialVersionUID = -8858305210044121641L;
	protected final Ticket ticket;
	protected final Prize prize;	
	
	public Winner(Ticket ticket, Prize prize) {
		super();
		this.ticket = Preconditions.checkNotNull(ticket);
		this.prize = Preconditions.checkNotNull(prize);
	}


	public Ticket getTicket() {
		return ticket;
	}


	public Prize getPrize() {
		return prize;
	}
	
	@Override
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.CANADA_FRENCH);
		formatter.setMaximumFractionDigits(0);
		BigDecimal actualValue = Preconditions.checkNotNull(prize.getActualValue()) ;
		return String.format("%s %s", ticket.getName(), formatter.format(actualValue ));
	}	
	
	
	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prize == null) ? 0 : prize.hashCode());
		result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
		return result;
	}
	
	public boolean canEqual(Object other) {
        return (other instanceof Winner);
    }

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!canEqual(obj))
			return false;
		Winner other = (Winner) obj;
		if (prize == null) {
			if (other.prize != null)
				return false;
		} else if (!prize.equals(other.prize))
			return false;
		if (ticket == null) {
			if (other.ticket != null)
				return false;
		} else if (!ticket.equals(other.ticket))
			return false;
		return true;
		
		
	}





	public static final class NoWinner extends Winner{		
		
		
		private static final long serialVersionUID = 4522100218580401849L;

		public NoWinner(Prize prize) {
			super(new Ticket("No Winner", prize.getBall()), prize);
		}
		
		@Override
		public Ticket getTicket() {
			throw new UnsupportedOperationException("");
		}
		
		@Override
		public String toString() {			
			return "No Winner";
		}	
		
		@Override
		public boolean canEqual(Object other) {
			 return (other instanceof NoWinner);
		}
		
	}
	
}
