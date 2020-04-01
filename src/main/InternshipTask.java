package main;

import main.reader.InputReader;

import java.io.PrintWriter;
import java.util.*;

public class InternshipTask {

    private static class GraphList {
        private int numVertices;
        private LinkedList<Pair>[] adjLists;

        GraphList() {
            numVertices = 0;
        }

        void resize(int n) {
            this.numVertices = n;
            adjLists = new LinkedList[n];
            for (int i = 0; i < numVertices; i++)
                adjLists[i] = new LinkedList();
        }

        void addEdge(Pair from, Pair to) {
            adjLists[from.vertexNumber].add(to);
        }

        public void printGraph() {
            for (int i = 0; i < numVertices; i++) {
                System.out.print(i + 1 + " : ");
                for (int j = 0; j < adjLists[i].size(); j++) {
                    System.out.print(adjLists[i].get(j).vertexNumber + 1 + " ");
                }
                System.out.println();
            }
        }

    }

    private static class Train {

        int number;
        String arrivalTime;
        String timeToUnload;
        int value;

        Train(int number, String arrivalTime, String timeToUnload, int value) {
            this.number = number;
            this.arrivalTime = arrivalTime;
            this.timeToUnload = timeToUnload;
            this.value = value;
        }
    }

    private static class Pair {

        int vertexNumber;
        int trainNumber;
        int trainValue;

        Pair(int vertexNumber, int trainValue, int trainNumber) {
            this.vertexNumber = vertexNumber;
            this.trainValue = trainValue;
            this.trainNumber = trainNumber;
        }
    }

    public static class TimeComparator implements Comparator<Train> {
        @Override
        public int compare(Train o, Train t1) {
            return (Integer.parseInt(o.arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(o.arrivalTime.substring(3))) -
                    (Integer.parseInt(t1.arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(t1.arrivalTime.substring(3)));
        }
    }


    private List<Train> trains = new ArrayList<>();
    private Integer n;
    private GraphList g = new GraphList();
    private boolean[] used;
    private List<Integer> topSort = new ArrayList<>();
    private int[] d;


    public int getSolution(InputReader in) {
        readInput(in);
        trains.sort(new TimeComparator());
        addEdges();
        topologicalSort();
        return maximizeValue();
    }

    private void readInput(InputReader in){
        n = in.nextInt();
        g.resize(n + 2);

        for (int i = 0; i < n; i++) {
            trains.add(new Train(in.nextInt(), in.next(), in.next(), in.nextInt()));
        }
    }


    private void addEdges(){
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (canUnload(trains.get(i), trains.get(j)))
                    g.addEdge(new Pair(i, trains.get(i).value, trains.get(i).number), new Pair(j, trains.get(j).value, trains.get(j).number));
            }
        }

        for (int i = 0; i < n; i++) {
            g.addEdge(new Pair(n, 0, -1), new Pair(i, trains.get(i).value, trains.get(i).number));
            g.addEdge(new Pair(i, trains.get(i).value, trains.get(i).number), new Pair(n + 1, 0, -1));
        }
    }


    private boolean canUnload(Train first, Train second) {
        int timeFirst = Integer.parseInt(first.arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(first.arrivalTime.substring(3));
        int timeSecond = Integer.parseInt(second.arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(second.arrivalTime.substring(3));
        int timeToUnloadFirst = Integer.parseInt(first.timeToUnload.substring(0, 2)) * 60 + Integer.parseInt(first.timeToUnload.substring(3));

        return timeFirst + timeToUnloadFirst <= timeSecond;
    }


    private void initDynamicProgramming() {
        d = new int[n + 2];
        d[n] = 0;
        d[n + 1] = 0;

        for (int i = 0; i < trains.size(); i++) {
            d[i] = trains.get(i).value;
        }

    }

    private int maximizeValue() {
        initDynamicProgramming();

        for (int train : topSort) {
            d[train] += findMaxValue(train);
        }

        return d[n];

    }

    private int findMaxValue(int v) {
        int maxValue = 0;

        for (int i = 0; i < g.adjLists[v].size(); i++) {
            Pair currentTrain = g.adjLists[v].get(i);
            if (Math.max(maxValue, d[currentTrain.vertexNumber]) != maxValue) {
                maxValue = d[currentTrain.vertexNumber];
            }
        }

        return maxValue;
    }


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

    private void dfs(int v) {
        used[v] = true;
        for (int i = 0; i < g.adjLists[v].size(); i++) {
            int to = g.adjLists[v].get(i).vertexNumber;
            if (!used[to]) {
                dfs(to);
            }
        }
        topSort.add(v);
    }


}