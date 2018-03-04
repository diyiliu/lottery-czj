package com.diyiliu.support.model;

/**
 * Description: BetReturn
 * Author: DIYILIU
 * Update: 2018-03-04 21:58
 */
public class BetReturn {

    private String period;
    private Integer returnCode;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Integer returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isSuccess(){
        if (returnCode == 0){

            return true;
        }

        return false;
    }
}
