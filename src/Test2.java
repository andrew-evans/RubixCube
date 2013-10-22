import java.io.IOException;


public class Test2 {

	public static void main(String[] args) throws IOException {
		Search s = new Search("rubbix.txt");
		System.out.println(s.IDA());
		System.out.println("------------------------------");
		System.out.println(s.frontier.toString());
		System.out.println("------------------------------");

	}

}
