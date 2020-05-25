package ie.tcd.pavel.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "groupadmins")
public class GroupAdmin {
    @Id
    private String id;
    private String admin;
    private String groupName;

    public GroupAdmin(String admin, String groupName){
        this.admin = admin;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "GroupAdmin [id=" +id+", admin="+admin+",groupName="+groupName+"]";
    }
}
