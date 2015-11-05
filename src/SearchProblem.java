import java.util.ArrayList;

public abstract class SearchProblem {
	String [] operators = {"right", "left", "up", "down"};
	Board intial_state;
	Board [] space_states;
	
	public static boolean goalTest(Board board, Tile tile, Tile[] possible_tiles, ArrayList<Tile> taken) {
		if(tile.getType().equals("goal")){
			return true;
		} else {
			for( int i = 0; i < possible_tiles.length; i++){
				if (!taken.contains(possible_tiles[i])) {
					taken.add(possible_tiles[i]);
					goalTest(board, possible_tiles[i], possible_tiles[i].around_tiles(), taken);
				}
			}
			return false;
		}
	}
}
