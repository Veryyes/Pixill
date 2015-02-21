package pl;
import java.util.Scanner;


public class SparseDriver {

	public static void main(String[] args) {
		SparseMatrix<String> matrix = new SparseMatrix(10, 10);
		Scanner scan = new Scanner(System.in);
		String opt = "";
		String value = "";
		while(!opt.equals("0")){
			System.out.println("Here is the Matrix of Size: "+matrix.size()+
					"\nMax row size of "+matrix.numRows()+
					"\nMax column size of "+matrix.numColumns());

			printTheMatrix(matrix);

			System.out.println("(1) Add");
			System.out.println("(2) Set");
			System.out.println("(3) Remove");
			System.out.println("(4) Contains?");
			System.out.println("(5) Get Location");
			System.out.println("(6) Is Empty?");
			System.out.println("(7) Clear");
			System.out.println("(0) Quit");
			opt = scan.next();
			if(opt.equals("1")){
				System.out.println("What do you want to add?");
				value = scan.next();
				System.out.println("X? Y?");
				matrix.add(Integer.parseInt(scan.next()),Integer.parseInt(scan.next()),value);
			}
			else if(opt.equals("2")){
				System.out.println("What do you want to Set?");
				value = scan.next();
				System.out.println("X? Y?");
				matrix.set(Integer.parseInt(scan.next()),Integer.parseInt(scan.next()),value);
				
			}
			else if(opt.equals("3")){
				System.out.println("X? Y?");
				matrix.remove(Integer.parseInt(scan.next()),Integer.parseInt(scan.next()));
			}
			else if(opt.equals("4")){
				System.out.println("Search for what item?");
				matrix.contains(scan.next());
			}
			else if(opt.equals("5")){
				System.out.println("Find the location of the first instance of what item?");
				value = scan.next();
				int[] coordinates =  matrix.getLocation(value);
				if(coordinates==null)
					System.out.println("Item not found");
				else
					System.out.println("Item at ("+coordinates[0]+","+coordinates[1]+")");
			}
			else if(opt.equals("6")){
				System.out.print("This matrix is ");
				if(!matrix.isEmpty())
					System.out.print("not ");
				System.out.println("empty.");
			}
			else if(opt.equals("7")){
				matrix.clear();
			}
		}
	}
	
	public static void printTheMatrix(SparseMatrix<String> a){
		Object[][] array = a.toArray();
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[0].length;j++){
				System.out.print(array[i][j]+",");
				
			}
			System.out.println();
		}
	}

}
