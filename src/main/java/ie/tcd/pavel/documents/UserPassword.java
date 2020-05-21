package ie.tcd.pavel.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "userpasswords")
public class UserPassword {
    @Id
    private String id;
    private String login;
    private String password;

    public UserPassword(String login, String password)
    {
        this.login = login;
        this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }
}
