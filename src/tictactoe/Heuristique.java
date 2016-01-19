package tictactoe;

public class Heuristique {

    private Cell[][] board;
    protected int ROWS = TTTGraphics2P.ROWS;  // number of rows
    protected int COLS = TTTGraphics2P.COLS;  // number of columns
    protected Seed mySeed;    // computer's seed
    protected Seed oppSeed;   // opponent's seed
    protected int _k;

    public void setK(int _k) {
        this._k = _k;
    }
    protected GameState currentState;

    public Heuristique(Cell[][] _board, int k, GameState _GS, Seed _seed) {
        board = _board;
        mySeed = _seed;
        oppSeed = Seed.CROSS;
        _k = k;
        currentState = _GS;
    }

    public int evaluate() {
        int score = 0;
        for (int i = 0; i < ROWS; i++) {
            score += evaluateLineRow(i);
        }
        for (int i = 0; i < COLS; i++) {
            score += evaluateLineColumn(i);
        }
        for (int i = 0; i + _k <= ROWS; i++) {
            score += evaluateLineDiagUP(i);
        }
        for (int i = 1; i + _k <= ROWS; i++) {
            score += evaluateLineDiagDOWN(i);
        }
        for (int i = 3; i <= ROWS; i++) {
            score += evaluateLineOppDiagUP(i);
            if (i != ROWS) {
                score += evaluateLineOppDiagDOWN(i);
            }
        }
        return score;
    }

    private int evaluateLineRow(int row) {
        int score = 0;
        int x = 0, o = 0;
        for (int i = 0; i < COLS; i++) {
            if (board[row][i].content == mySeed && !board[row][i].isWin()) {
                if (x == 0) {
                    score += Math.pow(10, x);
                } else {
                    score -= Math.pow(10, x - 1);
                    score += Math.pow(10, x);
                }
                x++;
                o = 0;
            }
            if (board[row][i].content == oppSeed && !board[row][i].isWin()) {
                if (o == 0) {
                    score -= Math.pow(10, o);
                } else {
                    score += Math.pow(10, o - 1);
                    score -= Math.pow(10, o);
                }
                o++;
                x = 0;
            }
        }
        return score;
    }

    private int evaluateLineColumn(int col) {
        int score = 0;
        int x = 0, o = 0;
        for (int i = 0; i < ROWS; i++) {
            if (board[i][col].content == mySeed && !board[i][col].isWin()) {
                if (x == 0) {
                    score += Math.pow(10, x);
                } else {
                    score -= Math.pow(10, x - 1);
                    score += Math.pow(10, x);
                }
                x++;
                o = 0;
            }
            if (board[i][col].content == oppSeed && !board[i][col].isWin()) {
                if (o == 0) {
                    score -= Math.pow(10, o);
                } else {
                    score += Math.pow(10, o - 1);
                    score -= Math.pow(10, o);
                }
                o++;
                x = 0;
            }
        }
        return score;
    }

    private int evaluateLineDiagUP(int depth) {
        int score = 0;
        int x = 0, o = 0;
        int j = depth;
        for (int i = 0; i < ROWS; i++) {
            if (j < ROWS) {
                if (board[i][j].content == mySeed && !board[i][j].isWin()) {
                    if (x == 0) {
                        score += Math.pow(10, x);
                    } else {
                        score -= Math.pow(10, x - 1);
                        score += Math.pow(10, x);
                    }
                    x++;
                    o = 0;
                }
                if (board[i][j].content == oppSeed && !board[i][j].isWin()) {
                    if (o == 0) {
                        score -= Math.pow(10, o);
                    } else {
                        score += Math.pow(10, o - 1);
                        score -= Math.pow(10, o);
                    }
                    o++;
                    x = 0;
                }
                j++;
            } else {
                break;
            }
        }
        return score;
    }

    private int evaluateLineDiagDOWN(int depth) {
        int score = 0;
        int x = 0, o = 0;
        int i = depth;
        for (int j = 0; j < ROWS; j++) {
            if (i < ROWS) {
                if (board[i][j].content == mySeed && !board[i][j].isWin()) {
                    if (x == 0) {
                        score += Math.pow(10, x);
                    } else {
                        score -= Math.pow(10, x - 1);
                        score += Math.pow(10, x);
                    }
                    x++;
                    o = 0;
                }
                if (board[i][j].content == oppSeed && !board[i][j].isWin()) {
                    if (o == 0) {
                        score -= Math.pow(10, o);
                    } else {
                        score += Math.pow(10, o - 1);
                        score -= Math.pow(10, o);
                    }
                    o++;
                    x = 0;
                }
            }
            i++;
        }
        return score;
    }

    private int evaluateLineOppDiagUP(int depth) {
        int score = 0;
        int x = 0, o = 0;
        int j = depth - 1;
        for (int i = 0; i < depth; i++) {
            if (j >= 0) {
                if (i + j == depth - 1) {
                    if (board[i][j].content == mySeed && !board[i][j].isWin()) {
                        if (x == 0) {
                            score += Math.pow(10, x);
                        } else {
                            score -= Math.pow(10, x - 1);
                            score += Math.pow(10, x);
                        }
                        x++;
                        o = 0;
                    }
                    if (board[i][j].content == oppSeed && !board[i][j].isWin()) {
                        if (o == 0) {
                            score -= Math.pow(10, o);
                        } else {
                            score += Math.pow(10, o - 1);
                            score -= Math.pow(10, o);
                        }
                        o++;
                        x = 0;
                    }
                }
                j--;
            }
        }
        return score;
    }

    private int evaluateLineOppDiagDOWN(int hauteur) {
        int score = 0;
        int x = 0, o = 0;
        int j = ROWS - hauteur;
        int i = ROWS - 1;
        int u = 2 * ROWS - hauteur - 1;
        for (int p = 0; p < hauteur; p++) {
            if (i > 0 && j < ROWS) {
                if (i + j == u) {
                    if (board[i][j].content == mySeed && !board[i][j].isWin()) {
                        if (x == 0) {
                            score += Math.pow(10, x);
                        } else {
                            score -= Math.pow(10, x - 1);
                            score += Math.pow(10, x);
                        }
                        x++;
                        o = 0;
                    }
                    if (board[i][j].content == oppSeed && !board[i][j].isWin()) {
                        if (o == 0) {
                            score -= Math.pow(10, o);
                        } else {
                            score += Math.pow(10, o - 1);
                            score -= Math.pow(10, o);
                        }
                        o++;
                        x = 0;
                    }
                }
            }
            j++;
            i--;
        }
        return score;
    }
}
