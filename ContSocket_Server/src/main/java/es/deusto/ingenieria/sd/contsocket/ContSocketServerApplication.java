package es.deusto.ingenieria.sd.contsocket;

import java.io.IOException;

import es.deusto.ingenieria.sd.contsocket.config.ServerConfig;
import es.deusto.ingenieria.sd.contsocket.data.CapacityStore;
import es.deusto.ingenieria.sd.contsocket.protocol.RequestProcessor;
import es.deusto.ingenieria.sd.contsocket.server.ContSocketServer;

public class ContSocketServerApplication {

    public static void main(String[] args) throws IOException {
        ServerConfig config = new ServerConfig("application.properties");
        CapacityStore store = new CapacityStore(config.getDefaultCapacityTons(), config.getDaysInAdvance());
        RequestProcessor processor = new RequestProcessor(store);

        new ContSocketServer(config.getPort(), processor).start();
    }
}