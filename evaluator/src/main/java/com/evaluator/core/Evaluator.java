package com.evaluator.core;

import com.collector.models.CandleData;
import com.evaluator.models.Rule;
import com.evaluator.utils.ConsumerUtil;
import com.evaluator.utils.RuleFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import com.evaluator.utils.HibernateUtil;

import java.util.*;


/**
 * The class in which we implement runnable method "run" so that it evaluates rules every 1 minute
 */
public class Evaluator implements Runnable {
    private final ConsumerUtil consumerUtil;
    private final HashMap<String /* market */, ArrayList<Rule>> rules = new HashMap<String, ArrayList<Rule>>();
    private final HashMap<String /* market */, TreeMap<Long, CandleData>> candleData = new HashMap<String, TreeMap<Long, CandleData>>();

    public Evaluator(ConsumerUtil consumerUtil) {
        this.consumerUtil = consumerUtil;
    }

    /**
     * @param market The market symbol for that specific rule
     * @param ruleFactory Abstract factory that creates rule
     */
    public void AddRule(String market, RuleFactory ruleFactory) {
        if (!rules.containsKey(market)) {
            rules.put(market, new ArrayList<Rule>());
        }
        var rule = ruleFactory.createRule();
        rules.get(market).add(rule);
    }

    public void run() {

        while (true) {
            ConsumerRecords<Long, CandleData> records = consumerUtil.getConsumer().poll(100);
            for (ConsumerRecord<Long, CandleData> record : records) {
                CandleData candle = record.value();
                if (candleData.containsKey(candle.getMarketSymbol())) {
                    candleData.get(candle.getMarketSymbol()).put(candle.getTimeStamp(), candle);
                    System.out.println(candle);
                } else {
                    TreeMap<Long, CandleData> candleList = new TreeMap<>();
                    candleList.put(candle.getTimeStamp(), candle);
                    candleData.put(candle.getMarketSymbol(), candleList);
                }
            }

            try {
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
            }catch (Exception e){
                System.out.println("An error occurred in evaluating rules");
            }

            try {
                Thread.sleep(1000*60);
            }catch (Exception e){
                System.out.println("Error: An internal error occurred");
            }
        }
    }
}

