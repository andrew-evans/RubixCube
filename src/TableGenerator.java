
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

		String file = "/heuristic-tables/cubie-table.txt";
		//BufferedOutputStream bos = null;
		try{
			//Create an object of FileOutputStream
			FileOutputStream fos = new FileOutputStream(new File(file));
			
			//create an object of BufferedOutputStream
			//bos = new BufferedOutputStream(fos);
			byte[] table = this.table;
			fos.write(table);
			fos.close();
		}catch(IOException e){
			System.err.println(e);
		}
	}

	public void makeCubieTable() throws FileNotFoundException {
		goal = new RubixCube("goal-cubies.txt");
		table = new byte[561610664]; //280805332

		branch(goal, (byte)0);
	}

	public void branch(RubixCube state, byte count) {
		shit++;
		if (++fuck % 1000000 == 0) {
			System.out.println("" + fuck + " recursions: " + shit + " active.");
		}
		int index = getIndex(state);
		System.out.println(index);
		if ((table[index] > count || table[index] == (byte)0) && count<= 11) {
			table[index] = count++;
			for (byte i = R; i <= W; i++) {
				branch(state.rotateCube(i), count);
			}
		}
		shit--;
	}

	public int getIndex(RubixCube state) {
		byte[] cubie = new byte[3];
		String indexA = "0";
		String indexB = "0";
		int currentCubie, currentOrientation;
		
		for (byte i=0; i<CCL.length-1; i++) {

			for (byte j=0; j<3; j++) {
				cubie[j] = state.cube[CCL[i][j][0]] [CCL[i][j][1]] ;
			}

			for (byte z=0; z<CCV.length; z++) {
				if (cubie[0] == CCV[z][0] && cubie[1] == CCV[z][1] && cubie[2] == CCV[z][2]) {
					currentCubie = z / 3;
					currentOrientation = z % 3;

					indexA += currentCubie;
					indexB += currentOrientation;

					break;
				}
			}
		}
		int a = Integer.parseInt(indexA, 8);
		int b = Integer.parseInt(indexB, 3);
		
		System.out.println("****a: " + a + "b: " + b + "****");
		return a * 2187 + b;
	}

}
