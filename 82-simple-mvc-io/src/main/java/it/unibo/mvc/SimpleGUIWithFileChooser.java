package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final JFrame frame = new JFrame();

    private SimpleGUIWithFileChooser(final Controller ctrl) {
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JTextField fieldText = new JTextField();
        fieldText.setEditable(false);

        final LayoutManager layout = new BorderLayout();
        final JTextArea text = new JTextArea();
        final JPanel panel1 = new JPanel();
        final JPanel panel2 = new JPanel();
        final LayoutManager layout2 = new BorderLayout();

        panel1.setLayout(layout);
        panel2.setLayout(layout2);

        final JButton save = new JButton("Save");
        final JButton browse = new JButton("Browse...");

        panel2.add(fieldText, BorderLayout.CENTER);
        panel2.add(browse, BorderLayout.EAST);
        panel1.add(panel2, BorderLayout.NORTH);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    ctrl.setString(text.getText());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final JFileChooser choose = new JFileChooser();
                final int ris = choose.showSaveDialog(browse);
    
                if (ris == JFileChooser.APPROVE_OPTION) {
                    final File dest = choose.getSelectedFile();
                    fieldText.setText(dest.getPath());
                } else {
                    
                }
            }
        });

        panel1.add(text, BorderLayout.CENTER);
        panel1.add(save, BorderLayout.SOUTH);
        frame.setContentPane(panel1);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screen.getWidth();
        final int height = (int) screen.getHeight();
        frame.setSize(width, height);
        frame.setLocationByPlatform(true);
    }

    private void display() {
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        final SimpleGUIWithFileChooser gui = new SimpleGUIWithFileChooser(new Controller());
        gui.display();
    }
}
