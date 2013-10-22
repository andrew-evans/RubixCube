import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;


class Search {
	
	private byte[] table = new byte[88179840];
	private final byte R = 0;
	private final byte G = 1;
	private final byte Y = 2;
	private final byte B = 3;
	private final byte O = 4;
	private final byte W = 5;
	private String history = "";
	public PriorityQueue<RubixCube> frontier;
	private byte[] faceArray = {R,G,Y,B,O,W};
	final RubixCube goalCube;
	final RubixCube inputCube;
	public Search(String inputCubeFile) throws IOException {
		
		//TableGenerator test = new TableGenerator();
		
		//test.main();
		
		this.goalCube = new RubixCube("goal.txt");
		this.inputCube = new RubixCube(inputCubeFile);
		this.frontier = new PriorityQueue<RubixCube>(11,new RubixCubeComparator());
		
		FileInputStream fis = new FileInputStream("heuristic-tables/cubie-table.txt");
		int index = 0;
		while(index < table.length){
			if(index % 1000000 == 0){
				System.out.println("Reading...."+index);
			}
			table[index] = (byte)fis.read();
			index++;
		}
		
		fis.close();
		
		
		
		//File f = new File("heuristic-tables/cubie-table.txt");
		//Scanner s = new Scanner(f);
		
		
		//System.out.println(f.exists());
		
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
	
	public String IDA() throws FileNotFoundException{
		boolean solutionFound = false;
		int bound = 1;
		
		while (!solutionFound){
			//System.out.println("Beginning of While: "+rotationsSoFar);
			this.history = "";
			int rotationsSoFar = 0;
			int costSoFar = 0;
			RubixCube solution = null;
			solution = aStar(this.inputCube, costSoFar, rotationsSoFar, bound);//, this.hist);
			if(Arrays.deepEquals(solution.cube, this.goalCube.cube)){
				solutionFound = true;
			};
			 bound += 1;
		}
		return this.history;
	}
	
	public RubixCube aStar(RubixCube cube, int costSoFar, int rotationsSoFar, int bound){//, byte[] hist){
		/*if(rotationsSoFar % 10 == 0){
			System.out.println("A*..."+bound);
		}*/
		System.out.println("1st best node with bound: "+bound+" and rotation: "+rotationsSoFar+"\n"+cube);
		if(rotationsSoFar < bound){
			
			//byte[] history = hist;
			//byte fn = costSoFar + heuristic(cube);
			RubixCube node1 = cube;
			RubixCube node2 = cube;
			RubixCube node3 = cube;
			RubixCube node4 = cube;
			RubixCube node5 = cube;
			RubixCube node6 = cube;
			RubixCube[] cubeArray = {node1,node2,node3,node4,node5,node6};
			
			int[] fnArray = {-1,-1,-1,-1,-1,-1};
			
			
			for(int i=0; i<cubeArray.length; i++){
				//cubeArray[i].rotateCube(faceArray[i]).rotateCube(faceArray[i]).rotateCube(faceArray[i]);
				cubeArray[i].rotateCube(this.faceArray[i]);
				int heuristic = heuristic(cubeArray[i]);
				cubeArray[i].setHeuristic(heuristic);
				fnArray[i] = costSoFar + heuristic;
			}
			
			if(bound==11 && rotationsSoFar == 4){
				System.out.println("***BEFORE SOLUTION***");
				System.out.println(cube);
				System.out.println(Arrays.toString(cubeArray));
				System.out.println("***BEFORE SOLUTION***");
			}
			
			for(int i=0; i<cubeArray.length; i++){
				frontier.add(cubeArray[i]);
			}
			
			//System.out.println(Arrays.toString(fnArray));
			int smallestFn = 9999;
			byte indexOfBest = -1;
			//need to resolve ties
			for(byte i=0; i<fnArray.length; i++){
				if (fnArray[i]<=smallestFn){
					smallestFn = fnArray[i];
					indexOfBest = i;
				}
			}
			//System.out.println(indexOfBest);
			//history[indexOfBest] += 1;
			
			System.out.println("2nd best node with bound: "+bound+" and rotation: "+rotationsSoFar+"\n"+this.frontier.element().toString());
			System.out.println("Heuristic: "+this.frontier.element().getHeuristic());
			rotationsSoFar += 1;
			if (Arrays.deepEquals(this.frontier.element().cube, this.goalCube.cube) ){
				return this.frontier.remove();
			}
			else{
				costSoFar += this.frontier.element().getHeuristic();
				this.history += indexOfBest;
				RubixCube solution = aStar(this.frontier.remove(), costSoFar, rotationsSoFar, bound);//, this.hist);
				if(Arrays.deepEquals(solution.cube, this.goalCube.cube)){
					return solution;
				}
			}
		}
		System.out.println("bound reached in IDA"+bound);
		return cube;
		
		/*node1.rotateCube(R);
		node2.rotateCube(R);
		System.out.println(node1.toString());
		System.out.println(node2.toString());
		*/
	}
	
	public int heuristic(RubixCube cube){
		return this.table[cube.getIndexCorner()];
	}
}

