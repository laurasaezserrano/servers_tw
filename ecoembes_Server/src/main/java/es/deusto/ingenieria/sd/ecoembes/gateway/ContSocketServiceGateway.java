package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDate;

public class ContSocketServiceGateway implements IRecyclingPlantGateway {

	private static final int TIMEOUT_MS = 5000;
	private static final String SEP = "#";

	private final String host;
	private final int port;

	public ContSocketServiceGateway(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public float getCapacity(LocalDate date) {
		String response = send("GET_CAPACITY" + SEP + date);   // respuesta: <toneladas>, p.ej. 100.0
		try {
			return Float.parseFloat(response.trim());
		} catch (NumberFormatException e) {
			throw new IllegalStateException("Invalid response from ContSocket: " + response, e);
		}
	}

	@Override
	public void notifyAssignment(int totalDumpsters, int totalContainers, LocalDate date) {
		send("NOTIFY_ASSIGNMENT" + SEP + totalDumpsters + SEP + totalContainers + SEP + date);
	}

	private String send(String request) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(host, port), TIMEOUT_MS);
			socket.setSoTimeout(TIMEOUT_MS);
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			DataInputStream in = new DataInputStream(socket.getInputStream());

			out.writeUTF(request);
			out.flush();
			String response = in.readUTF();

			if (response.startsWith("ERROR")) {
				throw new IllegalStateException("ContSocket error: " + response);
			}
			return response;
		} catch (IOException e) {
			throw new IllegalStateException("ContSocket not available: " + e.getMessage(), e);
		}
	}
}