package com.silanis.lottery;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class LotteryServiceTest {
	
	LotteryService lotteryService;
	
	@Before
	public void setUp(){
		lotteryService = new LotteryService();
	}
	
	@After
	public void tearDown(){
		lotteryService = null;
	}
	
	@Property(trials=15)
	public void testLotteryService() {
		assertThat(lotteryService.balls).hasSize(50).contains(1,50);
		assertThat(lotteryService.availableAmount).isEqualTo(BigDecimal.valueOf(200));
	}

	@Property(trials=15)
	public void testPeekTheThreeWinners() {
		assertThat(lotteryService.peekWinners()).hasSize(3).containsKeys(1, 2, 3);
	}	

	@Property(trials=15)
	public void testRemainBallsForPurchase() {
		assertThat(lotteryService.balls).hasSize(50).contains(1,50);
		lotteryService.purchase("carl");
		assertThat(lotteryService.remainBallsForPurchase()).hasSize(49);
	}

	@Property(trials=15)
	public void testPurchaseAll() {
		assertThat(lotteryService.balls).hasSize(50).contains(1,50);
		lotteryService.purchaseAll();
		assertThat(lotteryService.remainBallsForPurchase()).isEmpty();
	}	

	@Property(trials=15)
	public void testReset() {
		lotteryService.purchaseAll();
		assertThat(lotteryService.purchases).hasSize(50);		
		assertThat(lotteryService.drawnThreeBalls()).hasSize(3);		
		assertThat(lotteryService.peekWinners()).hasSize(3);		
		lotteryService.reset();
		assertThat(lotteryService.purchases).isEmpty();;
		assertThat(lotteryService.prizes).isEmpty();
	}

}
