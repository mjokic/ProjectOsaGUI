package model;


public class LoginCredentials {

    private String email;
    private String password;
    private String device_token;

    public LoginCredentials(String email, String password, String device_token){
        this.email = email;
        this.password = password;
        this.device_token = device_token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }
}
