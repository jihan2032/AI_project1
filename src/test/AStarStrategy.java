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
import main.RollTheBall;
import main.Tile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * @author MacBookAir
 *
 */

public class AStarStrategy {
	public String fileName = "boards";
	public Board[] boards;
	
	public Board[] readFile() {
		Board[] boards = new Board[0];
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			int numberOfBoards = Integer.parseInt(line);
			boards = new Board[numberOfBoards];
			for (int i = 1; i < numberOfBoards; i++) {
				line = br.readLine();
				String[] dimensions = line.split(" ");
				int rows = Integer.parseInt(dimensions[0]);
				int cols = Integer.parseInt(dimensions[1]);
				Board b = new Board(rows, cols);
				for (int row  = 0; row < rows; row++) {
					line = br.readLine();
					String[] rowData = line.split(" ");
					for(int col = 0; col < cols; col++) {
						Tile t = new Tile(b, row, col);
						b.grid[row][col] = t;
						t.setMovable(rowData[col].charAt(rowData[col].length()-1) == 'm');
						t.setType(Integer.parseInt(rowData[col].substring(0,2)));
					}
				}
				boards[i] = b;
			}
			// line is not visible here.
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return boards;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		boards = readFile();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		
	}

}
