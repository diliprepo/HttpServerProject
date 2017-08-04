
package com.org.api.server;
//Build and run the following code using JDK 7 or later, to establish an HTTP server that prints the current system time:
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
 
/*
Simple HTTP server class which writes the current system date as a response.
Be sure to change the port as needed.
*/
public class SimpleHTTPServer {
                public static void main(String args[]) throws IOException {
                                ServerSocket server = new ServerSocket(8080);
                                System.out.println("Listening for connection on port 8080 ....");
 
                                while (true) {
                                                try (Socket socket = server.accept()) {
                                                                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + (new Date());
                                                                socket.getOutputStream().write(httpResponse.getBytes("UTF-8"));
                                                }
                                }
                }
}