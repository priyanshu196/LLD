package com.rate.enums;

import com.rate.contants.RateLimiterContants;

public enum RateLimiterRuleEnum {

    POST("IP",3L,1L,RateLimiterContants.SECOND,RateLimiterContants.ALGO_SLIDING_WINDOW_LOG),
    READ("USERID",100L,10L,RateLimiterContants.SECOND,RateLimiterContants.ALGO_SLIDING_WINDOW_LOG),
    DELETE("USERID",100L,10L,RateLimiterContants.SECOND,RateLimiterContants.ALGO_SLIDING_WINDOW_LOG),
    READ_ARTICLE("USERID",5L,1L,RateLimiterContants.MONTH,RateLimiterContants.ALGO_SLIDING_WINDOW_COUNTER);

    String filterType;
    Long threshold;
    Long time;
    String timeFrame;
    String algoType;

    RateLimiterRuleEnum(String filterType, Long threshold, Long time, String timeFrame,String algoType) {
        this.filterType=filterType;
        this.threshold=threshold;
        this.time=time;
        this.timeFrame=timeFrame;
        this.algoType=algoType;
    }

    public String getAlgoType() {
        return algoType;
    }

    public String getFilterType() {
        return filterType;
    }


    public Long getThreshold() {
        return threshold;
    }


    public Long getTime() {
        return time;
    }

    public Long getTimeInMiliSecond() {
        if(timeFrame.equalsIgnoreCase("second"))
        return time*1000;
        else if(timeFrame.equalsIgnoreCase("Minute"))
            return time*60*1000;
        else
            return time*60*60*1000;

    }


    public String getTimeFrame() {
        return timeFrame;
    }

    public static RateLimiterRuleEnum fromString(String text) {
        for (RateLimiterRuleEnum rule : RateLimiterRuleEnum.values()) {
            if (rule.name().equalsIgnoreCase(text)) {
                return rule;
            }
        }
        return null;
    }

}
