// the body of POST /users/login. Jackson turns the fields into JSON: {"email": "...", "password": "..."}
public class LoginRequest {

    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Jackson reads the fields through the getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
