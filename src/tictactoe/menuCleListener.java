package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuCleListener implements ActionListener {

    private TTTGraphics2P gameWindow;
    private tailleCle _frameCle;
    
    public menuCleListener(TTTGraphics2P frame){
        gameWindow = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _frameCle = new tailleCle(gameWindow);
        _frameCle.setVisible(true);
    }
    
}
