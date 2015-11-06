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

public class RandomBoards {
	public String fileName = "boards";
	public Board[] boards;
	public RollTheBall[] algorithmsContainers;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		boards = new Board[1];
		boards[0] = RollTheBall.genGrid(6, 6);
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
			LinkedList<Board> resultBoards = algorithmContainer.search(boards[i], RollTheBall.greedy_h1_strategy, false);
			System.out.println("Greedy H1 - " + i);
			System.out.println(resultBoards.getLast());
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
			LinkedList<Board> resultBoards = algorithmContainer.search(boards[i], RollTheBall.greedy_h2_strategy, false);
			System.out.println("Greedy H2 - " + i);
			System.out.println(resultBoards.getLast());
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
