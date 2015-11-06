import java.util.ArrayList;

public class Board {
  public Tile [][] grid;
  Tile ball;
  Tile goal;
  public int width;
  public int height;

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
    Board new_board = new Board();
    new_board.height = height;
    new_board.width = width;
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

  public ArrayList<Board> possible_moves(Board current_board) {
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
}
