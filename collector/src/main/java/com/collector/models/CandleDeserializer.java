package com.collector.models;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;
import java.util.Map;

public class CandleDeserializer implements Deserializer<CandleData> {
    @Override
    public CandleData deserialize(String s, byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        var timeStamp = buffer.getLong();
        var openingPrice = buffer.getFloat();
        var closingPrice = buffer.getFloat();
        var highestPrice = buffer.getFloat();
        var lowestPrice = buffer.getFloat();
        var volume = buffer.getFloat();
        var amount = buffer.getFloat();
        var length = buffer.getInt();
        StringBuilder marketSymbol = new StringBuilder();
        for(int i=0; i<length; i++){
            marketSymbol.append(buffer.getChar());
        }
        return new CandleData(timeStamp, openingPrice, closingPrice, highestPrice, lowestPrice, volume, amount, marketSymbol.toString());
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public CandleData deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}

