import java.io.IOException;
import java.util.Date;


public class Test2 {

	public static void main(String[] args) throws IOException {
		Search s = new Search("rubbix.txt");
		long start = new Date().getTime();
		System.out.println("Started Search");
		System.out.println(s.IDA());
		long end = new Date().getTime() - start;
		System.out.println("----------Finish!-------------   "+ end/60000 + " min to complete.");
		//System.out.println(s.frontier.toString());
		//System.out.println("------------------------------");

	}

}
