package com.rate.executors;

import com.rate.enums.RateLimiterRuleEnum;
import com.rate.enums.Status;
import com.rate.mode.RateLimiterResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SlidingWindowLogRateLimiter implements RateLimiter{

    private ReentrantReadWriteLock rwLock ;
    private ReentrantReadWriteLock.WriteLock  wLock;
    private List<Long> requestLog;
    private RateLimiterRuleEnum ruleEnum;

    public SlidingWindowLogRateLimiter(RateLimiterRuleEnum ruleEnum){
       this.rwLock=   new ReentrantReadWriteLock();
       this.wLock = rwLock.writeLock();
       this.requestLog = new ArrayList<>();
        this.ruleEnum=ruleEnum;
    }


    @Override
    public RateLimiterResponse execute() {

        //current time stamp - threshold time stamp is expired time . so all entry lower than this are expired and should be removed



        Long threshold = ruleEnum.getThreshold();
        Long frameTime = ruleEnum.getTimeInMiliSecond();
        long currentTime = System.currentTimeMillis();
        Long expirationTime = currentTime-frameTime;
        try {
            wLock.lock();
            evictOlderLog(expirationTime);
            requestLog.add(currentTime);

        if(requestLog.size()>threshold)
        {
            String message = "Rate limit has been breached, Transaction Number: " + requestLog.size() +
                    " in delta time (milliseconds): " + ruleEnum.getTimeFrame() + ruleEnum.getTimeFrame()+
                    "Threshold: " + threshold;
            System.out.println();
            return RateLimiterResponse.build().setMessage(message)
                    .setRequestNumber(Long.valueOf(requestLog.size()))
                    .setThreshold(ruleEnum.getTimeFrame() + ruleEnum.getTimeFrame()).setStatus(Status.FAILURE);
        }

        return RateLimiterResponse.build().setStatus(Status.SUCCESS).setRequestNumber(Long.valueOf(requestLog.size()));
        }
        finally {
            if(wLock.isHeldByCurrentThread())
                wLock.unlock();
        }
    }

    private void evictOlderLog(Long expirationTime) {
        int index=0;
        while(requestLog.size()>0 && requestLog.get(index)<expirationTime){
            requestLog.remove(index);
            index++;
        }
    }
}
