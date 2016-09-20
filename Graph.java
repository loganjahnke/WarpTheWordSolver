import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.io.File;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Set;
 
/**
* Object that represents a puzzle maker
*/
/******************************************************************************
 *  Compilation:  javac Graph.java
 *  Execution:    java Graph
 *  Dependencies: ST.java SET.java In.java StdOut.java
 *  
 *  Undirected graph data type implemented using a symbol table
 *  whose keys are vertices (String) and whose values are sets
 *  of neighbors (SET of Strings).
 *
 *  Remarks
 *  -------
 *   - Parallel edges are not allowed
 *   - Self-loop are allowed
 *   - Adjacency lists store many different copies of the same
 *     String. You can use less memory by interning the strings.
 *
 ******************************************************************************/

/**
 *  The <tt>Graph</tt> class represents an undirected graph of vertices
 *  with string names.
 *  It supports the following operations: add an edge, add a vertex,
 *  get all of the vertices, iterate over all of the neighbors adjacent
 *  to a vertex, is there a vertex, is there an edge between two vertices.
 *  Self-loops are permitted; parallel edges are discarded.
 *  <p>
 *  For additional documentation, see <a href="http://introcs.cs.princeton.edu/45graph">Section 4.5</a> of
 *  <i>Introduction to Programming in Java: An Interdisciplinary Approach</i> by Robert Sedgewick and Kevin Wayne.
 */
public class Graph {

    // symbol table: key = string vertex, value = set of neighboring vertices
    private ST<String, SET<String>> st;

    // number of edges
    private int E;

    // files to open
    private String[] files;

   /**
     * Create an empty graph with no vertices or edges.
     */
    public Graph() {
        st = new ST<String, SET<String>>();
        files = new String[]{ "a.txt","b.txt","c.txt","d.txt","e.txt","f.txt","g.txt","h.txt","i.txt","j.txt","k.txt","l.txt","m.txt","n.txt","o.txt","p.txt","q.txt","r.txt","s.txt","t.txt","u.txt","v.txt","w.txt","x.txt","y.txt","z.txt" };
    }

   /**
     * Number of vertices.
     */
    public int V() {
        return st.size();
    }

   /**
     * Number of edges.
     */
    public int E() {
        return E;
    }

    // throw an exception if v is not a vertex
    private void validateVertex(String v) {
        if (!hasVertex(v)) throw new IllegalArgumentException(v + " is not a vertex");
    }

   /**
     * Degree of this vertex.
     */
    public int degree(String v) {
        validateVertex(v);
        return st.get(v).size();
    }

   /**
     * Add edge v-w to this graph (if it is not already an edge)
     */
    public void addEdge(String v, String w) {
        if (!hasVertex(v)) addVertex(v);
        if (!hasVertex(w)) addVertex(w);
        if (!hasEdge(v, w)) E++;
        st.get(v).add(w);
        st.get(w).add(v);
    }

   /**
     * Add vertex v to this graph (if it is not already a vertex)
     */
    public void addVertex(String v) {
        if (!hasVertex(v)) st.put(v, new SET<String>());
    }


   /**
     * Return the set of vertices as an Iterable.
     */
    public Iterable<String> vertices() {
        return st.keys();
    }

   /**
     * Return the set of neighbors of vertex v as an Iterable.
     */
    public Iterable<String> adjacentTo(String v) {
        validateVertex(v);
        return st.get(v);
    }

   /**
     * Is v a vertex in this graph?
     */
    public boolean hasVertex(String v) {
        return st.contains(v);
    }

   /**
     * Is v-w an edge in this graph?
     */
    public boolean hasEdge(String v, String w) {
        validateVertex(v);
        validateVertex(w);
        return st.get(v).contains(w);
    }

   /**
     * Return a string representation of the graph.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (String v : st) {
            s.append(v + ": ");
            for (String w : st.get(v)) {
                s.append(w + " ");
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        Graph G = new Graph();

        G.addData(G);
        G.connectData(G);

        // print out graph
        System.out.println(G);

        // print out graph again by iterating over vertices and edges
        for (String v : G.vertices()) {
            System.out.print(v + ": ");
            for (String w : G.adjacentTo(v)) {
                System.out.print(w + " ");
            }
            System.out.println();
        }

        ArrayList<String> tempList = new ArrayList<String>();
        ArrayList<String> smallestList = new ArrayList<String>();
        BreadthFirstPaths bfs = new BreadthFirstPaths(G, args[0], args[1]);
            

    }

    /**
     * Adds all words from dictionary.
     */
    public void addData(Graph G) {
        for (int i = 0; i < 26; i++) {
            File path = new File("Dictionary/"+files[i]);

            try (Scanner file = new Scanner(path)) {
        
                String wordInFile = file.next();

                while (wordInFile != "") {
                    G.addVertex(wordInFile);
                    if (file.hasNext()) {
                        wordInFile = file.next();
                    } else {
                        break;
                    }
                }

            } catch (Exception e) { 
        
                System.out.printf("A problem occurred: %s", e); 
        
            }

        }
    }

    /**
     * Connects all data if they are only 1 hamming distance apart.
     */
    public void connectData(Graph G) {
        for (String v : G.vertices()) {
            for (String compareTo : G.vertices()) {
                if (getHammingDistance(v, compareTo) == 1) {
                    G.addEdge(v, compareTo);
                }
            }   
        }
    }


    ///
    //  Calculating the Hamming Distance for two strings requires the string to be of the same length.
    ///
    public int getHammingDistance(String wordToCompareOne, String wordToCompareTwo)
    {
        if (wordToCompareOne.length() != wordToCompareTwo.length())
        {
            return -1;
        }
 
        int counter = 0;
 
        for (int i = 0; i < wordToCompareOne.length(); i++)
        {
            if (wordToCompareOne.charAt(i) != wordToCompareTwo.charAt(i)) counter++;
        }
 
        return counter;
    }
}
