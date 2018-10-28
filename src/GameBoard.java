import java.io.*;

/**
 * The GameBoard Class
 * Contains all the properties of the gameboard and methods to return useful metrics
 * Half of the code is interpreted from the sample code.
 * @author ckramp
 */
public class GameBoard 
{
    // class fields
    private int[][] playBoard;
    private int pieceCount;
    private int currentTurn;

    public void setPlayBoard(int[][] playBoard) {
		this.playBoard = playBoard;
	}


	public void setPieceCount(int pieceCount) {
		this.pieceCount = pieceCount;
	}


	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}
	
	/**
	 * Default GameBoard that is not initialized by an inputFile
	 */
	public GameBoard() {
		this.playBoard = new int[6][7];
		this.pieceCount = 0;
	}
	
	/**
	 * GameBoard that is initialized by inputFile.
	 * Used for the original GameBoard
	 * @param inputFile
	 */
    public GameBoard( String inputFile ) 
    {
	this.playBoard = new int[6][7];
	this.pieceCount = 0;
	int counter = 0;
	BufferedReader input = null;
	String gameData = null;

	// open the input file
	try 
	{
	    input = new BufferedReader( new FileReader( inputFile ) );
	} 
        catch( IOException e ) 
	{
	    System.out.println("\nProblem opening the input file!\nTry again." +
			       "\n");
	    e.printStackTrace();
	}

	//read the game data from the input file
	for(int i = 0; i < 6; i++) 
	{
	    try 
	    {
		gameData = input.readLine();
		
		// read each piece from the input file
		for( int j = 0; j < 7; j++ ) 
		{
		    this.playBoard[ i ][ j ] = gameData.charAt( counter++ ) - 48;
		    
		    // sanity check
		    if( !( ( this.playBoard[ i ][ j ] == 0 ) ||
			   ( this.playBoard[ i ][ j ] == 1 ) ||
			   ( this.playBoard[ i ][ j ] == 2 ) ) ) 
                    {
			System.out.println("\nProblems!\n--The piece read " +
					   "from the input file was not a 1, a 2 or a 0" );
			this.exit_function( 0 );
		    }
		    
		    if( this.playBoard[ i ][ j ] > 0 )
		    {
			this.pieceCount++;
		    }
		}
	    } 
	    catch( Exception e ) 
	    {
		System.out.println("\nProblem reading the input file!\n" +
				   "Try again.\n");
		e.printStackTrace();
		this.exit_function( 0 );
	    }
	    counter = 0;
	    
	}

	// read one more line to get the next players turn
	try 
        {
	    gameData = input.readLine();
	} 
	catch( Exception e ) 
	{
	    System.out.println("\nProblem reading the next turn!\n" +
			       "--Try again.\n");
	    e.printStackTrace();
	}

	this.currentTurn = gameData.charAt( 0 ) - 48;

	// make sure the turn corresponds to the number of pcs played already
	if(!( ( this.currentTurn == 1) || ( this.currentTurn == 2 ) ) ) 
	{
	    System.out.println("Problems!\n the current turn read is not a " +
			       "1 or a 2!");
	    this.exit_function( 0 );
	} 
	else if ( this.getCurrentTurn() != this.currentTurn ) 
	{
	    System.out.println("Problems!\n the current turn read does not " +
			       "correspond to the number of pieces played!");
	    this.exit_function( 0 );			
	}
    }

    /**
     * this method returns the score for the player given as an argument.
     * it checks horizontally, vertically, and each direction diagonally.
     * 
     * @param player the player whose score is being requested.  valid
     * values are 1 or 2
     * @return the integer of the players score
     */
    public int getScore( int player ) 
    {
	//reset the scores
	int playerScore = 0;

	//check horizontally
	for( int i = 0; i < 6; i++ ) 
        {
	    for( int j = 0; j < 4; j++ ) 
	    {
		if( ( this.playBoard[ i ][j] == player ) &&
		    ( this.playBoard[ i ][ j+1 ] == player ) &&
		    ( this.playBoard[ i ][ j+2 ] == player ) &&
		    ( this.playBoard[ i ][ j+3 ] == player ) ) 
		{
		    playerScore++;
		}
	    }
	} // end horizontal

	//check vertically
	for( int i = 0; i < 3; i++ ) {
	    for( int j = 0; j < 7; j++ ) {
		if( ( this.playBoard[ i ][ j ] == player ) &&
		    ( this.playBoard[ i+1 ][ j ] == player ) &&
		    ( this.playBoard[ i+2 ][ j ] == player ) &&
		    ( this.playBoard[ i+3 ][ j ] == player ) ) {
		    playerScore++;
		}
	    }
	} // end verticle
	
	//check diagonally - backs lash ->	\
	    for( int i = 0; i < 3; i++ ){
		for( int j = 0; j < 4; j++ ) {
		    if( ( this.playBoard[ i ][ j ] == player ) &&
			( this.playBoard[ i+1 ][ j+1 ] == player ) &&
			( this.playBoard[ i+2 ][ j+2 ] == player ) &&
			( this.playBoard[ i+3 ][ j+3 ] == player ) ) {
			playerScore++;
		    }
		}
	    }
	    
	    //check diagonally - forward slash -> /
	    for( int i = 0; i < 3; i++ ){
		for( int j = 0; j < 4; j++ ) {
		    if( ( this.playBoard[ i+3 ][ j ] == player ) &&
			( this.playBoard[ i+2 ][ j+1 ] == player ) &&
			( this.playBoard[ i+1 ][ j+2 ] == player ) &&
			( this.playBoard[ i ][ j+3 ] == player ) ) {
			playerScore++;
		    }
		}
	    }// end player score check
	    
	    return playerScore;
    } // end getScore
    
    /**
     * Checks how many two in a rows the player has
     * Useful in creating a heuristic function especially in the beginning rounds 
     */
    public int getTwos( int player ) 
    {
	//reset the scores
	int playerScore = 0;

	//check horizontally
	for( int i = 0; i < 6; i++ ) 
        {
	    for( int j = 0; j < 6; j++ ) 
	    {
		if( ( this.playBoard[ i ][j] == player ) &&
		    ( this.playBoard[ i ][ j+1 ] == player ) )
		{
		    playerScore++;
		}
	    }
	} // end horizontal

	//check vertically
	for( int i = 0; i < 5; i++ ) {
	    for( int j = 0; j < 7; j++ ) {
		if( ( this.playBoard[ i ][ j ] == player ) &&
		    ( this.playBoard[ i+1 ][ j ] == player ) ) {
		    playerScore++;
		}
	    }
	} // end verticle
	
	//check diagonally - backs lash ->	\
	    for( int i = 0; i < 5; i++ ){
		for( int j = 0; j < 6; j++ ) {
		    if( ( this.playBoard[ i ][ j ] == player ) &&
			( this.playBoard[ i+1 ][ j+1 ] == player )  ) {
			playerScore++;
		    }
		}
	    }
	    
	    //check diagonally - forward slash -> /
	    for( int i = 0; i < 5; i++ ){
		for( int j = 0; j < 6; j++ ) {
		    if( ( this.playBoard[ i+1 ][ j ] == player ) &&
			( this.playBoard[ i ][ j+1 ] == player )  ) {
			playerScore++;
		    }
		}
	    }// end player score check
	    
	    return playerScore;
    } // end getScore

    /**
     * Counts how many three in a rows the player has
     * Useful for the heuristic function
     * @param player
     * @return
     */
    public int getThrees( int player ) 
    {
	//reset the scores
	int playerScore = 0;

	//check horizontally
	for( int i = 0; i < 6; i++ ) 
        {
	    for( int j = 0; j < 5; j++ ) 
	    {
		if( ( this.playBoard[ i ][j] == player ) &&
		    ( this.playBoard[ i ][ j+1 ] == player ) &&
		    ( this.playBoard[ i ][ j+2 ] == player ) ) 
		{
		    playerScore++;
		}
	    }
	} // end horizontal

	//check vertically
	for( int i = 0; i < 4; i++ ) {
	    for( int j = 0; j < 7; j++ ) {
		if( ( this.playBoard[ i ][ j ] == player ) &&
		    ( this.playBoard[ i+1 ][ j ] == player ) &&
		    ( this.playBoard[ i+2 ][ j ] == player )  ) {
		    playerScore++;
		}
	    }
	} // end verticle
	
	//check diagonally - backs lash ->	\
	    for( int i = 0; i < 4; i++ ){
		for( int j = 0; j < 5; j++ ) {
		    if( ( this.playBoard[ i ][ j ] == player ) &&
			( this.playBoard[ i+1 ][ j+1 ] == player ) &&
			( this.playBoard[ i+2 ][ j+2 ] == player ) ) {
			playerScore++;
		    }
		}
	    }
	    
	    //check diagonally - forward slash -> /
	    for( int i = 0; i < 4; i++ ){
		for( int j = 0; j < 5; j++ ) {
		    if( ( this.playBoard[ i+2 ][ j ] == player ) &&
			( this.playBoard[ i+1 ][ j+1 ] == player ) &&
			( this.playBoard[ i ][ j+2 ] == player ) ) {
			playerScore++;
		    }
		}
	    }// end player score check
	    
	    return playerScore;
    } // end getScore
    
    /**
     * the method gets the current turn
     * @return an int value representing whose turn it is.  either a 1 or a 2
     */
    public int getCurrentTurn() 
    {
	return ( this.pieceCount % 2 ) + 1 ;
    } // end getCurrentTurn


    /**
     * this method returns the number of pieces that have been played on the
     * board 
     * 
     * @return an int representing the number of pieces that have been played
     * on board alread
     */
    public int getPieceCount() 
    {
	return this.pieceCount;
    }

    /**
     * this method returns the whole gameboard as a dual indexed array
     * @return a dual indexed array representing the gameboard
     */
    public int[][] getGameBoard() 
    {
	return this.playBoard;
    }

    /**
     * a method that determines if a play is valid or not. It checks to see if
     * the column is within bounds.  If the column is within bounds, and the
     * column is not full, then the play is valid.
     * @param column an int representing the column to be played in.
     * @return true if the play is valid<br>
     * false if it is either out of bounds or the column is full
     */
    public boolean isValidPlay( int column ) {
	
	if ( !( column >= 0 && column <= 7 ) ) {
	    // check the column bounds
	    return false;
	} else if( this.playBoard[0][ column ] > 0 ) {
	    // check if column is full
	    return false;
	} else {
	    // column is NOT full and the column is within bounds
	    return true;
	}
    }

    /**
     * This method plays a piece on the game board.
     * @param column the column where the piece is to be played.
     * @return true if the piece was successfully played<br>
     * false otherwise
     */
    public boolean playPiece( int column ) {
	
	// check if the column choice is a valid play
	if( !this.isValidPlay( column ) ) {
	    return false;
	} else {
	    
	    //starting at the bottom of the board,
	    //place the piece into the first empty spot
	    for( int i = 5; i >= 0; i-- ) {
		if( this.playBoard[i][column] == 0 ) {
		    if( this.pieceCount % 2 == 0 ){
			this.playBoard[i][column] = 1;
			this.pieceCount++;
			
		    } else { 
			this.playBoard[i][column] = 2;
			this.pieceCount++;
		    }
		    
		    return true;
		}
	    }
	    
	    return false;
	}
    } //end playPiece
    
   
    /**
     * this method prints the GameBoard to the screen in a nice, pretty,
     * readable format
     */
    public void printGameBoard() 
    {
	System.out.println(" -----------------");
	String ANSI_RESET = "\u001B[0m";
	String ANSI_RED = "\u001B[31m";
	String ANSI_GREEN = "\u001B[32m";
	for( int i = 0; i < 6; i++ ) 
        {
	    System.out.print(" | ");
	    for( int j = 0; j < 7; j++ ) 
            {
	    			if (this.playBoard[i][j] == 1) {
	    				System.out.print(ANSI_RED + "1 " + ANSI_RESET);
	    			} else if (this.playBoard[i][j] == 2) {
	    				System.out.print(ANSI_GREEN + "2 " + ANSI_RESET);
	    			} else {
	    				System.out.print( this.playBoard[i][j] + " " );
	    			}
            }

	    System.out.println("| ");
	}
	
	System.out.println(" -----------------");
    } // end printGameBoard
    
    /**
     * this method prints the GameBoard to an output file to be used for
     * inspection or by another running of the application
     * @param outputFile the path and file name of the file to be written
     */
    public void printGameBoardToFile( String outputFile ) {
	try {
	    BufferedWriter output = new BufferedWriter(
						       new FileWriter( outputFile ) );
	    
	    for( int i = 0; i < 6; i++ ) {
		for( int j = 0; j < 7; j++ ) {
		    output.write( this.playBoard[i][j] + 48 );
		}
		output.write("\r\n");
	    }
	    
	    //write the current turn
	    output.write( this.getCurrentTurn() + "\r\n");
	    output.close();
	    
	} catch( IOException e ) {
	    System.out.println("\nProblem writing to the output file!\n" +
			       "Try again.");
	    e.printStackTrace();
	}
    } // end printGameBoardToFile()
    
    private void exit_function( int value ){
	System.out.println("exiting from GameBoard.java!\n\n");
	System.exit( value );
    }
    
}  // end GameBoard class
