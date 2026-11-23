package es.deusto.ingenieria.sd.contsocket.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import es.deusto.ingenieria.sd.contsocket.protocol.RequestProcessor;

public class ClientHandler implements Runnable {

    private static final int TIMEOUT_MS = 5000;

    private final Socket socket;
    private final RequestProcessor processor;

    public ClientHandler(Socket socket, RequestProcessor processor) {
        this.socket = socket;
        this.processor = processor;
    }

    @Override
    public void run() {
        try (Socket client = socket;
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(
                     new OutputStreamWriter(client.getOutputStream(), StandardCharsets.UTF_8), true)) {

            client.setSoTimeout(TIMEOUT_MS); // a client that never sends anything cannot block the thread forever
            String request = in.readLine();
            String response = processor.process(request);
            out.println(response);

        } catch (IOException e) {
            System.err.println("Error with a client: " + e.getMessage());
        }
    }
}