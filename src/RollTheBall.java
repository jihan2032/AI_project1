import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class RollTheBall {

  static Board board;


  public static void genGrid(int m, int n){
    board = new Board(m, n);
    int random, random2, random3, random4;

    //adding goal state
    random = (int)(Math.random() * m);
    random2 = (int)(Math.random() * n);
    board.grid[random][random2] = new Tile();
    if(random < random2)
      board.grid[random][random2].setType(8);
    else
      board.grid[random][random2].setType(9);

    //adding the ball
    random3 = (int)(Math.random() * m);
    random4 = (int)(Math.random() * n);
    while(random == random3 && random2 == random4){
      random3 = (int)(Math.random() * m);
      random4 = (int)(Math.random() * n);
    }
    board.grid[random][random2] = new Tile();
    board.grid[random][random2].setType(10);

    int counter = 0;
    for(int i = 0; i < m; i++){
      for(int j = 0; j < n; j++){
        if(board.grid[i][j] == null){
        board.grid[i][j] = new Tile();
        random = (int)(Math.random() * 7);
        board.grid[i][j].setType(random);
        if(counter % 4 == 0)
          board.grid[i][j].setMovable(false);
        counter++;
        }
      }
    }
  }

  public Board BFS(){
    ArrayList<Board> possible_moves = new ArrayList<Board>(); //= board.possibleMoves();
    LinkedList<Board> linked_list = new LinkedList<Board>();

    //adding current possible moves to the linked list
    if(!possible_moves.isEmpty()){
      for(int i = 0; i < possible_moves.size(); i++){
        linked_list.addLast(possible_moves.remove(0));
      }
    }

    while(!linked_list.isEmpty()){
      if(SearchProblem.goalTest(linked_list.getFirst(), getBallTile(), new ArrayList<Tile>(), new ArrayList<Tile>()))
        return linked_list.getFirst();

      //possible_moves = linked_list.getFirst().possibleMoves();
      if(!possible_moves.isEmpty()){
        for(int j = 0; j < possible_moves.size(); j++){
          linked_list.addLast(possible_moves.remove(0));
        }
      }
      linked_list.removeFirst();
    }

    return null;
  }

  public static Tile getBallTile(){
    for(int i = 0; i < board.grid.length; i++){
      for(int j = 0; j < board.grid[i].length; j++){
        if(board.grid[i][j].getType() == 10)
          return board.grid[i][j];
      }
    }
    return null;
  }

  public Board DSF(){
    ArrayList<Board> possible_moves = new ArrayList<Board>(); //= board.possibleMoves();
    LinkedList<Board> linked_list = new LinkedList<Board>();

    //adding current possible moves to the linked list
    if(!possible_moves.isEmpty()){
      for(int i = 0; i < possible_moves.size(); i++){
        linked_list.addLast(possible_moves.remove(0));
      }
    }

    while(!linked_list.isEmpty()){
      if(SearchProblem.goalTest(linked_list.getFirst(), getBallTile(), new ArrayList<Tile>(), new ArrayList<Tile>()))
        return linked_list.getFirst();

      //possible_moves = linked_list.getFirst().possibleMoves();
      if(!possible_moves.isEmpty()){

        //from the arraylist to the stack
        Stack<Board> stack = new Stack<Board>();
        for(int j = 0; j < possible_moves.size(); j++){
          stack.push(possible_moves.remove(0));
        }

        //from stack to the linked list
        while(!stack.isEmpty()){
          linked_list.addFirst(stack.pop());
        }
      }
      linked_list.removeFirst();
    }

    return null;
  }

  public static void main(String []args){
    genGrid(4, 4);
    for(int i = 0; i < board.grid.length; i++){
      for(int j = 0; j < board.grid[i].length; j++){
        System.out.print(board.grid[i][j].getType());
      }
      System.out.println();
    }
  }
}
