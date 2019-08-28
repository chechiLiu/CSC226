/* ShortestPaths.java
   CSC 226 - Fall 2017
   
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
	
   An input file can contain an unlimited number of graphs; each will be 
   processed separately.
   
   B. Bird - 08/02/2014
*/

/*
Input: A weighted graph G of n vertices. Each edge weight corresponds
to the time to travel between two locations.

Output: For each charging station, the shortest path from the starting
location to that charging station and the total distance of the trip
	
The implemented algorithm is Dijkstra’s algorithm. The running time is at most O(V^2 + E log V ),
where the V^2 is due to the cost of reading the adjacency matrix.
*/

//Che-Chi (Jack) Liu
//V00850558

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.PriorityQueue;
import java.util.Stack;
import java.lang.StringBuilder;

public class ShortestPaths {
	public static class Vertex implements Comparable<Vertex> {
		private int index; //which vertex is this
		private int dist; //distance from the source to this vertex
		private Vertex last; //what is last vextex connecting this given vertex 
		
		public Vertex(int index, int dist) {
			this.index = index;
			this.dist = dist;
			this.last = null;
		}
		
		public void decreasekey(int dist) {
			this.dist = dist;
		}
		
		public int compareTo(Vertex other) {
			if(this.dist < other.dist) {
				return -1;
			}else if(this.dist > other.dist) {
				return 1;
			}else {
				return 0;
			}
		}

		public int distance() {
			return this.dist;
		}

		public int index() {
			return this.index;
		}
		
		public Vertex last() {
			return this.last;
		}
		
		public void setLast(Vertex vertex) {
			this.last = vertex;
		}
	}
	
    	public static int numVerts;
    	public static Vertex vertx[];
    
	/* ShortestPaths(G) 
	   Given an adjacency matrix for graph G, calculates and stores the
	   shortest paths to all the vertices from the source vertex.
		
		If G[i][j] == 0, there is no edge between vertex i and vertex j
		If G[i][j] > 0, there is an edge between vertices i and j, and the
		value of G[i][j] gives the weight of the edge.
		No entries of G will be negative.
	*/
	static void ShortestPaths(int[][] G, int source) {
		numVerts = G.length;
		PriorityQueue<Vertex> Prq = new PriorityQueue<Vertex>();
		vertx = new Vertex[numVerts];
		
		for(int i = 0; i < numVerts; i++) {
			if(i == source) {
				vertx[source] = new Vertex(source, 0);
			}else {
				vertx[i] = new Vertex(i, Integer.MAX_VALUE);
			}		
		}
		
		Prq.add(vertx[source]);
		
		while(Prq.peek() != null) {
			Vertex row = Prq.poll();
			for(int column = 0; column < G[0].length; column++) {
				if(G[row.index()][column] > 0) {
					if(vertx[column].distance() > (row.distance() + G[row.index()][column])) {
						vertx[column].decreasekey(row.distance() + G[row.index()][column]);
						vertx[column].setLast(row);
						Prq.add(vertx[column]);
					}
				}
			}
		}
	}
        
    	static void PrintPaths(int source) {
    		System.out.println("The path from " + source + " to " + source + " is: " + 0 + " and the total distance is: " + 0);
   		StringBuilder s = new StringBuilder(source + " -- > ");
   		Stack<Integer> stack = new Stack<Integer>();
		
    		for(int i = 0; i < vertx.length; i++) {
    			if(i != source) {
    				Vertex n = vertx[i].last();
    				while(n.index() != source) {
    					stack.push(n.index());
    					n = n.last();
    				}
				
    				while(!stack.empty()) {
    					s.append(stack.pop()+ " -- > ");
    				}
				
    				System.out.println("The path from " + source + " to " + vertx[i].index() + " is: " + s + vertx[i].index() + " and the total distance is: " + vertx[i].distance()); 
    			}
			
    			s = new StringBuilder(source + " -- > "); 
    		}
    	}
	
	/* main()
	   Contains code to test the ShortestPaths function. You may modify the
	   testing code if needed, but nothing in this function will be considered
	   during marking, and the testing process used for marking will not
	   execute any of the code below.
	*/
	
	//Provided
	public static void main(String[] args) throws FileNotFoundException {
		Scanner s;
		if(args.length > 0) {
			try {
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e) {
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else {
			s = new Scanner(System.in);
			System.out.printf("Reading input values from stdin.\n");
		}
		
		int graphNum = 0;
		double totalTimeSeconds = 0;
		
		//Read graphs until EOF is encountered (or an error occurs)
		while(true) {
			graphNum++;
			if(graphNum != 1 && !s.hasNextInt()) {
				break;
			}	
			System.out.printf("Reading graph %d\n",graphNum);
			int n = s.nextInt();
			int[][] G = new int[n][n];
			int valuesRead = 0;
			for(int i = 0; i < n && s.hasNextInt(); i++) {
				for(int j = 0; j < n && s.hasNextInt(); j++) {
					G[i][j] = s.nextInt();
					valuesRead++;
				}
			}
			if(valuesRead < n*n) {
				System.out.printf("Adjacency matrix for graph %d contains too few values.\n",graphNum);
				break;
			}
			long startTime = System.currentTimeMillis();
			
			ShortestPaths(G, 0);
                        PrintPaths(0);
			long endTime = System.currentTimeMillis();
			totalTimeSeconds += (endTime-startTime)/1000.0;
			
			//System.out.printf("Graph %d: Minimum weight of a 0-1 path is %d\n",graphNum,totalWeight);
		}
		graphNum--;
		System.out.printf("Processed %d graph%s.\nAverage Time (seconds): %.2f\n",graphNum,(graphNum != 1)?"s":"",(graphNum>0)?totalTimeSeconds/graphNum:0);
	}
}
