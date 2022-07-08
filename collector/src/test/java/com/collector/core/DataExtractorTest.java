package com.collector.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataExtractorTest {

    @Test
    void getCandleData() {
        var extractor = new DataExtractor("https://api.kucoin.com/api/v1/market/candles", "symbol", "BTC-USDT", "startAt", "endAt", "type", "1min");
        var candles = extractor.getCandleData(Long.toString(System.currentTimeMillis()/1000L-60*360), Long.toString(System.currentTimeMillis()/1000L));
        Assertions.assertEquals(360, candles.size());
    }
}
