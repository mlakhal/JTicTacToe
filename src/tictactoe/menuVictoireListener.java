package tictactoe;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuVictoireListener implements ActionListener{

    private TTTGraphics2P gameWindow;
    private tailleVictoire _frameV;
    
    public menuVictoireListener(TTTGraphics2P frame){
        gameWindow = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _frameV = new tailleVictoire(gameWindow);
        _frameV.setVisible(true);
    }
    
}
