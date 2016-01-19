package tictactoe;
/**
 * The Board class models the game-board.
 */
public class Board {  // save as Board.java
   // Named-constants for the dimensions
   public static final int ROWS = 3;
   public static final int COLS = 3;
 
   // package access
   Cell[][] cells;  // a board composes of ROWS-by-COLS Cell instances
   int currentRow, currentCol;  // the current seed's row and column
 
   /** Constructor to initialize the game board */
   public Board() {
      cells = new Cell[ROWS][COLS];  // allocate the array
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col] = new Cell(); // allocate element of the array
         }
      }
   }
 
   /** Initialize (or re-initialize) the contents of the game board */
   public void init() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            cells[row][col].clear();  // clear the cell content
         }
      }
   }
 
   /** Return true if it is a draw (i.e., no more EMPTY cell) */
   public boolean isDraw() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            if (cells[row][col].content == Seed.EMPTY) {
               return false; // an empty seed found, not a draw, exit
            }
         }
      }
      return true; // no empty cell, it's a draw
   }
 
}