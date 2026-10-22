import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// the answer of GET /users/me. Only the fields the tests need, the API sends more.
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfile {

    private String id;
    private String email;
    private String firstName;
    private String lastName;

    // Jackson needs an empty constructor and setters to fill the object from the JSON
    public UserProfile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonProperty("last_name")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
