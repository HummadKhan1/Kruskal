import java.io.*;
import java.util.*;

public class Kruskal {
    public class Edge implements Comparable<Edge> {
        String vertex1, vertex2;
        int distance;

        Edge(int d, String v1, String v2) {
            this.distance = d;
            this.vertex1 = v1;
            this.vertex2 = v2;
        }
        public int compareTo(Edge e) {
            return Integer.compare(this.distance, e.distance);
        }
    }
    public void kruskal() throws IOException {
        var edges = new ArrayList<Edge>();
        var vertex = new ArrayList<String>();

        File file = new File("assn9_data.csv");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        int numVertices = 0;
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            vertex.add(data[0]);
            edges.add(new Edge(Integer.parseInt(data[2]), data[0], data[1]));
            for (int i = 3; i < data.length; i++) {
                edges.add(new Edge(Integer.parseInt(data[i + 1]), data[0], data[i]));
                i++;
            }
            numVertices++;
        }

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (int i = 0; i < edges.size() -1; i++) {
            pq.add(edges.get(i));
        }

        int sum = 0;
        List<Edge> mst = new ArrayList<>();
        DisjSets ds = new DisjSets(numVertices);
        while (mst.size() != numVertices - 1) {
            // deleteMin
            Edge e = pq.poll();
            if (e != null) {
                int uset = ds.find(vertex.indexOf(e.vertex1));
                int vset = ds.find(vertex.indexOf(e.vertex2));
                if (uset != vset) {
                    mst.add(e);
                    ds.union(ds.find(vertex.indexOf(e.vertex1)), ds.find(vertex.indexOf(e.vertex2)));

                    System.out.println("Distance from " + e.vertex1 + " to " + e.vertex2 + " is: " + e.distance);
                    sum = sum + e.distance;
                }
            }
        }
        System.out.print("\nSum of all the distances in the tree: " + sum);
    }
    public static void main(String args[]) throws IOException {
        Kruskal kruskal = new Kruskal();
        kruskal.kruskal();
    }
}