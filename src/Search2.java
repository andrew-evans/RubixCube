import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;

// :3
class Search2 {
	
	private byte[] table1 = new byte[88179840];
	private byte[] table2 = new byte[42577920];
	private byte[] table3 = new byte[42577920];
	private final byte R = 0;
	private final byte G = 1;
	private final byte Y = 2;
	private final byte B = 3;
	private final byte O = 4;
	private final byte W = 5;
	private ArrayList<Integer> history = new ArrayList<Integer>();
	public PriorityQueue<RubixCube> frontier;
	private boolean success = false;
	private byte[] faceArray = {R,G,Y,B,O,W};
	final RubixCube goalCube;
	final RubixCube inputCube;
	
	public Search2(String inputCubeFile) throws IOException {
		
		//TableGenerator test = new TableGenerator();
		
		//test.main();
		
		this.goalCube = new RubixCube("goal.txt");
		this.inputCube = new RubixCube(inputCubeFile);
		this.frontier = new PriorityQueue<RubixCube>(100,new RubixCubeComparator());
		
		//FileInputStream fis1 = new FileInputStream("heuristic-tables/cornercubie-table.txt");
		int index = 0;
		/*while(index < table1.length){
			if(index % 1000000 == 0){
				System.out.println("Reading table1...."+index);
			}
			table1[index] = (byte)fis1.read();
			index++;
		}
		
		fis1.close();*/
		
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
		/*
		FileInputStream fis3 = new FileInputStream("heuristic-tables/edge2cubie-table.txt");
		index = 0;
		while(index < table3.length){
			if(index % 1000000 == 0){
				System.out.println("Reading table3...."+index);
			}
			table3[index] = (byte)fis3.read();
			index++;
		}
		
		fis3.close();*/
	}
	
	public ArrayList<Integer> ASearch() throws FileNotFoundException{
		int bound = 0;
		while (!success) {
			System.out.println("Searching with bound " + bound);
			frontier.clear();
			this.history = aStar(this.inputCube, new ArrayList<Integer>(0), bound);
			bound += 1;
		}
		
		return this.history;
	}
	
	public ArrayList<Integer> aStar(RubixCube state, ArrayList<Integer> hist, int bound) {
		if (state.cost >= bound) {
			return hist;
		}
		
		if (heuristic(state) == 0) {
			this.success = true;
			return hist;
		}
		
		byte i;
		for (i=0; i<6; i++) {
			RubixCube current = RubixCube.newInstance(state.rotateCube(i).getCube(), state.functionVal, state.cost + 1, i);
			current.functionVal = current.cost + heuristic(current);
			frontier.add(current);
			System.out.println("Rotate " + i + ":  " + current.cost + " + " + heuristic(current));
		}
		
		RubixCube nextState = frontier.remove();
		System.out.println("Chose state with function value " + nextState.functionVal);
		hist.add((int) nextState.lastMove);
		
		System.out.println(nextState);
		return aStar(nextState, hist, bound);
	}
	
	public int heuristic(RubixCube cube){
		//return (int)this.table2[cube.getIndexEdge1()];
		return Math.max(Math.max((int)this.table1[cube.getIndexCorner()],(int)this.table2[cube.getIndexEdge1()]),(int)this.table3[cube.getIndexEdge2()]);
	}
}

