package tictactoe;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerAlphaBeta {

    private Cell[][] board;
    protected int ROWS = TTTGraphics2P.ROWS;  // number of rows
    protected int COLS = TTTGraphics2P.COLS;  // number of columns
    protected Seed mySeed;    // computer's seed
    protected Seed oppSeed;   // opponent's seed
    protected int _k;
    protected GameState currentState;
    protected Evaluation _ev, _evOpp;
    protected Heuristique _h;
    public static int _depth = 4;
    
    public AIPlayerAlphaBeta(Cell[][] _board, int k, GameState _GS, Seed _seed, Evaluation ev) {
        board = _board;
        mySeed = _seed;
        oppSeed = (mySeed == Seed.CROSS)?Seed.NOUGHT :Seed.CROSS;
        _k = k;
        currentState = _GS;
        _ev = new Evaluation (board, k, _GS,_seed);
        _evOpp = ev;
        _h = new Heuristique(_board,k,_GS,_seed);
    }

    public void setK(int taille){
        _ev.setK(taille);
        _evOpp.setK(taille);
        _h.setK(taille);
    }
    
    public void setVictoryLength(int length){
        Evaluation._nbWin = length;
    }
    
    int[] move() {
        int[] result = minimax(_depth, mySeed, Integer.MIN_VALUE, Integer.MAX_VALUE);
        // depth, max-turn, alpha, beta
        return new int[]{result[1], result[2]};   // row, col
    }

    private int[] minimax(int depth, Seed player, int alpha, int beta) {
        // Generate possible next moves in a list of int[2] of {row, col}.
        List<int[]> nextMoves = generateMoves();

        // mySeed is maximizing; while oppSeed is minimizing
        int score;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            // Gameover or depth reached, evaluate score
            score = _h.evaluate();
            return new int[]{score, bestRow, bestCol};
        } else {
            for (int[] move : nextMoves) {
                // try this move for the current "player"
                board[move[0]][move[1]].content = player;
                if (player == mySeed) {  // mySeed (computer) is maximizing player
                    score = minimax(depth - 1, oppSeed, alpha, beta)[0];
                    if (score > alpha) {
                        alpha = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {  // oppSeed is minimizing player
                    score = minimax(depth - 1, mySeed, alpha, beta)[0];
                    if (score < beta) {
                        beta = score;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                // undo move
                board[move[0]][move[1]].content = Seed.EMPTY;
                // cut-off
                if (alpha >= beta) {
                    break;
                }
            }
            return new int[]{(player == mySeed) ? alpha : beta, bestRow, bestCol};
        }
    }

    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>(); // allocate List

        // If gameover, i.e., no next move
        if (_ev.hasWon(false) || _evOpp.hasWon(false)) {
            return nextMoves;   // return empty list
        }

        // Search for empty cells and add to the List
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col].content == Seed.EMPTY) {
                    nextMoves.add(new int[]{row, col});
                }
            }
        }
        return nextMoves;
    }

    public boolean hasWon(){
        return _ev.hasWon(true);
    }    
    
}
