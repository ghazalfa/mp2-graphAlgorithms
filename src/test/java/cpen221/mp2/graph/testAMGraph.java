package cpen221.mp2.graph;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class testAMGraph {

    // ---------------------------------------------------------------------------------
    // Adding vertices
    @Test
    public void testAddVerticesNormal() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(40);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertTrue(g.addVertex(v4));
    }

    @Test
    public void testAddVerticesOverMax() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertFalse(g.addVertex(v4));
    }

    @Test
    public void testAddVertexDuplicateId() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(2, "C");

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);

        assertFalse(g.addVertex(v3));
    }

    @Test
    public void testAddVertexDuplicateName() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "B");

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);

        assertTrue(g.addVertex(v3));
    }

    @Test
    public void testCheckExistsVerticesOverMax() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertFalse(g.vertex(v4));
    }

    @Test
    public void testCheckExistsVertexDuplicateName() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "B");

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);

        assertTrue(g.vertex(v3));
    }

    // ---------------------------------------------------------------------------------
    // Adding edges
    @Test
    public void testAddEdgeNormal() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        assertTrue(g.addEdge(e3));
    }

    @Test
    public void testAddEdgeNoLength() {
        // This is valid behaviour, see Edge.java
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 0);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        assertTrue(g.addEdge(e3));
    }

    @Test
    public void testAddEdgeNegativeLength() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        try {
            Edge<Vertex> e3 = new Edge<>(v1, v4, -2);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        fail();
    }

    @Test
    public void testAddThreeEdgesFromOneVertex() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 1);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        assertTrue(g.addEdge(e3));
    }

    @Test
    public void testAddEdgeToVertexNotInGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        // Do not add v2 to the graph

        assertFalse(g.addEdge(e1));
    }

    @Test
    public void testAddDuplicateEdge() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v2, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.addEdge(e2));
    }

    @Test
    public void testAddDuplicateEdgeDifferentLength() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v2, 5);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.addEdge(e2));
    }

    @Test
    public void isEdge() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        assertTrue(g.edge(e1));
        assertTrue(g.edge(v1, v2));
        assertFalse(g.edge(v2, v4));
    }

    // ---------------------------------------------------------------------------------
    // Get all edges and vertices
    @Test
    public void testGetAllVertices() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        g.remove(e1);
        g.remove(e3);

        Set<Vertex> expected = new HashSet<>();
        expected.add(v1);
        expected.add(v2);
        expected.add(v3);
        expected.add(v4);

        assertEquals(expected, g.allVertices());
    }

    @Test
    public void testGetAllEdges() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge<Vertex>> expected = new HashSet<>();
        expected.add(e1);
        expected.add(e2);
        expected.add(e3);

        assertEquals(expected, g.allEdges());
    }

    @Test
    public void testGetAllEdgesWithParam() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        Set<Edge<Vertex>> expected = new HashSet<>();
        expected.add(e3);

        assertEquals(expected, g.allEdges(v4));
    }

    // ---------------------------------------------------------------------------------
    // Removing edges
    @Test
    public void testRemoveEdgesNormal() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        g.remove(e1);
        g.remove(e3);

        Set<Edge<Vertex>> expected = new HashSet<>();
        expected.add(e2);

        assertEquals(expected, g.allEdges());
    }

    @Test
    public void testRemoveEdgeNotInGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertFalse(g.remove(e2));
    }

    @Test
    public void testRemoveEdgeGivenNewEdgeOfDifferentLengthButSameVertices() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v2, 99);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        assertTrue(g.remove(e2));
    }

    @Test
    public void testRemoveSameEdgeTwice() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);
        g.remove(e1);

        assertFalse(g.remove(e1));
    }

    @Test
    public void testRemoveEdgeToDisconnectVertex() {
        // When you remove an edge, you do not remove the vertices
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);
        g.remove(e1);

        assertTrue(g.vertex(v1) && g.vertex(v2));
    }

    // ---------------------------------------------------------------------------------
    // Removing vertices
    @Test
    public void TestRemoveVerticesAndDisconnectedEdges() {
        // When you remove a vertex, you have to remove
        // all the edges incident on that vertex
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 7);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        g.remove(v1);

        assertFalse(        g.edge(e1) || g.edge(e2));
    }

    @Test
    public void testRemoveVertexNotInGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addEdge(e1);

        assertFalse(g.remove(v2));
    }

    @Test
    public void testRemoveSameVertexTwice() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(2);
        g.addVertex(v1);
        g.addVertex(v2);
        g.remove(v1);

        assertFalse(g.remove(v1));
    }


    // ---------------------------------------------------------------------------------
    // Test total length
    @Test
    public void testTotalEdgeLengthNoEdgeEmptyGraph() {

        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);

        assertEquals(12, g.edgeLengthSum());
    }

    @Test
    public void testTotalEdgeLengthNoLengthWithVertices() {

        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.remove(e1);
        assertEquals(0, g.edgeLengthSum());
    }

    @Test
    public void testTotalEdgeLengthNormal() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(21, g.edgeLengthSum());
    }

    @Test
    public void testSingleEdgeLengthNormal() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(9, g.edgeLength(v1, v4));
    }

    @Test
    public void testSingleEdgeLengthBetweenVerticesNotConnected() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        try {
            g.edgeLength(v1, v3);
        } catch (RuntimeException e) {
            assertTrue(true);
            return;
        }
        fail();
    }

    // ---------------------------------------------------------------------------------
    // Get neighbours
    @Test
    public void testGetNeighboursTwo() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        Map<Vertex, Edge<Vertex>> neighbours = g.getNeighbours(v1);

        Map<Vertex, Edge<Vertex>> expected = new HashMap<Vertex, Edge<Vertex>>();
        expected.put(v2, e1);
        expected.put(v3, e2);

        assertEquals(expected, neighbours);
    }

    @Test
    public void testGetNeighboursWithExtras() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "B");
        Vertex v5 = new Vertex(5, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 100);
        Edge<Vertex> e3 = new Edge<>(v2, v4, 1);
        Edge<Vertex> e4 = new Edge<>(v3, v5, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addVertex(v5);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);
        g.addEdge(e4);

        Map<Vertex, Edge<Vertex>> neighbours = g.getNeighbours(v1);

        Map<Vertex, Edge<Vertex>> expected = new HashMap<Vertex, Edge<Vertex>>();
        expected.put(v2, e1);
        expected.put(v3, e2);

        assertEquals(expected, neighbours);
    }

    @Test
    public void testGetNeighboursOnlyEdgesNoVerticesConnected() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "A");
        Vertex v3 = new Vertex(3, "A");
        Edge<Vertex> e1 = new Edge<>(v2, v3, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);

        Map<Vertex, Edge<Vertex>> neighbours = g.getNeighbours(v1);

        assertEquals(new HashMap<Vertex, Edge<Vertex>>(), neighbours);
    }

    @Test
    public void testGetNeighboursNone() {
        Vertex v1 = new Vertex(1, "A");

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);

        Map<Vertex, Edge<Vertex>> neighbours = g.getNeighbours(v1);

        assertEquals(new HashMap<Vertex, Edge<Vertex>>(), neighbours);
    }

    // ---------------------------------------------------------------------------------
    // Mutability
    @Test
    public void testImmutableGetAllVertices1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        // Use this to compare with the graph after trying to mutate
        Vertex v1_unmodified = new Vertex(1, "A");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        Set<Vertex> allVertices = g.allVertices();
        for (Vertex v : allVertices) {
            v.updateName("Mutate worked from outside");
            v = new Vertex(5, "HELSFES");
        }

        // If not mutated, the name of v1 should still be "A"
        // This is checked with vertex() which further calls equals()
        // Vertex.java implements equals() to compare id and name
        // So mutating the name would return false
        assertTrue(g.vertex(v1_unmodified));
    }


    @Test
    public void testImmutableGetAllEdges1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(4);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addEdge(e1);

        // Use this to compare since we will try to mutate the graph after
        // Calculating edgeLengthSum() of the mutated graph would be incorrect
        int totalLength_unmodified = g.edgeLengthSum();

        // Try to modify the graph's edges
        Set<Edge<Vertex>> allEdges = g.allEdges();
        for (Edge<Vertex> e : allEdges) {
            e.updateLength(99);
        }

        // Check the total length
        Set<Edge<Vertex>> newCopyAllEdges = g.allEdges();
        int sum = 0;
        for (Edge<Vertex> e : newCopyAllEdges) {
            sum += e.length();
        }

        assertEquals(totalLength_unmodified, sum);
    }

    @Test
    public void testImmutableGetNeighbours1() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");

        // Use this to compare with the graph after trying to mutate
        Vertex v3_unmodified = new Vertex(3, "C");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 1);
        Edge<Vertex> e2 = new Edge<>(v1, v3, 1);

        AMGraph<Vertex, Edge<Vertex>> g = new AMGraph<>(3);
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addEdge(e1);
        g.addEdge(e2);

        // Map is many-to-one since there could be multiple edges per vertex
        Map<Vertex, Edge<Vertex>> neighbours = g.getNeighbours(v1);
        for (Map.Entry<Vertex, Edge<Vertex>> entry : neighbours.entrySet()) {
            entry.getKey().updateName("Mutate worked from outside");
        }

        // If not mutated, the name of v3 should still be "C"
        // This is checked with vertex() which further calls equals()
        // Vertex.java implements equals() to compare id and name
        // So mutating the name would return false
        assertTrue(g.vertex(v3_unmodified));
    }
}
