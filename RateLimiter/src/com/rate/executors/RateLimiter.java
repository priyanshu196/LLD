package com.rate.executors;

import com.rate.mode.RateLimiterResponse;

public interface RateLimiter {

    public RateLimiterResponse execute();
}
