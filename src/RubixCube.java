
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class RubixCube {

	private final byte R = 0;
	private final byte G = 1;
	private final byte Y = 2;
	private final byte B = 3;
	private final byte O = 4;
	private final byte W = 5;

	private byte[][] cube = new byte[6][8];

	public void loadFromFile(String filename) throws FileNotFoundException {
		File f = new File(filename);
		Scanner s = new Scanner(f);
		
		while(s.hasNext()){ //nextLine() reads in the spaces where next() does not
			int shift = 0;
			
			//file contains 4 faces vertically and 2 horizontally attached to the 2nd face from the top
			for(int i=0; i<4; i++){
				//loops 3 times for each row in the face
				//for (int j=0; j<3; j++){
					String row1 = s.next();
					String row2 = s.next();
					String row3 = s.next();
					
					if(row1.length() == 3){//takes care of the faces that don't have the horizontal faces attached
						byte[] face = new byte[8];
						byte[] temp1 = row1.getBytes();
						byte[] temp2 = {row2.getBytes()[0],row2.getBytes()[2]};
						byte[] temp3 = row3.getBytes();

						System.arraycopy(temp1, 0, face, 0, temp1.length);
						System.arraycopy(temp2, 0, face, temp1.length, temp2.length);
						System.arraycopy(temp3, 0, face, temp1.length + temp2.length, temp3.length);
						cube[shift] = face;
						shift += 1;
					}
					else{//takes care of the faces with the horizontal attached
						int bounds = 1;
						for(int j=0; j<3; j++){
							byte[] face = new byte[8];
							
							byte[] temp1 = row1.substring(3*j, 3*bounds).getBytes();
							byte[] temp2 = {row2.substring(3*j, 3*bounds).getBytes()[0],row2.substring(3*j, 3*bounds).getBytes()[2]};
							byte[] temp3 = row3.substring(3*j, 3*bounds).getBytes();
							
							System.arraycopy(temp1, 0, face, 0, temp1.length);
							System.arraycopy(temp2, 0, face, temp1.length, temp2.length);
							System.arraycopy(temp3, 0, face, temp1.length + temp2.length, temp3.length);
							
							cube[shift] = face;
							shift += 1;
							bounds += 1;
						}
					}
					System.out.println(row1);
					System.out.println(row2);
					System.out.println(row3);
					//System.out.println(Arrays.toString(face));
					
				//}
			}
		}
		System.out.println(Arrays.deepToString(cube));
		//System.out.println(System.getProperty("user.dir"));
		s.close();
	}

	
}
