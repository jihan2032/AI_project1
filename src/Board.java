import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.LinkedList;

import org.omg.CosNaming._BindingIteratorImplBase;

public class Board {
  public Tile [][] grid;
  Tile ball;
  Tile goal;
  public int rows;
  public int colums;
  int h1_value;
  int h2_value;
  int a_star_value;
  int level;

  public Board(int x, int y){
    rows = x;
    colums = y;
    grid = new Tile[x][y];
  }

  public boolean outOfBounds (int x, int y) {
    if (x < 0 || x > rows || y < 0 || y > colums)
      return true;
    return false;
  }

  ArrayList<Tile> getBlanks() {
    ArrayList<Tile> all_blanks = new ArrayList<Tile>();
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < colums; j++) {
        if(grid[i][j].type == Tile.blank)
          all_blanks.add(grid[i][j]);
      }
    }
    return all_blanks;
  }

  Board after_move_board(Tile prev_pos, Tile blank) {
    Board new_board = new Board(rows, colums);
    new_board.grid = new Tile [rows][colums];
    new_board.level = level + 1;
    for(int i = 0; i < rows; i++) {
      for(int j = 0; j < colums; j++) {
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
	for(int i = 0; i < rows; i++){
	  for(int j = 0; j < colums; j++){
	      if(grid[i][j].getType() == Tile.ball_horizontal || grid[i][j].getType() == Tile.ball_vertical)
	    	  return grid[i][j];
	  }
	}
	return null;
  }

  public Tile getGoalTile(){
	for(int i = 0; i < rows; i++){
	  for(int j = 0; j < colums; j++){
	      if(grid[i][j].getType() == Tile.goal_horizontal || grid[i][j].getType() == Tile.goal_vertical)
	    	  return grid[i][j];
	  }
	}
	return null;
  }

  public void setH1Value() {
	  int worst_case = rows * colums;
	  h1_value = worst_case;
	  Tile ball_tile = getBallTile();
	  h1_value = worst_case - ball_tile.connected_path(null);
  }

  public void setAStarValue() {
	  a_star_value = level + path_to_goal(getBallTile().last_connected(null), getGoalTile());
  }
  
  public void setH2Value() {
	  a_star_value = path_to_goal(getBallTile().last_connected(null), getGoalTile());
  }

  public int path_to_goal(Tile start, Tile goal) {
	  int path;
	  int diff_rows = goal.x - start.x;
	  int diff_cols = goal.y - start.y;
	  if (diff_rows <= 0) {
		// up left
		if (diff_cols <= 0) {
			path = (0 - diff_rows) + (0 - diff_cols) - 1;
			for (int i = start.x - 1; i > goal.x; i--) {
				if (grid[i][start.y].possible_north() != null)
					path -= 1;
			}
			for (int i = start.y; i > goal.y; i--) {
				if (grid[goal.x][i].possible_west() != null)
					path -= 1;
			}
		}
		// up right
		else {
			path = (0 - diff_rows) + diff_cols - 1;
			for (int i = start.x - 1; i > goal.x; i--) {
				if (grid[i][start.y].possible_north() != null)
					path -= 1;
			}
			for (int i = start.y; i < goal.y; i++) {
				if (grid[goal.x][i].possible_east() != null)
					path -= 1;
			}
		}
	  }
	  else {
		  // down left
		  if (diff_cols <= 0) {
			  path = diff_rows + (0 - diff_cols) - 1;
			  for (int i = start.x + 1; i < goal.x; i++) {
				  if (grid[i][start.y].possible_south() != null)
					  path -= 1;
			  }
			  for (int i = start.y; i > goal.y; i--) {
				  if (grid[goal.x][i].possible_west() != null)
					  path -= 1;
			  }

		  }
		  // down right
		  else {
			  path =  diff_rows + diff_cols - 1;
			  for (int i = start.x + 1; i < goal.x; i++) {
				  if (grid[i][start.y].possible_south() != null)
					  path -= 1;
			  }
			  for (int i = start.y; i < goal.y; i++) {
				  if (grid[goal.x][i].possible_west() != null)
					  path -= 1;
			  }
		  }
	  }
	  return path;

  }

  public boolean similar(Board other_board) {
	  for(int i = 0; i < rows; i++) {
		  for(int j = 0; j < colums; j++) {
			  if (grid[i][j].getType() != other_board.grid[i][j].getType())
				  return false;
		  }
	  }
	  return true;
  }

  public boolean isGoal() {
	return getBallTile().last_connected(null).isGoalTile();
  }
}
