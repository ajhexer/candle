package com.evaluator.utils;

import com.evaluator.core.SMARule;
import com.evaluator.models.Rule;

import java.util.Comparator;

public class SMARuleFactory implements RuleFactory {
    private String ruleName;
    private String fieldName1;
    private String fieldName2;
    private Long interval1;
    private Long interval2;
    private String alarmCondition;
    private String marketSymbol;

    public SMARuleFactory(String ruleName, String fieldName1, String fieldName2, Long interval1, Long interval2, String alarmCondition, String marketSymbol) {
        this.ruleName = ruleName;
        this.fieldName1 = fieldName1;
        this.fieldName2 = fieldName2;
        this.interval1 = interval1;
        this.interval2 = interval2;
        this.alarmCondition = alarmCondition;
        this.marketSymbol = marketSymbol;
    }

    @Override
    public Rule createRule() {
        return new SMARule(this.fieldName1, this.fieldName2, this.interval1, this.interval2, createComparator(alarmCondition), this.ruleName, this.marketSymbol);
    }

    private Comparator<Float> createComparator(String alarmCondition) {
        return switch (alarmCondition) {
            case "greater" -> (o1, o2) -> {
                if (o1 > o2) {
                    return 1;
                }
                return 0;
            };
            case "lower" -> (o1, o2) -> {
                if (o1 < o2) {
                    return 1;
                }
                return 0;
            };
            case "equal" -> (o1, o2) -> {
                if (o1.equals(o2)) {
                    return 1;
                }
                return 0;
            };
            default -> null;
        };
    }
}
