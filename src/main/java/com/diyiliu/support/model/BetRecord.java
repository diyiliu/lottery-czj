package com.diyiliu.support.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description: BetRecord
 * Author: DIYILIU
 * Update: 2018-03-04 21:44
 */

public class BetRecord {

    private String period;

    private String plan;

    private List<Integer> planNos;

    private Integer unit;

    private Integer money;

    private Long datetime;

    private List detail;

    private BigDecimal winLoss;

    // 下注结果 0:未开奖,1:赢,-1:输,
    private Integer result = 0;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public List<Integer> getPlanNos() {
        return planNos;
    }

    public void setPlanNos(List<Integer> planNos) {
        this.planNos = planNos;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Long getDatetime() {
        return datetime;
    }

    public void setDatetime(Long datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getWinLoss() {
        return winLoss;
    }

    public void setWinLoss(BigDecimal winLoss) {
        this.winLoss = winLoss;
    }

    public List getDetail() {
        return detail;
    }

    public void setDetail(List detail) {
        this.detail = detail;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
