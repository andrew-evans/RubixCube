import java.io.FileNotFoundException;


class Test {

	public static void main(String[] args) throws FileNotFoundException {
		RubixCube test = new RubixCube();
		test.loadFromFile("rubbix.txt");
	}

}
