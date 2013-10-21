import java.io.IOException;


public class Test {

	public static void main(String[] args) throws IOException {
		Search s = new Search();
		s.init("rubbix.txt");
		System.out.println(s.IDA());
	}

}
