package ie.tcd.pavel.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "exercises")
public class Exercise {


    public Exercise(String owner, String type, String information,long date)
    {
        this.owner = owner;
        this.type = type;
        this.information = information;
        this.date = date;
    }


    @Id
    private String id;
    private String owner;
    private String type;
    private String information;
    private long date;

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "Challenge [id=" +id+", owner="+owner+", type="+type+",information="+information+"]";
    }


}
