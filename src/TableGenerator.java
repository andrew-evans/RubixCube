
import java.io.*;
import java.util.*;

public class TableGenerator {

	private final byte R = 0;
	private final byte G = 1;
	private final byte Y = 2;
	private final byte B = 3;
	private final byte O = 4;
	private final byte W = 5;

	private byte[] table;

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

	public void main(String[] args) {
		makeCubieTable();

		Path file = "/heuristic-tables/cubie-table.txt";
		byte[] buffer = this.table;
		Files.write(file, buffer);
	}

	public void makeCubieTable() throws FileNotFoundException {
		goal.loadFromFile("goal-cubies.txt");
		table = new byte[561610664]; //280805332

		branch(goal, 0);
	}

	public void branch(RubixCube state, count) {
		int index = getIndex(state);
		if (table[index] > count || table[index] == 0) {
			table[index] = count;
			for (byte i = R; i <= W; i++) {
				branch(state.rotateCube(i, count);
			}
		}
	}

	public int getIndex(RubixCube state) {
		byte[] cubie = new byte[3];
		String indexA = "";
		String indexB = "";
		int currentCubie, currentOrientation;
		
		for (int i=0; i<CCL.length-1; i++) {

			for (int j=0; j<3; j++) {
				cubie[j] = state.cube[CCL[i][j][0]] [CCL[i][j][1]];
			}

			for (int z=0; z<CCV.length; z++) {
				if (cubie.equals(CCV[z])) {
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

		return a * 2187 + b;
	}

}
