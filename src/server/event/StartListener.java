package server.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import server.StartServer;

public class StartListener implements ActionListener {

    private JMenuItem menuItem;

    public StartListener(JMenuItem menuItem) {
        this.menuItem = menuItem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        new Thread(new StartServer()).start();

        menuItem.setEnabled(false);
    }

}
