
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TableGenerator {

	private final byte R = 0;
	private final byte G = 1;
	private final byte Y = 2;
	private final byte B = 3;
	private final byte O = 4;
	private final byte W = 5;

	private byte[] table;
	private int fuck;
	private int shit;

	//Corner Cubie Locations
	private final byte[][][] CCL = {
		{{0,0}, {1,0}, {5,5}},
		{{0,2}, {3,2}, {5,7}},
		{{0,5}, {1,2}, {2,0}},
		{{0,7}, {2,2}, {3,0}},
		{{4,0}, {1,7}, {2,5}},
		{{4,2}, {2,7}, {3,5}},
		{{4,5}, {1,5}, {5,0}},
		{{4,7}, {3,7}, {5,2}}
	};

	//Corner Cubie Values
	private final byte[][] CCV = {
		{R,G,W}, {G,W,R}, {W,R,G},
		{R,B,W}, {B,W,R}, {W,R,B},
		{R,G,Y}, {G,Y,R}, {Y,R,G},
		{R,Y,B}, {Y,B,R}, {B,R,Y},
		{O,G,Y}, {G,Y,O}, {Y,O,G},
		{O,Y,B}, {Y,B,O}, {B,O,Y},
		{O,G,W}, {G,W,O}, {W,O,G},
		{O,B,W}, {B,W,O}, {W,O,B}
	};

	private RubixCube goal;

	public void main() throws FileNotFoundException {
		this.makeCubieTable();

		String file = "heuristic-tables/cubie-table.txt";
		//BufferedOutputStream bos = null;
		try{
			//Create an object of FileOutputStream
			//PrintWriter writer = new PrintWriter(file, "UTF-8");
			FileOutputStream fos = new FileOutputStream(file);//new File(file));
			//FileWriter writer = new FileWriter(file);
			//create an object of BufferedOutputStream
			//BufferedOutputStream bos = new BufferedOutputStream(fos);
			byte[] table = this.table;
			/*for (int i=0; i<table.length; i++) {
				fos.write(table[i]);
				//fos.write(",");
				//fos.write("\n");
			}*/
			//writer.flush();
			//writer.close();
			//writer.close();
			fos.write(table);
			fos.close();
		}catch(IOException e){
			System.err.println(e);
		}
	}

	public void makeCubieTable() throws FileNotFoundException {
		goal = new RubixCube("goal-cubies.txt");
		table = new byte[88179840]; //280805332   561610664

		branch(goal, (byte)0);
	}

	public void branch(RubixCube state, byte count) {
		shit++;
		if (++fuck % 1000000 == 0) {
			System.out.println("" + fuck + " recursions: " + shit + " active.");
		}
		
		int index = getIndex(state);
		//System.out.println(index);
		if (table[index] > count || table[index] == (byte)0) {
			table[index] = count++;
			for (byte i = R; i <= W; i++) {
				branch(state.rotateCube(i), count);
			}
		}
		shit--;
	}

	public int getIndex(RubixCube state) {
		byte[] cubie = new byte[3];
		int[] indexA = new int[7];
		int counter = 0;
		ArrayList<Integer> remaining = new ArrayList<Integer>(8);
		for (int cocksandwich=0; cocksandwich<8; cocksandwich++) {
			remaining.add(cocksandwich);
		}
		String indexB = "0";
		int currentCubie, otherCubie, currentOrientation;
		
		for (byte i=0; i<CCL.length-1; i++) {

			for (byte j=0; j<3; j++) {
				cubie[j] = state.cube[CCL[i][j][0]] [CCL[i][j][1]];
			}

			for (byte z=0; z<CCV.length; z++) {
				if (cubie[0] == CCV[z][0] && cubie[1] == CCV[z][1] && cubie[2] == CCV[z][2]) {
					currentCubie = z / 3;
					currentOrientation = z % 3;

					indexA[i] = currentCubie;
					otherCubie = currentCubie;
					counter = 0;
					while (otherCubie >= 0) {
						if (!remaining.contains(otherCubie--))
							counter++;
					}
					indexA[i] -= counter;
					remaining.remove(new Integer(currentCubie));
					
					indexB += currentOrientation;

					break;
				}
			}
		}
		int a = indexA[6] +
				indexA[5] * 2 +
				indexA[4] * 6 +
				indexA[3] * 24 +
				indexA[2] * 120 +
				indexA[1] * 720 +
				indexA[0] * 5040;
		int b = Integer.parseInt(indexB, 3);
		//System.out.println(Arrays.toString(indexA));
		//System.out.println("****a: " + a + "b: " + b + "****");
		return a * 2187 + b;
	}

}
