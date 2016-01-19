package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class modifierVictoireListener implements ActionListener {

    private JTextField _jtf;
    private TTTGraphics2P game;
    private tailleVictoire _tv;

    public modifierVictoireListener(JTextField tf, TTTGraphics2P window, tailleVictoire tv) {
        _jtf = tf;
        game = window;
        _tv = tv;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int taille = Integer.parseInt(_jtf.getText());
            if (taille >= 1 && taille <= 5) {
                game.setVictoryLength(taille);
            }
            _tv.dispose();
        } catch (Exception ex) {
        }
    }
}
