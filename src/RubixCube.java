
import java.io.*;
import java.util.*;

public class RubixCube {

	private final byte R = 0;
	private final byte G = 1;
	private final byte Y = 2;
	private final byte B = 3;
	private final byte O = 4;
	private final byte W = 5;

	private byte[][] cube;

	public void loadFromFile(String filename) {
		File f = new File(filename);
		Scanner s = new Scanner(f);

		while(s.hasNext()) {
			
		}
	}

	public void rotateCube(byte face) {

		byte[][] start;
		for (int i=0; i<6; i++) {
			for (int j=0; j<8; j++) {
				start[i][j] = cube[i][j];
			}
		}

		cube[face][0] = start[face][5];
		cube[face][1] = start[face][3];
		cube[face][2] = start[face][0];
		cube[face][3] = start[face][6];
		cube[face][4] = start[face][1];
		cube[face][5] = start[face][7];
		cube[face][6] = start[face][4];
		cube[face][7] = start[face][2];

		switch (face) {
			case (R):
				cube[G][0] = start[Y][0];
				cube[G][1] = start[Y][1];
				cube[G][2] = start[Y][2];

				cube[Y][0] = start[B][0];
				cube[Y][1] = start[B][1];
				cube[Y][2] = start[B][2];

				cube[B][0] = start[W][7];
				cube[B][1] = start[W][6];
				cube[B][2] = start[W][5];

				cube[W][7] = start[G][0];
				cube[W][6] = start[G][1];
				cube[W][5] = start[G][2];
				break;

			case (G):
				cube[R][0] = start[W][0];
				cube[R][3] = start[W][3];
				cube[R][5] = start[W][5];

				cube[Y][0] = start[R][0];
				cube[Y][3] = start[R][3];
				cube[Y][5] = start[R][5];

				cube[O][0] = start[Y][0];
				cube[O][3] = start[Y][3];
				cube[O][5] = start[Y][5];

				cube[W][0] = start[O][0];
				cube[W][3] = start[O][3];
				cube[W][5] = start[O][5];
				break;

			case (Y):
				cube[R][5] = start[G][7];
				cube[R][6] = start[G][4];
				cube[R][7] = start[G][2];

				cube[G][7] = start[O][2];
				cube[G][4] = start[O][1];
				cube[G][2] = start[O][0];

				cube[B][0] = start[R][5];
				cube[B][3] = start[R][6];
				cube[B][5] = start[R][7];

				cube[O][0] = start[B][5];
				cube[O][1] = start[B][3];
				cube[O][2] = start[B][0];
				break;

			case (B):
				cube[R][2] = start[Y][2];
				cube[R][4] = start[Y][4];
				cube[R][7] = start[Y][7];

				cube[Y][2] = start[O][2];
				cube[Y][4] = start[O][4];
				cube[Y][7] = start[O][7];

				cube[O][2] = start[W][2];
				cube[O][4] = start[W][4];
				cube[O][7] = start[W][7];

				cube[W][2] = start[R][2];
				cube[W][4] = start[R][4];
				cube[W][7] = start[R][7];
				break;

			case (O):
				cube[G][5] = start[W][2];
				cube[G][6] = start[W][1];
				cube[G][7] = start[W][0];

				cube[Y][0] = start[G][0];
				cube[Y][1] = start[G][1];
				cube[Y][2] = start[G][2];

				cube[B][0] = start[Y][0];
				cube[B][1] = start[Y][1];
				cube[B][2] = start[Y][2];

				cube[W][0] = start[B][7];
				cube[W][1] = start[B][6];
				cube[W][2] = start[B][5];
				break;

			case (W):
				cube[R][0] = start[B][2];
				cube[R][1] = start[B][4];
				cube[R][2] = start[B][7];

				cube[G][0] = start[R][2];
				cube[G][3] = start[R][1];
				cube[G][5] = start[R][0];

				cube[B][2] = start[O][7];
				cube[B][4] = start[O][6];
				cube[B][7] = start[O][5];

				cube[O][5] = start[G][0];
				cube[O][6] = start[G][3];
				cube[O][7] = start[G][5];
				break;
		}
	}
}
