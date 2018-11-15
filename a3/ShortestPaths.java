/* ShortestPaths.java
   CSC 226 - Fall 2018
   
   This template includes some testing code to help verify the implementation.
   To interactively provide test inputs, run the program with
   java ShortestPaths
   
   To conveniently test the algorithm with a large input, create a text file
   containing one or more test graphs (in the format described below) and run
   the program with
   java ShortestPaths file.txt
   where file.txt is replaced by the name of the text file.
   
   The input consists of a series of graphs in the following format:
   
   <number of vertices>
   <adjacency matrix row 1>
   ...
   <adjacency matrix row n>
   
   Entry A[i][j] of the adjacency matrix gives the weight of the edge from 
   vertex i to vertex j (if A[i][j] is 0, then the edge does not exist).
   Note that since the graph is undirected, it is assumed that A[i][j]
   is always equal to A[j][i].
   
   An input file can contain an unlimited number of graphs; each will be processed separately.
   
   NOTE: For the purpose of marking, we consider the runtime (time complexity)
         of your implementation to be based only on the work done starting from
	 the ShortestPaths() method. That is, do not not be concerned with the fact that
	 the current main method reads in a file that encodes graphs via an
	 adjacency matrix (which takes time O(n^2) for a graph of n vertices).
   
   
   (originally from B. Bird - 08/02/2014)
   (revised by N. Mehta - 10/24/2018)
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.LinkedList;
import java.util.Iterator;


//Do not change the name of the ShortestPaths class
public class ShortestPaths{

    //TODO: Your code here
    public static int n; // number of vertices
	public static int[] distance;
	public static LinkedList<Integer>[] path;
    /* ShortestPaths(adj) 
       Given an adjacency list for an undirected, weighted graph, calculates and stores the
       shortest paths to all the vertices from the source vertex.
       
       The number of vertices is adj.length
       For vertex i:
         adj[i].length is the number of edges
         adj[i][j] is an int[2] that stores the j'th edge for vertex i, where:
           the edge has endpoints i and adj[i][j][0]
           the edge weight is adj[i][j][1] and assumed to be a positive integer
       
       All weights will be positive.
    */
    static void ShortestPaths(int[][][] adj, int source){
		n = adj.length;
		distance = new int[n];	//stores the minimum distance from source to vertex n
		path = new LinkedList[n];	//stores the path from source to vertex n as a queue
		LinkedList<Integer> q = new LinkedList<Integer>();	//queue used for graph traversal
		q.add(source);	//add the source to the queue
		distance[source] = 0;	//distance for the source vertex is 0
		path[source] = new LinkedList<Integer>();	//path for source vertex contains only the source vertex
		path[source].push(source);
		
		while(!q.isEmpty()){
			
			for(int x = 0; x < adj[q.peek()].length; x++){
				
				if(distance[adj[q.peek()][x][0]] == 0 || distance[adj[q.peek()][x][0]] > distance[q.peek()] + adj[q.peek()][x][1]){
					//if a node connected to the current vertex creates a minimum weight path
					//update distance/path values and add the vertex to the queue
					distance[adj[q.peek()][x][0]] = distance[q.peek()] + adj[q.peek()][x][1];
					q.add(adj[q.peek()][x][0]);
					path[adj[q.peek()][x][0]] = new LinkedList<Integer>();
					path[adj[q.peek()][x][0]] = (LinkedList<Integer>)path[q.peek()].clone();
					path[adj[q.peek()][x][0]].add(adj[q.peek()][x][0]);
					
				}
			}
			q.poll();	//after all vertices connected to the current vertex are checked, remove it from the queue
		}
    }
    
    static void PrintPaths(int source){
		String out_text;
		for(int x = 0; x < path.length; x++){
			if(x == source){
				System.out.print("The shortest path from " + source + " to " + x + " is: 0 and the total distance is : 0");
			} else{
				System.out.print("The shortest path from " + source + " to " + x + " is: 0");
				while(!path[x].isEmpty()){
					if(path[x].peek() == 0){
						path[x].poll();
					}
					System.out.print(" --> " + path[x].poll());
				}
				System.out.print(" and the total distance is : " + distance[x]);
			}
			System.out.println();
		}
		
		
    }
    
    
    /* main()
       Contains code to test the ShortestPaths function. You may modify the
       testing code if needed, but nothing in this function will be considered
       during marking, and the testing process used for marking will not
       execute any of the code below.
    */
    public static void main(String[] args) throws FileNotFoundException{
	Scanner s;
	if (args.length > 0){
	    //If a file argument was provided on the command line, read from the file
	    try{
		s = new Scanner(new File(args[0]));
	    } catch(java.io.FileNotFoundException e){
		System.out.printf("Unable to open %s\n",args[0]);
		return;
	    }
	    System.out.printf("Reading input values from %s.\n",args[0]);
	}
	else{
	    //Otherwise, read from standard input
	    s = new Scanner(System.in);
	    System.out.printf("Reading input values from stdin.\n");
	}
	
	int graphNum = 0;
	double totalTimeSeconds = 0;
	
	//Read graphs until EOF is encountered (or an error occurs)
	while(true){
	    graphNum++;
	    if(graphNum != 1 && !s.hasNextInt())
		break;
	    System.out.printf("Reading graph %d\n",graphNum);
	    int n = s.nextInt();
	    int[][][] adj = new int[n][][];
	    
	    int valuesRead = 0;
	    for (int i = 0; i < n && s.hasNextInt(); i++){
		LinkedList<int[]> edgeList = new LinkedList<int[]>(); 
		for (int j = 0; j < n && s.hasNextInt(); j++){
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
	    if (valuesRead < n * n){
		System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
		break;
	    }
	    
	    // output the adjacency list representation of the graph
	    for(int i = 0; i < n; i++) {
	    	System.out.print(i + ": ");
	    	for(int j = 0; j < adj[i].length; j++) {
	    	    System.out.print("(" + adj[i][j][0] + ", " + adj[i][j][1] + ") ");
	    	}
	    	System.out.print("\n");
	    }
	    
	    long startTime = System.currentTimeMillis();
	    
	    ShortestPaths(adj, 0);
	    PrintPaths(0);
	    long endTime = System.currentTimeMillis();
	    totalTimeSeconds += (endTime-startTime)/1000.0;
	    
	    //System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
	}
	graphNum--;
	System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
    }
}
