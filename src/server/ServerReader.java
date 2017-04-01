package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import server.gui.MainWindow;

public class ServerReader implements Runnable {

    private Socket socket;
    private final JTextArea readTextArea;

    public ServerReader(Socket socket, JTextArea readTextArea) {
        this.socket = socket;
        this.readTextArea = readTextArea;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));) {
            String in;
            while ((in = reader.readLine()) != null) {
                synchronized (readTextArea) {
                    readTextArea.append("client > ");
                    readTextArea.append(new Date().toString() + "  ");
                    readTextArea.append(in);
                    readTextArea.append("\n");
                }

            }
        } catch (IOException ex) {
           JOptionPane.showMessageDialog(MainWindow.getFrame(), "Что-то сломалось с той стороны");
        }
    }

}
