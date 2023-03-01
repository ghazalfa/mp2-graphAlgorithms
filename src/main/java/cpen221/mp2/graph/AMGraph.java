package cpen221.mp2.graph;

import java.util.*;

/**
 * A graph represented by an adjacency matrix
 */


public class AMGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {
    private int numVertices;
    private int maxVertices;
    private List<V> vertices;
    private List<E> edges;
    private int[][] graph;
    // Abstraction Function:
    //  a graph such that for any V, V.id = graph[V.id-1][] && V.id = graph[][V.id-1]
    //  graph[E.v1.id -1][E.v2.id -1] == E.length, and 0 if no E intersects two V's

    // rep invariant:
    //  for any V, 1 <= V.id <= maxVertices
    //  vertices does not contain V's with the same id
    //  edges does not contain duplicate entries
    //  for all 0 <= i < maxVertices, graph[i][i] == 0

    // TODO: should checkRep() be used like specified in the course readings
    // TODO: as in use checkRep() within methods of the class at the start/end as necessary


    /**
     * Create an empty graph with an upper-bound on the number of vertices
     * @param maxVertices is greater than 1
     */
    public AMGraph(int maxVertices) {
        this.maxVertices = maxVertices;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        graph = new int[maxVertices][maxVertices];
    }

    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false if the vertex id
     * already exists in the graph or if vertices exceed the max amount of the graph.
     * effects: adds the vertex to graph if the vertex is valid
     */
    public boolean addVertex(V v) {
        checkRep();
        if (numVertices + 1 > maxVertices) {
            return false;
        }
        // check if this vertex id already exists in the graph
        for (V vertex: vertices) {
            if (vertex.id() == v.id()) {
                return false;
            }
        }

        numVertices++;
        vertices.add(v);

        return true;
    }

    /**
     * Check if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    public boolean vertex(V v) {
        if (vertices.contains(v)) {
            return true;
        }
        return false;
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added and false
     * if the edge is between nonexistent vertices, if the edge already exists in the graph,
     * or if the edge connects itself
     * effects: adds the edge to graph if edge is valid
     */
    public boolean addEdge(E e) {
        // checks if the vertices exist in the graph
        if (!(vertices.contains(e.v2()) && vertices.contains(e.v1()))) {
            return false;
        }

        //check if edge is connecting itself
        if (e.v1() == e.v2()) {
            return false;
        }

        //check if edge already exists between these vertices
        if (graph[e.v2().id() - 1][e.v1().id()-1] != 0 ) {
            return false;
        }
        // add edge two both corresponding indexes in graph
        edges.add(e);
        graph[e.v2().id() - 1][e.v1().id()-1] = e.length();
        graph[e.v1().id() - 1][e.v2().id()-1] = e.length();
        return true;
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graph and false otherwise
     */
    public boolean edge(E e) {
        if (edges.contains(e)) {
            return true;
        }
        return false;
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true if the v1-v2 edge is part of the graph and false otherwise
     */

    public boolean edge(V v1, V v2) {
        if (graph[v1.id() - 1][v2.id()-1] != 0) {
            return true;
        }
        return false;
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 edge if this edge is part of the graph
     */
    public int edgeLength(V v1, V v2) {
        if (graph[v1.id() - 1][v2.id()-1] == 0) {
            throw new RuntimeException("This edge does not exist in the graph");
        }

        int edgeLength = graph[v1.id() - 1][v2.id()-1];
        return edgeLength;
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    public int edgeLengthSum() {
        int sum = 0;
        if (numVertices <= 1) {
            return sum;
        }
        // sum the entries below the diagonal of the matrix
        for (int i = 1; i <numVertices; i++) {
            for (int j = 0; j < i; j++) {
                sum += graph[i][j];
            }
        }
        return sum;
    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false if e is not in the graph
     *         note, if the two connected vertices of an edge match, the edge should
     *         be removed despite any difference in length (see Edge.java -> equals())
     * Effects: removes the edge from the graph.
     */
    public boolean remove(E e) {
        if (!edges.contains(e)) {
            return false;
        }
        edges.remove(e);
        graph[e.v2().id() - 1][e.v1().id()-1] = 0;
        graph[e.v1().id() - 1][e.v2().id()-1] = 0;
        return true;
    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false if v is not in the graph
     * Effects: removes the vertex and all incident edges from the graph.
     */
    public boolean remove(V v) {
        if (!vertices.contains(v)){
            return false;
        }

        // remove all the vertices with the same id as given
        vertices.removeIf((vertex) -> vertex.id() == v.id());

        // removing incident edges
        edges.removeIf( (edge) -> edge.incident(v));

        // vertex row and column back to 0
        for (int i = 0; i < numVertices; i++) {
            graph[v.id() - 1][i] = 0;
            graph[i][v.id() - 1] = 0;
        }
        return true;
    }


    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    public Set<V> allVertices() {
        Set<V> allVertices = new HashSet<>();

        for (V vertex: vertices) {
            V cloneV = (V)vertex.clone();
            allVertices.add(cloneV);
        }
        return allVertices;
    }

    /**
     * Obtain a set of all edges incident on v.
     * Access to this set **should not** permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    public Set<E> allEdges(V v) {
        HashSet<E> incidentEdges = new HashSet<>();
        for (E edge: edges) {
            if (edge.incident(v)) {
                E edge1 = (E)edge.clone();
                incidentEdges.add(edge1);
            }
        }
        return incidentEdges;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return all edges in the graph
     */
    public Set<E> allEdges() {
        Set<E> allEdges = new HashSet<>();

        for (E edge: edges) {
            E edgeClone = (E)edge.clone();
            allEdges.add(edgeClone);
        }
        return allEdges;
    }

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map **should not** permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    public Map<V, E> getNeighbours(V v) {
        HashMap<V,E> mapOfNeighbors = new HashMap<>();
        int i = v.id() - 1;

        for (int j = 0; j < maxVertices; j++) {
            if (graph[i][j] != 0) {
                int neighborVertex = j + 1;
                V vertexClone = null;
                E edgeClone = null;
                for (V vertex: vertices) {
                    if (vertex.id() == neighborVertex) {
                        vertexClone = (V)vertex.clone();
                    }
                }
                for (E edge: edges) {
                    if (edge.incident(v) && edge.incident(vertexClone)) {
                        edgeClone = (E)edge.clone();
                    }
                }
                mapOfNeighbors.put(vertexClone, edgeClone);
            }
        }

        return mapOfNeighbors;
    }

    private void checkRep() {
        for (V vertex: vertices) {
            if (vertex.id() < 0 || vertex.id() > maxVertices) {
                throw new RuntimeException("V id out of bounds");
            }
        }

        for (E edge: edges) {
            if (graph[edge.v1().id() - 1][edge.v2().id() - 1] != edge.length() ||
                    graph[edge.v2().id() - 1][edge.v1().id() - 1] != edge.length()) {
                throw new RuntimeException("edge incorrectly stored");
            }
        }
    }

}