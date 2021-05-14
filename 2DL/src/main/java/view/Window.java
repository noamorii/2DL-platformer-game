package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class Window extends JFrame implements ActionListener{

    public Window() {

        setTitle("2DL");
        setSize(new Dimension(1500, 800));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        //addKeyListener(c);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

}
