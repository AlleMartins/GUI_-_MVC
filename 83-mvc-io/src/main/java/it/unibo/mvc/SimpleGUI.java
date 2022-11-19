package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame();
    private final Controller controller;
    /**
     * builds a new {@link SimpleGUI}.
     * @param controller the controller instance.
     */
    @SuppressFBWarnings(
        value = { "EI_EXPOSE_REP2" },
        justification = "The controller is designed to be manipulated this way."
    )

    public SimpleGUI(final Controller controller) {
        this.controller = controller;
        /**
         * Costruzione JPanel & graphical interface senza bottoni
         * 
         */
        final JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BorderLayout());
        final JTextField fieldText = new JTextField();
        final JTextArea areaText = new JTextArea();
        firstPanel.add(fieldText, BorderLayout.NORTH);
        firstPanel.add(areaText, BorderLayout.CENTER);

        /**
         * Aggiunta bottoni 
         * 
         */
        final JPanel secondPanelSouth = new JPanel();
        secondPanelSouth.setLayout(new BoxLayout(secondPanelSouth, BoxLayout.LINE_AXIS));
        final JButton buttonPrint = new JButton("Print");
        final JButton buttonShowHystory = new JButton("Show Hystory");
        secondPanelSouth.add(buttonPrint);
        secondPanelSouth.add(buttonShowHystory);

        firstPanel.add(secondPanelSouth, BorderLayout.SOUTH);

        frame.setContentPane(firstPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int) screen.getWidth();
        final int height = (int) screen.getHeight();
        frame.setSize(width / 4, height / 4);
        frame.setLocationByPlatform(true);

        buttonPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimpleGUI.this.controller.setNextString(fieldText.getText());
                SimpleGUI.this.controller.currentString();
            }
        });

        buttonShowHystory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final List<String> hystory = SimpleGUI.this.controller.hystoryPrintedString();
                for (final String put : hystory) {
                    areaText.setText(put);
                }
            }
        });
    }

    private void display() {
        frame.setVisible(true);
    }
    /**
     * 
     * @param args
     *            ignored
     */
    public static void main(final String[] args) {
            new SimpleGUI(new SimpleController()).display();
    }
}
