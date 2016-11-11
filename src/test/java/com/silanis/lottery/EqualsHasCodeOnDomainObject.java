package com.silanis.lottery;

import org.junit.Ignore;
import org.junit.Test;

import com.silanis.lottery.Winner.NoWinner;

import nl.jqno.equalsverifier.EqualsVerifier;

public  class EqualsHasCodeOnDomainObject {

	@Test
	public void equalsContractTicket() {
	    EqualsVerifier.forClass(Ticket.class).withIgnoredFields("name", "purchaseDateTime", "price").verify();
	}
	
	@Test
	public void equalsContractPrize() {		
	    EqualsVerifier.forClass(Prize.class).withIgnoredFields("prizeNo", "availableAmount", "actualValue").verify();
	}
	
	@Test
	public void equalsContractWinner() {		
	    EqualsVerifier.forClass(Winner.class).verify();
	}
	
	@Test @Ignore
	public void equalsContractNoWinner() {		
	    EqualsVerifier.forClass(NoWinner.class).verify();
	}

}
