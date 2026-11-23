package es.deusto.ingenieria.sd.contsocket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.deusto.ingenieria.sd.contsocket.protocol.RequestProcessor;

public class ContSocketServer {

    private final int port;
    private final RequestProcessor processor;
    private final ExecutorService pool = Executors.newFixedThreadPool(10);

    public ContSocketServer(int port, RequestProcessor processor) {
        this.port = port;
        this.processor = processor;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("ContSocket server listening on port " + port);
            while (true) {
                Socket client = serverSocket.accept();
                pool.execute(new ClientHandler(client, processor));
            }
        } finally {
            pool.shutdown();
        }
    }
}