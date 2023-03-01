package cpen221.mp2.graph;

import java.util.*;

public class ALGraph<V extends Vertex, E extends Edge<V>> implements MGraph<V, E> {

    private List<V> listOfVertices;
    private List<E> listOfEdges;
    private Map<V, List<E>> mapOfALGraph;
    private int numVertices = 0;

    // Abstraction Function:
    //  a graph such that for any V, V.id maps to the keyset of mapOfALGraph
    //  The list stored in the value of mapOfALGraph.get(V.id) contains all E's that intersect
    //  with that vertex


    // rep invariant:
    // listOfVertices contains all and only the keyset in mapOfALGraph
    // listOfEdges contains all and only the edges in the valueset of mapOfALGraph
    //  for any V, 1 <= V.id <= maxVertices
    //  vertices does not contain V's with the same id
    //  edges does not contain duplicate entries

    /**
     * Create an empty graph in the form of an adjacency list
     */


    public ALGraph(){
        this.mapOfALGraph = new HashMap<V,List<E>>();
        this.listOfVertices = new ArrayList<>();
        this.listOfEdges = new ArrayList<>();

    }

    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return false if the vertex is null or its ID is already in included in the graph, otherwise
     * returns true and adds the vertex to the graph
     */
    @Override
    public boolean addVertex(V v) {

        for(V vertex: listOfVertices){
            if(vertex.id()==v.id()){
                return false;
            }
        }

        listOfVertices.add(v);
        numVertices += 1;
        mapOfALGraph.put(v,new ArrayList<E>());
        return true;

    }

    /**
     * Check if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    @Override
    public boolean vertex(V v) {

        if(listOfVertices.contains(v)){
            return true;
        }
        return false;
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * modifies:
     * @return true if the edge was successfully added and false
     * if the edge is between nonexistent vertices, if the edge already exists,
     * or if the edge connects itself
     */
    @Override
    public boolean addEdge(E e) {

        //checks to see if vertices are in the graph
        if(!(listOfVertices.contains(e.v1())&&listOfVertices.contains(e.v2()))){
            return false;
        }

        //checks if edge is already in graph
        if(listOfEdges.contains(e)==true){
            return false;
        }

        //checks if vertices are connected to themselves
        if(e.v1().equals(e.v2())){
            return false;
        }

        listOfEdges.add(e);

        //adds the vertex to the corresponding list of vertices in the graph
        mapOfALGraph.get(e.v1()).add(e);
        mapOfALGraph.get(e.v2()).add(e);

        return true;

    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graph and false otherwise
     */

    @Override
    public boolean edge(E e) {
        if(listOfEdges.contains(e)){
            return true;
        }
        return false;
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true of the v1-v2 edge is part of the graph and false otherwise
     */

    @Override
    public boolean edge(V v1, V v2) {

        for(E edges: listOfEdges){

            if(edges.v1().equals(v1)&&edges.v2().equals(v2) ){
                return true;
            }

            if(edges.v2().equals(v1)&&edges.v1().equals(v2)){
                return true;
            }
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
    @Override
    public int edgeLength(V v1, V v2) {

        //checks if edge is in the graph
        if(!edge(v1, v2)){
            throw new RuntimeException("Edge is not in the graph");
        }

        for(Edge edges:listOfEdges){
            if(edges.v1().id()==v1.id()&&edges.v2().id()==v2.id() ){
                return edges.length();
            }

            if(edges.v2().id()==v1.id()&&edges.v1().id()==v2.id()){
                return edges.length();
            }

        }
        return -1;
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    @Override
    public int edgeLengthSum() {
        int sum = 0;

        if(listOfEdges.size()==0){
            return sum;
        }

        for(Edge currentEdge: listOfEdges){
            sum += currentEdge.length();
        }

        return sum;
    }
    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return returns false if e is null or not in the graph and returns true
     * if e is in the graph. Also removes e from the graph.
     */

    @Override
    public boolean remove(E e) {
        if(e==null||!edge(e)){
            return false;
        }

        listOfEdges.remove(e);
        mapOfALGraph.get(e.v1()).remove(e);
        mapOfALGraph.get(e.v2()).remove(e);

        return true;

    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return returns false if v is null or not in the graph and returns true
     * if v is in the graph. Also removes v from the graph and any edges that were connected to v.
     */
    @Override
    public boolean remove(V v) {

        if(v==null||vertex(v)==false){
            return false;
        }


        listOfEdges.removeIf( (edge) -> edge.incident(v));
        mapOfALGraph.remove(v);

        //removes vertex 'v' from the list of vertexs apart of the ALGraph of each vertex
        E tempEdge;
        for(V vertex:listOfVertices){
            List temp = new ArrayList();
            temp = mapOfALGraph.get(vertex);
            if(temp!=null) {
                for (int i = 0; i < temp.size(); i++) {
                    tempEdge = (E) temp.get(i);
                    if (tempEdge.incident(v)) {
                        mapOfALGraph.get(vertex).remove(tempEdge);
                    }

                }
            }

        }

        listOfVertices.remove(v);
        numVertices -= 1;


        return true;
    }


    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return a set of all vertices in the graph, empty set is returned if no vertices
     */

    @Override
    public Set<V> allVertices() {

        Set<V> allVertices = new HashSet<>();

        for(V vertex: listOfVertices){
            V cloneV = (V)vertex.clone();
            allVertices.add(cloneV);
        }

        return allVertices;
    }

    /**
     * Obtain a set of all vertices incident on v.
     * Access to this set **should not** permit graph mutations.
     *
     * @param v the vertex of interest
     * @return a set containing all edges incident on v and an empty set if
     * vertex is connected to no edges
     */

    @Override
    public Set<E> allEdges(V v) {
        Set<E> allEdges = new HashSet<>();

        for(E edge: listOfEdges){
            if(edge.v1().id()==v.id()||edge.v2().id()==v.id()){
                E cloneOfEdge = (E)edge.clone();
                allEdges.add(cloneOfEdge);
            }
        }

        return allEdges;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return all edges in the graph
     */

    @Override
    public Set<E> allEdges() {
        Set<E> allEdges = new HashSet<>();

        for(E edge: listOfEdges){
            E cloneOfEdge = (E)edge.clone();
            allEdges.add(cloneOfEdge);
        }

        return allEdges;
    }

    public int getNumVertices() {
        return numVertices;
    }


    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map **should not** permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    @Override
    public Map<V, E> getNeighbours(V v) {

        //checks if vertex is in the graph
        if(!listOfVertices.contains(v)){
            throw new RuntimeException("Vertex is not in the graph");
        }

        Map<V,E> mapOfNeighbours = new HashMap<>();
        List<E> listOfNeighbours = mapOfALGraph.get(v);

        //returns an empty map if the vertex has no neighbors
        if(listOfNeighbours.size()==0){
            return mapOfNeighbours;
        }


        for(int i = 0; i<listOfNeighbours.size(); i++){
            E cloneOfEdge = null;
            V cloneOfVertex = null;

            cloneOfEdge = (E) listOfNeighbours.get(i).clone();
            mapOfNeighbours.put((V) cloneOfEdge.distinctVertex(v).clone(),cloneOfEdge);
        }

        return mapOfNeighbours;
    }

}