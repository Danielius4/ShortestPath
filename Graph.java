package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
	
	  private int adjMatrix[][];
	  private int numVertices;
	  public static final int NO_PARENT = 0;

	  public Graph(int numVertices) {
	    this.numVertices = numVertices;
	    adjMatrix = new int[numVertices][numVertices];
	  }

	  public void addEdge(int i, int j, int value) {
	    adjMatrix[i][j] = value;
	    adjMatrix[j][i] = value;
	  }

	  public void removeEdge(int i, int j) {
	    adjMatrix[i][j] = 0;
	    adjMatrix[j][i] = 0;
	  }

	  // Print the matrix
	
	  public String toString() {
	    StringBuilder s = new StringBuilder();
	    for (int i = 0; i < numVertices; i++) {
	      s.append(i + ": ");
	      
	      for (int j : adjMatrix[i]) {
	        s.append(j + " ");
	      }
	      s.append("\n");
	    }
	    return s.toString();
	  }
	  
	  public void fillGraph(ReadCities readCities) throws FileNotFoundException{
		  File data = new File("\\Users\\danie\\OneDrive\\Desktop\\Univeras\\antras semestras\\OOP\\ADS4\\src\\main\\data.txt");
		  try (Scanner myReader = new Scanner(data)) {
			while(myReader.hasNextLine() != false) {
				
				  String firstCity = myReader.next();
				  String secondCity = myReader.next();
				  int distance = myReader.nextInt();
				  
				  int x = 0, y = 0;
				  
				  for(int i = 0; i < readCities.cities.size(); ++i) {
					  
					  if(readCities.cities.get(i).equals(firstCity)) {
						  x = i;
						  for(int j = 0; j < readCities.cities.size(); ++j) {
							  if(readCities.cities.get(j).equals(secondCity)) {
								  y = j;
								  break;
							  }
						  }
						  break;
					  }
				  }
				  
				  addEdge(x, y, distance);
				  
			  }
		}
		  
	  }
	  
	  public ArrayList<Integer> findPath(ArrayList<Integer> path, int distance, int destination, int departure, int [][] adjMatrix, int n) {
		  
		  int tempDistance;
		  ArrayList<Integer> tempPath = new ArrayList<Integer>();
		  
		  for(int i = departure; i < n; ++i) {
			  tempDistance = distance;
			  if(adjMatrix[i][destination] == distance) {
				  path.add(i);
				  path.add(destination);
				  return path;
			  }
			  if(tempDistance < 1)
				  return path;
			  
			  System.out.println(i);
			  for(int g = i; g < n; ++g) {
				  if(adjMatrix[i][g] !=0)
					  tempPath = findPath(path, tempDistance, destination, departure, adjMatrix, n);
			  }
			  
			  
			  
		  }
		  
		  if(path.size() == 0) {
			  return path;
		  }
		  
		  for(int i : tempPath)
			  path.add(i);
		  
		  return path;
	  }
	
	 

	 
	    
	  
	  //get the vertex with minimum distance which is not included in shortestPath array
	  int getMinimumVertex(boolean [] mst, int [] key, ReadCities read){
		  int minKey = Integer.MAX_VALUE;
		  int vertex = 0;
		  for (int i = 0; i <read.n ; i++) {
			  if(mst[i]==false && minKey>key[i]){
				  minKey = key[i];
				  vertex = i;
			  }
		  }
		  return vertex;
	  }
	  
	  
	  
	  public void dijkstra_GetMinDistance(int departure, ReadCities read, int destination){
		  boolean[] shortestPath = new boolean[read.n];
		  int [] distance = new int[read.n];

		  int[] path = new int [read.n];
		  
		  for(int i = 0 ; i < read.n; i++) {
			  path[i] = -1;
		  }

		  //Initialize all the distances to infinity
		  
		  for (int i = 0; i <read.n ; i++) {
			  distance[i] = Integer.MAX_VALUE;
		  }

		  distance[departure] = 0;

		  for (int i = 0; i <read.n ; i++) {
	
			  int vertex1 = getMinimumVertex(shortestPath, distance, read);
			  
	
			  //include vertex in shortestPath
			  
			  shortestPath[vertex1] = true;
	
			  //iterate through all the adjacent vertices of above vertex and update the keys
			  
			  for (int vertex2 = 0; vertex2 <read.n ; vertex2++) {
				  
				  //check of the edge between vertex_U and vertex_V
				  
				  if(adjMatrix[vertex1][vertex2]>0){

					  //check if this vertex is already in shortestPath
					  
					  if(shortestPath[vertex2]==false && adjMatrix[vertex1][vertex2]!=Integer.MAX_VALUE){
						  
						  //check if distance needs an update or not
						  
						  int newKey = adjMatrix[vertex1][vertex2] + distance[vertex1];
						  
						  if(newKey<distance[vertex2]) {
							  distance[vertex2] = newKey;
							  
							  //get previous vertex to know the path
							  
							  path[vertex2] = vertex1;
						  }
					  }
				  }

			  }
		  }
		  
		  if(distance[destination] == Integer.MAX_VALUE) {
			  System.out.println("\nThe path does not exist!");
			  return;
		  }
		  
		  //print shortest path
		  
		  printDijkstra(read.cities.get(departure), destination, distance, read.cities.get(destination));
		  
		  ArrayList<String> realPath = new ArrayList<String>();
		  int index = destination;
		  realPath.add(read.cities.get(index));
		  while(path[index] != -1) {
			  index = path[index];
			  realPath.add(read.cities.get(index));
		  }
		  
		  System.out.print("Path: ");
		  for(int i = realPath.size() - 1; i >= 0; i--) {
			  System.out.print( realPath.get(i) + " ");
		  }
		  
	  }

		  public void printDijkstra(String departure, int index, int distance[], String destination){
			  
			  System.out.println("\nDistance from " + departure + " to " +  destination +
			  " is: " + distance[index]);
		  }
		  
		  
		  

	  public static void main(String args[]) throws FileNotFoundException {
	    
		ArrayList<Integer> path2 = new ArrayList<Integer>();
		path2.add(3);
		path2.add(3);
		path2.add(3);
		
		  
		ReadCities read = new ReadCities();  
		read.findCities();
		//System.out.println(read.n);
		System.out.println(read.cities);
		  
		Graph g = new Graph(read.cities.size());
		g.fillGraph(read);

		Scanner input = new Scanner(System.in);
		System.out.println("Departure: ");
		String departure = input.next();
		System.out.println("Destination: ");
		String destination = input.next();
		input.close();
		
		int x = -1, y = -1;
		
		for(int i = 0; i < read.cities.size(); ++i) {
			if(departure.equals(read.cities.get(i))) {
				x = i;
			}
			if(destination.equals(read.cities.get(i))) {
				y = i;
			}
		}
		
		if(x == -1 || y == -1) {
			System.out.println("Wrong departure or destination city!");
			return;
		}

		g.dijkstra_GetMinDistance(x, read, y);
		

	   // System.out.print(g.toString());
	  }

}
