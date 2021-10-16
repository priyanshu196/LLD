package com.rate.mode;

import com.rate.enums.Status;

public class RateLimiterResponse {

    String message;
    String threshold;
    Long requestNumber;
    Status status;

    public Status getStatus() {
        return status;
    }

    public RateLimiterResponse setStatus(Status status) {
        this.status = status;
        return this;
    }

    static public RateLimiterResponse build(){
       return new RateLimiterResponse();
    }

    public String getMessage() {
        return message;
    }

    public RateLimiterResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getThreshold() {
        return threshold;
    }

    public RateLimiterResponse setThreshold(String threshold) {
        this.threshold = threshold;
        return this;
    }

    public Long getRequestNumber() {
        return requestNumber;
    }

    public RateLimiterResponse setRequestNumber(Long requestNumber) {
        this.requestNumber = requestNumber;
        return this;
    }

    @Override
    public String toString() {
        return "RateLimiterResponse{" +
                "message='" + message + '\'' +
                ", threshold='" + threshold + '\'' +
                ", requestNumber=" + requestNumber +
                ", status=" + status +
                '}';
    }
}
