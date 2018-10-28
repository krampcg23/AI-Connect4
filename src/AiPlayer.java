import java.util.*;

/**
 * The AI Player Class
 * Implements depth limited alpha-beta pruning search algorithm
 * @author ckramp
 */
public class AiPlayer 
{
	// Global variables to prevent re-initialization
    int v = -99999999;
    int me;
    int opp;
    int alpha;
    int beta;
    /**
     * The constructor essentially does nothing except instantiate an
     * AiPlayer object.
     *
     */
    public AiPlayer() 
    {
	// nothing to do here
    }

/**
 * Finds the best play by using depth limited alpha-beta pruning search
 * @param currentGame
 * @param depth
 * @return an int corresponding to the column
 */
    public int findBestPlay( GameBoard currentGame, int depth ) 
    {
    	
    Map<Integer, Integer> move = new HashMap<Integer, Integer>();
    // Figure out who is the enemy and who the comp is
    me = currentGame.getCurrentTurn();
    opp = me % 2 + 1;
    ArrayList<Integer> acts = actions(currentGame);
    for (int i = 0; i < acts.size(); i++) {
    		GameBoard temp = makeTemp(currentGame);
    		temp.playPiece(acts.get(i));
    		alpha = -999999;
    		beta = 999999;
    		int val = min_search(temp, depth-1, alpha, beta);
    		move.put(acts.get(i), val);
    }
    int v = -99999;
    	int playChoice = 0;
    	for (Map.Entry<Integer, Integer> entry : move.entrySet()) {
    		if (v <= entry.getValue()) {
    			v = entry.getValue();
    			playChoice = entry.getKey();
    		}
    	}	
	return playChoice;
    }
    
    /**
     * Finds the list of possible moves
     * @param game
     * @return
     */
    public ArrayList<Integer> actions (GameBoard game) {
    	ArrayList<Integer> act = new ArrayList<Integer>();
    	for (int i = 0; i < 7; i++) {
    		if (game.isValidPlay(i)) {
    			act.add(i);
    		}
    	}
    	return act;
    }
    
    /**
     * Implements the max search of the alpha-beta pruning.
     * Find the best out of the moves to play
     * @param game
     * @param depth
     * @param alpha
     * @param beta
     * @return
     */
    public int max_search (GameBoard game, int depth, int alpha, int beta) {
		if (terminal(game, depth)) {
			return utility(game);
		}
		Map<Integer, GameBoard> move = new HashMap<Integer, GameBoard>();
		ArrayList<Integer> acts = actions(game);
		GameBoard temp = makeTemp(game);
		for (int i = 0; i < acts.size(); i++) {
			temp = makeTemp(game);
		    	temp.playPiece(acts.get(i));
		    	move.put(acts.get(i), temp);
		}
		
		int v = -99999999;
		for (int i = 0; i < move.size(); i++) {
			v = Math.max(v, min_search(move.get(acts.get(i)), depth-1, alpha, beta));
			if (v >= beta) {
				return v;
			}
			alpha = Math.max(alpha, v);
		}
		return v;
}
    /**
     * Implements the min search of the alpha-beta pruning.
     * Find the worst out of the moves to play
     * @param game
     * @param depth
     * @param alpha
     * @param beta
     * @return
     */
    public int min_search (GameBoard game, int depth, int alpha, int beta) {
    		if (terminal(game, depth)) {
    			return utility(game);
    		}
    		Map<Integer, GameBoard> move = new HashMap<Integer, GameBoard>();
    		ArrayList<Integer> acts = actions(game);
    		GameBoard temp = makeTemp(game);
    		for (int i = 0; i < acts.size(); i++) {
    			temp = makeTemp(game);
    		    	temp.playPiece(acts.get(i));
    		    	move.put(acts.get(i), temp);
    		}
    		
    		int v = 99999999;
    		for (int i = 0; i < move.size(); i++) {
    			v = Math.min(v, max_search(move.get(acts.get(i)), depth-1, alpha, beta));
    			if (v <= alpha) {
    				return v;
    			}
    			beta = Math.min(beta, v);
    		}
    		return v;
    }
    /**
     * Checks if the node is at a terminal state
     * @param game
     * @param depth
     * @return
     */
    public Boolean terminal (GameBoard game, int depth) {
    		if (game.getPieceCount() == 42 || depth <= 0) {
    			return true;
    		} else return false;
    }
    
    /**
     * Calculates the utility values of the terminal node
     * High weight is placed on fours, and then threes, and lastly twos
     * Returns negative if the enemy is winning
     * @param game
     * @return
     */
    public int utility (GameBoard game) {
    		int fourScore = game.getScore(me) - game.getScore(opp);
    		int threeScore = game.getThrees(me) - game.getThrees(opp);
    		int twoScore = game.getTwos(me) - game.getTwos(opp);
    		return fourScore * 500 + threeScore * 10 + twoScore;
    }
    
    /**
     * Creates a temporary GameBoard, which has all the properties of the original
     * @param game
     * @return
     */
    public GameBoard makeTemp (GameBoard game) {
		GameBoard temp = new GameBoard();
		int[][] arr = new int[6][7];
		int[][] anotherArr = new int[6][7];
		anotherArr = game.getGameBoard();
		for (int k = 0; k < 6; k++) {
			for (int j = 0; j < 7; j++) {
				arr[k][j] = anotherArr[k][j];
			}
		}
		temp.setPlayBoard(arr);
		temp.setCurrentTurn(game.getCurrentTurn());
		temp.setPieceCount(game.getPieceCount());
		return temp;
    }
}
