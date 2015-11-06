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
import java.util.LinkedList;

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

public class PredefinedBoards {
	public String fileName = "boards";
	public Board[] boards;
	public RollTheBall[] algorithmsContainers;
	
	public Board[] readFile() {
		Board[] boards = new Board[0];
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line = br.readLine();
			int numberOfBoards = Integer.parseInt(line);
//			int numberOfBoards = 1;
			boards = new Board[numberOfBoards];
			for (int i = 0; i < numberOfBoards; i++) {
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
		algorithmsContainers = new RollTheBall[boards.length];
		for (int i = 0; i < algorithmsContainers.length; i++) {
			algorithmsContainers[i] = new RollTheBall(boards[i]);
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	
	@Test(timeout=10000)
	public void testGreedyH1() {
		for (int i = 0; i < algorithmsContainers.length; i++) {
			RollTheBall algorithmContainer = algorithmsContainers[i];
			Board resultBoard = algorithmContainer.search(boards[i], RollTheBall.greedy_h1_strategy, false).getLast();
			System.out.println("Greedy H1 - " + i);
			System.out.println(resultBoard);
			System.out.println();
			System.out.println("============================");
			assert true;
		}
		assert true;
	}
	
	@Test(timeout=10000)
	public void testGreedyH2() {
		for (int i = 0; i < algorithmsContainers.length; i++) {
			RollTheBall algorithmContainer = algorithmsContainers[i];
			Board resultBoard = algorithmContainer.search(boards[i], RollTheBall.greedy_h2_strategy, false).getLast();
			System.out.println("Greedy H2 - " + i);
			System.out.println(resultBoard);
			System.out.println();
			System.out.println("============================");
			assert true;
		}
		assert true;
	}
	
	@Test(timeout=10000)
	public void testAStar() {
		for (int i = 0; i < algorithmsContainers.length; i++) {
			RollTheBall algorithmContainer = algorithmsContainers[i];
			LinkedList<Board> resultBoards = algorithmContainer.search(boards[i], RollTheBall.a_strategy, false);
			System.out.println("A star - " + i);
			System.out.println(boards[i]);
			for (int j = 0; j < resultBoards.size(); j++) {
				System.out.println(j);
				System.out.println(resultBoards.get(j));
			}
			System.out.println();
			System.out.println("============================");
			assert true;
		}
		assert true;
	}
	@Test(timeout=10000)
	public void testBFS() {
		for (int i = 0; i < algorithmsContainers.length; i++) {
			RollTheBall algorithmContainer = algorithmsContainers[i];
			LinkedList<Board> resultBoards = algorithmContainer.search(boards[i], RollTheBall.bfs_strategy, false);
			System.out.println("BFS - " + i);
			System.out.println(boards[i]);
			for (int j = 0; j < resultBoards.size(); j++) {
				System.out.println(j);
				System.out.println(resultBoards.get(j));
			}
			System.out.println();
			System.out.println("============================");
			assert true;
		}
	}
	
	@Test(timeout=10000)
	public void testDFS() {
		for (int i = 0; i < algorithmsContainers.length; i++) {
			RollTheBall algorithmContainer = algorithmsContainers[i];
			LinkedList<Board> resultBoards = algorithmContainer.search(boards[i], RollTheBall.dfs_strategy, false);
			System.out.println("DFS - " + i);
			System.out.println(boards[i]);
			for (int j = 0; j < resultBoards.size(); j++) {
				System.out.println(j);
				System.out.println(resultBoards.get(j));
			}
			System.out.println();
			System.out.println("============================");
			assert true;
		}
	}
	@Test(timeout=10000)
	public void testIterative() {
		for (int i = 0; i < algorithmsContainers.length; i++) {
			RollTheBall algorithmContainer = algorithmsContainers[i];
			LinkedList<Board> resultBoards = algorithmContainer.search(boards[i], RollTheBall.iterative_strategy, false);
			System.out.println("Iterative - " + i);
			System.out.println(boards[i]);
			for (int j = 0; j < resultBoards.size(); j++) {
				System.out.println(j);
				System.out.println(resultBoards.get(j));
			}
			System.out.println();
			System.out.println("============================");
			assert true;
		}
	}
}
