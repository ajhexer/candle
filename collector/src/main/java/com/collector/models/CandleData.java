package com.collector.models;

public class CandleData implements Cloneable{
    private long timeStamp;
    private float openingPrice;
    private float closingPrice;
    private float highestPrice;
    private float lowestPrice;
    private float volume;
    private float amount;
    private String marketSymbol;

    public CandleData(long timeStamp, float openingPrice, float closingPrice, float highestPrice, float lowestPrice, float volume, float amount, String marketSymbol) {
        this.timeStamp = timeStamp;
        this.openingPrice = openingPrice;
        this.closingPrice = closingPrice;
        this.highestPrice = highestPrice;
        this.lowestPrice = lowestPrice;
        this.volume = volume;
        this.amount = amount;
        this.marketSymbol = marketSymbol;
    }

    /**
     * @param arr The array that generated from retrieved json file.
     * @param marketSymbol Market symbol for that candle
     * @return One Candle data
     */
    public static CandleData BuildFromArray(String[] arr, String marketSymbol) {
        return new CandleData(Long.parseLong(arr[0]), Float.parseFloat(arr[1]), Float.parseFloat(arr[2]), Float.parseFloat(arr[3]), Float.parseFloat(arr[4]), Float.parseFloat(arr[5]), Float.parseFloat(arr[6]), marketSymbol);
    }

    public float getOpeningPrice() {
        return openingPrice;
    }

    public float getClosingPrice() {
        return closingPrice;
    }

    public float getHighestPrice() {
        return highestPrice;
    }

    public float getLowestPrice() {
        return lowestPrice;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public float getVolume() {
        return volume;
    }

    public float getAmount() {
        return amount;
    }

    public String getMarketSymbol() {
        return marketSymbol;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public Object clone()  {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return new CandleData(this.timeStamp, this.openingPrice, this.closingPrice, this.highestPrice, this.lowestPrice, this.volume, this.amount, this.marketSymbol);
        }
    }

    @Override
    public String toString() {
        return "CandleData{" +
                "timeStamp=" + timeStamp +
                ", openingPrice=" + openingPrice +
                ", closingPrice=" + closingPrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", volume=" + volume +
                ", amount=" + amount +
                ", marketSymbol='" + marketSymbol + '\'' +
                '}';
    }
}
