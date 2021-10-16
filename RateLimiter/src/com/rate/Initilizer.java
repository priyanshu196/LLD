package com.rate;

import com.rate.business.RateLimiterBusiness;
import com.rate.mode.Request;

public class Initilizer {

    private static long iterations = 10;
    private static long sleepTime = 0;
    public static void main(String as[]){

        new Initilizer().test();

    }

    private void test() {

        for (int i=0;i<iterations;i++)
        {
            Request request = new Request("READ_ARTICLE","120.1.1.1","82315");
            try {
                Thread.sleep(sleepTime);
                new RateLimiterBusiness().rateLimit(request);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
