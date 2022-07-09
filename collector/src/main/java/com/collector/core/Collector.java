package com.collector.core;

import com.collector.models.CandleData;
import com.collector.utils.ProducerUtil;

import java.util.ArrayList;

public class Collector implements Runnable{
    private ProducerUtil producer;
    private DataExtractor dataExtractor;

    /**
     * @param producer The producer that sends data to the Kafka topic
     * @param dataExtractor The data extractor that extracts data from provided api
     */
    public Collector(ProducerUtil producer, DataExtractor dataExtractor) {
        this.producer = producer;
        this.dataExtractor = dataExtractor;
    }

    @Override
    public void run() {

        while(true){
            var candles = ExtractCandles(System.currentTimeMillis() / 1000L - 60, System.currentTimeMillis() / 1000L);
            try{
                for (CandleData candle : candles) {
                    try {
                        System.out.println("Open: " + candle.getOpeningPrice() + " " + "Close: " + candle.getClosingPrice() + " " + "Market: " + candle.getMarketSymbol());
                        this.producer.Send(candle.getTimeStamp(), candle);
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                        break;
                    }
                }
            }catch (Exception e){
                System.out.println("Error: An error occurred in retrieving candle data");
            }

            try {
                Thread.sleep(1000*60);
            } catch (InterruptedException e) {
                System.out.println("Error: An error occurred in sleeping");
            }

        }
    }

    /**
     * @param startTime The time from when it gets candles
     * @param endTime The time until when it gets candles
     * @return ArrayList of candles correspond to provided start and end time;
     */
    private ArrayList<CandleData> ExtractCandles(long startTime, long endTime){
        return this.dataExtractor.getCandleData(Long.toString(startTime), Long.toString(endTime));
    }

}
