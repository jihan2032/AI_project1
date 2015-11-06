import java.util.ArrayList;

public class Tile {
  int type; // path_buttom_right, path_buttom_left, path_top_right, path_top_left, path_horizontal, path_vertical, block, blank, goal_horizontal, goal_vertical, ball
  boolean movable;
  int row;
  int col;
  Board myBoard;
  final static int path_bottom_right = 0;
  final static int path_bottom_left = 1;
  final static int path_top_right = 2;
  final static int path_top_left = 3;
  final static int path_horizontal = 4;
  final static int path_vertical = 5;
  final static int block = 6;
  final static int blank = 7;
  final static int goal_horizontal = 8;
  final static int goal_vertical = 9;
  final static int ball_horizontal = 10;
  final static int ball_vertical = 11;

  public Tile() {

  }
  public Tile(Board b, int row, int col) {
	  myBoard = b;
	  this.row = row;
	  this.col = col;
  }
  public Tile(int row, int col) {
    this.row = row;
    this.col = col;
  }
  public int getType() {
    return type;
  }
  public void setType(int type) {
    this.type = type;
  }
  public boolean isMovable() {
    if (this.type == block || this.type == goal_horizontal || this.type == goal_vertical || this.type == ball_horizontal || this.type == ball_vertical) {
      return false;
    }
    return movable;
  }
  public void setMovable(boolean movable) {
    this.movable = movable;
  }

  boolean isGoalTile() {
	if(this.type == goal_horizontal || this.type == goal_vertical)
	  return true;
	return false;
  }

  public ArrayList<Tile> around_tiles() {
    ArrayList<Tile> around_tiles_list = new ArrayList<Tile>();
    if(possible_north() != null)
      around_tiles_list.add(possible_north());
    if(possible_south() != null)
    	around_tiles_list.add(possible_south());
    if(possible_east() != null)
    	around_tiles_list.add(possible_east());
    if(possible_west() != null)
    	around_tiles_list.add(possible_west());
    return around_tiles_list;
  }

  public Tile possible_north() {
    if (myBoard.outOfBounds(row, col + 1))
      return null;
    Tile upwards = myBoard.grid[row + 1][ col ];
    if (this.type == path_horizontal || this.type == path_top_right || this.type == path_top_left) {
      return null;
    }
    if ((this.type == path_vertical || this.type == path_bottom_right || this.type == path_bottom_left ) && (upwards.type == path_vertical || upwards.type == path_top_left || upwards.type == path_top_right || upwards.type == goal_vertical)) {
      return upwards;
    }
    return null;
  }

  public Tile possible_south() {
    if (myBoard.outOfBounds(row -1, col ))
      return null;
    Tile downwards = myBoard.grid[row - 1][ col];
    if (this.type == path_horizontal || this.type == path_bottom_right || this.type == path_bottom_left) {
      return null;
    }
    if ((this.type == path_vertical || this.type == path_top_right || this.type == path_top_left ) && (downwards.type == path_vertical || downwards.type == path_bottom_left || downwards.type == path_bottom_right || downwards.type == goal_vertical)) {
      return downwards;
    }
    return null;
  }

  public Tile possible_east() {
    if (myBoard.outOfBounds(row, col + 1))
      return null;
    Tile east = myBoard.grid[row][col + 1];
    if (this.type == path_vertical || this.type == path_top_right || this.type == path_bottom_right) {
      return null;
    }
    if ((this.type == path_horizontal || this.type == path_top_left || this.type == path_bottom_left ) && (east.type == path_horizontal || east.type == path_bottom_right || east.type == path_top_right || east.type == goal_horizontal)) {
      return east;
    }
    return null;
  }

  public Tile possible_west() {
    if (myBoard.outOfBounds(row, col - 1))
      return null;
    Tile west = myBoard.grid[row][col -1];
    if (this.type == path_vertical || this.type == path_top_left || this.type == path_bottom_left) {
      return null;
    }
    if ((this.type == path_horizontal || this.type == path_top_right || this.type == path_bottom_right ) && (west.type == path_horizontal || west.type == path_bottom_left || west.type == path_top_left || west.type == goal_horizontal)) {
      return west;
    }
    return null;
  }

  public int connected_path(Tile previous) {
	  ArrayList<Tile> around = around_tiles();
	  for (int i = 0; previous != null && i < around.size(); i++) {
		  if (around.get(i).row == previous.row && around.get(i).col == previous.col)
			  around.remove(i);
	  }
	  if (around.size() > 0) {
		  return 1 + around.get(0).connected_path(this);
	  }
	  return 0;
  }

  public Tile last_connected(Tile previous) {
	  ArrayList<Tile> around = around_tiles();
	  for (int i = 0; i < around.size() && previous != null; i++) {
		  if (around.get(i).row == previous.row && around.get(i).col == previous.col)
			  around.remove(i);
	  }
	  if (around.size() > 0) {
		  return around.get(0).last_connected(this);
	  }
	  return this;
  }

}
