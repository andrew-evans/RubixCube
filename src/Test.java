import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;


class Test {

	public static void main(String[] args) throws IOException {
		
		TableGenerator test = new TableGenerator();
		test.main();
		
		//File f = new File("heuristic-tables/cubie-table.txt");
		//Scanner s = new Scanner(f);
		
		
		//System.out.println(f.exists());
		/*FileInputStream fis = new FileInputStream("heuristic-tables/cubie-table.txt");
		int c;
		while((c = fis.read()) != 0){
			System.out.println(c);
		}*/
		
		//fis.close();
		/*
		 * RubixCube test = new RubixCube("rubbix.txt");
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
		*/
	}
	
	public String IDA(RubixCube cube, byte costSoFar) throws FileNotFoundException{
		boolean solutionFound = false;
		double bound = 1;
		byte[] hist = new byte[6];
		RubixCube solution = null;
		RubixCube goal = new RubixCube("goal-cubies.txt");
		while (!solutionFound){
			solution = aStar(cube, costSoFar, rotationsSoFar, bound, goal, hist);
			if(solution.equals(goal)){
				solutionFound = true;
				return "";
			};
			bound += 1;
		}
	}
	
	public RubixCube aStar(RubixCube cube, byte costSoFar, double rotationsSoFar, double bound, byte goal, byte[] hist){
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
			//System.arraycopy(history, 0, actionPerformed, 0, actionPerformed.length);
			history[indexOfBest] += 1;
			
			if (smallestFn == goal){
				return cubeArray[indexOfBest];
			}
			else{
				//increase costSoFar
				rotationsSoFar += 1;
				//I don't think I need this but idk
				RubixCube solution = aStar(cubeArray[indexOfBest], costSoFar, rotationsSoFar, bound, goal, history);
				if(solution.equals(goal)){
					return solution;
				}
			}
		}
		
		/*node1.rotateCube(R);
		node2.rotateCube(R);
		System.out.println(node1.toString());
		System.out.println(node2.toString());
		*/
	}
	
	public int heuristic(RubixCube cube){
		
		
		return 0;
		
	}
}

