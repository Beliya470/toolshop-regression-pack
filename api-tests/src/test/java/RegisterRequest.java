import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

// the body of POST /users/register (UserRequest in the Swagger docs).
// The API names are first_name and last_name, so @JsonProperty gives the JSON name.
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dob;

    // only for the with-bugs API (-Dregister.flat=true). It wants address, city and country as plain
    // text and refuses the body without them. On the normal API these stay null and are not sent.
    private String address;
    private String city;
    private String country;

    public RegisterRequest(String firstName, String lastName, String email, String password, String dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.dob = dob;
        if (Boolean.getBoolean("register.flat")) {
            this.address = "1 Main Street";
            this.city = "Nairobi";
            this.country = "KE";
        }
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
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
