package tictactoe;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Tic-Tac-Toe: Two-player Graphics version with Simple-OO
 */
@SuppressWarnings("serial")
public class TTTGraphics2P extends JFrame {

    // Named-constants for the game board
    public static int ROWS = 8;  // ROWS by COLS cells
    public static int COLS = 8;
    // Named-constants of the various dimensions used for graphics drawing
    public static final int CELL_SIZE = 50; // cell width and height (square)
    public static int CANVAS_WIDTH = CELL_SIZE * COLS;  // the drawing canvas
    public static int CANVAS_HEIGHT = CELL_SIZE * ROWS;
    public static final int GRID_WIDTH = 8;                   // Grid-line's width
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // width/height
    public static final int SYMBOL_STROKE_WIDTH = 8; // pen's stroke width
    private AIPlayerAlphaBeta _AI;
    private AIPlayerMinMax _AIMinMax;
    public static int choise = 1;
    public int _k = 4;
    private GameState currentState;  // the current game state
    private Seed currentPlayer;  // the current player
    private Cell[][] board; // Game board of ROWS-by-COLS cells
    private DrawCanvas canvas; // Drawing canvas (JPanel) for the game board
    private JLabel statusBar;  // Status Bar
    private Evaluation _ev;

    public void setK(int taille) {
        if (choise == 1) {
            _AI.setK(taille);
        }
        if (choise == 2) {
            _AIMinMax.setK(taille);
        }
        _k = taille;
    }

    public void setVictoryLength(int length) {
        if (choise == 1) {
            _AI.setVictoryLength(length);
        }
        if (choise == 2) {
            _AIMinMax.setVictoryLength(length);
        }
    }

    public void setDepth(int depth) {
        AIPlayerAlphaBeta._depth = depth;
        AIPlayerMinMax._depth = depth;
    }

