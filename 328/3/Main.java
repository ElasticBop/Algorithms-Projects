//Jay Park
import java.lang.Math;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.HashMap;

class Path{
	public ArrayList<Integer> path;
	public double cost;
	Path(){
		cost = 0;
	}
	
}
//djikstra maybe? if apsp, postman 27:47 for 3/23, apsp 2/23 1:22:43
class Main { 
  public static void main(String[] args) {
    double [][] adj = new double [1][1];
		int n = 0;
    try{
			Scanner read = new Scanner ( new File("input.txt") );
			String line = read.nextLine();
			n = Integer.parseInt(line);
			adj = new double[n][n];
			int [] inAndOut = new int[n];

			for( int i = 0; i < n; i++ ){
				line = read.nextLine();
				String [] temp = line.split(" ");
				for( int j = 0; j < n; j++ ){
					adj[i][j] = Double.parseDouble(temp[j]);
					//check how many out and in degrees
					if( adj[i][j] != 0 ){
						inAndOut[i] -= 1;
						inAndOut[j] += 1;
					}
				}
			}
			for ( int i = 0; i < inAndOut.length; i++ ){

			}
			read.close();
		}
		catch( FileNotFoundException fnf ) {
			System.out.println( "File not Found" );
		}

		Path temp = sssp(n, adj, 1, 3);
		System.out.println(temp.cost);

		/*
		try{
			PrintWriter writer = new PrintWriter("output.txt");
			for( int i = 0; i < inputList.size(); i++ ){
				writer.println(inputList.get(i).intersect);
			}
			writer.close();
		}
		catch( FileNotFoundException fnf ) {
			System.out.println( "File not Found" );
		}
		*/
  }

	public static void apsp( int n, double [][] adj){
		Path [][] result = new Path[n][n];
		Path [][] prevResult = new Path[n][n];
		for( int i = 0; i < n; i++ ){
			for( int j = 0; j < n; j++){
				result[i][j].path.add(i);
				result[i][j].path.add(j);
				if( i != j && adj[i][j] == 0){
					result[i][j].cost = Double.POSITIVE_INFINITY;
				}
				else{
					result[i][j].cost = adj[i][j];					
				}

			}
		}

		prevResult = Result;
		
		for( int i = 0; i < n; i++ ){
			for( int j = 0; j < n; j++ ){
				int bestCostIndex = 0;
				double bestCost = 0;
				double combinedCost = 0;
				for( int k = 0; k < n; k++ ){
					combinedCost = prevResult[i][k].cost + adj[k][j];
					if( k == 0 ){
						bestCost = combinedCost;
					}
					if( combinedCost < bestCost ){
						bestCostIndex = k;
						bestCost = combinedCost;
					}
				}

				
			}
		}

	}

	public static Path sssp(int n, double [][] adj, int s, int d ){
		Path p = new Path();
		ArrayList<Integer> check = new ArrayList<Integer>();
		int [][] path = new int[n][2];
		boolean [] finished = new boolean[n];

		check.add(s);
		path[s][0] = s; // store prev node
		path[s][1] = 0; // stores cost
		while( !finished[d] ){
			int minIndexInCheck = 0;
			for( int i = 0; i < check.size(); i++ ){
				//find the node with minimum cost
				if( path[check.get(minIndexInCheck)][1] > path[check.get(minIndexInCheck)][1]  ){
					minIndexInCheck = i;
				}
			}
			int minNode = check.get(minIndexInCheck);
			finished[minNode] = true;
			check.remove(minIndexInCheck);
			//add the adjacent nodes of minnode to check and give the path a cost
			for( int i = 0; i < n; i++ ){
				int cost = (int)adj[minNode][i];
				if( !finished[i] && cost != 0 ){
					int costForAdj = cost + path[minNode][1];
					if( path[i][1] != 0 && path[i][1] < costForAdj  ){
						//don't add a path from minNode to adj if there already exists a path that is cheaper
					}
					else{
						check.add(i);
						path[i][0] = minNode;
						path[i][1] = costForAdj;						
					}
					
				}
				//end of for loop
			}
			//end of while loop
		}

		p.cost = Double.valueOf(path[d][1]);
		int cNode = d;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		temp.add(cNode);
		while( cNode != s ){
			cNode = path[cNode][0];
			temp.add( cNode );
		}
		p.path = temp;
		return p;
	}
}