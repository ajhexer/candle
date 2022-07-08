package com.evaluator.models;

import com.collector.models.CandleData;

import java.util.ArrayList;
import java.util.Collection;

public interface Rule {
    Alarm evaluate(Object candleData);

}
