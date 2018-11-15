/* MST.java
   CSC 226 - Fall 2018
   Problem Set 2 - Template for Minimum Spanning Tree algorithm
   
   The assignment is to implement the mst() method below, using Kruskal's algorithm
   equipped with the Weighted Quick-Union version of Union-Find. The mst() method computes
   a minimum spanning tree of the provided graph and returns the total weight
   of the tree. To receive full marks, the implementation must run in time O(m log m)
   on a graph with n vertices and m edges.
   
   This template includes some testing code to help verify the implementation.
   Input graphs can be provided with standard input or read from a file.
   
   To provide test inputs with standard input, run the program with
       java MST
   To terminate the input, use Ctrl-D (which signals EOF).
   
   To read test inputs from a file (e.g. graphs.txt), run the program with
       java MST graphs.txt
   
   The input format for both methods is the same. Input consists
   of a series of graphs in the following format:
   
       <number of vertices>
       <adjacency matrix row 1>
       ...
       <adjacency matrix row n>
   	
   For example, a path on 3 vertices where one edge has weight 1 and the other
   edge has weight 2 would be represented by the following
   
   3
   0 1 0
   1 0 2
   0 2 0
   	
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the mst() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   (originally from B. Bird - 03/11/2012)
   (revised by N. Mehta - 10/9/2018)
*/

import java.util.Scanner;
import java.io.File;
import java.util.LinkedList;
import java.util.Iterator;

public class MST {


    /* mst(adj)
       Given an adjacency matrix adj for an undirected, weighted graph, return the total weight
       of all edges in a minimum spanning tree.

       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
    */
    static int mst(int[][][] adj) {
	int n = adj.length;

	/* Find a minimum spanning tree using Kruskal's algorithm */
	/* (You may add extra functions if necessary) */
		
	/* ... Your code here ... */
		
	//create list of edges
	int[][]  edges = edgeList(adj);
	
	//sort edges by weight (qs)
	sort(edges, 0, edges.length - 1);
	
	//from least to greatest, check if adding the edge creates a cycle
	//if not add it to the mst
	int[][][] mst = new int[n][n][2];
	for(int x = 0; x < edges.length; x++){
		int[] visited = new int[n];
		if(!isConnected(mst, visited, edges[x][1], edges[x][2])){
			mst[edges[x][1]][edges[x][2]][0] = 1;
			mst[edges[x][2]][edges[x][1]][0] = 1;
			mst[edges[x][1]][edges[x][2]][1] = edges[x][0];
		}
	}
	/* Add the weight of each edge in the minimum spanning tree
	   to totalWeight, which will store the total weight of the tree.
	*/
	int totalWeight = 0;
	/* ... Your code here ... */
	for(int x = 0; x < mst.length; x++){
		for(int y = 0; y < mst[x].length; y++){
			if(mst[x][y][0] == 1){
				totalWeight += mst[x][y][1];
			}
		}
	}
	return totalWeight;	
    }
	
	private static int[][] edgeList(int[][][] adj){
		//create a list of edges. does not contain duplicate edges
		//count unmber of edges first vvv
		int n = 0;
		for(int x = 0; x < adj.length; x++){
			for(int y = 0; y < adj[x].length; y++){
				if(adj[x][y][0] >= x){
					n++;
				}
			}
		}
		
		int[][]edges = new int[n][3];
		n = 0;
		for(int x = 0; x < adj.length; x++){
			for(int y = 0; y < adj[x].length; y++){
				if(adj[x][y][0] >= x){
					edges[n][0] = adj[x][y][1];	//edge weight
					edges[n][1] = x;			//edge start
					edges[n][2] = adj[x][y][0];	//edge end
					n++;
				}	
			}
		}
		
		return edges;
	}
	
	private static int[][] sort(int[][] edges, int low, int high){
		//quicksort
		
		if(high <= low){
			return edges;
		}
		
		
		int x = low;
		int y = high;
		
		while(x < y){
			if(edges[x][0] < edges[high][0]){
				x++;
			}else if(edges[y][0] >= edges[high][0]){
				y--;
			}else{
				int[] temp = edges[x];
				edges[x] = edges[y];
				edges[y] = temp;
			}
		}
		
		int[] temp = edges[x];
		edges[x] = edges[high];
		edges[high] = temp;
		
		sort(edges, low, x - 1);
		sort(edges, x + 1, high);
		return edges;
	}
	
	private static boolean isConnected(int[][][] mst, int[] visited, int a, int b){
		//check that adding edge at index x from adj to mst does not create a cycle
		//dfs
		visited[a] = 1;
		if(a == b){
			return true;
		}else{
			for(int x = 0; x < mst[a].length; x++){
				if(mst[a][x][0] == 1 && visited[x] != 1){
					if(isConnected(mst, visited, x, b)){
						return true;
					}
				}
			}
		}
		return false;
	}
   
   public static void main(String[] args) {
	/* Code to test your implementation */
	/* You may modify this, but nothing in this function will be marked */

	int graphNum = 0;
	Scanner s;

	if (args.length > 0) {
	    //If a file argument was provided on the command line, read from the file
	    try {
		s = new Scanner(new File(args[0]));
	    }
	    catch(java.io.FileNotFoundException e) {
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else {
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
		
	//Read graphs until EOF is encountered (or an error occurs)
	while(true) {
	    graphNum++;
	    if(!s.hasNextInt()) {
		break;
	    }
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();

	    int[][][] adj = new int[n][][];
	    
	    
	    
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++) {
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++) {
		    int weight = s.nextInt();
		    if(weight > 0) {
			edgeList.add(new int[]{j, weight});
		    }
		    valuesRead++;
		}
		adj[i] = new int[edgeList.size()][2];
		Iterator it = edgeList.iterator();
		for(int k = 0; k < edgeList.size(); k++) {
		    adj[i][k] = (int[]) it.next();
		}
	    }
	    if (valuesRead < n * n) {
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }

	    // // output the adjacency list representation of the graph
	    // for(int i = 0; i < n; i++) {
	    // 	System.out.print(i + ": ");
	    // 	for(int j = 0; j < adj[i].length; j++) {
	    // 	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    // 	}
	    // 	System.out.print("\n");
	    // }

	    int totalWeight = mst(adj);
	    System.out.printf("Graph %d: Total weight of MST is %d\n",graphNum,totalWeight);

				
	}
    }

    
}
