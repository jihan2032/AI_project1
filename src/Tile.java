
public class Tile {
	String type; // up_right, up_left, down_right, down_left, horizontal, vertical, block, blank, goal, ball 
	boolean movable;
	int x;
	int y;
	Board myBoard;
	
	
	public Tile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isMovable() {
		if (this.type.equals("block") || this.type.equals("goal") || this.type.equals("ball")) {
			return false;
		}
		return movable;
	}
	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public boolean outOfBound (int pos_x, int pos_y) {
		if (x < 0 || x > myBoard.width || y < 0 || y > myBoard.height)
			return true;
		return false;
	}
	
	public Tile [] around_tiles() {
		return null;
	}
	
	public Tile possible_north() {
		Tile upwards = new Tile(x, y + 1);
		if (this.type == "horizontal") {
			return null;
		}
		if (this.type == "vertical") {
			return upwards;
		}
		return null;
	}
	
}
