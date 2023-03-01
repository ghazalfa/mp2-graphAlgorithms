package cpen221.mp2.graph;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
import cpen221.mp2.graph.Vertex;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class testTask3 {

    @Test
    public void testCreateGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(e2, g.getEdge(v2, v3));
//        assertEquals(21, g.pathLength(g.shortestPath(v3, v4)));
    }

    // ---------------------------------------------------------------------------------
    // shortestPath

    @Test
    public void testShortestPathNormal() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 6);
        Edge<Vertex> eAD = new Edge<>(vA, vD, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 5);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 2);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eCE = new Edge<>(vC, vE, 5);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAD);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCE);
        g.addEdge(eDE);

        List<Vertex> expected = new ArrayList<>();
        expected.add(vA);
        expected.add(vD);
        expected.add(vE);
        expected.add(vC);

        List<Vertex> actual = g.shortestPath(vA, vC);

        assertEquals(expected, actual);
    }

    @Test
    public void testShortestPathNormal2() {
        // https://www.freecodecamp.org/news/dijkstras-shortest-path-algorithm-visual-introduction/#:~:text=You%20need%20to%20follow%20these,%3E%204%20%2D%20%3E%206%20automatically.
        Vertex v0 = new Vertex(0, "0");
        Vertex v1 = new Vertex(1, "1");
        Vertex v2 = new Vertex(2, "2");
        Vertex v3 = new Vertex(3, "3");
        Vertex v4 = new Vertex(4, "4");
        Vertex v5 = new Vertex(5, "5");
        Vertex v6 = new Vertex(6, "6");

        Edge<Vertex> e01 = new Edge<>(v0, v1, 2);
        Edge<Vertex> e02 = new Edge<>(v0, v2, 6);
        Edge<Vertex> e13 = new Edge<>(v1, v3, 5);
        Edge<Vertex> e23 = new Edge<>(v2, v3, 8);
        Edge<Vertex> e34 = new Edge<>(v3, v4, 10);
        Edge<Vertex> e35 = new Edge<>(v3, v5, 15);
        Edge<Vertex> e45 = new Edge<>(v4, v5, 6);
        Edge<Vertex> e46 = new Edge<>(v4, v6, 2);
        Edge<Vertex> e56 = new Edge<>(v5, v6, 6);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addEdge(e01);
        g.addEdge(e02);
        g.addEdge(e13);
        g.addEdge(e23);
        g.addEdge(e34);
        g.addEdge(e35);
        g.addEdge(e45);
        g.addEdge(e46);
        g.addEdge(e56);


        List<Vertex> expected = new ArrayList<>();
        expected.add(v0);
        expected.add(v1);
        expected.add(v3);
        expected.add(v4);
        expected.add(v6);

        List<Vertex> actual = g.shortestPath(v0, v6);

        assertEquals(expected, actual);
    }

    @Test
    public void testShortestPathNormal3() {
        // https://www.freecodecamp.org/news/dijkstras-shortest-path-algorithm-visual-introduction/#:~:text=You%20need%20to%20follow%20these,%3E%204%20%2D%20%3E%206%20automatically.
        Vertex v0 = new Vertex(0, "0");
        Vertex v1 = new Vertex(1, "1");
        Vertex v2 = new Vertex(2, "2");
        Vertex v3 = new Vertex(3, "3");
        Vertex v4 = new Vertex(4, "4");
        Vertex v5 = new Vertex(5, "5");
        Vertex v6 = new Vertex(6, "6");

        Edge<Vertex> e01 = new Edge<>(v0, v1, 2);
        Edge<Vertex> e02 = new Edge<>(v0, v2, 6);
        Edge<Vertex> e13 = new Edge<>(v1, v3, 5);
        Edge<Vertex> e23 = new Edge<>(v2, v3, 8);
        Edge<Vertex> e34 = new Edge<>(v3, v4, 10);
        Edge<Vertex> e35 = new Edge<>(v3, v5, 15);
        Edge<Vertex> e45 = new Edge<>(v4, v5, 6);
        Edge<Vertex> e46 = new Edge<>(v4, v6, 2);
        Edge<Vertex> e56 = new Edge<>(v5, v6, 6);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addEdge(e01);
        g.addEdge(e02);
        g.addEdge(e13);
        g.addEdge(e23);
        g.addEdge(e34);
        g.addEdge(e35);
        g.addEdge(e45);
        g.addEdge(e46);
        g.addEdge(e56);


        List<Vertex> expected = new ArrayList<>();
        expected.add(v0);
        expected.add(v1);
        expected.add(v3);
        expected.add(v5);

        List<Vertex> actual = g.shortestPath(v0, v5);

        assertEquals(expected, actual);
    }

    @Test
    public void testShortestPathNormal4() {
        // https://www.codingame.com/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/dijkstras-algorithm
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 3);
        Edge<Vertex> eAC = new Edge<>(vA, vC, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 7);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 5);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 1);
        Edge<Vertex> eCD = new Edge<>(vC, vD, 2);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAC);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCD);
        g.addEdge(eDE);

        List<Vertex> expected = new ArrayList<>();
        expected.add(vC);
        expected.add(vA);
        expected.add(vB);
        expected.add(vE);

        List<Vertex> actual = g.shortestPath(vC, vE);

        assertEquals(expected, actual);
    }

    @Test
    public void testShortestPathNoPath() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAD = new Edge<>(vA, vD, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 5);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eCE = new Edge<>(vC, vE, 5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAD);
        g.addEdge(eBC);
        g.addEdge(eBE);
        g.addEdge(eCE);

        List<Vertex> expected = new ArrayList<>();

        List<Vertex> actual = g.shortestPath(vA, vC);

        assertEquals(expected, actual);
    }

    @Test
    public void testShortestPathDisconnectedSourceAlone() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eBC = new Edge<>(vB, vC, 5);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 2);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eCE = new Edge<>(vC, vE, 5);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCE);
        g.addEdge(eDE);

        List<Vertex> expected = new ArrayList<>();

        List<Vertex> actual = g.shortestPath(vA, vE);

        assertEquals(expected, actual);
    }

    @Test
    public void testShortestPathDisconnectedSinkAlone() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 6);
        Edge<Vertex> eAD = new Edge<>(vA, vD, 1);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 2);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAD);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eDE);

        List<Vertex> expected = new ArrayList<>();

        List<Vertex> actual = g.shortestPath(vA, vC);

        assertEquals(expected, actual);
    }

    // ---------------------------------------------------------------------------------
    // path length

    @Test
    public void testPathLengthNormal1() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 6);
        Edge<Vertex> eAD = new Edge<>(vA, vD, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 5);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 2);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eCE = new Edge<>(vC, vE, 5);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAD);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCE);
        g.addEdge(eDE);

        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(vA);
        shortestPath.add(vD);
        shortestPath.add(vE);
        shortestPath.add(vC);

        assertEquals(7, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthNormal2() {
        // https://www.freecodecamp.org/news/dijkstras-shortest-path-algorithm-visual-introduction/#:~:text=You%20need%20to%20follow%20these,%3E%204%20%2D%20%3E%206%20automatically.
        Vertex v0 = new Vertex(0, "0");
        Vertex v1 = new Vertex(1, "1");
        Vertex v2 = new Vertex(2, "2");
        Vertex v3 = new Vertex(3, "3");
        Vertex v4 = new Vertex(4, "4");
        Vertex v5 = new Vertex(5, "5");
        Vertex v6 = new Vertex(6, "6");

        Edge<Vertex> e01 = new Edge<>(v0, v1, 2);
        Edge<Vertex> e02 = new Edge<>(v0, v2, 6);
        Edge<Vertex> e13 = new Edge<>(v1, v3, 5);
        Edge<Vertex> e23 = new Edge<>(v2, v3, 8);
        Edge<Vertex> e34 = new Edge<>(v3, v4, 10);
        Edge<Vertex> e35 = new Edge<>(v3, v5, 15);
        Edge<Vertex> e45 = new Edge<>(v4, v5, 6);
        Edge<Vertex> e46 = new Edge<>(v4, v6, 2);
        Edge<Vertex> e56 = new Edge<>(v5, v6, 6);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addEdge(e01);
        g.addEdge(e02);
        g.addEdge(e13);
        g.addEdge(e23);
        g.addEdge(e34);
        g.addEdge(e35);
        g.addEdge(e45);
        g.addEdge(e46);
        g.addEdge(e56);


        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(v0);
        shortestPath.add(v1);
        shortestPath.add(v3);
        shortestPath.add(v4);
        shortestPath.add(v6);

        assertEquals(19, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthNormal3() {
        // https://www.freecodecamp.org/news/dijkstras-shortest-path-algorithm-visual-introduction/#:~:text=You%20need%20to%20follow%20these,%3E%204%20%2D%20%3E%206%20automatically.
        Vertex v0 = new Vertex(0, "0");
        Vertex v1 = new Vertex(1, "1");
        Vertex v2 = new Vertex(2, "2");
        Vertex v3 = new Vertex(3, "3");
        Vertex v4 = new Vertex(4, "4");
        Vertex v5 = new Vertex(5, "5");
        Vertex v6 = new Vertex(6, "6");

        Edge<Vertex> e01 = new Edge<>(v0, v1, 2);
        Edge<Vertex> e02 = new Edge<>(v0, v2, 6);
        Edge<Vertex> e13 = new Edge<>(v1, v3, 5);
        Edge<Vertex> e23 = new Edge<>(v2, v3, 8);
        Edge<Vertex> e34 = new Edge<>(v3, v4, 10);
        Edge<Vertex> e35 = new Edge<>(v3, v5, 15);
        Edge<Vertex> e45 = new Edge<>(v4, v5, 6);
        Edge<Vertex> e46 = new Edge<>(v4, v6, 2);
        Edge<Vertex> e56 = new Edge<>(v5, v6, 6);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addEdge(e01);
        g.addEdge(e02);
        g.addEdge(e13);
        g.addEdge(e23);
        g.addEdge(e34);
        g.addEdge(e35);
        g.addEdge(e45);
        g.addEdge(e46);
        g.addEdge(e56);


        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(v0);
        shortestPath.add(v1);
        shortestPath.add(v3);
        shortestPath.add(v5);

        assertEquals(22, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthNormal4() {
        // https://www.codingame.com/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/dijkstras-algorithm
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 3);
        Edge<Vertex> eAC = new Edge<>(vA, vC, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 7);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 5);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 1);
        Edge<Vertex> eCD = new Edge<>(vC, vD, 2);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAC);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCD);
        g.addEdge(eDE);

        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(vC);
        shortestPath.add(vA);
        shortestPath.add(vB);
        shortestPath.add(vE);

        assertEquals(5, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthNoPath() {
        // https://www.codingame.com/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/dijkstras-algorithm
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 3);
        Edge<Vertex> eAC = new Edge<>(vA, vC, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 7);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 5);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 1);
        Edge<Vertex> eCD = new Edge<>(vC, vD, 2);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAC);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCD);
        g.addEdge(eDE);

        List<Vertex> shortestPath = new ArrayList<>();

        assertEquals(Integer.MAX_VALUE, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthOneElement() {
        // https://www.codingame.com/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/dijkstras-algorithm
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 3);
        Edge<Vertex> eAC = new Edge<>(vA, vC, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 7);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 5);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 1);
        Edge<Vertex> eCD = new Edge<>(vC, vD, 2);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 7);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAC);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCD);
        g.addEdge(eDE);

        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(vC);

        assertEquals(0, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthDisconnect() {
        // https://www.codingame.com/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/dijkstras-algorithm
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addEdge(eAB);

        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(vA);
        shortestPath.add(vB);
        shortestPath.add(vC);

        assertEquals(Integer.MAX_VALUE, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthVertexMissing() {
        // https://www.codingame.com/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/dijkstras-algorithm
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        //g.addVertex(vB);
        g.addVertex(vC);
        g.addEdge(eAB);

        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(vA);
        shortestPath.add(vB);   // this was not added to the graph
        shortestPath.add(vC);

        assertEquals(Integer.MAX_VALUE, g.pathLength(shortestPath));
    }

    @Test
    public void testPathLengthVertexMissing2() {
        // https://www.codingame.com/playgrounds/1608/shortest-paths-with-dijkstras-algorithm/dijkstras-algorithm
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 3);
        Edge<Vertex> eAC = new Edge<>(vA, vC, 3);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        //g.addVertex(vB);
        g.addVertex(vC);
        g.addEdge(eAB);
        g.addEdge(eAC);

        List<Vertex> shortestPath = new ArrayList<>();
        shortestPath.add(vA);
        shortestPath.add(vB);   // this was not added to the graph
        shortestPath.add(vC);

        assertEquals(Integer.MAX_VALUE, g.pathLength(shortestPath));
    }

    // ---------------------------------------------------------------------------------
    // getNeighbours

    @Test
    public void testGetNeighboursNormal1() {
        // https://www.freecodecamp.org/news/dijkstras-shortest-path-algorithm-visual-introduction/#:~:text=You%20need%20to%20follow%20these,%3E%204%20%2D%20%3E%206%20automatically.
        Vertex v0 = new Vertex(0, "0");
        Vertex v1 = new Vertex(1, "1");
        Vertex v2 = new Vertex(2, "2");
        Vertex v3 = new Vertex(3, "3");
        Vertex v4 = new Vertex(4, "4");
        Vertex v5 = new Vertex(5, "5");
        Vertex v6 = new Vertex(6, "6");

        Edge<Vertex> e01 = new Edge<>(v0, v1, 2);
        Edge<Vertex> e02 = new Edge<>(v0, v2, 6);
        Edge<Vertex> e13 = new Edge<>(v1, v3, 5);
        Edge<Vertex> e23 = new Edge<>(v2, v3, 8);
        Edge<Vertex> e34 = new Edge<>(v3, v4, 10);
        Edge<Vertex> e35 = new Edge<>(v3, v5, 15);
        Edge<Vertex> e45 = new Edge<>(v4, v5, 6);
        Edge<Vertex> e46 = new Edge<>(v4, v6, 2);
        Edge<Vertex> e56 = new Edge<>(v5, v6, 6);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addEdge(e01);
        g.addEdge(e02);
        g.addEdge(e13);
        g.addEdge(e23);
        g.addEdge(e34);
        g.addEdge(e35);
        g.addEdge(e45);
        g.addEdge(e46);
        g.addEdge(e56);


        Map<Vertex, Edge<Vertex>> expected = new HashMap<>();
        expected.put(v0, e01);
        expected.put(v1, e13);
        expected.put(v2, e23);

        Map<Vertex, Edge<Vertex>> actual = g.getNeighbours(v3, 8);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeighboursNormal2() {
        // https://www.freecodecamp.org/news/dijkstras-shortest-path-algorithm-visual-introduction/#:~:text=You%20need%20to%20follow%20these,%3E%204%20%2D%20%3E%206%20automatically.
        Vertex v0 = new Vertex(0, "0");
        Vertex v1 = new Vertex(1, "1");
        Vertex v2 = new Vertex(2, "2");
        Vertex v3 = new Vertex(3, "3");
        Vertex v4 = new Vertex(4, "4");
        Vertex v5 = new Vertex(5, "5");
        Vertex v6 = new Vertex(6, "6");

        Edge<Vertex> e01 = new Edge<>(v0, v1, 2);
        Edge<Vertex> e02 = new Edge<>(v0, v2, 6);
        Edge<Vertex> e13 = new Edge<>(v1, v3, 5);
        Edge<Vertex> e23 = new Edge<>(v2, v3, 8);
        Edge<Vertex> e34 = new Edge<>(v3, v4, 10);
        Edge<Vertex> e35 = new Edge<>(v3, v5, 15);
        Edge<Vertex> e45 = new Edge<>(v4, v5, 6);
        Edge<Vertex> e46 = new Edge<>(v4, v6, 2);
        Edge<Vertex> e56 = new Edge<>(v5, v6, 6);


        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v0);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addEdge(e01);
        g.addEdge(e02);
        g.addEdge(e13);
        g.addEdge(e23);
        g.addEdge(e34);
        g.addEdge(e35);
        g.addEdge(e45);
        g.addEdge(e46);
        g.addEdge(e56);


        Map<Vertex, Edge<Vertex>> expected = new HashMap<>();
        expected.put(v6, e56);
        expected.put(v4, e45);
        expected.put(v3, e35);
        expected.put(v1, e13);

        Map<Vertex, Edge<Vertex>> actual = g.getNeighbours(v5, 21);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeighboursDisconnected() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAD = new Edge<>(vA, vD, 1);
        Edge<Vertex> eBC = new Edge<>(vB, vC, 5);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eCE = new Edge<>(vC, vE, 5);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAD);
        g.addEdge(eBC);
        g.addEdge(eBE);
        g.addEdge(eCE);

        Map<Vertex, Edge<Vertex>> expected = new HashMap<>();
        expected.put(vD, eAD);

        Map<Vertex, Edge<Vertex>> actual = g.getNeighbours(vA, 420);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeighboursDisconnectedSourceAlone() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eBC = new Edge<>(vB, vC, 5);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 2);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eCE = new Edge<>(vC, vE, 5);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eBC);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eCE);
        g.addEdge(eDE);

        Map<Vertex, Edge<Vertex>> expected = new HashMap<>();

        Map<Vertex, Edge<Vertex>> actual = g.getNeighbours(vA, 420);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetNeighboursDisconnectedNoNeighbours() {
        // https://www.youtube.com/watch?v=pVfj6mxhdMw&ab_channel=SpanningTree
        Vertex vA = new Vertex(1, "A");
        Vertex vB = new Vertex(2, "B");
        Vertex vC = new Vertex(3, "C");
        Vertex vD = new Vertex(4, "D");
        Vertex vE = new Vertex(5, "E");

        Edge<Vertex> eAB = new Edge<>(vA, vB, 6);
        Edge<Vertex> eAD = new Edge<>(vA, vD, 1);
        Edge<Vertex> eBD = new Edge<>(vB, vD, 2);
        Edge<Vertex> eBE = new Edge<>(vB, vE, 2);
        Edge<Vertex> eDE = new Edge<>(vD, vE, 1);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(vA);
        g.addVertex(vB);
        g.addVertex(vC);
        g.addVertex(vD);
        g.addVertex(vE);
        g.addEdge(eAB);
        g.addEdge(eAD);
        g.addEdge(eBD);
        g.addEdge(eBE);
        g.addEdge(eDE);

        Map<Vertex, Edge<Vertex>> expected = new HashMap<>();

        Map<Vertex, Edge<Vertex>> actual = g.getNeighbours(vA, 0);

        assertEquals(expected, actual);
    }
}
