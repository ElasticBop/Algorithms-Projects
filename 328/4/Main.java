//Jay Park
import java.lang.Math;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.util.Collections;


class Wall implements Comparable<Wall>{
	public double cost;
	public int num;
	public int room1;
	public int room2;
	public Wall(int n, double c, int r1, int r2){
		cost = c;
		num = n;
		room1 = r1;
		room2 = r2;
	}

	@Override
	public int compareTo(Wall w){
		if( this.cost > w.cost ){
			return 1;
		}
		else if( this.cost < w.cost ){
			return -1;
		}
		else{
			return 0;
		}
	}

}

class Main {
  public static void main(String[] args) {
		ArrayList<Wall> walls = new ArrayList<Wall>();
		int n = 0;
		int maxw = 0;
		int maxr = 0;
		int maxhw = 0;
		int minvw = 0;
		try{
			Scanner read = new Scanner ( new File("input.txt") );
			String line = read.nextLine();
			n = Integer.parseInt(line);
			maxr = (int) Math.pow(n,2);
			maxw = 2*(maxr - n);
			maxhw = maxr - n;
			minvw = maxhw + 1;

			int edge = 0;
			int edgesInserted = 0;
			int hrow = 1;
			int vcol = 1;
			while( read.hasNext() ){
				edge+=1;
				edgesInserted += 1;
				line = read.nextLine();

				if( edge < minvw){
					if(edgesInserted > (n-1) ){
						edgesInserted = 1;
						hrow+=1;
					}
					walls.add(new Wall(edge, Double.parseDouble(line), hrow + edge - 1 , hrow + edge));
				}
				else{
					if( edge == minvw ){
						edgesInserted = 1;
					}
					if( edgesInserted > (n-1) ){
						edgesInserted = 1;
						vcol += 1;
					}
					walls.add(new Wall(edge, Double.parseDouble(line), vcol + n*(edgesInserted-1) , vcol + n*(edgesInserted) ));
				}

			}	
			read.close();
		}
		catch( FileNotFoundException fnf ) {
			System.out.println( "File not Found" );
		}

		/*
		for( int i = 0; i < walls.size(); i++ ){
			Wall c = walls.get(i);
			System.out.println( "Wall " + c.num + ": n1 =  " + c.room1 + " , n2 = " + c.room2 + " , cost = " + c.cost );
		}
		*/
		
		String [] coord = new String[maxr];
		int column = 0;
		int row = 0;
		for(int i = 0; i < coord.length; i++ ){
			coord[i] = column  + " " + row;
			row += 1;
			if( row > (n-1) ){
				row = 0;
				column += 1;
			}
		}
		
		
		Collections.sort(walls);
		ArrayList<Wall> chosen = choseWalls( walls, maxr, maxw );
		
		try{
			PrintWriter writer = new PrintWriter("output.txt");
			for( int i = 0; i < chosen.size(); i++ ){
				Wall c = chosen.get(i);
				writer.println(coord[c.room1-1] + " " + coord[c.room2-1]);
			}
			writer.close();
		}
		catch( FileNotFoundException fnf ) {
			System.out.println( "File not Found" );
		}

		
		/*
		for( int i = 0; i < chosen.size(); i++ ){
			Wall c = chosen.get(i);
			System.out.println( "Wall " + c.num + ": n1 =  " + c.room1 + " , n2 = " + c.room2 + " , cost = " + c.cost );
		}
		*/
		
  }

	public static ArrayList<Wall> choseWalls( ArrayList<Wall> walls, int maxr, int maxw){
		int [] room2Group = new int[maxr+1];
		ArrayList<Wall> chosen = new ArrayList<Wall>();
		int pointer = maxw - 1;
		int edgesChosen = 0;
		int groupNum = 1;
		
		//select wall, if no room matches to any of the rooms in the exisiting groups, add new group. otherwise add non matching node to the existing groups that it matches with

		
		while( edgesChosen != (maxr-1) ){
			Wall current = walls.get(pointer);
			boolean r1ingroup = room2Group[current.room1] != 0;
			boolean r2ingroup = room2Group[current.room2] != 0;;

			if( r1ingroup && r2ingroup  ){
				if( room2Group[current.room1] != room2Group[current.room2] ){
					//merge groups
					
					for(int i = 1; i < room2Group.length; i++ ){
						if( room2Group[i] == room2Group[current.room1] ){
							room2Group[i] = room2Group[current.room2];
						}
					}
					chosen.add(current);
					edgesChosen += 1;					
				}
			}
			else if( r1ingroup || r2ingroup ){
				if( r1ingroup ){
					room2Group[current.room2] = room2Group[current.room1];
					//room2Group.put(current.room2, room2Group.get(current.room1));
				}
				else{
					room2Group[current.room1] = room2Group[current.room2];
					//room2Group.put(current.room1, room2Group.get(current.room2));
				}
				chosen.add(current);
				edgesChosen += 1;
			}
			else{
				room2Group[current.room1] = groupNum;
				room2Group[current.room2] = groupNum;
				groupNum += 1;
				chosen.add(current);
				edgesChosen += 1;
			}
			pointer -= 1;
		}

		return chosen;
		//end of function
	}

}