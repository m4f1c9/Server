package server.gui;

import java.awt.BorderLayout;
import java.io.OutputStream;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import server.ServerWriter;
import server.StartServer;
import server.event.StartListener;

public class MainWindow {

    private static JFrame frame;
    private static JTabbedPane tabbedPane;

    public static JFrame getFrame() {
        return frame;
    }

    public static void showMainWindow() {
        frame = new JFrame("Server");
        tabbedPane = new JTabbedPane();
        frame.add(tabbedPane);

        frame.setJMenuBar(createMenuBar());

        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setVisible(true);
    }

    private static JMenuBar createMenuBar() {
        JMenuBar menubar = new JMenuBar();

        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(new StartListener(start));

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener((e) -> {
            System.exit(0);
        });

        JMenu menu = new JMenu("Menu");
        menu.add(start);
        menu.add(exit);

        menubar.add(menu);

        return menubar;
    }

    public static synchronized void addToTabbedPane(JPanel panel) {
        tabbedPane.add(panel);
    }

    public static JPanel createJPanel(JTextArea readTextArea, JTextArea writeTextArea, OutputStream outputStream) {
        JTextArea in;
        JTextArea out;

        JButton button = new JButton("Send");

        in = readTextArea;
        out = writeTextArea;

        button.addActionListener((event) -> {
            StartServer.getWriteThreadPool().submit(new ServerWriter(outputStream, out.getText()));

            synchronized (in) {
                readTextArea.append("me > ");
                readTextArea.append(new Date().toString() + "  ");
                readTextArea.append(out.getText());
                readTextArea.append("\n");
            }

            out.setText(null);

        });

        in.setEnabled(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(in), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(button, BorderLayout.EAST);

        southPanel.add(new JScrollPane(out), BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);

        return panel;

    }

}
