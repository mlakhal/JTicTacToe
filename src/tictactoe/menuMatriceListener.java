package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuMatriceListener implements ActionListener{

      private TTTGraphics2P gameWindow;
    private tailleWindow _frameW;
    
    public menuMatriceListener(TTTGraphics2P frame){
        gameWindow = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _frameW = new tailleWindow(gameWindow);
        _frameW.setVisible(true);
    }
    
}
