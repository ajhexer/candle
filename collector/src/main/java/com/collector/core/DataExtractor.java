package com.collector.core;

import com.collector.models.CandleData;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class DataExtractor {
    private String url;
    private String symbolQuery;
    private String symbolValue;
    private String startQuery;
    private String endQuery;
    private String intervalQuery;
    private String intervalValue;

    /**
     * @param url The url of the API
     * @param symbolQuery The query string for the symbol
     * @param symbolValue The value of the symbol
     * @param startQuery The query string for the start time
     * @param endQuery The query string for the end time
     * @param intervalQuery The query string for the interval
     * @param intervalValue The value of the interval of candle window
     */
    public DataExtractor(String url, String symbolQuery, String symbolValue, String startQuery, String endQuery, String intervalQuery, String intervalValue) {
        this.url = url;
        this.symbolQuery = symbolQuery;
        this.symbolValue = symbolValue;
        this.startQuery = startQuery;
        this.endQuery = endQuery;
        this.intervalQuery = intervalQuery;
        this.intervalValue = intervalValue;
    }

    /**
     * @param startValue The time from when it gets candles
     * @param endValue The time until when it gets candles
     * @return ArrayList of candles corresponds to provided start and end time.
     */
    public ArrayList<CandleData> getCandleData(String startValue, String endValue) {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(URI.create(url + "?" + symbolQuery + "=" + symbolValue + "&" + startQuery + "=" + startValue + "&" + endQuery + "=" + endValue + "&" + intervalQuery + "=" + intervalValue)).header("accept", "application/json").build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject obj = new JSONObject(response.body());
            var t = obj.getJSONArray("data");
            var res = new ArrayList<CandleData>();
            for (int i = 0; i < t.length(); i++) {
                var o = t.get(i);
                var m = o.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
                res.add(CandleData.BuildFromArray(m, symbolValue));
            }
            return res;
        } catch (Exception e) {
            System.out.println("Error: An error occurred in retrieving data");
        }
        return null;
    }
}
