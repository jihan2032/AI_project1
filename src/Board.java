import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.LinkedList;

public class Board {
  public Tile [][] grid;
  Tile ball;
  Tile goal;
  public int width;
  public int height;
  int h1_value;

  public Board(int x, int y){
    width = x;
    height = y;
    grid = new Tile[x][y];
  }

  public boolean outOfBounds (int x, int y) {
    if (x < 0 || x > width || y < 0 || y > height)
      return true;
    return false;
  }

  ArrayList<Tile> getBlanks() {
    ArrayList<Tile> all_blanks = new ArrayList<Tile>();
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        if(grid[i][j].type == Tile.blank)
          all_blanks.add(grid[i][j]);
      }
    }
    return all_blanks;
  }

  Board after_move_board(Tile prev_pos, Tile blank) {
    Board new_board = new Board(width, height);
    new_board.grid = new Tile [width][height];
    for(int i = 0; i < width; i++) {
      for(int j = 0; j < height; j++) {
        new_board.grid[i][j] = grid[i][j];
      }
    }
    new_board.grid[prev_pos.x][prev_pos.y] = grid[blank.x][blank.y];
    new_board.grid[blank.x][blank.y] = grid[prev_pos.x][prev_pos.y];
    return new_board;
  }

  public ArrayList<Board> possibleMoves() {
    ArrayList<Board> possible_boards = new ArrayList<Board>();
    for (int i = 0; i < getBlanks().size(); i++) {
      Tile current_blank = getBlanks().get(i);
      Tile target;
      // north
      target = grid[current_blank.x][current_blank.y + 1];
      if (outOfBounds(current_blank.x, current_blank.y + 1) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
      // south
      target = grid[current_blank.x][current_blank.y - 1];
      if (outOfBounds(current_blank.x, current_blank.y - 1) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
      // east
      target = grid[current_blank.x + 1][current_blank.y];
      if (outOfBounds(current_blank.x + 1, current_blank.y) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
      // west
      target = grid[current_blank.x - 1][current_blank.y];
      if (outOfBounds(current_blank.x - 1, current_blank.y) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
    }
    return possible_boards;
  }

  public LinkedList<Board> possibleMoves2() {
	  LinkedList<Board> possible_boards = new LinkedList<Board>();
    for (int i = 0; i < getBlanks().size(); i++) {
      Tile current_blank = getBlanks().get(i);
      Tile target;
      // north
      target = grid[current_blank.x][current_blank.y + 1];
      if (outOfBounds(current_blank.x, current_blank.y + 1) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
      // south
      target = grid[current_blank.x][current_blank.y - 1];
      if (outOfBounds(current_blank.x, current_blank.y - 1) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
      // east
      target = grid[current_blank.x + 1][current_blank.y];
      if (outOfBounds(current_blank.x + 1, current_blank.y) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
      // west
      target = grid[current_blank.x - 1][current_blank.y];
      if (outOfBounds(current_blank.x - 1, current_blank.y) && target.isMovable()) {
        possible_boards.add(after_move_board(target, current_blank));
      }
    }
    return possible_boards;
  }
  
  public Tile getBallTile(){
	for(int i = 0; i < width; i++){
	  for(int j = 0; j < height; j++){
	      if(grid[i][j].getType() == Tile.ball)
	    	  return grid[i][j];
	  }
	}
	return null;
  }
  
  
  public void setH1Value() {
	  int worst_case = width * height;
	  h1_value = worst_case;
	  Tile ball_tile = getBallTile();
	  h1_value = worst_case - ball_tile.connected_path(null);
  }
  
  public boolean similar(Board other_board) {
	  for(int i = 0; i < width; i++) {
		  for(int j = 0; j < height; j++) {
			  if (grid[i][j].getType() != other_board.grid[i][j].getType())
				  return false;
		  }
	  }
	  return true;
  }
	
  public static boolean isGoal(Tile tile, ArrayList<Tile> possible_tiles, ArrayList<Tile> taken) {
		if(tile.getType() == Tile.goal_horizontal || tile.getType() == Tile.goal_vertical){
			return true;
		} else {
			for( int i = 0; i < possible_tiles.size(); i++){
				if (!taken.contains(possible_tiles.get(i))) {
					taken.add(possible_tiles.get(i));
					isGoal(possible_tiles.get(i), possible_tiles.get(i).around_tiles(), taken);
				}
			}
			return false;
		}
	}
}
