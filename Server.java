import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * @Author: Yang
 * @Copyright: Yang
 * @Date: 2021-02-24 10:01:20
 * @LastEditors: Yang
 * @LastEditTime: 2021-02-24 10:26:58
 * @FilePath: /test/Server.java
 */

/**
 * @author yang
 */
public class Server {
    public static void main(String[] args) {
        ExecutorService socketThreadPool = Executors.newCachedThreadPool();
        try (var server = new ServerSocket(8120)) {
            while (true) {
                var socket = server.accept();
                socketThreadPool.execute(new ServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerHandler implements Runnable {
    private Socket incoming;

    public ServerHandler(Socket incoming) {
        this.incoming = incoming;
    }

    @Override
    public void run() {
        try (var out = new PrintWriter(
                new OutputStreamWriter(new BufferedOutputStream(incoming.getOutputStream()), StandardCharsets.UTF_8),
                true); var in = new Scanner(incoming.getInputStream(), StandardCharsets.UTF_8)) {
            out.println("Server is successfully accept your connection, please enter some strings.");
            incoming.shutdownOutput();

            boolean flag = false;
            while (!flag && in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println(incoming.getInetAddress() + ": " + line);
                if ("BYE".equals(line.trim())) {
                    flag = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}