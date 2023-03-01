package cpen221.mp2.graph;

import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class testTask4 {

@Test
    public void disconnetedGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");

        Edge<Vertex> e2 = new Edge<>(v2, v3, 3);
        Edge<Vertex> e3 = new Edge<>(v1, v3, 2);
        Edge<Vertex> e4 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e5 = new Edge<>(v5, v4, 4);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        
        Vertex testA = g.getCenter();
        assertTrue(testA.id() ==v1.id());
        int testB = g.diameter();
        assertTrue(testB==3);

        Set<ImGraph<Vertex, Edge<Vertex>>> test2 = g.minimumSpanningComponents(2);
        assertTrue(test2.size()==2);
        Set<ImGraph<Vertex, Edge<Vertex>>> test3 = g.minimumSpanningComponents(3);
        assertTrue(test3.size()==3);
    }

        @Test
        public void connectedGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Vertex v5 = new Vertex(5, "E");
        Vertex v6 = new Vertex(6, "F");
        Vertex v7 = new Vertex(7, "G");
        Vertex v8 = new Vertex(8, "H");
        Vertex v9 = new Vertex(9, "I");
        Vertex v10 = new Vertex(10, "J");

        Edge<Vertex> e2 = new Edge<>(v2, v1, 1);
        Edge<Vertex> e3 = new Edge<>(v2, v3, 8);
        Edge<Vertex> e4 = new Edge<>(v1, v8, 0);
        Edge<Vertex> e5 = new Edge<>(v1, v7, 3);
        Edge<Vertex> e6 = new Edge<>(v7, v9, 3);
        Edge<Vertex> e7 = new Edge<>(v6, v9, 4);
        Edge<Vertex> e8 = new Edge<>(v6, v7, 6);
        Edge<Vertex> e9 = new Edge<>(v2, v6, 1);
        Edge<Vertex> e10 = new Edge<>(v6, v10, 14);
        Edge<Vertex> e11 = new Edge<>(v3, v10, 20);
        Edge<Vertex> e12 = new Edge<>(v3, v5, 2);
        Edge<Vertex> e13 = new Edge<>(v6, v5, 9);
        Edge<Vertex> e14 = new Edge<>(v7, v8, 11);
        Edge<Vertex> e15 = new Edge<>(v4, v5, 8);
        Edge<Vertex> e16 = new Edge<>(v3, v4, 10);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addVertex(v6);
        g.addVertex(v7);
        g.addVertex(v8);
        g.addVertex(v9);
        g.addVertex(v10);

        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);
        g.addEdge(e5);
        g.addEdge(e6);
        g.addEdge(e7);
        g.addEdge(e8);
        g.addEdge(e9);
        g.addEdge(e10);
        g.addEdge(e11);
        g.addEdge(e12);
        g.addEdge(e13);
        g.addEdge(e14);
        g.addEdge(e15);
        g.addEdge(e16);

        Set<ImGraph<Vertex, Edge<Vertex>>> test1 = g.minimumSpanningComponents(1);
        Graph<Vertex, Edge<Vertex>> graph1 = null;
        for(ImGraph<Vertex, Edge<Vertex>> graph: test1) {
            graph1 = (Graph<Vertex, Edge<Vertex>>)graph;
        }
        //mst
        assertTrue(test1.size()==1);
        assertTrue(graph1.edge(e2));
        assertTrue(graph1.edge(e3));
        assertTrue(graph1.edge(e4));
        assertTrue(graph1.edge(e5));
        assertTrue(graph1.edge(e6));
        assertTrue(graph1.edge(e9));
        assertTrue(graph1.edge(e10));
        assertTrue(graph1.edge(e12));
        assertTrue(graph1.edge(e15));
        assertFalse(graph1.edge(e7));
        assertFalse(graph1.edge(e8));
        assertFalse(graph1.edge(e11));
        assertFalse(graph1.edge(e13));
        assertFalse(graph1.edge(e14));
        assertFalse(graph1.edge(e16));

        //k>=2
            Set<ImGraph<Vertex, Edge<Vertex>>> test2 = g.minimumSpanningComponents(2);
            assertTrue(test2.size()==2);
            for(ImGraph<Vertex, Edge<Vertex>> graph: test2) {
                graph1 = (Graph<Vertex, Edge<Vertex>>)graph;
                assertFalse(graph1.edge(e10));
            }

            Set<ImGraph<Vertex, Edge<Vertex>>> test3 = g.minimumSpanningComponents(3);
            assertTrue(test3.size()==3);
            for(ImGraph<Vertex, Edge<Vertex>> graph: test3) {
                graph1 = (Graph<Vertex, Edge<Vertex>>)graph;
                assertFalse(graph1.edge(e3) && graph1.edge(e15));
            }

            Set<ImGraph<Vertex, Edge<Vertex>>> testAll = g.minimumSpanningComponents(10);
            assertTrue(testAll.size()==10);
            for(ImGraph<Vertex, Edge<Vertex>> graph: testAll) {
                graph1 = (Graph<Vertex, Edge<Vertex>>)graph;
                assertFalse(graph1.edge(e2));
                assertFalse(graph1.edge(e3));
                assertFalse(graph1.edge(e4));
                assertFalse(graph1.edge(e5));
                assertFalse(graph1.edge(e6));
                assertFalse(graph1.edge(e9));
                assertFalse(graph1.edge(e10));
                assertFalse(graph1.edge(e12));
                assertFalse(graph1.edge(e15));
            }

    }

    @Test
    public void testNoCyclesForDiameterAndCenter() {
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

        Vertex testA = g.getCenter();
        assertTrue(testA.id() ==v1.id());
        int testB = g.diameter();
        assertTrue(testB==21);

    }

}

