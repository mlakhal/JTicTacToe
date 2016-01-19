package tictactoe;

import java.util.ArrayList;
import java.util.List;

public class Evaluation {

    private List<int[]> _winCells = new ArrayList<>();
    private Cell[][] board;
    protected int ROWS = TTTGraphics2P.ROWS;  
    protected int COLS = TTTGraphics2P.COLS;  
    protected Seed mySeed;    
    protected int _k;
    protected GameState currentState;
    public static int _nbWin = 1;
    private int cpt = 0;

    public Evaluation(Cell[][] _board, int k, GameState _GS, Seed _seed) {
        board = _board;
        mySeed = _seed;
        _k = k;
        currentState = _GS;
        cpt = 0;
    }

    private boolean evaluateWonLineRow(Seed _seed, int row, int taille, boolean add) {
        boolean result = false;
        int k = 0;
        _winCells.removeAll(_winCells);
        for (int i = 0; i < COLS; i++) {
            if (board[row][i].content == _seed && !board[row][i].isWin()) {
                k++;
                if (add) {
                    _winCells.add(new int[]{row, i});
                }
                if (k == taille) {
                    currentState = (_seed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
                    result = true;
                    break;
                }
            } else {
                k = 0;
                if (add) {
                    _winCells.removeAll(_winCells);
                }
            }
        }
        return result;
    }

    private boolean evaluateWonLineColumn(Seed _seed, int col, int taille, boolean add) {
        boolean result = false;
        int k = 0;
        _winCells.removeAll(_winCells);
        for (int i = 0; i < ROWS; i++) {
            if (board[i][col].content == _seed && !board[i][col].isWin()) {
                k++;
                if (add) {
                    _winCells.add(new int[]{i, col});
                }
                if (k == taille) {
                    currentState = (_seed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
                    result = true;
                    break;
                }
            } else {
                k = 0;
                if (add) {
                    _winCells.removeAll(_winCells);
                }
            }
        }
        return result;
    }

    private boolean evaluateWonLineDiagUP(Seed _seed, int taille, int depth, boolean add) {
        boolean result = false;
        int k = 0;
        int j = depth;
        _winCells.removeAll(_winCells);
        for (int i = 0; i < ROWS; i++) {
            if (j < ROWS) {
                if (board[i][j].content == _seed && !board[i][j].isWin()) {
                    k++;
                    if (add) {
                        _winCells.add(new int[]{i, j});
                    }
                    if (k == taille) {
                        currentState = (_seed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
                        result = true;
                        break;
                    }
                } else {
                    k = 0;
                    if (add) {
                        _winCells.removeAll(_winCells);
                    }
                }
                j++;
            } else {
                break;
            }
        }
        return result;
    }

    public void setK(int _k) {
        this._k = _k;
    }

    private boolean evaluateWonLineDiagDOWN(Seed _seed, int taille, int depth, boolean add) {
        boolean result = false;
        int k = 0;
        int i = depth;
        _winCells.removeAll(_winCells);
        for (int j = 0; j < ROWS; j++) {
            if (i < ROWS) {
                if (board[i][j].content == _seed && !board[i][j].isWin()) {
                    k++;
                    if (add) {
                        _winCells.add(new int[]{i, j});
                    }
                    if (k == taille) {
                        currentState = (_seed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
                        result = true;
                        break;
                    }
                } else {
                    k = 0;
                    if (add) {
                        _winCells.removeAll(_winCells);
                    }
                }
            }
            i++;
        }
        return result;
    }

    private boolean evaluateWonLineOppDiagUP(Seed _seed, int taille, int depth, boolean add) {
        boolean result = false;
        int k = 0;
        int j = depth - 1;
        int i;
        _winCells.removeAll(_winCells);
        for (i = 0; i < depth; i++) {
            if (j >= 0) {
                if (i + j == depth - 1) {
                    if (board[i][j].content == _seed && !board[i][j].isWin()) {
                        k++;
                        if (add) {
                            _winCells.add(new int[]{i, j});
                        }
                        if (k == taille) {
                            currentState = (_seed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
                            result = true;
                            break;
                        }
                    } else {
                        k = 0;
                        if (add) {
                            _winCells.removeAll(_winCells);
                        }
                    }
                }
                j--;
            }
        }
        return result;
    }

    private boolean evaluateWonLineOppDiagDOWN(Seed _seed, int taille, int hauteur, boolean add) {
        boolean result = false;
        int k = 0;
        int j = ROWS - hauteur;
        int i = ROWS - 1;
        int u = 2 * ROWS - hauteur - 1;
        _winCells.removeAll(_winCells);
        for (int p = 0; p < hauteur; p++) {
            if (i > 0 && j < ROWS) {
                if (i + j == u) {
                    if (board[i][j].content == _seed && !board[i][j].isWin()) {
                        k++;
                        if (add) {
                            _winCells.add(new int[]{i, j});
                        }
                        if (k == taille) {
                            currentState = (_seed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
                            result = true;
                            break;
                        }
                    } else {
                        k = 0;
                        if (add) {
                            _winCells.removeAll(_winCells);
                        }
                    }
                }
            }
            j++;
            i--;
        }
        return result;
    }

    private void fillBoard() {
        for (int[] cell : _winCells) {
            board[cell[0]][cell[1]].setWin(true);
        }
    }

    public boolean hasWon(boolean evaluate) {

        boolean result = false;

        for (int i = 0; i < ROWS; i++) {
            if (evaluate) {
                result = evaluateWonLineRow(mySeed, i, _k, true);
            } else {
                result = evaluateWonLineRow(mySeed, i, _k, false);
            }

            if (result == true && evaluate) {
                fillBoard();
                cpt++;
                if (cpt == _nbWin) {
                    cpt = 0;
                    return true;
                }
            }
        }
        
        
        for (int i = 0; i < COLS; i++) {
            if (evaluate) {
                result = evaluateWonLineColumn(mySeed, i, _k, true);
            } else {
                result = evaluateWonLineColumn(mySeed, i, _k, false);
            }

            if (result == true && evaluate) {
                fillBoard();
                cpt++;                
                if (cpt == _nbWin) {
                    cpt = 0;
                    return true;
                }
            }
        }

        for (int i = 0; i + _k <= ROWS; i++) {
            if (!result) {
                if (evaluate) {
                    result = evaluateWonLineDiagUP(mySeed, _k, i, true);
                } else {
                    result = evaluateWonLineDiagUP(mySeed, _k, i, false);
                }

                if (result == true && evaluate) {
                    fillBoard();
                    cpt++;
                    if (cpt == _nbWin) {
                        cpt = 0;
                        return true;
                    } else {
                        result = false;
                    }
                }
            }
        }

        for (int i = 1; i + _k <= ROWS; i++) {
            if (!result) {
                if (evaluate) {
                    result = evaluateWonLineDiagDOWN(mySeed, _k, i, true);
                } else {
                    result = evaluateWonLineDiagDOWN(mySeed, _k, i, false);
                }

                if (result == true && evaluate) {
                    fillBoard();
                    cpt++;
                    if (cpt == _nbWin) {
                        cpt = 0;
                        return true;
                    } else {
                        result = false;
                    }
                }
            }
        }

        for (int i = 3; i <= ROWS; i++) {
            if (!result) {
                if (evaluate) {
                    result = evaluateWonLineOppDiagUP(mySeed, _k, i, true);
                } else {
                    result = evaluateWonLineOppDiagUP(mySeed, _k, i, false);
                }

                if (result == true && evaluate) {
                    fillBoard();
                    cpt++;
                    if (cpt == _nbWin) {
                        cpt = 0;
                        return true;
                    } else {
                        result = false;
                    }
                }
            }
            if (!result && i != ROWS) {
                if (evaluate) {
                    result = evaluateWonLineOppDiagDOWN(mySeed, _k, i, true);
                } else {
                    result = evaluateWonLineOppDiagDOWN(mySeed, _k, i, false);
                }

                if (result == true && evaluate) {
                    fillBoard();
                    cpt++;
                    if (cpt == _nbWin) {
                        cpt = 0;
                        return true;
                    } else {
                        result = false;
                    }
                }
            }
        }

        return false;
    }
}
