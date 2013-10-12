import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


class Test {

	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("rubbix.txt");
		Scanner s = new Scanner(f);
		char[][][] cube = new char[6][3][3];
		
		while(s.hasNext()){ //nextLine() reads in the spaces where next() does not
			int shift = 0;
			
			//file contains 4 faces vertically and 2 horizontally attached to the 2nd face from the top
			for(int i=0; i<4; i++){
				
				//loops 3 times for each row in the face
				for (int j=0; j<3; j++){
					String row = s.next();
					
					if(row.length() == 3){
						//takes care of the faces that don't have the horizontal faces attached
						cube[shift][j] = row.toCharArray(); 
					}
					else{
						//takes care of the faces with the horizontal attached
						cube[1][j] = row.substring(0, 3).toCharArray();
						cube[2][j] = row.substring(3, 6).toCharArray();
						cube[3][j] = row.substring(6, 9).toCharArray();
						shift = 3;
					}
					System.out.println(row);
				}
				shift += 1;
			}
		}
		System.out.println(Arrays.deepToString(cube));
		//System.out.println(System.getProperty("user.dir"));
		s.close();
	}

}
