package com.evaluator.core;

import com.collector.models.CandleData;
import com.evaluator.models.Alarm;
import com.evaluator.models.Rule;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

public class SMARule implements Rule {
    private String ruleName;
    private String fieldName1;
    private String fieldName2;
    private Long interval1;
    private Long interval2;
    private Comparator<Float> comparator;
    private String marketSymbol;


    public SMARule(String fieldName1, String fieldName2, Long interval1, Long interval2, Comparator<Float> comparator, String ruleName, String marketSymbol) {
        this.fieldName1 = fieldName1;
        this.fieldName2 = fieldName2;
        this.interval1 = interval1;
        this.interval2 = interval2;
        this.comparator = comparator;
        this.ruleName = ruleName;
        this.marketSymbol = marketSymbol;

    }

    /**
     * @param candleData The candle data that will be evaluated
     * @return Alarm if the rule is triggered, otherwise null
     */
    @Override
    public Alarm evaluate(Object candleData) {
        var candleMap = (TreeMap<Long, CandleData>) candleData;
        var timeStamp = candleMap.lastEntry().getValue().getTimeStamp();
        var sum1 = getAverage(candleMap, fieldName1, timeStamp - interval1*60);
        var sum2 = getAverage(candleMap, fieldName2, timeStamp - interval2*60);

        if (comparator.compare(sum1, sum2) > 0) {
            return new Alarm(ruleName, marketSymbol, "SMA", candleMap.lastEntry().getValue().getClosingPrice(), new Time(System.currentTimeMillis()));
        }
        return null;

    }

    /**
     * @param dataTreeMap Tree map of candle data
     * @param fieldName The name of the field to be averaged
     * @param timeStamp The time stamp of candle form which to calculate the average
     * @return
     */
    private float getAverage(TreeMap<Long, CandleData> dataTreeMap, String fieldName, Long timeStamp) {
        var data = dataTreeMap.tailMap(timeStamp);
        float result = 0;
        for (CandleData c : data.values()) {
            try {
                Field f = c.getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                result += (float) f.get(c);
            } catch (Exception e) {
                System.out.println("Error: An error occurred while calculating sma average.");
            }
        }
        return result / data.values().size();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SMARule smaRule = (SMARule) o;
        return ruleName.equals(smaRule.ruleName) && marketSymbol.equals(smaRule.marketSymbol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleName, marketSymbol);
    }

    @Override
    public String toString() {
        return "SMARule{" +
                "ruleName='" + ruleName + '\'' +
                '}';
    }
}
