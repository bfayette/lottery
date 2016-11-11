package com.silanis.lottery;

public class Preconditions {
	public static <T> T checkNotNull(T value){
		if(value == null) throw new LotteryException("Value must not be null. Please Retry.");
		else return value;
	}
	
	public static String checkNotNullOrEmpy(String value){
		if(value == null || value.trim().length() == 0) throw new LotteryException("Value is empty. Please Retry.");
		else return value;
	}
	
	public static int checkInRange(int value, int nb){
		if(value < 0 || value > nb) throw new LotteryException(String.format("Value %d is not in range 1..%d. Please Retry.", value, nb));
		else return value;
	}
}
