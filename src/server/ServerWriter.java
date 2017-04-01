package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import javax.swing.JOptionPane;
import server.gui.MainWindow;

public class ServerWriter implements Runnable {

    private String text;
    private OutputStream outputStream;

    public ServerWriter(OutputStream outputStream, String text) {
        this.outputStream = outputStream;
        this.text = text;
    }

    @Override
    public void run() {
        try  {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            writer.write(text);
            writer.write("\n");
            writer.flush();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(MainWindow.getFrame(), "Не получилось отправить сообщение");
        }

    }

}
