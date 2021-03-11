import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*
 * @Author: Yang
 * @Copyright: Yang
 * @Date: 2021-02-24 10:01:30
 * @LastEditors: Yang
 * @LastEditTime: 2021-02-24 10:57:28
 * @FilePath: /test/Client.java
 */

/**
 * @author yang
 */
public class Client {
    public static void main(String[] args) {
        try (var connection = new Socket("localhost", 8120);
                var in = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8);
                var out = new PrintWriter(new OutputStreamWriter(connection.getOutputStream(), StandardCharsets.UTF_8),
                        true);
                var system = new Scanner(System.in)) {
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
            while (system.hasNextLine()) {
                String line = system.nextLine();
                System.out.println("Your input: " + line);
                out.println(line);
                if ("BYE".equals(line)) {
                    System.out.println("Client will be shutdown within 1s.");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
