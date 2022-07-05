package models;

import model.CandleData;
import models.Alarm;

import java.util.ArrayList;

public interface Rule {
    public Alarm evaluate(ArrayList<CandleData> candleData);
}
