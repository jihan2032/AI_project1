package main;
import java.util.ArrayList;

public abstract class SearchProblem {
	String [] operators = {"right", "left", "up", "down"};
	Board intial_state;
	Board [] space_states;
	
	public static boolean goalTest(Board board, Tile tile, ArrayList<Tile> possible_tiles, ArrayList<Tile> taken) {
		if(tile.getType() == Tile.goal_horizontal || tile.getType() == Tile.goal_vertical){
			return true;
		} else {
			for( int i = 0; i < possible_tiles.size(); i++){
				if (!taken.contains(possible_tiles.get(i))) {
					taken.add(possible_tiles.get(i));
					goalTest(board, possible_tiles.get(i), possible_tiles.get(i).around_tiles(), taken);
				}
			}
			return false;
		}
	}
}
