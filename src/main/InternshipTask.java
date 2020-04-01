package main;

import main.models.*;
import java.util.*;

public class InternshipTask {

    /**
     * number of trains
     */
    private Integer n;

    /**
     * oriented graph
     */
    private Graph g = new Graph();

    /**
     * array of vertex states during dfs
     */
    private boolean[] used;

    /**
     * dynamic programming array
     */
    private int[] d;

    /**
     * list with information about trains
     */
    private List<Train> trains = new ArrayList<>();

    /**
     * list with result of topological sorting
     */
    private List<Integer> topSort = new ArrayList<>();


    /**
     * Method
     *
     * @param in input reader
     * @return maximum profit from unloading trains
     */

    public int getSolution(InputReader in) {
        readInput(in);
        trains.sort(new TimeComparator());
        addEdges();
        topologicalSort();
        return maximizeValue();
    }

    /**
     * Method
     *
     * @param in input reader
     */

    private void readInput(InputReader in) {
        n = in.nextInt();
        g.resize(n + 2);

        for (int i = 0; i < n; i++) {
            trains.add(new Train(in.nextInt(), in.next(), in.next(), in.nextInt()));
        }
    }

    /**
     *
     */

    private void addEdges() {
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (canUnload(trains.get(i), trains.get(j))) {
                    g.addEdge(new Pair(i, trains.get(i).getValue(), trains.get(i).getNumber()),
                            new Pair(j, trains.get(j).getValue(), trains.get(j).getNumber()));
                }
            }
        }

        for (int i = 0; i < n; i++) {
            g.addEdge(new Pair(n, 0, -1), new Pair(i, trains.get(i).getValue(), trains.get(i).getNumber()));
            g.addEdge(new Pair(i, trains.get(i).getValue(), trains.get(i).getNumber()),
                    new Pair(n + 1, 0, -1));
        }
    }

    /**
     *
     * @param first
     * @param second
     * @return
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


    /**
     *
     */

    private void initDynamicProgramming() {
        d = new int[n + 2];
        d[n] = 0;
        d[n + 1] = 0;

        for (int i = 0; i < trains.size(); i++) {
            d[i] = trains.get(i).getValue();
        }

    }


    /**
     *
     * @return
     */

    private int maximizeValue() {
        initDynamicProgramming();

        for (int train : topSort) {
            d[train] += findMaxValue(train);
        }

        return d[n];

    }

    /**
     *
     * @param v
     * @return
     */

    private int findMaxValue(int v) {
        int maxValue = 0;

        for (int i = 0; i < g.size(v); i++) {
            Pair currentTrain = g.get(v, i);
            if (Math.max(maxValue, d[currentTrain.getVertexNumber()]) != maxValue) {
                maxValue = d[currentTrain.getVertexNumber()];
            }
        }

        return maxValue;
    }

    /**
     *
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

    /**
     *
     * @param v
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