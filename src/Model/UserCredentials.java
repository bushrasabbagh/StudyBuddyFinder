package Model;

/**
 * Represents a <code>User</code>
 *
 * @author fredrikpettersson
 */
public class UserCredentials {

    private String username;
    private String password;
    private String email;
    private final String[] credentialDTO;

    public UserCredentials(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.credentialDTO = new String[]{username, password, email};
    }


    public String[] getcredentialDTO() {
        return credentialDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
