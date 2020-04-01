package main.models;

public class Train {
    private int number;
    private String arrivalTime;
    private String timeToUnload;
    private int value;

    public Train(int number, String arrivalTime, String timeToUnload, int value) {
        this.number = number;
        this.arrivalTime = arrivalTime;
        this.timeToUnload = timeToUnload;
        this.value = value;
    }

    public int getNumber() {
        return number;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public int getValue() {
        return value;
    }

    public String getTimeToUnload() {
        return timeToUnload;
    }
}
