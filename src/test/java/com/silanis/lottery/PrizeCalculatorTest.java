package com.silanis.lottery;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.runner.RunWith;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;

@RunWith(JUnitQuickcheck.class)
public class PrizeCalculatorTest {

	@Property(trials=50)
	public void testCalculatorFirstPrize(@InRange(minInt=50, maxInt=1000) Double value) {
		Prize prize =  Prize.create(50, 1, BigDecimal.valueOf(value));
		PrizeCalculator calculator = new PrizeCalculator();
		assertThat(BigDecimal.valueOf(0.75d * (value/2)).intValue()).isEqualTo(calculator.apply(prize).intValue());
	}
	
	@Property(trials=50)
	public void testCalculatorSecondPrize(@InRange(minInt=50, maxInt=1000) Double value) {
		Prize prize =  Prize.create(50, 1, BigDecimal.valueOf(value));
		PrizeCalculator calculator = new PrizeCalculator();
		assertThat(BigDecimal.valueOf(0.15d * (value/2)).intValue()).isEqualTo(calculator.apply(prize).intValue());
	}
	
	@Property(trials=50)
	public void testCalculatorThirdPrize(@InRange(minInt=50, maxInt=1000) Double value) {
		Prize prize =  Prize.create(50, 1, BigDecimal.valueOf(value));
		PrizeCalculator calculator = new PrizeCalculator();
		assertThat(BigDecimal.valueOf(0.10d * (value/2)).intValue()).isEqualTo(calculator.apply(prize).intValue());
	}

}
