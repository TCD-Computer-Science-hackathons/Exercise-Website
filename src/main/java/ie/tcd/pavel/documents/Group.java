package ie.tcd.pavel.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groups")
public class Group {

    @Id
    private String id;

    private String name;
    private String user;

    public Group(String name, String user)
    {
        this.name = name;
        this.user = user;
    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String users) {
        this.user = user;
    }

    public String toString()
    {
        return "Group [id="+id+", name="+name+", user="+user+"]";
    }

}
