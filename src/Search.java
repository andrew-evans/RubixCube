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
		ArrayList<Integer> path = new ArrayList<Integer>();

		while (!success){
			this.frontier.clear();

			int rotationsSoFar = 0;
			int costSoFar = 0;

			path = new ArrayList<Integer>(); 
			this.frontier.add(this.inputCube);
			path = aStar(this.frontier.remove(), costSoFar, rotationsSoFar, bound, new ArrayList<Integer>());

			bound += 1;
		}
		
		return convertPath(path);
	}
	
	public ArrayList<Integer> aStar(RubixCube cube, int costSoFar, int rotationsSoFar, int bound, ArrayList<Integer> hist){
	
		if (heuristic(cube) == 0) {
			this.success = true;
			return hist;
		}
		
		if(rotationsSoFar < bound){
			
			for(byte i=0; i<6; i++){
				RubixCube node = RubixCube.newInstance(cube.getCube());
				node.rotateCube(i);
				node.setfunctionVal(costSoFar + heuristic(node));
				frontier.add(node);
			}
			
			for(byte i=0; i<6; i++){
				RubixCube node = RubixCube.newInstance(cube.getCube());
				node.rotateCube(i).rotateCube(i);
				node.setfunctionVal(costSoFar + heuristic(node));
				frontier.add(node);
			}
			
			for(byte i=0; i<6; i++){
				RubixCube node = RubixCube.newInstance(cube.getCube());
				node.rotateCube(i).rotateCube(i).rotateCube(i);
				node.setfunctionVal(costSoFar + heuristic(node));
				frontier.add(node);
			}
			
			rotationsSoFar += 1;
			costSoFar += 1;

			hist.addAll(this.frontier.element().lastMoveList);
			System.out.println(this.frontier.element());
			System.out.println(this.frontier.element().lastMoveList + " fn val of: "+this.frontier.element().getfunctionVal());
			return aStar(this.frontier.remove(), costSoFar, rotationsSoFar, bound, hist);
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
}

