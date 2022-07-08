package com.evaluator.core;

import com.collector.models.CandleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

class SMARuleTest {



    @Test
    void evaluate() {
        SMARule mockRule = new SMARule("openingPrice", "closingPrice", 1L, 1L, new Comparator<Float>() {
            @Override
            public int compare(Float o1, Float o2) {
                if(o1 > o2){
                    return 1;
                }
                return -1;
            }
        }, "SMA", "BTC-USDT");

        TreeMap<Long, CandleData> mockCandleData = new TreeMap<>();
        var candle1 = new CandleData(System.currentTimeMillis()/1000L-60, 2f, 1f, 4f, 4f, 2f, 6f, "BTC");
        var candle2 = new CandleData(System.currentTimeMillis()/1000L, 2f, 1f, 4f, 4f, 2f, 6f, "BTC");
        mockCandleData.put(candle1.getTimeStamp(), candle1);
        mockCandleData.put(candle2.getTimeStamp(), candle2);
        Assertions.assertNotNull(mockRule.evaluate(mockCandleData));
        var alarm = mockRule.evaluate(mockCandleData);
        Assertions.assertEquals("BTC-USDT", alarm.getMarketSymbol());
        Assertions.assertEquals("SMA", alarm.getIndicator());
        Assertions.assertEquals(mockCandleData.firstEntry().getValue().getClosingPrice(), alarm.getCurrentPrice());
    }
}