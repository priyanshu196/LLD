package com.rate.mode;

import java.util.Objects;

public class RateKey {

    String apiName;
    String filter;

    public RateKey(String apiName, String filter) {
        this.apiName = apiName;
        this.filter = filter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RateKey)) return false;
        RateKey rateKey = (RateKey) o;
        return Objects.equals(apiName, rateKey.apiName) &&
                Objects.equals(filter, rateKey.filter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(apiName, filter);
    }
}
