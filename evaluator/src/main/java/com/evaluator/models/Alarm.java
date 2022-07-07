package com.evaluator.models;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;

@Entity
@Data
@Table(name = "Alarms")
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "ALARM_NAME")
    private String alarmName;
    @Column(name = "INDICATOR")
    private String indicator;
    @Column(name = "MARKET_SYMBOL")
    private String marketSymbol;
    @Column(name = "CURRENT_PRICE")
    private float currentPrice;
    @Column(name = "TIME")
    private Time time;


    public Alarm(String alarmName, String marketSymbol, String indicator, float currentPrice, Time time) {
        this.alarmName = alarmName;
        this.marketSymbol = marketSymbol;
        this.indicator = indicator;
        this.currentPrice = currentPrice;
        this.time = time;
    }

    public Alarm() {
    }

    public Long getId() {
        return id;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public String getMarketSymbol() {
        return marketSymbol;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public Time getTime() {
        return time;
    }

    public String getIndicator() {
        return indicator;
    }
}
