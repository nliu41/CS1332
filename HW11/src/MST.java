import java.util.Map;
import java.util.Set;
import java.util.Collection;

import java.util.HashSet;
import java.util.PriorityQueue;

public class MST {

    /**
     * Using disjoint set(s), run Kruskal's algorithm on the given graph and
     * return the MST. Return null if no MST exists for the graph.
     *
     * @param g The graph to be processed. Will never be null.
     * @return The MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> kruskals(Graph g) {

        Collection<Edge> mstpath = new HashSet<>();
        DisjointSetsInterface<Vertex> djs = new DisjointSets<>(g.getVertices());
        PriorityQueue<Edge> pq = new PriorityQueue<>(g.getEdgeList().size());

        // fill priority queue with edges;
        g.getEdgeList().forEach((Edge e) -> pq.add(e));

        while (!pq.isEmpty()) {
            // find min edge that contains two disjoint sets
            Edge minEdge = pq.remove();
            while (!pq.isEmpty()
                    && djs.sameSet(minEdge.getU(), minEdge.getV())) {
                minEdge = pq.remove();
            }

            // if a valid minEdge
            // then merge
            if (!djs.sameSet(minEdge.getU(), minEdge.getV())) {
                mstpath.add(minEdge);
                djs.merge(minEdge.getU(), minEdge.getV());
            }
        }

        return (mstpath.size() > 0) ? mstpath : null;
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree. If no MST exists, return null.
     *
     * @param g The graph to be processed. Will never be null.
     * @param start The ID of the start node. Will always exist in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> prims(Graph g, int start) {

        Collection<Edge> mstpath = new HashSet<>();
        Set<Vertex> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(g.getEdgeList().size());

        Vertex init = new Vertex(start);
        visited.add(init);

        // add all adjacent edges to the pq
        Map<Vertex, Integer> initAdj = g.getAdjacencies(init);
        for (Vertex dest : initAdj.keySet()) {
            Integer weight = initAdj.get(dest);
            if (!visited.contains(dest)) {
                pq.add(new Edge(init, dest, weight));
            }
        }

        while (!pq.isEmpty()) {
            // find min edge that leads to a new vertex
            Edge minEdge = pq.remove();
            while (!pq.isEmpty() && visited.contains(minEdge.getV())) {
                minEdge = pq.remove();
            }

            if (!visited.contains(minEdge.getV())) {
                // add vertex to mst path, add dest to visited
                mstpath.add(minEdge);
                Vertex v = minEdge.getV();
                visited.add(v);

                // add new edges to pq
                Map<Vertex, Integer> adj = g.getAdjacencies(minEdge.getV());
                for (Vertex dest : adj.keySet()) {
                    Integer weight = adj.get(dest);
                    if (!visited.contains(dest)) {
                        pq.add(new Edge(v, dest, weight));
                    }
                }
            }
        }

        return (mstpath.size() > 0) ? mstpath : null;
    }

}
