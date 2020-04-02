package main.models;

import java.util.Comparator;

/*
    class to represent comparator for sorting by time
 */

public class TimeComparator implements Comparator<Train> {
    @Override
    public int compare(Train first, Train second) {
        return (Integer.parseInt(first.getArrivalTime().substring(0, 2)) * 60 + Integer.parseInt(first.getArrivalTime().substring(3))) -
                (Integer.parseInt(second.getArrivalTime().substring(0, 2)) * 60 + Integer.parseInt(second.getArrivalTime().substring(3)));
    }
}