    public static void newN(int n) {
        ROWS = n;
        COLS = n;
        CANVAS_WIDTH = CELL_SIZE * COLS; 
        CANVAS_HEIGHT = CELL_SIZE * ROWS;
    }
    
    
    public TTTGraphics2P() {
        
        canvas = new DrawCanvas();  // Construct a drawing canvas (a JPanel)
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        // The canvas (JPanel) fires a MouseEvent upon mouse-click
        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                int rowSelected = mouseY / CELL_SIZE;
                int colSelected = mouseX / CELL_SIZE;

                if (currentState == GameState.PLAYING) {
                    if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0
                            && colSelected < COLS && board[rowSelected][colSelected].content == Seed.EMPTY) {

                        board[rowSelected][colSelected].content = Seed.CROSS; // Make a move
                        updateGame(Seed.CROSS, rowSelected, colSelected); // update state
                        // Switch player
                        currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                        try {
                            if (currentState == GameState.PLAYING) {

                                int[] result;
                                if (choise == 1) {
                                    result = _AI.move();
                                    board[result[0]][result[1]].content = currentPlayer;
                                }
                                if (choise == 2) {
                                    result = _AIMinMax.move();
                                    board[result[0]][result[1]].content = currentPlayer;
                                }
                                updateGameAI(currentPlayer, rowSelected, colSelected); // update state
                                // Switch player
                                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                            }
                        } catch (Exception ea) {
                        }

                    }
                } else {       // game over
                    initGame(); // restart the game
                }
                // Refresh the drawing canvas
                repaint();  // Call-back paintComponent().
            }
        });

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel("  ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(canvas, BorderLayout.CENTER);
        cp.add(statusBar, BorderLayout.SOUTH); 

        JMenuBar menuBar = new JMenuBar();
        // Add a JMenu
        JMenu file = new JMenu("Fichier");
        JMenu jeu = new JMenu("Jeu");
        JMenuItem fermer = new JMenuItem("Fermer");
        JMenu taille = new JMenu("Taille");
        JMenuItem _tMatrice = new JMenuItem("Matrice");
        _tMatrice.addActionListener(new menuMatriceListener(this));
        JMenuItem _tCle = new JMenuItem("Clé");
        _tCle.addActionListener(new menuCleListener(this));
        JMenuItem _nbAlig = new JMenuItem("Alignement");
        _nbAlig.addActionListener(new menuVictoireListener(this));
        JMenuItem _depth = new JMenuItem("Profondeur");
        _depth.addActionListener(new menuDepthListener(this));
        taille.add(_tMatrice);
        taille.add(_tCle);
        taille.add(_nbAlig);
        taille.add(_depth);
        JMenu _algo = new JMenu("Algorithme");
        JMenuItem _minMax = new JMenuItem("MinMax");
        _minMax.addActionListener(new minMaxListener());
        JMenuItem _minMaxAB = new JMenuItem("MinMax-AlphaBeta");
        _minMaxAB.addActionListener(new minMaxAlphaBetaListener());
        _algo.add(_minMax);
        _algo.add(_minMaxAB);
        fermer.addActionListener(new closeWindowListener(this));
        fermer.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_F4,
                java.awt.Event.ALT_MASK));
        fermer.setPreferredSize(new Dimension(100, 30));
        file.add(fermer);
        jeu.add(taille);
        jeu.add(_algo);
        menuBar.add(file);
        menuBar.add(jeu);
        cp.add(menuBar, BorderLayout.NORTH);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();  // pack all the components in this JFrame
        setTitle("Tic Tac Toe");
        setVisible(true);  // show this JFrame

        board = new Cell[ROWS][COLS]; // allocate array
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col] = new Cell(); // allocate element of the array
            }
        }
        initGame(); // initialize the game board contents and game variables
        _ev = new Evaluation(board, _k, currentState, Seed.CROSS);
        _AI = new AIPlayerAlphaBeta(board, _k, currentState, Seed.NOUGHT, _ev);
        _AIMinMax = new AIPlayerMinMax(board, _k, currentState, Seed.NOUGHT, _ev);
        setLocation(500, 150);
    }

    public TTTGraphics2P(Cell[][] _b) {
        this();
        board = _b;
    }

    public void initGame() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board[row][col].content = Seed.EMPTY; // all cells empty
                board[row][col].setWin(false);
            }
        }
        currentState = GameState.PLAYING; // ready to play
        currentPlayer = Seed.CROSS;       // cross plays first
    }

    public void updateGame(Seed theSeed, int rowSelected, int colSelected) {
        if (_ev.hasWon(true)) {  // check for win
            currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (isDraw()) {  // check for draw
            currentState = GameState.DRAW;
        }
        // Otherwise, no change to current state (still GameState.PLAYING).
    }

    public void updateGameAI(Seed theSeed, int rowSelected, int colSelected) {

        if (choise == 1) {
            if (_AI.hasWon()) {  // check for win
                currentState = GameState.NOUGHT_WON;
            } else if (isDraw()) {  // check for draw
                currentState = GameState.DRAW;
            }
        }
        if (choise == 2) {
            if (_AIMinMax.hasWon()) {  // check for win
                currentState = GameState.NOUGHT_WON;
            } else if (isDraw()) {  // check for draw
                currentState = GameState.DRAW;
            }
        }

    }

    public boolean isDraw() {
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col].content == Seed.EMPTY) {
                    return false; // an empty cell found, not draw, exit
                }
            }
        }
        return true;  // no more empty cell, it's a draw
    }

    class DrawCanvas extends JPanel {

        @Override
        public void paintComponent(Graphics g) {  // invoke via repaint()
            super.paintComponent(g);    // fill background
            setBackground(Color.WHITE); // set its background color

            // Draw the grid-lines
            g.setColor(Color.LIGHT_GRAY);
            for (int row = 1; row < ROWS; ++row) {
                g.fillRoundRect(0, CELL_SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                        GRID_WIDTH, CANVAS_HEIGHT - 1, GRID_WIDTH, GRID_WIDTH);
            }

            // Draw the Seeds of all the cells if they are not empty
            // Use Graphics2D which allows us to set the pen's stroke
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(SYMBOL_STROKE_WIDTH, BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND));  // Graphics2D only
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    int x1 = col * CELL_SIZE + CELL_PADDING;
                    int y1 = row * CELL_SIZE + CELL_PADDING;
                    if (board[row][col].content == Seed.CROSS) {
                        g2d.setColor(Color.RED);
                        int x2 = (col + 1) * CELL_SIZE - CELL_PADDING;
                        int y2 = (row + 1) * CELL_SIZE - CELL_PADDING;
                        g2d.drawLine(x1, y1, x2, y2);
                        g2d.drawLine(x2, y1, x1, y2);
                    } else if (board[row][col].content == Seed.NOUGHT) {
                        g2d.setColor(Color.BLUE);
                        g2d.drawOval(x1, y1, SYMBOL_SIZE, SYMBOL_SIZE);
                    }
                }
            }

            // Print status-bar message
            if (currentState == GameState.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                if (currentPlayer == Seed.CROSS) {
                    statusBar.setText("Tour de X");
                } else {
                    statusBar.setText("Tour de O");
                }
            } else if (currentState == GameState.DRAW) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("Matche nul! Cliquer pour rejouer.");
            } else if (currentState == GameState.CROSS_WON) {
                statusBar.setForeground(Color.RED);
                statusBar.setText("'X' a gagné! Cliquer pour rejouer.");
            } else if (currentState == GameState.NOUGHT_WON) {
                statusBar.setForeground(Color.BLUE);
                statusBar.setText("'O' a gagné! Cliquer pour rejouer.");
            }
        }
    }

    /**
     * The entry main() method
     */
    public static void main(String[] args) {
        // Run GUI codes in the Event-Dispatching thread for thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TTTGraphics2P(); // Let the constructor do the job
            }
        });
    }
}
