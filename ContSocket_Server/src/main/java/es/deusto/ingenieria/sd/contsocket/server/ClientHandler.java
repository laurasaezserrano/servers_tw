package es.deusto.ingenieria.sd.contsocket.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
             DataInputStream in = new DataInputStream(client.getInputStream());
             DataOutputStream out = new DataOutputStream(client.getOutputStream())) {

            client.setSoTimeout(TIMEOUT_MS); // a client that never sends anything cannot block the thread forever
            
            String request = in.readUTF();
            String response = processor.process(request);
            out.writeUTF(response);
            out.flush();

        } catch (IOException e) {
            System.err.println("Error with a client in ContSocket: " + e.getMessage());
        }
    }
}