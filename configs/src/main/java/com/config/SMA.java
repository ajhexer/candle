package com.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SMA {
    @JsonProperty
    private String market;
    @JsonProperty
    private String fieldName1;
    @JsonProperty
    private String fieldName2;
    @JsonProperty
    private int timeInterval1;
    @JsonProperty
    private int timeInterval2;
    @JsonProperty
    private String alarmCondition;
    @JsonProperty
    private String name;

    public String getMarket() {
        return market;
    }

    public String getFieldName1() {
        return fieldName1;
    }

    public String getFieldName2() {
        return fieldName2;
    }

    public int getTimeInterval1() {
        return timeInterval1;
    }

    public int getTimeInterval2() {
        return timeInterval2;
    }

    public String getAlarmCondition() {
        return alarmCondition;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SMA{" +
                "market='" + market + '\'' +
                ", fieldName1='" + fieldName1 + '\'' +
                ", fieldName2='" + fieldName2 + '\'' +
                ", timeInterval1=" + timeInterval1 +
                ", timeInterval2=" + timeInterval2 +
                ", alarmCondition='" + alarmCondition + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
