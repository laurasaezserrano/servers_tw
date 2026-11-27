package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class ContSocketServiceGateway implements IRecyclingPlantGateway {

	private static final int TIMEOUT_MS = 5000;

	private final String host;
	private final int port;

	public ContSocketServiceGateway(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public float getCapacity(LocalDate date) {
		String[] parts = send("GET_CAPACITY;" + date); 
		if (parts.length < 2) {
			throw new IllegalStateException("Invalid response from ContSocket");
		}
		return Float.parseFloat(parts[1]);
	}

	@Override
	public void notifyAssignment(int totalDumpsters, int totalContainers, LocalDate date) {
		send("NOTIFY;" + date + ";" + totalDumpsters + ";" + totalContainers);   // respuesta: OK
	}

	private String[] send(String request) {
		try (Socket socket = new Socket()) {
			socket.connect(new InetSocketAddress(host, port), TIMEOUT_MS);
			socket.setSoTimeout(TIMEOUT_MS);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			out.println(request);
			String response = in.readLine();
			if (response == null || !response.startsWith("OK")) {
				throw new IllegalStateException("ContSocket error: " + response);
			}
			return response.split(";");
		} catch (IOException e) {
			throw new IllegalStateException("ContSocket not available: " + e.getMessage(), e);
		}
	}
}