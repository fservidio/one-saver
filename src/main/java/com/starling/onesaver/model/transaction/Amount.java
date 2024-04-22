
package com.starling.onesaver.model.transaction;

public class Amount {


    private String currency;

    private Long minorUnits;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getMinorUnits() {
        return minorUnits;
    }

    public void setMinorUnits(Long minorUnits) {
        this.minorUnits = minorUnits;
    }

}
