package ie.tcd.pavel.documents;

import ie.tcd.pavel.BeanUtil;
import ie.tcd.pavel.utility.ExerciseAdaptor;
import ie.tcd.pavel.utility.ExerciseTypes;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "exercises")
public class Exercise {


    public Exercise(String owner, String type, String information,long date)
    {
        this.owner = owner;
        this.type = type;
        this.information = information;
        this.date = date;
        this.normalDate = new Date(date);
        ExerciseTypes exerciseTypes = BeanUtil.getBean(ExerciseTypes.class);
        if(exerciseTypes.getDistanceExercises().contains(type))
        {
            this.normalInfo = String.valueOf(ExerciseAdaptor.getDistanceValue(information))+" m";
        }
        else if(exerciseTypes.getRepExercises().contains(type))
        {
            this.normalInfo = String.valueOf(ExerciseAdaptor.getRepsValue(information));
        }
        else if(exerciseTypes.getTimeExercises().contains(type))
        {
            this.normalInfo = String.valueOf(ExerciseAdaptor.getTimeValue(information))+" mins";
        }
        else if(exerciseTypes.getWeightExercises().contains(type))
        {
            this.normalInfo = String.valueOf(ExerciseAdaptor.getWeightValue(information))+" total kg";
        }
    }


    @Id
    private String id;
    private String owner;
    private String type;
    private String information;
    private long date;
    private Date normalDate;
    private String normalInfo;

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


    public Date getNormalDate() {
        return normalDate;
    }

    public void setNormalDate(Date normalDate) {
        this.normalDate = normalDate;
    }

    public String getNormalInfo() {
        return normalInfo;
    }

    public void setNormalInfo(String normalInfo) {
        this.normalInfo = normalInfo;
    }
}
