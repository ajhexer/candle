package com.collector.core;

import com.collector.models.CandleData;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.collector.utils.ProducerUtil;

public class Collector implements Runnable{
    private ProducerUtil producer;
    private DataExtractor dataExtractor;

    public Collector(ProducerUtil producer, DataExtractor dataExtractor) {
        this.producer = producer;
        this.dataExtractor = dataExtractor;
    }

    @Override
    public void run() {
        while(true){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            var candles = this.dataExtractor.getCandleData(Long.toString(System.currentTimeMillis() / 1000L - 60), Long.toString((System.currentTimeMillis() / 1000L) ));
            try{
                for (CandleData candle : candles) {
                    try {
                        System.out.println("Open: " + candle.getOpeningPrice() + " " + "Close: " + candle.getClosingPrice() + " " + "Market: " + candle.getMarketSymbol());
                        this.producer.Send(candle.getTimeStamp(), candle);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
