package com.evaluator.core;

import com.collector.models.CandleData;
import com.evaluator.models.Alarm;
import com.evaluator.models.Rule;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    @Override
    public Alarm evaluate(ArrayList<CandleData> candleData) {
        Collections.sort(candleData, Comparator.comparingLong(CandleData::getTimeStamp));
        var searchObject = (CandleData)candleData.get(candleData.size()-1).clone();
        searchObject.setTimeStamp(searchObject.getTimeStamp()-60*interval1);
        var index1 = Collections.binarySearch(candleData, searchObject, Comparator.comparingLong(CandleData::getTimeStamp));
        searchObject.setTimeStamp(searchObject.getTimeStamp()+60*(interval1-interval2));
        var index2 = Collections.binarySearch(candleData, searchObject, Comparator.comparingLong(CandleData::getTimeStamp));


        var sum1 = getAverage(candleData, index1, fieldName1);
        var sum2 = getAverage(candleData, index2, fieldName2);
        if (comparator.compare(sum1, sum2)>=0){
            return new Alarm(ruleName, marketSymbol, "SMA", candleData.get(candleData.size()-1).getClosingPrice(), new Time(System.currentTimeMillis()));
        }

        return null;
    }

    private float getAverage(ArrayList<CandleData> candleData, int index, String fieldName){
        float result = 0;
        for(int i=index; i<candleData.size(); i++){
            try{
                Field f = candleData.get(i).getClass().getDeclaredField(fieldName);
                f.setAccessible(true);
                result += (float) f.get(candleData.get(i));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return result/(candleData.size()-index);
    }
}
