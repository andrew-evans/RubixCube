import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


class Test {

	public static void main(String[] args) throws FileNotFoundException {
		RubixCube test = new RubixCube();
		test.loadFromFile("rubbix.txt");
	}

}
