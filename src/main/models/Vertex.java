package main.models;

/*
    class to represent a graph vertex
 */

public class Vertex {
    private int vertexNumber;
    private int trainNumber;
    private int trainValue;

    public Vertex(int vertexNumber, int trainValue, int trainNumber) {
        this.vertexNumber = vertexNumber;
        this.trainValue = trainValue;
        this.trainNumber = trainNumber;
    }

    public int getVertexNumber() {
        return vertexNumber;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public int getTrainValue(){
        return trainValue;
    }
}
