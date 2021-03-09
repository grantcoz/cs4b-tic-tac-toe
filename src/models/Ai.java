// package models;
// import java.util.ArrayList;
// import java.util.UUID;
// import javafx.scene.paint.Color;
// import org.javatuples.Pair;


// import java.util.ArrayList;

// public class Ai extends Player{

//     /*==========================================================================================================
//      * CLASS VARIABLES
//      *==========================================================================================================*/

//      /** 
//      * Constructs a new Ai object by using the super class constructor.
//      * @param color The color of the Ai's marker.
//      * @param id    The Ai's uuid.
//      * @param name  The name of the Ai.
//      * @param shape The shape of the Ai's marker.
//      */
//      public Ai(Color color, UUID id, String name, MarkerShape shape) {
//         super(color, id, name, shape);
//     }

//     /*==========================================================================================================
//      * ACCESSORS & MUTATORS
//      *==========================================================================================================*/

//     /**
//      * Returns whether the player is an Ai.
//      * @return True if the player is an Ai, false if they are not.
//      */
//     @Override
//     public boolean getIsAI() {return true;}

//     /**
//      * Returns whether there are any available cells on the board.
//      * @param  board A model of the playable board by using player objects.
//      * @return True if there are any available cells, false if all cells are taken.
//      */
//     static boolean isMovesLeft(Player[][] board){
//         for(int i = 0; i < 3; i ++){
//             for(int j = 0; j < 3; j++){
//                 if(board[i][j] == null)
//                     return true;
//             }
//         }
//         return false;
//     }

//     /**
//      * Returns Returns a value based on who is winning.
//      * @param  board A model of the playable board by using player objects.
//      * @return Returns a value based on who is winning.
//      */
//     static int evaluate(Player[][] board){

//         //Checks rows for a victory.
//         for(int i = 0; i < 3; i++){

//             if (board[i][0] == board[i][1] && board[i][1] == board[i][2])
//             {
//                 if (board[i][0] == ai)
//                 return +10;
//                 else if (board[i][0] == opponentPlayer)
//                 return -10;
//             }
//         } 

//         //Checks columns for a victory.
//         for(int i = 0; i < 3; i++){

//             if (board[0][i] == board[1][i] && board[1][i] == board[2][i])
//             {
//                 if (board[0][i] == ai)
//                 return +10;
//                 else if (board[0][i] == opponentPlayer)
//                 return -10;
//             }
//         } 

//         //Checking for Diagonals for X or O victory.
//         if (board[0][0] == board[1][1] && board[1][1] == board[2][2])
//         {
//             if (board[0][0] == ai)
//                 return +10;
//             else if (board[0][0] == opponentPlayer)
//                 return -10;
//         }

//         if (board[0][2] == board[1][1] && board[1][1] == board[2][0])
//         {
//             if (board[0][2] == ai)
//                 return +10;
//             else if (board[0][2] == opponentPlayer)
//                 return -10;
//         }

//         //Returns zero if no winning condition was met.
//         return 0;
//     }

//     /**
//      * This methods considers all possible choice and determines the best play for the Ai.
//      * Returns The value of the board. 
//      * @param depth The depth of the search 
//      * @param isMax A boolean that determines if the current object is a maximizer or minimizer
//      * @param gameState gameState The current state  of the game.
//      * @return The value of the board.
//      */
//     static int miniMax(int depth, boolean isMax, GameState gameState){

//         ArrayList<Pair<Integer,Integer>> emptySpaces = new ArrayList<Pair<Integer,Integer>>();

//         int score = evaluate(board);

//         //If maximizer has won return the score.
//         if(score == 10){
//             return score;
//         }

//         //If minimizer has won return the score.
//         if(score == -10){
//             return score;
//         }

//         if(isMovesLeft(board) == false){
//             return 0;
//         }

//         // If this maximizer's move
//         if (isMax)
//         {
//             int best = Integer.MIN_VALUE;
    
//             // Traverse all cells.
//             for (int i = 0; i < 3; i++)
//             {
//                 for (int j = 0; j < 3; j++)
//                 {
//                     // Check if cell is empty.
//                     if (board[i][j]== null)
//                     {
//                         // Make the move.
//                         board[i][j] = ai;
    
//                         // Call minimax recursively and choose the maximum value.
//                         best = Math.max(best, miniMax(board, depth + 1, !isMax, gameState));
    
//                         // Undo the move.
//                         board[i][j] = null;
//                     }
//                 }
//             }
//             return best;
//         }
 
//         // If this minimizer's move.
//         else
//         {
//             int best = Integer.MAX_VALUE;
    
//             // Traverse all cells.
//             for (int i = 0; i < 3; i++)
//             {
//                 for (int j = 0; j < 3; j++)
//                 {
//                     // Check if cell is empty.
//                     if (board[i][j] == null)
//                     {
//                         // Make the move.
//                        board[i][j] = opponentPlayer;
    
//                         // Call minimax recursively and choose the minimum value.
//                         best = Math.min(best, miniMax(board, 
//                                         depth + 1, !isMax, gameState));
    
//                         // Undo the move.
//                         board[i][j] = null;
//                     }
//                 }
//             }
//             return best;
//         }
//     }

//     /**
//      * This method will determine the best play that the AI can make with the current state of the board.
//      * Returns the best pair of x and y values for the AI player to play.
//      * @param gameState The current state  of the game.
//      * @return the best pair of x and y values for the AI player to play.
//      */
//     public Pair<Integer, Integer> generateMove(GameState gameState){
        
//         int bestVal = Integer.MIN_VALUE; //

//         int x = 0;
//         int y = 0;

//         for(int i = 0; i < 3; i ++){
//             for(int j = 0; j < 3; j ++){
                
//                 //checks if the cell is empty.
//                 if(gameState.getCell(i, j) == null){

//                     //making a move.
//                     gameState.getCell(i, j) = ;

//                     //evaluting the played move.
//                     int moveVal = miniMax(board, 0, false, gameState);

//                     //Undoing move.
//                     board[i][j] = null;

//                     // If the value of the current move is
//                     // more than the best value, then update
//                     // best.
//                     if (moveVal > bestVal)
//                     {
//                         x=i;
//                         y=j;

//                         bestVal = moveVal;
//                     }
//                 }
//             }
//         }  

//         Pair<Integer, Integer> bestMove = new Pair<Integer,Integer>(x, y); //The xy pair for the best cell placement for the Ai.
//         return bestMove;
//     }
// }