package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;

public class modifierTailleMatriceListener implements ActionListener{

    private JTextField _jtf;
    private TTTGraphics2P game;
    private tailleWindow _tW;
    
    public modifierTailleMatriceListener(JTextField tf,TTTGraphics2P window, tailleWindow tW){
        _jtf = tf;
        game = window;
        _tW = tW;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        try{
            int taille = Integer.parseInt(_jtf.getText());
            if ( taille >=3 && taille <=10){
                TTTGraphics2P.newN(taille);
                TTTGraphics2P _nFrame = new TTTGraphics2P();
                game.dispose();
                game = _nFrame;
                game.setVisible(true);
            }
            _tW.dispose();
        }
        catch(Exception ex){}
        
    }
    
}
