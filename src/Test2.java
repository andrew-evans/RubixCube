import java.io.IOException;
import java.util.Date;


public class Test2 {

	public static void main(String[] args) throws IOException {
		String fileString = "samplestates/cube";
		
		Search s = new Search("samplestates/cube00");
		//Search2 s = new Search2("samplestates/cube03");
		
		for (int i=0; i<=20; i+=1) {
			long start = new Date().getTime();
			System.out.println("Started Search " + i);
			String file="";
			if (i < 10){
				file = fileString + "0" + i;
			}
			else{
				file = fileString + i;
			}
			s.success = false;
			s.setCube(file);
		
			System.out.println(s.IDA());
			//System.out.println(s.ASearch());
		
			long end = new Date().getTime() - start;
			System.out.println("----------Finish!-------------   "+ end + " msec to complete.");
		}
	}

}
