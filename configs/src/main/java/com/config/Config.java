package com.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Config {
    @JsonProperty
    private ArrayList<SMA> smas;


    public ArrayList<SMA> getSmas() {
        return smas;
    }
}
