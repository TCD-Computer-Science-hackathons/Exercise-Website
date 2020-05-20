package ie.tcd.pavel;

import ie.tcd.pavel.documents.Exercise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ie.tcd.pavel.documents.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MongoDBOperations {

    @Autowired
    MongoTemplate mongoTemplate;

    public void insertUser(String login, String password) {
        mongoTemplate.insert(new User(login, password));
    }

    public User searchUserByLogin(String login) {

        Query searchUser = new Query(Criteria.where("login").is(login));
        User user = mongoTemplate.findOne(searchUser, User.class);
        return user;

    }

    public boolean userExists(String login, String password)
    {
        Query searchUser = new Query(Criteria.where("login").is(login).and("password").is(password));
        List<User> resultUsers = mongoTemplate.query(User.class).matching(searchUser).all();
        return resultUsers.size() > 0;
    }

    public List<User> searchUserByName(String forename, String surname) {

        Query searchUser = new Query(Criteria.where("forename").is(forename).and("surname").is(surname));
        List<User> resultUsers = mongoTemplate.query(User.class).matching(searchUser).all();
        return resultUsers;
    }

    public void deleteUserByLogin(String login) {
        User user = searchUserByLogin(login);
        mongoTemplate.remove(user);
    }

    public void insertExercise(String owner, String type, String information, long date) {
        mongoTemplate.insert(new Exercise(owner, type, information, date));
    }

    public List<Exercise> getExercisesByOwner(String owner)
    {
        Query searchExercise = new Query(Criteria.where("owner").is(owner));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByType(String type)
    {
        Query searchExercise = new Query(Criteria.where("type").is(type));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByTypeAndOwner(String owner,String type)
    {
        Query searchExercise = new Query(Criteria.where("type").is(type).and("owner").is(owner));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public Exercise getExerciseByOwnerAndTypeAndDate(String owner, String type, Date date)
    {
        Query searchExercise = new Query(Criteria.where("type").is(type).and("owner").is(owner).and("date").
                is(date.getTime()));
        Exercise Exercise = mongoTemplate.findOne(searchExercise, Exercise.class);

        return  Exercise;
    }

    public List<Exercise> getExercisesByOwnerAndDateGt(String owner, Date date)
    {
        Query searchExercise = new Query(Criteria.where("owner").is(owner).and("date").gt(date.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByOwnerAndDateLt(String owner, Date date)
    {
        Query searchExercise = new Query(Criteria.where("owner").is(owner).and("date").lt(date.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByOwnerAndDateInterval(String owner, Date date1, Date date2)
    {
        Query searchExercise = new Query(Criteria.where("owner").is(owner).and("date").gt(date1.getTime()).
                and("date").lt(date2.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByTypeAndDateGt(String type, Date date)
    {
        Query searchExercise = new Query(Criteria.where("type").is(type).and("date").gt(date.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByTypeAndDateLt(String type, Date date)
    {
        Query searchExercise = new Query(Criteria.where("type").is(type).and("date").lt(date.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByTypeAndDateInterval(String type, Date date1, Date date2)
    {
        Query searchExercise = new Query(Criteria.where("type").is(type).and("date").gt(date1.getTime()).
                and("date").lt(date2.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByOwnerAndTypeAndDateGt(String owner, String type, Date date)
    {
        Query searchExercise = new Query(Criteria.where("owner").is(owner).and("type").is(type).and("date").
                gt(date.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByOwnerAndTypeAndDateLt(String owner, String type, Date date)
    {
        Query searchExercise = new Query(Criteria.where("owner").is(owner).and("type").is(type).and("date").
                lt(date.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public List<Exercise> getExercisesByOwnerAndTypeAndDateInterval(String owner, String type, Date date1, Date date2)
    {
        Query searchExercise = new Query(Criteria.where("owner").is(owner).and("type").is(type).and("date").
                gt(date1.getTime()).and("date").lt(date2.getTime()));
        List<Exercise> resultExercises = mongoTemplate.query(Exercise.class).matching(searchExercise).all();
        return resultExercises;
    }

    public void deleteExercise(String owner, String type, Date date)
    {
        Exercise Exercise = getExerciseByOwnerAndTypeAndDate(owner, type, date);
        mongoTemplate.remove(Exercise);
    }












}
