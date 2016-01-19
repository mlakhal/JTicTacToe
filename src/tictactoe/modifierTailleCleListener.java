package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class modifierTailleCleListener implements ActionListener {

    private JTextField _jtf;
    private TTTGraphics2P game;
    private tailleCle _tc;

    public modifierTailleCleListener(JTextField tf, TTTGraphics2P window, tailleCle tc) {
        _jtf = tf;
        game = window;
        _tc = tc;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int taille = Integer.parseInt(_jtf.getText());
            if (taille >= 3 && taille <= 10) {
                game._k = taille;
                game.setK(taille);
            }
            _tc.dispose();
        } catch (Exception ex) {
        }
    }
}
