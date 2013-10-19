import java.io.FileNotFoundException;


class Test {

	public static void main(String[] args) throws FileNotFoundException {
		RubixCube test = new RubixCube("rubbix.txt");
		RubixCube test2 = new RubixCube("rubbix.txt");
		
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
		
		//System.out.println(test.toString());
		
		//aStar(test2,R);
	}
	
	public void IDA(RubixCube cube, byte costSoFar){
		boolean solutionFound = false;
		double bound = 1;
		//Something solution = null;
		while (!solutionFound){
			//solution = aStar(cube, costSoFar, rotationsSoFar, bound, goal);
			//if(solution == goal){return solutionFound};
			bound += 1;
		}
	}
	
	public static void aStar(RubixCube cube, byte costSoFar, double rotationsSoFar, double bound, byte goal, byte[] hist){
		if(rotationsSoFar != bound){
			byte[] byteArray = {R,G,Y,B,O,W};
			byte[] history = hist;
			//byte fn = costSoFar + heuristic(cube);
			RubixCube node1 = cube;
			RubixCube node2 = cube;
			RubixCube node3 = cube;
			RubixCube node4 = cube;
			RubixCube node5 = cube;
			RubixCube node6 = cube;
			RubixCube[] cubeArray = {node1,node2,node3,node4,node5,node6};
			byte[] fnArray = {-1,-1,-1,-1,-1,-1};
			
			
			for(int i=0; i<cubeArray.length; i++){
				cubeArray[i].rotateCube(byteArray[i]);
				fnArray[i] = costSoFar + heuristic(cubeArray[i]);
			}
			
			byte smallestFn = 127;
			byte indexOfBest = -1;
			for(byte i=0; i<fnArray.length; i++){
				if (fnArray[i]<smallestFn){
					smallestFn = fnArray[i];
					indexOfBest = i;
				}
			}
			
			byte[] actionPerformed = {indexOfBest};
			System.arraycopy(history, 0, actionPerformed, 0, actionPerformed.length);
			
			if (smallestFn == goal){
				//return cubeArray[i];
			}
			else{
				//increase costSoFar
				rotationsSoFar += 1;
				aStar(cubeArray[indexOfBest], costSoFar, rotationsSoFar, bound, goal, history);
			}
		}
		
		/*node1.rotateCube(R);
		node2.rotateCube(R);
		System.out.println(node1.toString());
		System.out.println(node2.toString());
		*/
	}
}

