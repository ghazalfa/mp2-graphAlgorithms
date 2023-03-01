package cpen221.mp2.graph;

import java.util.*;

import static java.lang.Integer.MAX_VALUE;

    // Abstraction Function:
    //  creates a graph so that all V's and E's map to the graph object
    //  The list stored in the value of mapOfALGraph.get(V.id) contains all E's that intersect
    //  with that vertex


    // rep invariant:
    //  for any V, 1 <= V.id <= maxVertices
    //  vertices does not contain V's with the same id
    //  edges does not contain duplicate entries

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */

public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V,E>, MGraph<V, E> {

    private final ALGraph<V, E> graph;

    public Graph() {
        this.graph = new ALGraph<>();
    }
    public Graph(ALGraph<V,E> graph) {
       this.graph = graph;
   }

    /**
     * Find the edge that connects two vertices if such an edge exists.
     * This method should not permit graph mutations.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2
     */
    public E getEdge(V v1, V v2) {
        if (graph.edge(v1, v2)) {
            Set<E> incidentEdges = graph.allEdges(v1);

            for (E edge: incidentEdges) {
                if (edge.incident(v2)) {
                    return edge;
                }
            }

        }
        return null;
    }

    /**
     * Compute the shortest path from source to sink
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink (both end points are part of the list)
     *         when the sink is the source, return a single element list of the source (same as sink)
     *         when there is no path from sink to source, return an empty list
     */
    public List<V> shortestPath(V source, V sink) {
        if (source.equals(sink)) {
            List<V> l = new ArrayList<>();
            l.add(source);
            return l;
        }

        // Create local class to store
        // Fields: Vertex to go to, Shortest distance from source, Previous vertex to get to this one
        class Node {
            public final V toVertex;
            public int shortestDistanceToSource;
            public V previousVertex;

            Node(V vertex, int distance) {
                this.toVertex = vertex;
                this.shortestDistanceToSource = distance;
            }
        }

        // Initialize a list of nodes where the source has a distance of 0
        // and all other nodes have a shortest distant from source of infinity
        // this is so that any shorter path will overwrite the initial shortest path distance
        // All previous vertices are null/uninitialized fields
        List<Node> unvisited = new ArrayList<>();
        for (V v : allVertices()) {
            if (v.equals(source)) {
                unvisited.add(new Node(v, 0));
            } else {
                unvisited.add(new Node(v, MAX_VALUE));
            }
        }
        List<Node> allNodes = new ArrayList<>(unvisited);

        // Update the fields of the nodes until all nodes have been visited
        // TODO: can optimize here by ending while loop when the sink vertex is reached
        // TODO: since this is a greedy algorithm, the shortest path will be found on the first appearance of the sink vertex
        while (unvisited.size() != 0) {
            // Go to the unvisited node with the smallest distance from the start node
            Node currentNode = unvisited.get(0);
            for (Node n : unvisited) {
                if (n.shortestDistanceToSource < currentNode.shortestDistanceToSource) {
                    currentNode = n;
                }
            }

            // For each unvisited neighbour of the currentNode (currentNode the best path so far, because greedy algorithm)
            // Calculate distance from source and update shortest distance and previous vertex if shorter path found
            // Note, we do not loop through visited nodes since anything that has been visited has been calculated optimally already
            Map<V,E> neighbours = new HashMap<>();
            neighbours = graph.getNeighbours(currentNode.toVertex);
            for (Map.Entry<V, E> currentNeighbouringKeyValue : neighbours.entrySet()) {
                // By the nature of getNeighbours(), we get back a Map of the neighbouring vertices to the currentNode
                // Get node associated with each vertex of the Map
                Node currentNeighbouringNode = null;    // this is a neighbour of the "currentNode"
                for (Node n : unvisited) {
                    if (n.toVertex.equals(currentNeighbouringKeyValue.getKey())) {
                        currentNeighbouringNode = n;
                        break;
                    }
                }
                // If there exists a neighbour to update
                // Again, this is the greedy algorithm that updates only if a shorter path is found from the neighbours
                if (currentNeighbouringNode != null) {
                    // Shortest distance to neighbouring vertex =
                    //      shortest distance to get to currentNode
                    //      +
                    //      length of edge between currentNode's vertex and neighbouring vertex
                    int edgeLength = graph.edgeLength(currentNode.toVertex, currentNeighbouringNode.toVertex);
                    int newDistance = currentNode.shortestDistanceToSource + edgeLength;
                    // Update previous node which will be used to find the path of vertices
                    if (newDistance < currentNeighbouringNode.shortestDistanceToSource) {
                        currentNeighbouringNode.shortestDistanceToSource = newDistance;
                        currentNeighbouringNode.previousVertex = currentNode.toVertex;
                    }
                }
            }

            // Remove currentNode from unvisited
            unvisited.remove(currentNode);
        }

        // Use the previous vertex field in the nodes to return the shortest path of vertices
        // Traverses the previous vertices until starting from sink and ending at source
        // Add the elements to the front/start so that steps are in forward order
        List<V> pathSteps = new ArrayList<>();
        Vertex previous = null; // the previous vertex we are looking for
        // Find previous vertex of sink
        for (Node n : allNodes) {
            if (n.toVertex.equals(sink)) {

                // Check for the case when the sink is disconnected
                if (n.previousVertex == null) {
                    return new ArrayList<V>();
                }

                pathSteps.add(0, n.toVertex);
                previous = n.previousVertex;
                break;
            }
        }
        // Find all previous vertices and stop once you reach source
        // or break out early inside using the logic when the previous is null meaning the path is disconnected
        while (previous !=null && !previous.equals(source)) {
            for (Node n : allNodes) {
                if (n.toVertex.equals(previous)) {
                    // Logic for no shortest path
                    // If the previous vertex is null but the source hasn't been reached
                    // that means there is a break in the path or no path
                    if (n.previousVertex == null) {
                        return new ArrayList<V>();
                    }

                    // Logic for updating previous and path
                    pathSteps.add(0, n.toVertex);
                    previous = n.previousVertex;
                    break;
                }
            }
        }
        // We broke out of while loop before adding the source
        pathSteps.add(0, source);

        return pathSteps;
    }

    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path
     * @return the length of path
     *         return path length of 0 when there is only 1 vertex in the path
     *         return path length of infinity when the path is empty, or
     *         if a vertex in path does not exist or
     *         if the edge between two subsequent vertices in the path does not exist
     */
    public int pathLength(List<V> path) {
        int totalLength = 0;

        // If the path is disconnected, shortestPath would have returned an empty list of steps
        // This implies a path of infinite length which we represent with Integer.MAX_VALUE
        if (path.size() == 0) {
            return Integer.MAX_VALUE;
        }

        // Check that the path exists in the graph
        // If any of the vertices are not in the graph, return a path of infinite length
        // or if the edge between two subsequent vertices in the path does not exist
        if (!(vertex(path.get(0)))) {
            return Integer.MAX_VALUE;
        }
        for (int i = 1; i < path.size(); i++) {
            if (!(vertex(path.get(i)))) {
                return Integer.MAX_VALUE;
            }
            if (!(edge(path.get(i),path.get(i-1)))) {
                return Integer.MAX_VALUE;
            }
        }

        // Case when source = sink and the path is 1 element with a distance of 0
        if (path.size() == 1) {
            return 0;
        }

        for (int i = 1; i < path.size(); i++) {
            totalLength += graph.edgeLength(path.get(i), path.get(i - 1));
        }
        return totalLength;
    }

    /**
     * Obtain all vertices w that are no more than a <em>path distance</em> of range from v.
     *
     * @param source the vertex to start the search from.
     * @param range the radius of the search (range is inclusive)
     * @return a map where the keys are the vertices in the neighbourhood of v,
     *          and the value for key w is the last edge on the shortest path
     *          from v to w.
     *          Note, the map will be empty when there are no neighbours
     */
     public Map<V, E> getNeighbours(V source, int range) {
         Map<V, E> neighbours = new HashMap<>();
         for (V sink : graph.allVertices()) {
             List<V> currentPath = shortestPath(source, sink);
             if (currentPath.size() > 1) {
                 // Note, edge we are looking at is between neighbour vertex (sink)
                 // and the last vertex on the shortest path (before the sink)
                 if (pathLength(currentPath) <= range) {
                    neighbours.put(sink, getEdge( sink, currentPath.get(currentPath.size() - 2)));
                 }

                 // In the above logic block, before it accounted for the edge not existing
                 // But the shortestPath() should return an empty list if the path doesn't exist
                 // So that case is dealt with
                 // This is correct because that logic should be dealt with in the shortestPath() method itself, encapsulation
                 // Before:
                 // if the edge does not exist, it means that the graph is disconnected there
                 // if (edge(sink, currentPath.get(currentPath.size() - 2))) {
             }
         }
         return neighbours;
     }

    /**
     * Return a set with k connected components of the graph.
     *
     * <ul>
     * <li>When k = 1, the method returns one graph in the set, and that graph
     * represents the minimum spanning tree of the graph.
     * See: https://en.wikipedia.org/wiki/Minimum_spanning_tree</li>
     *
     * <li>When k = n, where n is the number of vertices in the graph, then
     * the method returns a set of n graphs, and each graph contains a
     * unique vertex and no edge.</li>
     *
     * <li>When k is in [2, n-1], the method partitions the graph into k connected sub-graphs
     * such that for any two vertices V_i and V_j, if vertex V_i is in subgraph
     * G_a and vertex V_j is in subgraph G_b (a != b), and there is an edge
     * between V_i and V_j, and |G_a| > 1, then there must exist some vertex V_k in G_a such
     * that the length of the edge between V_i and V_k is at most the length
     * of the edge between V_i and V_j.</li>
     * </ul>
     *
     * @return a set of graph partitions such that a vertex in one partition
     * is no closer to a vertex in a different partition than it is to a vertex
     * in its own partition.
     */
    public Set<ImGraph<V, E>> minimumSpanningComponents(int k) {
        int numVertices = graph.getNumVertices();
        List<V> vertices = new ArrayList<>(graph.allVertices());

        // sort edges in ascending order based on edge length
        PriorityQueue<E> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getLength));
        pq.addAll(graph.allEdges());

        // sort edges in descending order based on edge length, for removing edges
        PriorityQueue<E> reversePq = new PriorityQueue<>( (e1, e2) -> e2.length() - e1.length());

        Graph<V, E> minSpanningTree = new Graph<>(); //empty spanning tree where our edges and vertices will be placed

        do {
            if (pq.size() == 0) { //add independent vertices
                for (V vertex: vertices){
                    minSpanningTree.addVertex(vertex);
                }
                continue;
            }
            E edge = pq.poll(); //choose the shortest edge ( poll from front end of priority queue)

            //check if adding this edge and its vertices will create a cycle (represent temp tree)
            boolean addV1 = minSpanningTree.addVertex(edge.v1());
            boolean addV2 = minSpanningTree.addVertex(edge.v2());
            boolean addEdge =minSpanningTree.addEdge(edge);

            //if cycle exists, remove successfully added components
            if (minSpanningTree.isCyclic(numVertices)) {
                minSpanningTree.remove(edge);
                if (addV1) {
                    minSpanningTree.remove(edge.v1());
                }
                if (addV2) {
                    minSpanningTree.remove(edge.v2());
                }
            } else {
                if (addEdge) {
                    reversePq.add(edge); //add edge to the second priority queue
                }
            }

        } while (minSpanningTree.graph.getNumVertices() < numVertices); //do while not all vertices have been connected
        //mst found!
        Set<ImGraph<V, E>> minimumSpanningComponents = minSpanningTree.connectedComponents(numVertices);

        //Separate tree into components until k target components reached
        while (minimumSpanningComponents.size()!= k) {
            E longEdge = reversePq.poll(); //get longest edge in spanning tree
            Set<ImGraph<V, E>> subComponents = null;
            //iterate through the set to find graph containing this edge
            Iterator<ImGraph<V, E>> iterator = minimumSpanningComponents.iterator();
            while (iterator.hasNext()) {
                Graph<V, E> graphCheck = (Graph)iterator.next();
                if (graphCheck.edge(longEdge)) {
                    iterator.remove(); // remove from hashSet to be split
                    //remove long edge
                    graphCheck.remove(longEdge);
                    subComponents = graphCheck.connectedComponents(numVertices);

                }
            }
            minimumSpanningComponents.addAll(subComponents);
        }

        return minimumSpanningComponents;
    }

    /**
     * A recursive function that takes an array of visited vertices and parent to detect cycle in subgraph reachable from vertex v.
     * @param v current vertex
     * @param visited whether the vertices in the graph have been visited or not
     * @param parent previously visited vertex
     * @return
     */
    private Boolean isCyclicUtil(V v, Boolean[] visited, V parent) {
        // Mark the current node as visited
        visited[v.id()-1] = true;
        int i;

        // Recur for all the vertices adjacent to this vertex
        Map<V,E> neighbors = graph.getNeighbours(v);
        for (V neighbor: neighbors.keySet()) {
            i = neighbor.id();

            // If an adjacent is not visited, then recur for that adjacent
            if (!visited[i-1]) {
                if (isCyclicUtil(neighbor, visited, v))
                    return true;
            }

            // If an adjacent is visited and not parent of current vertex, then there is a cycle.
            else if (i != parent.id())
                return true;
        }
        return false;
    }

    /**
     * Check if there is a cycle in an undirected graph
     * @param numVertices number of distinct vertices in the graph
     * @return true if the graph contains a cycle, else false
     */
    private Boolean isCyclic(int numVertices)
    {

        // Mark all the vertices as not visited and not part of recursion stack
        Boolean[] visited = new Boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        // Call the recursive helper function to detect cycle in different DFS trees
        for (V vertex: graph.allVertices()) {

            // Don't recur for u if already visited
            if (!visited[vertex.id()-1])
                if (isCyclicUtil(vertex, visited, null))
                    return true;
        }

        return false;
    }

    /**
     * Depth-first search to reach all the components of a connected graph
     * @param v
     * @param visited whether a vertex has been visited or not
     * @param component an empty graph to add traversed vertices and edges to,
     *                  modifies: component graph
     */
    private void DFSUtil(V v, boolean[] visited, Graph<V, E> component)
    {   //fill component with subgraph
        // Mark the current node as visited and print it
        visited[v.id() - 1] = true;
        // Recur for all the vertices
        // adjacent to this vertex
        Map<V,E> neighbors = graph.getNeighbours(v);
        for (V neighbor: neighbors.keySet()) {
            if (!visited[neighbor.id() - 1]) {
                component.addVertex(neighbor);
                for (E edge : graph.allEdges()) {

                    if (edge.v1().equals(v) && edge.v2().equals(neighbor) ||
                            edge.v1().equals(neighbor) && edge.v2().equals(v)) {
                        component.addEdge(edge);
                        break;
                    }
                }
                DFSUtil(neighbor, visited, component);
            }
        }
    }

    /**
     * Get all the connected components of a graph.
     * @param numVertices number of vertices in the graph
     * @return A set of graphs in which within each graph, the components are connected
     */
    private Set<ImGraph<V, E>> connectedComponents(int numVertices)
    {
        Set<ImGraph<V, E>> connectedComponents = new HashSet<>();
        // Mark all the vertices as not visited
        boolean[] visited = new boolean[numVertices];
        for (V vertex: graph.allVertices()) {
            if (!visited[vertex.id()-1]) {
                // print all reachable vertices
                // from v
                Graph<V, E> component = new Graph<>();
                component.addVertex(vertex);
                DFSUtil(vertex, visited, component);
                connectedComponents.add(component);
            }
        }
        return connectedComponents;
    }

    /**
     * Get the largest connected component of a graph
     * @return a graph which is a connected component of the original graph that
     * contains the largest number of vertices
     */
    public Graph<V,E> largest(){
        Set<ImGraph<V,E>> componentsOfGraph = new HashSet<>();

        componentsOfGraph = this.connectedComponents(this.allVertices().size());
        Graph<V,E> largestComponent = new Graph<>();

        for(ImGraph<V, E> temp: componentsOfGraph){
            Graph<V, E> graphCheck = (Graph)temp;
            if(graphCheck.allVertices().size()>largestComponent.allVertices().size()){
                largestComponent = graphCheck;
            }

        }
        return largestComponent;
    }


    /**
     * Compute the diameter of the graph.
     *
     * The diameter of a graph is the length of the longest shortest path in the graph.
     * If a graph has multiple components then we will define the diameter
     * as the diameter of the largest component.
     *
     *
     * @return the diameter of the graph.
     */
    public int diameter() {


        Graph<V,E> largestComponent = new Graph<>();
        largestComponent = this.largest();


        List<V> vertices = new ArrayList<>(largestComponent.allVertices());
        List<List<V>> allPossiblePairs = new ArrayList<>();
        int size = 0;
        int diameter = 0;
        int count = 0;

        //makes a list of all possible pairs of vertices in the graph
        for (int i = 0; i < vertices.size(); i++) {
            for (int j = i+1; j < vertices.size(); j++) {
                    allPossiblePairs.add(count, new ArrayList<>());
                    allPossiblePairs.get(count).add(vertices.get(i));
                    allPossiblePairs.get(count).add(vertices.get(j));
                    count++;
            }
        }

        for (List<V> allPossiblePair : allPossiblePairs) {
            //finds length of the shortest path of the given pair
            size = pathLength(shortestPath(allPossiblePair.get(0), allPossiblePair.get(1)));
            //stores largest shortest path in diameter
            if (size > diameter) {
                diameter = size;
            }
        }

        return diameter;

    }


    /**
     * Compute the center of the graph.
     *
     *
     * For a vertex s, the eccentricity of s is defined as the maximum distance
     * between s and any other vertex t in the graph.
     *
     * The center of a graph is the vertex with minimum eccentricity.
     *
     * If a graph is not connected, we will define the graph's center to be the
     * center of the largest connected component.
     *
     * @return the center of the graph.
     */

    public V getCenter() {


        Graph<V,E> largestComponent = new Graph<>();
        largestComponent = this.largest();

        List<V> vertices = new ArrayList<>(largestComponent.allVertices());
        Map<V,Integer> eccentricity = new HashMap<>();
        for(int i = 0; i<vertices.size(); i++){
            eccentricity.put(vertices.get(i),0);
        }
        int length;
        int smallest = MAX_VALUE;
        V smallestV = null;



        //make map of eccentricity of each vertex by finding the smallest shortest path of each vertex
        for(int i = 0; i<vertices.size(); i++){
            for(int m = 0; m<vertices.size(); m++){
                length = pathLength(shortestPath(vertices.get(i),vertices.get(m)));
                if(length>eccentricity.get(vertices.get(i))){
                    eccentricity.replace(vertices.get(i),length);
                }
            }

        }

        //vertex with the smallest eccentricity is the centre
        for(V currentVertex:vertices){
            if(eccentricity.get(currentVertex)<smallest){
                smallest = eccentricity.get(currentVertex);
                smallestV = currentVertex;
            }
        }

        return smallestV;



    }

    //// add all new code above this line ////

    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement allVertices() and allEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the cpen221.mp2.graph.Edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.allVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.allEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            remove(trim);
        }
    }



    // Methods using composition of the ALGraph object as a field

    @Override
    public boolean addVertex(V v) {
        return graph.addVertex(v);
    }

    @Override
    public boolean vertex(V v) {
        return graph.vertex(v);
    }

    @Override
    public boolean addEdge(E e) {
        return graph.addEdge(e);
    }

    @Override
    public boolean edge(E e) {
        return graph.edge(e);
    }

    @Override
    public boolean edge(V v1, V v2) {
        return graph.edge(v1, v2);
    }

    @Override
    public int edgeLength(V v1, V v2) {
        return graph.edgeLength(v1, v2);
    }

    @Override
    public int edgeLengthSum() {
        return graph.edgeLengthSum();
    }

    @Override
    public boolean remove(E e) {
        return graph.remove(e);
    }

    @Override
    public boolean remove(V v) {
        return graph.remove(v);
    }

    @Override
    public Set<V> allVertices() {
        return graph.allVertices();
    }

    @Override
    public Set<E> allEdges(V v) {
        return graph.allEdges(v);
    }

    @Override
    public Set<E> allEdges() {
        return graph.allEdges();
    }

    @Override
    public Map<V, E> getNeighbours(V v) {
        return graph.getNeighbours(v);
    }

    }


