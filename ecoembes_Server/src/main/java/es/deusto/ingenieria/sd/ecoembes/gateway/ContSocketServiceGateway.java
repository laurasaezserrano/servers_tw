package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDate;

public class ContSocketServiceGateway implements IRecyclingPlantGateway {
	
	private static final String HOST = "localhost";
	private static final int PORT = 9000;
	
	@Override
	public float getCapacity(LocalDate date) {
		try (Socket socket = new Socket(HOST, PORT);
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				DataInputStream in = new DataInputStream(socket.getInputStream())) {

			// Adjust delimiter/command to match what your ContSocket server parses
			out.writeUTF("GET_CAPACITY#" + date.toString());
			String response = in.readUTF();
			return Float.parseFloat(response);
		} catch (Exception e) {
			System.err.println("Error getting capacity from ContSocket: " + e.getMessage());
			return 0f;
		}
	}

	@Override
	public void notifyAssignment(int totalDumpsters, int totalContainers, LocalDate date) {
		try (Socket socket = new Socket(HOST, PORT);
				 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				 DataInputStream in = new DataInputStream(socket.getInputStream())) {

			// Adjust delimiter/command to match what your ContSocket server parses
			out.writeUTF("NOTIFY_ASSIGNMENT#" + totalDumpsters + "#" + totalContainers + "#" + date.toString());
			in.readUTF(); // Wait for server acknowledgement (e.g., "OK")
		} catch (Exception e) {
			System.err.println("Error notifying assignment to ContSocket: " + e.getMessage());
		}	
	}

}
