package server.gui;

import javax.swing.SwingUtilities;

public class MainClass {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> MainWindow.showMainWindow());
    }
}
