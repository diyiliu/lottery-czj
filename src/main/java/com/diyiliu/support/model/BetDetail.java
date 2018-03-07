package com.diyiliu.support.model;

import java.math.BigDecimal;

/**
 * Description: BetDetail
 * Author: DIYILIU
 * Update: 2018-03-07 22:19
 */
public class BetDetail {

    private String gameNo;

    private String betType;

    private String betOn;

    private Integer betMoney;

    private BigDecimal winLoss;

    public String getGameNo() {
        return gameNo;
    }

    public void setGameNo(String gameNo) {
        this.gameNo = gameNo;
    }

    public String getBetType() {
        return betType;
    }

    public void setBetType(String betType) {
        this.betType = betType;
    }

    public String getBetOn() {
        return betOn;
    }

    public void setBetOn(String betOn) {
        this.betOn = betOn;
    }

    public Integer getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(Integer betMoney) {
        this.betMoney = betMoney;
    }

    public BigDecimal getWinLoss() {
        return winLoss;
    }

    public void setWinLoss(BigDecimal winLoss) {
        this.winLoss = winLoss;
    }
}
