import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class RollTheBall {

  static Board board; //initial board
  static final int dfs_strategy = 1;
  static final int bfs_strategy = 2;
  static final int greedy_h1_strategy = 3;
  static final int greedy_h2_strategy = 4;  
  static final int a_strategy = 5;

  public RollTheBall(Board board) {
    this.board = board;
  }

  public static void genGrid(int m, int n){
    board = new Board(m, n);
    int random, random2, random3, random4;

    //adding goal state
    random = (int)(Math.random() * m);
    random2 = (int)(Math.random() * n);
    board.grid[random][random2] = new Tile(board, random, random2);
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

  public static Tile getBallTile(){
    for(int i = 0; i < board.grid.length; i++){
      for(int j = 0; j < board.grid[i].length; j++){
        if(board.grid[i][j].getType() == Tile.ball_horizontal || board.grid[i][j].getType() == Tile.ball_vertical)
          return board.grid[i][j];
      }
    }
    return null;
  }

  public Board BFS(){
    ArrayList<Board> possible_moves = board.possibleMoves();
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

  public Board DSF() {
    ArrayList<Board> possible_moves = board.possibleMoves();
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
  
  public LinkedList<Board> search(Board initial_board, int strategy, Boolean visualize) {
	  if (strategy == greedy_h1_strategy) {
		  LinkedList<Board> original_board = new LinkedList<>();
		  original_board.add(initial_board);
		  return H1(new LinkedList<Board>(), original_board);
	  }
	  
	  if (strategy == greedy_h2_strategy) {
		  LinkedList<Board> original_board = new LinkedList<>();
		  original_board.add(initial_board);
		  return H2(new LinkedList<Board>(), original_board);
	  }
	  
	  if (strategy == a_strategy) {
		  LinkedList<Board> original_board = new LinkedList<>();
		  original_board.add(initial_board);
		  return admissible(new LinkedList<Board>(), original_board);
	  }
	  
	  
	  return null;
  }

  // H1 depends on the number of connected tiles from the goal
  public LinkedList<Board> H1(LinkedList<Board> output_sequence, LinkedList<Board> h1_queue) {
	output_sequence.add(h1_queue.removeFirst());
	Board current_board_shape = output_sequence.getLast();
	if (current_board_shape.isGoal())
		return output_sequence;
    LinkedList<Board> node_queue = current_board_shape.possibleMoves2();
    //remove repeated nodes
    //remove repeated from output
    for (int i = 0; i < node_queue.size(); i ++) {
    	for (int j = 0; j < output_sequence.size(); j++) {
    		if (node_queue.get(i).similar(output_sequence.get(j)))
    			node_queue.remove(i);
    	}
    }
    //remove repeated from queue
    for (int i = 0; i < node_queue.size(); i ++) {
    	for (int j = 0; j < h1_queue.size(); j++) {
    		if (node_queue.get(i).similar(h1_queue.get(j)))
    			node_queue.remove(i);
    	}
    }
	for(int i = 0; i < node_queue.size(); i++) {
    	Board node = node_queue.get(i);
    	node.setH1Value();
    	h1_queue.add(node);
    }
    if (h1_queue.isEmpty()) {
    	return output_sequence;
    }
    int min_h1_value = h1_queue.getFirst().h1_value;
    int min_board_index = 0;
    for (int i = 1; i < h1_queue.size(); i++) {
    	if (h1_queue.get(i).h1_value < min_h1_value ) {
    		min_h1_value = h1_queue.get(i).h1_value;
    		min_board_index = i;
    	}
    }
    h1_queue.addFirst(h1_queue.get(min_board_index));
    h1_queue.remove(min_board_index);
    return H1(output_sequence, h1_queue);
  }
  
  public LinkedList<Board> H2(LinkedList<Board> output_sequence, LinkedList<Board> h2_queue) {
	  	output_sequence.add(h2_queue.removeFirst());
		Board current_board_shape = output_sequence.getLast();
		if (current_board_shape.isGoal())
			return output_sequence;
	    LinkedList<Board> node_queue = current_board_shape.possibleMoves2();
	    //remove repeated nodes
	    //remove repeated from output
	    for (int i = 0; i < node_queue.size(); i ++) {
	    	for (int j = 0; j < output_sequence.size(); j++) {
	    		if (node_queue.get(i).similar(output_sequence.get(j)))
	    			node_queue.remove(i);
	    	}
	    }
	    //remove repeated from queue
	    for (int i = 0; i < node_queue.size(); i ++) {
	    	for (int j = 0; j < h2_queue.size(); j++) {
	    		if (node_queue.get(i).similar(h2_queue.get(j)))
	    			node_queue.remove(i);
	    	}
	    }
		for(int i = 0; i < node_queue.size(); i++) {
	    	Board node = node_queue.get(i);
	    	node.setH2Value();
	    	h2_queue.add(node);
	    }
	    if (h2_queue.isEmpty()) {
	    	return output_sequence;
	    }
	    int min_h2_value = h2_queue.getFirst().h2_value;
	    int min_board_index = 0;
	    for (int i = 1; i < h2_queue.size(); i++) {
	    	if (h2_queue.get(i).h2_value < min_h2_value ) {
	    		min_h2_value = h2_queue.get(i).h2_value;
	    		min_board_index = i;
	    	}
	    }
	    h2_queue.addFirst(h2_queue.get(min_board_index));
	    h2_queue.remove(min_board_index);
	    return H2(output_sequence, h2_queue);
}

  
  public LinkedList<Board> admissible(LinkedList<Board> output_sequence, LinkedList<Board> admiss_queue) {
	  	output_sequence.add(admiss_queue.removeFirst());
		Board current_board_shape = output_sequence.getLast();
		if (current_board_shape.isGoal())
			return output_sequence;
	    LinkedList<Board> node_queue = current_board_shape.possibleMoves2();
	    //remove repeated nodes
	    //remove repeated from output
	    for (int i = 0; i < node_queue.size(); i ++) {
	    	for (int j = 0; j < output_sequence.size(); j++) {
	    		if (node_queue.get(i).similar(output_sequence.get(j)))
	    			node_queue.remove(i);
	    	}
	    }
	    //remove repeated from queue
	    for (int i = 0; i < node_queue.size(); i ++) {
	    	for (int j = 0; j < admiss_queue.size(); j++) {
	    		if (node_queue.get(i).similar(admiss_queue.get(j)))
	    			node_queue.remove(i);
	    	}
	    }
		for(int i = 0; i < node_queue.size(); i++) {
	    	Board node = node_queue.get(i);
	    	node.setAStarValue();
	    	admiss_queue.add(node);
	    }
	    if (admiss_queue.isEmpty()) {
	    	return output_sequence;
	    }
	    int min_a_start_value = admiss_queue.getFirst().a_star_value;
	    int min_board_index = 0;
	    for (int i = 1; i < admiss_queue.size(); i++) {
	    	if (admiss_queue.get(i).a_star_value < min_a_start_value ) {
	    		min_a_start_value = admiss_queue.get(i).a_star_value;
	    		min_board_index = i;
	    	}
	    }
	    admiss_queue.addFirst(admiss_queue.get(min_board_index));
	    admiss_queue.remove(min_board_index);
	    return admissible(output_sequence, admiss_queue);
  }


//  public int getLeastH1ValueIndex(LinkedList<Board> list) {
//	  int min = list.getFirst().h1_value;
//	  int best_board_index = 0;
//	  for (int i = 1; i < list.size(); i++) {
//		  if (list.get(i).h1_value < min) {
//			min = list.get(i).h1_value;
//			best_board_index = i;
//		  }
//	  }
//	  return best_board_index;
//  }
  
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
