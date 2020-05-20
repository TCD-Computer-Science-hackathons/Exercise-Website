package ie.tcd.pavel.documents;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    public User(String login, String password)
    {
        this.login = login;
        this.password = password;
    }

    @Id
    private String id;
    private String login;
    private String password;
    private String forename = "Anonymous";
    private String surname = "Anonymous";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User [id=" +id+", login="+login+", password="+password+",forename="+forename+
                ", surname="+surname+"]";
    }

}
