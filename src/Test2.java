import java.io.IOException;
import java.util.Date;


public class Test2 {

	public static void main(String[] args) throws IOException {
		Search s = new Search("goal.txt");
		RubixCube goal = new RubixCube("goal.txt");
		System.out.println(""+(int)s.table1[goal.getIndexCorner()]);
		System.out.println(""+(int)s.table2[goal.getIndexEdge1()]);
		System.out.println(""+(int)s.table3[goal.getIndexEdge2()]);
		long start = new Date().getTime();
		System.out.println("Started Search");
		System.out.println(s.IDA());
		long end = new Date().getTime() - start;
		System.out.println("----------Finish!-------------   "+ end/60000 + " min to complete.");
		//System.out.println(s.frontier.toString());
		//System.out.println("------------------------------");

	}

}
