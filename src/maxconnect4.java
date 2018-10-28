import java.util.Scanner;

/**
 * The main maxConnect4 class
 * Contains main, and initializes the GameBoard and AIPlayer to play the game 
 * @author ckramp
 */
public class maxconnect4
{
  public static void main(String[] args) 
  {
    // check for the correct number of arguments
    if( args.length != 4 ) 
    {
      System.out.println("Four command-line arguments are needed:\n"
                         + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n"
                         + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");

      exit_function( 0 );
     }
		
    // parse the input arguments
    String game_mode = args[0].toString();				// the game mode
    String input = args[1].toString();					// the input game file
    int depthLevel = Integer.parseInt( args[3] );  		// the depth level of the ai search
    
    // Ensure valid depth
    if (depthLevel <= 0) {
    		System.out.println("Error, Depth Level not valid");
    		exit_function( 0 );
    }
		
    // create and initialize the game board
    GameBoard currentGame = new GameBoard( input );
    
    // create the Ai Player
    AiPlayer calculon = new AiPlayer();
		
    //  variables to keep up with the game
    int playColumn;				//  the players choice of column to play

    if( game_mode.equalsIgnoreCase( "interactive" ) ) 
    {
    		String nextP = args[2].toString();
    	    
    	    System.out.print("\nMaxConnect-4 game\n");
    	    System.out.print("game state before move:\n");
    	    
    	    //print the current game board
    	    currentGame.printGameBoard();
    	    // print the current scores
    	    System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
    				", Player2 = " + currentGame.getScore( 2 ) + "\n " );
    	    int current_player = currentGame.getCurrentTurn();
    	    boolean human = false;
    	    Scanner sc = null;
    	    if (nextP.equalsIgnoreCase("human-next")) human = true;
    	    while (currentGame.getPieceCount() != 42) {
    	    	// Humans turn, loop until human plays valid column
    	    		if (human) {
    	    			System.out.println("Enter a Column to Play 1~7");
    	    			sc = new Scanner(System.in);
    	    			int i = sc.nextInt();
    	    			i = i - 1;
    	    			boolean dumbhuman = false;
    	    			if(!currentGame.playPiece( i )) {
    	    				dumbhuman = true;
    	    				while(dumbhuman) {
    	    					System.out.println("Bad Input, Try Again. Play 1~7");
    	    	    			 	sc = new Scanner(System.in);
    	    	    			 	i = sc.nextInt() - 1;
    	    	    			 	dumbhuman = !currentGame.playPiece( i );
    	    				}
    	    			};
    	            	
    	    	        // display the current game board
    	    	        System.out.println("move " + currentGame.getPieceCount() 
    	    	                           + ": Player " + current_player
    	    	                           + ", column " + i);
    	    	        System.out.print("game state after move:\n");
    	    	        currentGame.printGameBoard();
    	    	    
    	    	        // print the current scores
    	    	        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
    	    	                            ", Player2 = " + currentGame.getScore( 2 ) + "\n " );
    	    	        human = false;
    	    			
    	    		} else {
    	    			// computer turn
    	    			playColumn = calculon.findBestPlay( currentGame, depthLevel );
    	    			currentGame.playPiece( playColumn );
    	            	
    	    	        // display the current game board
    	    	        System.out.println("move " + currentGame.getPieceCount() 
    	    	                           + ": Player " + current_player
    	    	                           + ", column " + playColumn);
    	    	        System.out.print("game state after move:\n");
    	    	        currentGame.printGameBoard();
    	    	    
    	    	        // print the current scores
    	    	        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
    	    	                            ", Player2 = " + currentGame.getScore( 2 ) + "\n " );
    	    	        human = true;
    	    		}
    	    }
	sc.close();
    	
	return;
    } 
			
    else if( !game_mode.equalsIgnoreCase( "one-move" ) ) 
    {
      System.out.println( "\n" + game_mode + " is an unrecognized game mode \n try again. \n" );
      return;
    }

    /////////////   one-move mode ///////////
    // get the output file name
    String output = args[2].toString();				// the output game file
    
    System.out.print("\nMaxConnect-4 game\n");
    System.out.print("game state before move:\n");
    
    //print the current game board
    currentGame.printGameBoard();
    // print the current scores
    System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
			", Player2 = " + currentGame.getScore( 2 ) + "\n " );
    
    // ****************** this chunk of code makes the computer play
    if( currentGame.getPieceCount() < 42 ) 
    {
        int current_player = currentGame.getCurrentTurn();
        playColumn = calculon.findBestPlay( currentGame, depthLevel );
	
	// play the piece
	currentGame.playPiece( playColumn );
        	
        // display the current game board
        System.out.println("move " + currentGame.getPieceCount() 
                           + ": Player " + current_player
                           + ", column " + playColumn);
        System.out.print("game state after move:\n");
        currentGame.printGameBoard();
    
        // print the current scores
        System.out.println( "Score: Player 1 = " + currentGame.getScore( 1 ) +
                            ", Player2 = " + currentGame.getScore( 2 ) + "\n " );
        
        currentGame.printGameBoardToFile( output );
    } 
    else 
    {
	System.out.println("\nI can't play.\nThe Board is Full\n\nGame Over");
    }
	
    //************************** end computer play
			
    
    return;
    
} // end of main()
	
  /**
   * This method is used when to exit the program prematurly.
   * @param value an integer that is returned to the system when the program exits.
   */
  private static void exit_function( int value )
  {
      System.out.println("exiting from MaxConnectFour.java!\n\n");
      System.exit( value );
  }
} // end of class connectFour
