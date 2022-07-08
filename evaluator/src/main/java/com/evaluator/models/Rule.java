package com.evaluator.models;


public interface Rule {
    Alarm evaluate(Object candleData);

}
