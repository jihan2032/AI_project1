/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.Board;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * @author MacBookAir
 *
 */

public class AlgorithmsTest {
	public String fileName = "boards";

	public void readFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			int numberOfBoards = Integer.parseInt(line);
			for (int i = 1; i < numberOfBoards; i++) {
				line = br.readLine();
				String[] dimensions = line.split(" ");
				int rows = Integer.parseInt(dimensions[0]);
				int cols = Integer.parseInt(dimensions[1]);
				Board b = new Board(rows, cols);
				b.grid[0][1] = 0;
			}
			// line is not visible here.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
