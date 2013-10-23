import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;


class Search {
	
	protected byte[] table1 = new byte[88179840];
	protected byte[] table2 = new byte[42577920];
	protected byte[] table3 = new byte[42577920];
	private final byte R = 0;
	private final byte G = 1;
	private final byte Y = 2;
	private final byte B = 3;
	private final byte O = 4;
	private final byte W = 5;
	private String history = "Moves: ";
	public PriorityQueue<RubixCube> frontier;
	private byte[] faceArray = {R,G,Y,B,O,W};
	final RubixCube goalCube;
	final RubixCube inputCube;
	public Search(String inputCubeFile) throws IOException {
		
		//TableGenerator test = new TableGenerator();
		
		//test.main();
		
		this.goalCube = new RubixCube("goal.txt");
		this.inputCube = new RubixCube(inputCubeFile);
		this.inputCube.rotateCube(Y).rotateCube(Y).rotateCube(Y).rotateCube(B).rotateCube(B).rotateCube(B);
		this.frontier = new PriorityQueue<RubixCube>(11,new RubixCubeComparator());
		
		FileInputStream fis1 = new FileInputStream("heuristic-tables/cornercubie-table.txt");
		int index = 0;
		while(index < table1.length){
			if(index % 1000000 == 0){
				System.out.println("Reading table1...."+index);
			}
			table1[index] = (byte)fis1.read();
			index++;
		}
		
		fis1.close();
		
		FileInputStream fis2 = new FileInputStream("heuristic-tables/edge1cubie-table.txt");
		index = 0;
		while(index < table2.length){
			if(index % 1000000 == 0){
				System.out.println("Reading table2...."+index);
			}
			table2[index] = (byte)fis2.read();
			index++;
		}
		
		fis2.close();
		
		FileInputStream fis3 = new FileInputStream("heuristic-tables/edge2cubie-table.txt");
		index = 0;
		while(index < table3.length){
			if(index % 1000000 == 0){
				System.out.println("Reading table3...."+index);
			}
			table3[index] = (byte)fis3.read();
			index++;
		}
		
		fis3.close();
		
		
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
	
	public ArrayList<Integer> IDA() throws FileNotFoundException{
		boolean solutionFound = false;
		int bound = 1;
		ArrayList<Integer> path = new ArrayList<Integer>();
		//System.out.println(heuristic(this.goalCube));
		while (!solutionFound){
			this.frontier.clear();
			//System.out.println("Beginning of While: "+rotationsSoFar);
			this.history = "Moves: ";
			int rotationsSoFar = 0;
			int costSoFar = 0;
			//RubixCube solution = null;
			
			this.frontier.add(this.inputCube);
			path = aStar(this.frontier.remove(), costSoFar, rotationsSoFar, bound, new ArrayList<Integer>());//, this.hist);
			/*if(Arrays.deepEquals(solution.getCube(), this.goalCube.getCube())){
				solutionFound = true;
			};*/
			 bound += 1;
		}
		return path;
		//return this.history;
	}
	
	public ArrayList<Integer> aStar(RubixCube cube, int costSoFar, int rotationsSoFar, int bound, ArrayList<Integer> hist){
	
		if (heuristic(cube) == 0) {
			return hist;
		}
			
		/*if(rotationsSoFar % 1000 == 0){
			System.out.println("A* working..."+bound);
		}*/
		//System.out.println("1st best node with bound: "+bound+" and rotation: "+rotationsSoFar+"\n"+cube);
		if(rotationsSoFar < bound){
			
			//byte[] history = hist;
			//byte fn = costSoFar + heuristic(cube);
			RubixCube node1 = RubixCube.newInstance(cube.getCube());
			RubixCube node2 = RubixCube.newInstance(cube.getCube());
			RubixCube node3 = RubixCube.newInstance(cube.getCube());
			RubixCube node4 = RubixCube.newInstance(cube.getCube());
			RubixCube node5 = RubixCube.newInstance(cube.getCube());
			RubixCube node6 = RubixCube.newInstance(cube.getCube());
			RubixCube[] cubeArray = {node1,node2,node3,node4,node5,node6};
			
			int[] fnArray = {-1,-1,-1,-1,-1,-1};
			/*node1.rotateCube(R);
			System.out.println(node1.toString());
			node2.rotateCube(R);
			System.out.println(node2.toString());*/
			
			for(int i=0; i<cubeArray.length; i++){
				//cubeArray[i].rotateCube(faceArray[i]).rotateCube(faceArray[i]).rotateCube(faceArray[i]);
				//System.out.println(i);
				//System.out.println("ORIGINAL: \n"+cubeArray[i]);
				cubeArray[i].rotateCube(this.faceArray[i]);
				//System.out.println("ROTATED: \n"+cubeArray[i]);
				//System.out.println(Arrays.toString(cubeArray));
				//int heuristic = heuristic(cubeArray[i]);
				fnArray[i] = costSoFar + heuristic(cubeArray[i]);
				cubeArray[i].setfunctionVal(fnArray[i]);
			}
			
			
			for(int i=0; i<cubeArray.length; i++){
				frontier.add(cubeArray[i]);
			}
			
			//System.out.println(Arrays.toString(fnArray));
			//int smallestFn = 9999;
			byte indexOfBest = -1;
			/*			
			for(byte i=0; i<fnArray.length; i++){
				if (fnArray[i]<=smallestFn){
					smallestFn = fnArray[i];
					indexOfBest = i;
				}
			}*/
			//System.out.println(indexOfBest);
			//history[indexOfBest] += 1;
			//System.out.println("---------PQ---------");
			//System.out.println(this.frontier.toString());
			//System.out.println("---------PQ---------");
			//System.out.println("2nd best node with bound: "+bound+" and rotation: "+rotationsSoFar+"\n"+this.frontier.element().toString());
			//System.out.println("Heuristic: "+this.frontier.element().getHeuristic());
			//System.out.println("Passed recursively from array @ index "+indexOfBest+"(lowest fn=" +fnArray[indexOfBest] +" ):\n"+cubeArray[indexOfBest].toString());
			//System.out.println("Passed recursively from PQ(lowest fn="+this.frontier.element().getfunctionVal()+" val):\n"+this.frontier.element().toString());
			rotationsSoFar += 1;
			costSoFar += 1;
			/*for(byte i=0; i<cubeArray.length; i++){
				if(this.frontier.element().getCube() == cubeArray[i].getCube()){
					indexOfBest = i;
				}
			}*/
			this.history += this.frontier.element().lastMove + ", ";
			hist.add((int) this.frontier.element().lastMove);
			return aStar(this.frontier.remove(), costSoFar, rotationsSoFar, bound, hist);
		}
		return hist;
			
			/*if (Arrays.deepEquals(this.frontier.element().getCube(), this.goalCube.getCube()) ){
				return this.frontier.remove();
			}
			else{
				// - costSoFar;
				//return aStar(this.frontier.remove(), costSoFar, rotationsSoFar, bound, hist);//, this.hist);
				//if(Arrays.deepEquals(solution.getCube(), this.goalCube.getCube())){
				//	return hist;
				//}
			}
		}
		//System.out.println("bound reached in IDA"+bound);
		//return cube;
		
		/*node1.rotateCube(R);
		node2.rotateCube(R);
		System.out.println(node1.toString());
		System.out.println(node2.toString());
		*/
	}
	
	public int heuristic(RubixCube cube){
		//System.out.println(""+(int)this.table1[cube.getIndexCorner()]);
		//System.out.println(""+(int)this.table2[cube.getIndexEdge1()]);
		//System.out.println(""+(int)this.table3[cube.getIndexEdge2()]);
		return Math.max(Math.max((int)this.table1[cube.getIndexCorner()],(int)this.table2[cube.getIndexEdge1()]),(int)this.table3[cube.getIndexEdge2()]);
	}
}

