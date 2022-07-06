package models;

import model.CandleData;

import java.util.ArrayList;

public interface Rule {
    Alarm evaluate(ArrayList<CandleData> candleData);
}
