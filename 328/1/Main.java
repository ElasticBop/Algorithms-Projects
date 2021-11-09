import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.math.BigInteger;
import java.io.PrintWriter;

class Main {
  public static void main(String[] args) {

		ArrayList<BigInteger> inputList = new ArrayList<BigInteger>();
		
		try{
			Scanner read = new Scanner ( new File("input.txt") );
			BigInteger line;
			while( read.hasNext() ){
				line = new BigInteger(read.nextLine());
				inputList.add(line);
			}	
			read.close();
		}
		catch( FileNotFoundException fnf ) {
			System.out.println( "File not Found" );
		}

		ArrayList<BigInteger> coef = solve(inputList);

		try{
			PrintWriter writer = new PrintWriter("output.txt");
			for( int i = 0; i < coef.size(); i++ ){
				if( coef.get(i).intValue() != 0 ){
					writer.println(inputList.get(i+1).toString() + " " + coef.get(i).toString() );
				}
			}
			writer.close();
		}
		catch( FileNotFoundException fnf ) {
			System.out.println( "File not Found" );
		}
  }

	public static ArrayList<BigInteger> solve(ArrayList<BigInteger> list){
		ArrayList<BigInteger> coef  = new ArrayList<BigInteger>();	
		BigInteger factor;
		BigInteger gcd;
		BigInteger[] eeResult = ee(list.get(1), list.get(2));
		gcd = eeResult[0];
		coef.add(eeResult[1]);
		coef.add(eeResult[2]);

		for( int i = 3; i < list.size(); i++ ){
			eeResult = ee(gcd, list.get(i));
			gcd = eeResult[0];
			factor = eeResult[1];
			coef.add(eeResult[2]);
			for( int j = 0; j < i-1; j++){
				BigInteger c = coef.get(j);
				c = c.multiply(factor);
				coef.set(j, c);
			}
		}

		factor = list.get(0).divide(gcd);
		for( int i = 0; i < coef.size(); i++ ){
			BigInteger f = coef.get(i);
			f = f.multiply(factor);
			coef.set(i, f);
		}
	
		return coef;
	}


	public static BigInteger[] ee(BigInteger a, BigInteger b){
		if ( b.intValue() == 0 ){
			return new BigInteger[] {a, BigInteger.valueOf(1), BigInteger.valueOf(0)};
		}
		BigInteger[] output = ee(b, a.mod(b));
		return new BigInteger[] {output[0], output[2], output[1].subtract(output[2].multiply(a.divide(b)))};
		
	}

}