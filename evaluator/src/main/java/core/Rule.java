package core;

import model.CandleData;
import models.Alarm;

public interface Rule {
    public Alarm evaluate(CandleData candleData);
}
