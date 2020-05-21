package ie.tcd.pavel.utility;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
public class ExerciseTypes
{
    private final String[] exercises = new String[] {"Running", "Swimming", "Plank", "Bench Press", "Push Ups", "Lunges",
            "Weighted Squats", "Squats", "Overhead Dumbbell Press", "Dumbbell Rows", "Deadlift", "Burpees", "Sit Ups",
                "Skipping", "Cycling", "Pull Ups"};

    private final String[] distanceExercises = new String[]{"Running", "Swimming","Cycling"};
    private final String[] repExercises = new String[]{"Push Ups","Lunges","Squats","Burpees","Sit Ups","Skipping",
            "Pull Ups"};
    private final String[] weightExercises = new String[]{"Bench Press","Weighted Squats","Overhead Dumbbell Press",
    "Dumbbell Rows","Deadlift"};

    private final String[] timeExercises = new String[]{"Plank"};


    public String[] getExerciseTypes()
    {
        return  exercises;
    }

    public ArrayList<String> getDistanceExercises()
    {
        return new ArrayList<String>(Arrays.asList(distanceExercises));
    }

    public ArrayList<String> getRepExercises()
    {
        return new ArrayList<String>(Arrays.asList(repExercises));
    }

    public ArrayList<String> getWeightExercises()
    {
        return new ArrayList<String>(Arrays.asList(weightExercises));
    }

    public ArrayList<String> getTimeExercises()
    {
        return new ArrayList<String>(Arrays.asList(timeExercises));
    }





}
