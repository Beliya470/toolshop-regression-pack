import com.fasterxml.jackson.annotation.JsonProperty;

// the body of POST /users/register (UserRequest in the Swagger docs).
// The API names are first_name and last_name, so @JsonProperty gives the JSON name.
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dob;

    public RegisterRequest(String firstName, String lastName, String email, String password, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("last_name")
    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDob() {
        return dob;
    }
}
