import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.PriorityQueue;


class Search {
	
	protected byte[] table1 = new byte[88179840];
	protected byte[] table2 = new byte[42577920];
	protected byte[] table3 = new byte[42577920];

	public PriorityQueue<RubixCube> frontier;
	public RubixCube[] fArray;
	final RubixCube goalCube;
	final RubixCube inputCube;
	private boolean success = false;
	public Search(String inputCubeFile) throws IOException {
		
		this.goalCube = new RubixCube("goal.txt");
		this.inputCube = new RubixCube(inputCubeFile);
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

	}
	
	public String IDA() throws FileNotFoundException{

		int bound = 1;
		String poop = "";
		//ArrayList<Integer> path = new ArrayList<Integer>();

		while (!success){
			this.frontier.clear();

			//int rotationsSoFar = 0;
			int costSoFar = 0;
			poop = "";
			//path = new ArrayList<Integer>(); 
			this.frontier.add(this.inputCube);
			poop = aStar(this.frontier.remove(), costSoFar, bound, poop);

			bound += 1;
		}
		
		return poop;
	}
	
	public String aStar(RubixCube cube, int costSoFar, int bound, String hist){
	
		if (heuristic(cube) == 0) {
			this.success = true;
			return hist;
		}
		
		if(costSoFar < bound){
			
			costSoFar += 1;
			
			for(byte i=0; i<6; i++){
				RubixCube node = RubixCube.newInstance(cube.getCube());
				node.rotateCube(i);
				node.setfunctionVal(costSoFar + heuristic(node));
				frontier.add(node);
			}
			
			for(byte i=0; i<6; i++){
				RubixCube node2 = RubixCube.newInstance(cube.getCube());
				node2.rotateCube(i);
				node2.rotateCube(i);
				node2.setfunctionVal(costSoFar + heuristic(node2));
				frontier.add(node2);
			}
			
			for(byte i=0; i<6; i++){
				RubixCube node3 = RubixCube.newInstance(cube.getCube());
				node3.rotateCube(i);
				node3.rotateCube(i);
				node3.rotateCube(i);
				node3.setfunctionVal(costSoFar + heuristic(node3));
				frontier.add(node3);
			}
			
			

			hist += "" + byteToFace(this.frontier.element().lastMove) + "" + this.frontier.element().lastTurns;
			System.out.println(hist + "                 " + byteToFace(this.frontier.element().lastMove) + "" + this.frontier.element().lastTurns + "\n");
			//hist.addAll(this.frontier.element().lastMoveList);
			
			this.fArray = this.frontier.toArray(new RubixCube[0]);
			for (RubixCube cubez : fArray) {
				System.out.print("  " + cubez.functionVal);
			}
			
			System.out.println(this.frontier.element());
			System.out.println(this.frontier.element().lastMoveList + " fn val of: "+this.frontier.element().getfunctionVal());
			return aStar(this.frontier.remove(), costSoFar, bound, hist);
		}
		return hist;

	}
	
	public int heuristic(RubixCube cube){
		return Math.max(Math.max((int)this.table1[cube.getIndexCorner()],(int)this.table2[cube.getIndexEdge1()]),(int)this.table3[cube.getIndexEdge2()]);
	}
	public String convertPath(ArrayList<Integer> oldPath){
		int previous = -1;
		int[] faceCounter = {0,0,0,0,0,0};
		String[] faces = {"R","G","Y","B","O","W"};
		String newPath = "";
		for (int face : oldPath){
			if(previous==face){
				faceCounter[face] += 1; 
			}else{
				if(previous != -1){
					newPath = newPath + faces[previous] + faceCounter[previous] + " ";
					faceCounter[previous] = 0;
				}
				faceCounter[face] = 1;
			}
			previous = face;
		}
		return newPath;
	}
	
	public String byteToFace(byte face) {
		String[] faces = {"R","G","Y","B","O","W"};
		return faces[face];
	}
	
	public void setCube(String inputCubeFile) {
		this.inputCube = new RubixCube(inputCubeFile);
	}
}

