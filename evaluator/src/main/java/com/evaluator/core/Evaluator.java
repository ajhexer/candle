package com.evaluator.core;

import com.collector.models.CandleData;
import com.evaluator.models.Rule;
import com.evaluator.utils.RuleFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.evaluator.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

public class Evaluator implements Runnable {
    private final KafkaConsumer<Long, CandleData> consumer;
    private final HashMap<String, ArrayList<Rule>> rules = new HashMap<String, ArrayList<Rule>>();
    private final HashMap<String, ArrayList<CandleData>> candleData = new HashMap<String, ArrayList<CandleData>>();
    private final String topicName;

    public Evaluator(Properties props, String topicName) {
        consumer = new KafkaConsumer<Long, CandleData>(props);
        this.topicName = topicName;
    }

    public void AddRule(String market, RuleFactory ruleFactory) {
        if (!rules.containsKey(market)) {
            rules.put(market, new ArrayList<Rule>());
        }
        rules.get(market).add(ruleFactory.createRule());
    }

    public void run() {

        consumer.subscribe(Collections.singletonList(topicName));
        while (true) {
            ConsumerRecords<Long, CandleData> records = consumer.poll(100);
            for (ConsumerRecord<Long, CandleData> record : records) {
                CandleData candle = record.value();
                if (candleData.containsKey(candle.getMarketSymbol())) {
                    candleData.get(candle.getMarketSymbol()).add(candle);
                } else {
                    ArrayList<CandleData> candleList = new ArrayList<CandleData>();
                    candleList.add(candle);
                    candleData.put(candle.getMarketSymbol(), candleList);
                }
            }
            for (String marketSymbol : candleData.keySet()) {
                for (Rule rule : rules.get(marketSymbol)) {
                    new Thread(() -> {
                        synchronized (candleData.get(marketSymbol)) {
                            if (rule.evaluate(candleData.get(marketSymbol)) != null) {
                                try (var session = HibernateUtil.getSessionFactory().openSession()){
                                    session.beginTransaction();
                                    session.save(rule.evaluate(candleData.get(marketSymbol)));
                                    session.getTransaction().commit();
                                }catch (Exception e){
                                    System.out.println("An error occurred in saving alarm");
                                }

                            }
                        }
                    }).start();
                }
            }
        }
    }
}

