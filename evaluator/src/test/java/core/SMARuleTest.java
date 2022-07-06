package core;

import model.CandleData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

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

        ArrayList<CandleData> mockCandleData = new ArrayList<>();
        mockCandleData.add(new CandleData(System.currentTimeMillis()/1000L-60, 2f, 1f, 4f, 4f, 2f, 6f, "BTC"));
        mockCandleData.add(new CandleData(System.currentTimeMillis()/1000L, 2f, 1f, 4f, 4f, 2f, 6f, "BTC"));
        Assertions.assertNotNull(mockRule.evaluate(mockCandleData));
        var alarm = mockRule.evaluate(mockCandleData);
        Assertions.assertEquals("BTC-USDT", alarm.getMarketSymbol());
        Assertions.assertEquals("SMA", alarm.getIndicator());
        Assertions.assertEquals(mockCandleData.get(1).getClosingPrice(), alarm.getCurrentPrice());
    }
}