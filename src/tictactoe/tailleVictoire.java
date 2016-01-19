package tictactoe;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class tailleVictoire extends JFrame {

    private JButton _btn;
    private JTextField jtf;
    private TTTGraphics2P gameWindow;

    public tailleVictoire(TTTGraphics2P win) {
        gameWindow = win;
        jtf = new JTextField();
        jtf.setPreferredSize(new Dimension(150, 30));
        _btn = new JButton("Modifier");
        _btn.addActionListener(new modifierVictoireListener(jtf,win,this));
        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(jtf);
        cp.add(_btn);
        setSize(new Dimension(250, 90));
        setResizable(false);
        setTitle("Nombre d'alignement");
        setLocation(700,300);
    }
}
