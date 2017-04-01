package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import server.gui.MainWindow;

public class StartServer implements Runnable {

    private static ThreadPoolExecutor writeThreadPool = new ThreadPoolExecutor(2, 128, 10L, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(256));
    private static ThreadPoolExecutor readThreadPool = new ThreadPoolExecutor(2, 128, 10L, TimeUnit.DAYS, new ArrayBlockingQueue<Runnable>(256));
    private JPanel panel;

    public static ThreadPoolExecutor getWriteThreadPool() {
        return writeThreadPool;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(9090);) {
            while (true) {

                Socket socket = serverSocket.accept();
                JTextArea readTextArea = new JTextArea();
                JTextArea writeTextArea = new JTextArea();

                panel = MainWindow.createJPanel(readTextArea, writeTextArea, socket.getOutputStream());

                readThreadPool.submit(new ServerReader(socket, readTextArea));
                MainWindow.addToTabbedPane(panel);
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(panel, "Не получилось включить сервер.");

        }
    }

}
