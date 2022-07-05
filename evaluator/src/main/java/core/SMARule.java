package core;

import model.CandleData;
import models.Alarm;
import models.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Logger;

public class SMARule implements Rule {
    private String ruleName;
    private String marketSymbol;
    private String fieldName1;
    private String fieldName2;
    private Long interval1;
    private Long interval2;
    private Comparator<Float> comparator;

    public SMARule(String fieldName1, String fieldName2, Long interval1, Long interval2, Comparator<Float> comparator, String marketSymbol, String ruleName) {
        this.fieldName1 = fieldName1;
        this.fieldName2 = fieldName2;
        this.interval1 = interval1;
        this.interval2 = interval2;
        this.comparator = comparator;
        this.marketSymbol = marketSymbol;
        this.ruleName = ruleName;
    }

    @Override
    public Alarm evaluate(ArrayList<CandleData> candleData) {
        Collections.sort(candleData, Comparator.comparingLong(CandleData::getTimeStamp));
        var searchObject = (CandleData)candleData.get(candleData.size()-1).clone();
        searchObject.setTimeStamp(searchObject.getTimeStamp()-60*interval1);
        var index1 = Collections.binarySearch(candleData, searchObject, Comparator.comparingLong(CandleData::getTimeStamp));
        searchObject.setTimeStamp(searchObject.getTimeStamp()+60*(interval1-interval2));
        var index2 = Collections.binarySearch(candleData, searchObject, Comparator.comparingLong(CandleData::getTimeStamp));

        float sum1 = 0;
        for(int i=index1; i<candleData.size(); i++){
            try {
                sum1+=(float)candleData.get(i).getClass().getDeclaredField(fieldName1).get(candleData.get(i));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        sum1/=(candleData.size()-index1);

        float sum2 = 0;
        for(int i=index2; i<candleData.size(); i++){
            try {
                sum2+=(float)candleData.get(i).getClass().getDeclaredField(fieldName2).get(candleData.get(i));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if (comparator.compare(sum1, sum2)<0){
            return new Alarm();
        }
        return null;

    }
}
