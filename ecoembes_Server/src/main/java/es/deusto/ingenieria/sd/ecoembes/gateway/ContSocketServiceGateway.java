package es.deusto.ingenieria.sd.ecoembes.gateway;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ContSocketServiceGateway implements IRecyclingPlantGateway {
	
	@Value("${contsocket.host}")
	private String host;
	
	@Value("${contsocket.port}")
	private int port;
	
	@Override
	public float getCapacity(LocalDate date) {
		try (Socket socket = new Socket(host, port);
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				DataInputStream in = new DataInputStream(socket.getInputStream())) {

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
		try (Socket socket = new Socket(host, port);
				 DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				 DataInputStream in = new DataInputStream(socket.getInputStream())) {

			out.writeUTF("NOTIFY_ASSIGNMENT#" + totalDumpsters + "#" + totalContainers + "#" + date.toString());
			in.readUTF();
		} catch (Exception e) {
			System.err.println("Error notifying assignment to ContSocket: " + e.getMessage());
		}	
	}
}