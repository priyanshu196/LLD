package com.rate.business;

import com.rate.contants.RateLimiterContants;
import com.rate.enums.RateLimiterRuleEnum;
import com.rate.executors.RateLimiter;
import com.rate.executors.SlidingWindowCounter;
import com.rate.executors.SlidingWindowLogRateLimiter;
import com.rate.mode.RateKey;
import com.rate.mode.RateLimiterResponse;
import com.rate.mode.Request;

import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterBusiness {

    private static ConcurrentHashMap<RateKey, RateLimiter> userRateLimiterMap = new ConcurrentHashMap<>();

    public void rateLimit(Request request){

        RateLimiterRuleEnum rateLimiterRuleEnum =  RateLimiterRuleEnum.fromString(request.getType());
        RateKey rateKey = new RateKey(request.getType(),rateLimiterRuleEnum.getFilterType().equalsIgnoreCase("IP")?request.getIP():request.getUserID());
        if(!userRateLimiterMap.containsKey(rateKey)){
            userRateLimiterMap.put(rateKey, getExecutor(rateLimiterRuleEnum));
        }
        RateLimiterResponse response =userRateLimiterMap.get(rateKey).execute();
        System.out.println(response);

    }

    //will replace it with spring bean finder on real life project .
    private RateLimiter getExecutor(RateLimiterRuleEnum rateLimiterRuleEnum) {
        if(RateLimiterContants.ALGO_SLIDING_WINDOW_COUNTER.equalsIgnoreCase(rateLimiterRuleEnum.getAlgoType())){
            return new SlidingWindowCounter(rateLimiterRuleEnum);
        }
        else
          return  new SlidingWindowLogRateLimiter(rateLimiterRuleEnum);
    }

}
