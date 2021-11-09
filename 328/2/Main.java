//Jay Park

import java.lang.Math;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.math.BigInteger;
import java.io.PrintWriter;


class Line {
	public double x1;
	public double y1;
	public double x2;
	public double y2;
	public char intersect = '0';

	public Line(double x1, double y1, double x2, double y2){
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
}

class Main{
  public static void main(String[] args) {
		ArrayList<Line> inputList = new ArrayList<Line>();
		int n = 0;
    try{
			Scanner read = new Scanner ( new File("input.txt") );
			String line = read.nextLine();
			n = Integer.parseInt(line);
			while( read.hasNext() ){
				line = read.nextLine();
				String [] temp = line.split(" ");
				inputList.add(new Line(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]), Double.parseDouble(temp[2]), Double.parseDouble(temp[3])));
			}	
			read.close();
		}
		catch( FileNotFoundException fnf ) {
			System.out.println( "File not Found" );
		}
		
		fract(n, inputList);

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
  }

	public static void fract(int n, ArrayList<Line> list){
		int s = (int)Math.pow(3, n);
		double[] tsf = new double[s];

		for(int i = 1; i <= n; i++){
			int l = (int)Math.pow(3,i);
			int d = l / 3;

			int t = 0;
			for(int j = d*1; j < d*2; j++ ){
				tsf[j] = tsf[t];
				t += 1;
			}
			t = d*1;
			for(int j = 0; j < d*1; j++){
				tsf[j] = -tsf[t] + 60;
				t += 1;
			}
			t = d*1;
			for( int j = d*2; j < l; j++) {
				tsf[j] = -tsf[t] - 60;
				t += 1;
			}

		}	

		double sx = 0;
		double sy = 0;
		double ex = 0;
		double ey = 0;
		
		for(int i = 0; i < s; i++){
			ex = Math.cos(Math.toRadians(tsf[i]))+sx;
			ey =  Math.sin(Math.toRadians(tsf[i]))+sy;
			check(sx, sy, ex, ey, list);
			sx = ex;
			sy = ey;
		}

	}

	public static void check(double p1x, double p1y, double p2x, double p2y, ArrayList<Line> list){
		for( int i = 0; i < list.size(); i++ ){
			if( list.get(i).intersect == '0' ){
				Line l = list.get(i);
				double m1 = (p2y-p1y)/(p2x-p1x);
				double m2 = (l.y2 - l.y1)/(l.x2 - l.x1);
				double b1 = p1y-(m1*p1x);
				double b2 = l.y1-(m2*l.x1);


				if( (m1 == Double.POSITIVE_INFINITY || m1 == Double.NEGATIVE_INFINITY) &&  !(m2 == Double.POSITIVE_INFINITY || m2 == Double.NEGATIVE_INFINITY)){
					//check vertical and not vertical
					double py = m2*p1x + b2;
					double m = Math.max(p1y, p2y);
					if (m == p1y){
						if(p2y <= py && py <= p1y ){
							l.intersect = '1';
						}
					}
					else{
						if(p2y >= py && py >= p1y ){
							l.intersect = '1';
						}
					}

				}
				else if( !(m1 == Double.POSITIVE_INFINITY || m1 == Double.NEGATIVE_INFINITY) &&  (m2 == Double.POSITIVE_INFINITY || m2 == Double.NEGATIVE_INFINITY) ){
					//check vertical and not overtical
					double py = m1*l.x1 + b1;
					double m = Math.max(l.y1, l.y2);
					if (m == l.y1){
						if(l.y2 <= py && py <= l.y1 ){
							l.intersect = '1';
						}
					}
					else{
						if(l.y2 >= py && py >= l.y1 ){
							l.intersect = '1';
						}
					}
				}
				else if( (m1 == Double.POSITIVE_INFINITY || m1 == Double.NEGATIVE_INFINITY) &&  (m2 == Double.POSITIVE_INFINITY || m2 == Double.NEGATIVE_INFINITY) ){
					//check both vertical
					if(p1x == l.x1){
						double l1m = Math.max(p1y, p2y);
						double l2m = Math.max(l.y1, l.y2);
						double g = Math.max(l1m, l2m);
						if( g == l1m){
							if( l2m >= Math.min(p1y,p2y)){
								l.intersect = '1';
							}
						}
						else{
							if( l1m >= Math.min(l.y1, l.y2)){
								l.intersect = '1';
							}
						}
					}
				}
				else{
					//check parralel and normal
					if( m1 == m2 ){
						//check parralel, if the intercept is the same, check if the max of the leftmost line intersects the rightmost line
						if(b1 == b2){
							double l1m = Math.max(p1x, p2x);
							double l2m = Math.max(l.x1, l.x2);
							double g = Math.max(l1m, l2m);
							if( g == l1m){
								if( l2m >= Math.min(p1x,p2x)){
									l.intersect = '1';
								}
							}
							else{
								if( l1m >= Math.min(l.x1, l.x2)){
									l.intersect = '1';
								}
							}	
						}
					}
					else{
						//check normal, we want to see if this intersection point lies inside of both lines
						double ix = (b1-b2)/(m2-m1);
						double iy = m1 * ((b1-b2)/(m2-m1)) + b1;

						if( (ix >= p1x && ix <= p2x) || (ix <= p1x && ix >= p2x) ){
							if( (ix >= l.x1 && ix <= l.x2) || (ix <= l.x1 && ix >= l.x2) ){
								l.intersect = '1';
							}
						}

						
					}

				}
				//end of if checking if intersection was found 
			}
			//end of for
		}
		//end of check
	}
	//end of main
}



