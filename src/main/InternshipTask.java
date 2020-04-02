package main;

import main.models.*;

import java.util.*;

public class InternshipTask {


    //number of trains
    private Integer n;

    //oriented graph
    private Graph g = new Graph();

    //array of vertex states during dfs
    private boolean[] used;

    // dynamic programming array
    private int[] d;

    //list with information about trains
    private List<Train> trains = new ArrayList<>();

    //list with result of topological sorting
    private List<Integer> topSort = new ArrayList<>();


    /*
        body of the algorithm:
            reading input data
            sorting trains by time using TimeComparator
            creating an oriented graph
            using of topological sorting
            finding the maximum value and returning it
     */

    public int getSolution(InputReader in) {
        readInput(in);
        trains.sort(new TimeComparator());
        addEdges();
        topologicalSort();
        return maximizeValue();
    }


    /*
        Reading number of trains and information about them, adding them to list
     */

    private void readInput(InputReader in) {
        n = in.nextInt();
        g.resize(n + 2);

        for (int i = 0; i < n; i++) {
            trains.add(new Train(in.nextInt(), in.next(), in.next(), in.nextInt()));
        }
    }


    /*
        Adding an oriented edges between vertices if we can unload trains and connecting each vertex to source and sink
     */

    private void addEdges() {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (canUnload(trains.get(i), trains.get(j))) {
                    g.addEdge(new Vertex(i, trains.get(i).getValue(), trains.get(i).getNumber()),
                            new Vertex(j, trains.get(j).getValue(), trains.get(j).getNumber()));
                }
            }
        }


        //n - source
        //n + 1 - sink

        for (int i = 0; i < n; i++) {
            g.addEdge(new Vertex(n, 0, -1), new Vertex(i, trains.get(i).getValue(), trains.get(i).getNumber()));
            g.addEdge(new Vertex(i, trains.get(i).getValue(), trains.get(i).getNumber()),
                    new Vertex(n + 1, 0, -1));
        }
    }


    /*
        Parsing time to check do we need an edge between the "trains"
     */

    private boolean canUnload(Train first, Train second) {
        int timeFirst = Integer.parseInt(first.getArrivalTime().substring(0, 2)) * 60 +
                Integer.parseInt(first.getArrivalTime().substring(3));

        int timeSecond = Integer.parseInt(second.getArrivalTime().substring(0, 2)) * 60 +
                Integer.parseInt(second.getArrivalTime().substring(3));

        int timeToUnloadFirst = Integer.parseInt(first.getTimeToUnload().substring(0, 2)) * 60 +
                Integer.parseInt(first.getTimeToUnload().substring(3));

        return timeFirst + timeToUnloadFirst <= timeSecond;
    }


    /*
        Dynamic programming initialization
            d[source] = 0
            d[sink] = 0
            For each other vertex d[vertex] = value of unloading an appropriate train
     */

    private void initDynamicProgramming() {
        d = new int[n + 2];
        d[n] = 0;
        d[n + 1] = 0;

        for (int i = 0; i < trains.size(); i++) {
            d[i] = trains.get(i).getValue();
        }

    }


    /*
        Finding the maximum profit of unloading
     */

    private int maximizeValue() {
        initDynamicProgramming();

        for (int train : topSort) {
            d[train] += findMaxValue(train);
        }

        return d[n];

    }


    /*
       Finding the maximum value among adjacent vertices
     */

    private int findMaxValue(int v) {
        int maxValue = 0;

        for (int i = 0; i < g.size(v); i++) {
            Vertex currentTrain = g.get(v, i);
            if (Math.max(maxValue, d[currentTrain.getVertexNumber()]) != maxValue) {
                maxValue = d[currentTrain.getVertexNumber()];
            }
        }

        return maxValue;
    }


    /*
        topological sorting
     */

    private void topologicalSort() {
        used = new boolean[n + 2];
        Arrays.fill(used, false);
        topSort.clear();
        for (int i = 0; i < n + 2; i++) {
            if (!used[i]) {
                dfs(i);
            }
        }
    }


    /*
        depth first search
     */

    private void dfs(int v) {
        used[v] = true;
        for (int i = 0; i < g.size(v); i++) {
            int to = g.get(v, i).getVertexNumber();
            if (!used[to]) {
                dfs(to);
            }
        }
        topSort.add(v);
    }

}