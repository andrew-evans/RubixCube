
import java.io.*;
import java.util.*;

public class TableGenerator {

	private RubixCube goal;

	public void makeCubieTable() throws FileNotFoundException {
		goal.loadFromFile("goal-cubies.txt");
	}

}
