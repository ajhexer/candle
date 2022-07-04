package model;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataCollector {
    private String url;
    private String symbolQuery;
    private String symbolValue;
    private String startQuery;
    private String startValue;
    private String endQuery;
    private String endValue;
    private String intervalQuery;
    private String intervalValue;

    public DataCollector(String url, String symbolQuery, String symbolValue, String startQuery, String startValue, String endQuery, String endValue, String intervalQuery, String intervalValue) {
        this.url = url;
        this.symbolQuery = symbolQuery;
        this.symbolValue = symbolValue;
        this.startQuery = startQuery;
        this.startValue = startValue;
        this.endQuery = endQuery;
        this.endValue = endValue;
        this.intervalQuery = intervalQuery;
        this.intervalValue = intervalValue;
    }
    public CandleData getCandleData() {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                        URI.create(url + "?" + symbolQuery + "=" + symbolValue + "&" + startQuery + "=" + startValue + "&" + endQuery + "=" + endValue + "&" + intervalQuery + "=" + intervalValue))
                .header("accept", "application/json")
                .build();
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject obj = new JSONObject(response.body());
            var t = obj.getJSONArray("data");
            var o = t.get(0);
            var m = o.toString().replace("[", "").replace("]", "").replace("\"", "").split(",");
            return CandleData.BuildFromArray(m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
