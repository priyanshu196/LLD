package com.rate.executors;

import com.rate.contants.RateLimiterContants;
import com.rate.enums.RateLimiterRuleEnum;
import com.rate.enums.Status;
import com.rate.mode.RateLimiterResponse;

import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SlidingWindowCounter implements RateLimiter {

    private ReentrantReadWriteLock lock;
    private ReentrantReadWriteLock.WriteLock writeLock;
    private Map<Long, Long> counterMap;
    private RateLimiterRuleEnum ruleEnum;

    public SlidingWindowCounter(RateLimiterRuleEnum rateLimiterRuleEnum) {
        this.lock = new ReentrantReadWriteLock();
        this.writeLock = this.lock.writeLock();
        this.counterMap = new ConcurrentHashMap<>();
        this.ruleEnum = rateLimiterRuleEnum;
    }

    @Override
    public RateLimiterResponse execute() {
        String frame = ruleEnum.getTimeFrame();
        Calendar rightNow = Calendar.getInstance();
        Long index = getIndex(frame, rightNow);

        try {
            writeLock.lock();
               evictOldIndex(counterMap,index);
            if (!counterMap.containsKey(index)) {
                counterMap.put(index, 1L);
                return RateLimiterResponse.build().setStatus(Status.SUCCESS).setRequestNumber(1L);

            } else {
                Long newConter = counterMap.get(index) + 1;
                counterMap.put(index,newConter);
                if (newConter > ruleEnum.getThreshold()) {
                    String message = "Rate limit has been breached, Transaction Number: " + newConter +
                            " in delta time (milliseconds): " + ruleEnum.getTimeFrame() + ruleEnum.getTimeFrame() +
                            "Threshold: " + ruleEnum.getThreshold();
                    System.out.println();
                    return RateLimiterResponse.build().setMessage(message)
                            .setRequestNumber(newConter)
                            .setThreshold(ruleEnum.getTimeFrame() + ruleEnum.getTimeFrame()).setStatus(Status.FAILURE);
                }
                return RateLimiterResponse.build().setStatus(Status.SUCCESS).setRequestNumber(newConter);
            }
        }
        finally {
            if(writeLock.isHeldByCurrentThread())
                writeLock.unlock();
        }

    }

    private void evictOldIndex(Map<Long, Long> counterMap, Long index) {

       for(Long key:counterMap.keySet()){
           if(key<index)
               counterMap.remove(key);
       }

    }


    private Long getIndex(String frame, Calendar rightNow) {

        switch (frame) {
            case RateLimiterContants.HOUR:
                return Long.valueOf(rightNow.get(Calendar.HOUR_OF_DAY));

            case RateLimiterContants.SECOND:
                return Long.valueOf(rightNow.get(Calendar.SECOND));

            case RateLimiterContants.MINUTE:
                return Long.valueOf(rightNow.get(Calendar.MINUTE));

            case RateLimiterContants.MONTH:
                return Long.valueOf(rightNow.get(Calendar.MONTH));

            case RateLimiterContants.DAY:
                return Long.valueOf(rightNow.get(Calendar.DAY_OF_MONTH));
        }
        return 1L;

    }
}
