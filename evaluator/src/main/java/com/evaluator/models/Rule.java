package com.evaluator.models;

import com.collector.models.CandleData;

import java.util.ArrayList;

public interface Rule {
    Alarm evaluate(ArrayList<CandleData> candleData);
}
