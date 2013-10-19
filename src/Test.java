import java.io.FileNotFoundException;
import java.util.Arrays;


class Test {

	public static void main(String[] args) throws FileNotFoundException {
		RubixCube test = new RubixCube("rubbix.txt");
		
		byte R = 0;
		byte G = 1;
		byte Y = 2;
		byte B = 3;
		byte O = 4;
		byte W = 5;
		
		test.rotateCube(R);
		test.rotateCube(G);
		test.rotateCube(Y);
		test.rotateCube(B);
		test.rotateCube(O);
		test.rotateCube(W);
		
		System.out.println(test.toString());
		
		
		if(!solutionFound){
			
		}
		
		
	}

}
