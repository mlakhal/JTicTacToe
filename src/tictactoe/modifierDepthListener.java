package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class modifierDepthListener implements ActionListener {
    
    private JTextField _jtf;
    private TTTGraphics2P game;
    private tailleDepth _tD;

    public modifierDepthListener(JTextField tf, TTTGraphics2P window, tailleDepth td) {
        _jtf = tf;
        game = window;
        _tD = td;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int depth = Integer.parseInt(_jtf.getText());
            if (depth >= 1 && depth <= 10) {
                game.setDepth(depth);
            }
            _tD.dispose();
        } catch (Exception ex) {
        }
    }
    
}
