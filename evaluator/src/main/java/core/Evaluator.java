package core;

import model.CandleData;

import java.util.ArrayList;
import java.util.HashMap;

public class Evaluator implements Runnable {
    private final ArrayList<Rule> rules = new ArrayList<Rule>();
    private final HashMap<String, CandleData> candleData = new HashMap<String, CandleData>();

    public void run() {


    }
}
