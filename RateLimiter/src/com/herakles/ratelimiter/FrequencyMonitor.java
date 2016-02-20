package com.herakles.ratelimiter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class FrequencyMonitor implements Runnable{
	private HashMap<String, AtomicInteger> callCounters = new HashMap<String, AtomicInteger>();
	private HashMap<String, Integer> maxCalls = new HashMap<String, Integer>();
	private Queue<QueueElement> beeLine = new ConcurrentLinkedQueue<QueueElement>();
	public static FrequencyMonitor Instance = new FrequencyMonitor();
	static {
		(new Thread(Instance)).start();
	}

	private FrequencyMonitor() {
		// Private constructor
	}
	
	public int increment(String api) throws ExceededMaximumInvocationsException{
		AtomicInteger ai = callCounters.get(api);
		ai.incrementAndGet();
		if (ai.get()>maxCalls.get(api)) {
			ai.decrementAndGet();
			throw new ExceededMaximumInvocationsException();
		}
		beeLine.add(new QueueElement(api));
		return ai.intValue();
	}

	private void decrement(String api){
		AtomicInteger ai = callCounters.get(api);
		if (ai.decrementAndGet()<0) 
			ai.set(0);
	}

	public void setMax(String api, int maxValue) {
		callCounters.put(api, new AtomicInteger(0));
		maxCalls.put(api, new Integer(maxValue));
	}
	
	public void printStatsForLastHour() {
	    System.out.println("API\t:\tcalls in last hour\t/\tmax allowed");

		for (Map.Entry<String, Integer> entry : maxCalls.entrySet()) {
		    System.out.println(entry.getKey() + "\t:\t" + callCounters.get(entry.getKey()) + "\t/\t" + entry.getValue() );
		}
	}

	@Override
	public void run() {
		QueueElement nodeAtHead = null;
		do {
			if ((nodeAtHead = beeLine.poll())==null) { // Empty queue
				try {
					Thread.sleep(60*1000); // Minute
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				long diffInMillis = nodeAtHead.getTimestamp().getTime() - (new Date()).getTime();
				diffInMillis = (diffInMillis>0)?diffInMillis:0;
				if (diffInMillis>0){
					try {
						Thread.sleep(diffInMillis);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				decrement(nodeAtHead.getApiId());
			}
		} while (Boolean.TRUE);
	}
}
