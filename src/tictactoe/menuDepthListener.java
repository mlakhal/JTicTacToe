package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuDepthListener implements ActionListener{
    
     private TTTGraphics2P gameWindow;
    private tailleDepth _frameD;
    
    public menuDepthListener(TTTGraphics2P frame){
        gameWindow = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _frameD = new tailleDepth(gameWindow);
        _frameD.setVisible(true);
    }
    
}
