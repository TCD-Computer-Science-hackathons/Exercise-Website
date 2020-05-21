package ie.tcd.pavel.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "grouppasswords")
public class GroupPassword {
    @Id
    private String id;
    private String name;
    private String password;

    public GroupPassword(String name, String password)
    {
        this.name = name;
        this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(password);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
