package es.deusto.ingenieria.sd.contsocket.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ServerConfig {

    private final Properties props = new Properties();

    public ServerConfig(String resourceName) {
        try (InputStream in = ServerConfig.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (in == null) {
                throw new IllegalStateException("Configuration file not found: " + resourceName);
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read " + resourceName, e);
        }
    }

    public int getPort() {
        return Integer.parseInt(props.getProperty("server.port"));
    }

    public double getDefaultCapacityTons() {
        return Double.parseDouble(props.getProperty("capacity.default.tons"));
    }

    public int getDaysInAdvance() {
        return Integer.parseInt(props.getProperty("capacity.days.in.advance"));
    }
}