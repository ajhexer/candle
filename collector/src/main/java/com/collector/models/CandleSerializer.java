package com.collector.models;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class CandleSerializer implements Serializer<CandleData> {
    private String encoding = "UTF8";

    @Override
    public byte[] serialize(String s, CandleData candleData) {
        ByteBuffer buffer = ByteBuffer.allocate(8 + 6*4 + 3*candleData.getMarketSymbol().length());
        buffer.putLong(candleData.getTimeStamp());
        buffer.putFloat(candleData.getOpeningPrice());
        buffer.putFloat(candleData.getClosingPrice());
        buffer.putFloat(candleData.getHighestPrice());
        buffer.putFloat(candleData.getLowestPrice());
        buffer.putFloat(candleData.getVolume());
        buffer.putFloat(candleData.getAmount());
        var length = candleData.getMarketSymbol().length();
        buffer.putInt(length);
        for(int i=0; i<length; i++){
            buffer.putChar(candleData.getMarketSymbol().charAt(i));
        }
        return buffer.array();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, CandleData data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
