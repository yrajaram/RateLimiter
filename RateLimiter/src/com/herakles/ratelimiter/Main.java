package com.herakles.ratelimiter;

public class Main {
	public static void main(String... args) throws ExceededMaximumInvocationsException{
		
		FrequencyMonitor.Instance.setMax("api1", 3); // max calls per hour allowed
		
		System.out.println(FrequencyMonitor.Instance.increment("api1"));
		System.out.println(FrequencyMonitor.Instance.increment("api1"));
		System.out.println(FrequencyMonitor.Instance.increment("api1"));
		
		FrequencyMonitor.Instance.printStatsForLastHour();
	}

}
