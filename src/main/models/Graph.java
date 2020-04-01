package main.models;

import java.nio.file.Path;
import java.util.LinkedList;

public class Graph {
    private int numVertices;
    private LinkedList<Pair>[] adjLists;

    public Graph() {
        numVertices = 0;
    }

    public void resize(int n) {
        this.numVertices = n;
        adjLists = new LinkedList[n];
        for (int i = 0; i < numVertices; i++)
            adjLists[i] = new LinkedList();
    }

    public void addEdge(Pair from, Pair to) {
        adjLists[from.getVertexNumber()].add(to);
    }

    public void printGraph() {
        for (int i = 0; i < numVertices; i++) {
            System.out.print(i + 1 + " : ");
            for (int j = 0; j < adjLists[i].size(); j++) {
                System.out.print(adjLists[i].get(j).getVertexNumber() + 1 + " ");
            }
            System.out.println();
        }
    }

    public int size(int v){
        return adjLists[v].size();
    }

    public Pair get(int from, int to){
        return adjLists[from].get(to);
    }

}