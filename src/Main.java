import java.io.*;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.util.*;
import java.util.spi.AbstractResourceBundleProvider;
import java.util.stream.Collector;


public class Main {
    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream("loader.in");
        OutputStream outputStream = new FileOutputStream("loader.out");
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        TaskInternship solution = new TaskInternship();
        solution.solve(in, out);
        out.close();
        out.flush();
    }


    static class TaskInternship {

        class GraphMatrix {
            private int[][] matrix;
            int numVertices;

            GraphMatrix() {
                numVertices = 0;
            }


            public void resize(int n) {
                this.numVertices = n;
                matrix = new int[n][n];
                for (int i = 0; i < n; i++)
                    for (int j = 0; j < n; j++)
                        matrix[i][j] = 0;

            }

            public void printGraph() {
                for (int i = 0; i < numVertices; i++) {
                    for (int j = 0; j < numVertices; j++) {
                        System.out.print(matrix[i][j] + " ");
                    }
                    System.out.println();
                }

            }
        }


        class GraphList {
            private int numVertices;
            private LinkedList<Pair> adjLists[];

            GraphList(int n) {
                numVertices = 0;
            }

            public GraphList() {
                numVertices = 0;
            }

            public void resize(int n) {
                this.numVertices = n;
                adjLists = new LinkedList[n];
                for (int i = 0; i < numVertices; i++)
                    adjLists[i] = new LinkedList();
            }

            public void addEdge(Pair from, Pair to) {
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

        private class Train {
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

        private class Pair {
            int vertexNumber;
            int trainNumber;
            int trainValue;

            Pair(int vertexNumber, int trainValue, int trainNumber) {
                this.vertexNumber = vertexNumber;
                this.trainValue = trainValue;
                this.trainNumber = trainNumber;
            }
        }


        private List<Train> trains = new ArrayList<>();
        private List<Integer> unloadedTrains = new ArrayList<>();
        private Integer n;
        private GraphList g = new GraphList();
        private boolean[] used;
        private List<Integer> topSort = new ArrayList<>();
        private int[] d;


        public void solve(InputReader in, PrintWriter out) {
            n = in.nextInt();

            g.resize(n + 2);


            for (int i = 0; i < n; i++) {
                trains.add(new Train(in.nextInt(), in.next(), in.next(), in.nextInt()));
            }


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

            g.printGraph();
            topologicalSort();
            for (int u : topSort)
                System.out.print(u + 1 + " ");
            System.out.println();
            //System.out.println(maximizeValue());


        }



        private boolean canUnload(Train first, Train second) {
            int timeFirst = Integer.parseInt(first.arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(first.arrivalTime.substring(3));
            int timeSecond = Integer.parseInt(second.arrivalTime.substring(0, 2)) * 60 + Integer.parseInt(second.arrivalTime.substring(3));
            int timeToUnloadFirst = Integer.parseInt(first.timeToUnload.substring(0, 2)) * 60 + Integer.parseInt(first.timeToUnload.substring(3));

            //System.out.println(first.number + " " + timeFirst + " " + second.number + " " + timeSecond);
            return timeFirst + timeToUnloadFirst <= timeSecond;
        }


        /*
        private int maximizeValue() {
            d = new int[n + 2];
            Arrays.fill(d, 0);
            int value = 0;
            for (int train : topSort) {
                value += findMaxValue(train);
            }
            System.out.println(unloadedTrains);
            return value;


        }
        /*

        private int findMaxValue(int v) {
            for (int i = 0; i < g.adjLists[v].size(); i++) {
                Pair currentTrain = g.adjLists[v].get(i);
                if(Math.max(d[v], currentTrain.trainValue) !=d[v]){
                    d[v] = currentTrain.trainValue;
                    unloadedTrains.add(currentTrain.trainNumber);
                }
            }
            if(unloadedTrains.size() != 0)
            System.out.println(unloadedTrains.get(unloadedTrains.size() - 1) + " " + maxValue);


            return maxValue;
        }

         */

        private void topologicalSort() {
            used = new boolean[n + 2];
            Arrays.fill(used, false);
            topSort.clear();
            for (int i = 0; i < n + 2; i++)
                if (!used[i])
                    dfs(i);
        }

        private void dfs(int v) {
            used[v] = true;
            for (int i = 0; i < g.adjLists[v].size(); i++) {
                int to = g.adjLists[v].get(i).vertexNumber;
                if (!used[to])
                    dfs(to);
            }
            topSort.add(v);
        }


    }


    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;

        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public Long nextLong() {
            return Long.parseLong(next());
        }

    }
}
