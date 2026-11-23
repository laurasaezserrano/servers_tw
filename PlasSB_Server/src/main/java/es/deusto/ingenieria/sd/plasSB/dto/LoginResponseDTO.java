package es.deusto.ingenieria.sd.plasSB.dto;

public class LoginResponseDTO {

    private String token;

    //CONSTRUCTOR
    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token) {
        this.token = token;
    }

    //GETTERS AND SETTERS
    //Token
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}