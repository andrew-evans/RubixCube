import java.io.IOException;
import java.util.Date;


public class Test2 {

	public static void main(String[] args) throws IOException {

		Search s = new Search("samplestates/cube04");
		//Search2 s = new Search2("samplestates/cube03");
		
		long start = new Date().getTime();
		System.out.println("Started Search");
		
		System.out.println(s.IDA());
		//System.out.println(s.ASearch());
		
		long end = new Date().getTime() - start;
		System.out.println("----------Finish!-------------   "+ end + " msec to complete.");

	}

}
