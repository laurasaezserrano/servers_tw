package es.deusto.ingenieria.sd.plasSB.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.deusto.ingenieria.sd.plasSB.dto.LoginRequestDTO;
import es.deusto.ingenieria.sd.plasSB.dto.LoginResponseDTO;

@Service
public class EcoembesAppService {

    private final Map<String, String> loggedEmployees = new HashMap<>();

    public LoginResponseDTO login(LoginRequestDTO request) {

        String token = String.valueOf(System.currentTimeMillis());

        loggedEmployees.put(token, request.getEmail());

        return new LoginResponseDTO(token);
    }
}