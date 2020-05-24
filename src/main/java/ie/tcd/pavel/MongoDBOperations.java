package ie.tcd.pavel;

import com.google.common.collect.ImmutableList;
import ie.tcd.pavel.documents.*;
import ie.tcd.pavel.security.Role;
import ie.tcd.pavel.utility.ExerciseAdaptor;
import ie.tcd.pavel.utility.ExerciseTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MongoDBOperations {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    ExerciseTypes exerciseTypes;
    @Autowired
    BCryptPasswordEncoder encoder;

    public void insertUser(String login, String password) {
        mongoTemplate.insert(new User(login));
        mongoTemplate.insert(new UserPassword(login, ImmutableList.of(Role.USER),
                encoder.encode(password),true,true,true,
                true));
    }

    public User getUserByLogin(String login) {

        Query searchUser = new Query(Criteria.where("login").is(login));
        User user = mongoTemplate.findOne(searchUser, User.class);
        return user;

    }

    public boolean userNameExists(String login) {
        Query searchUser = new Query(Criteria.where("login").is(login));
        List<User> resultUsers = mongoTemplate.query(User.class).matching(searchUser).all();
        return resultUsers.size()>0;
    }

    public boolean userExists(String login, String password)
    {
        Query searchUserPassword = new Query(Criteria.where("login").is(login));
        List<UserPassword> resultUsers = mongoTemplate.query(UserPassword.class).matching(searchUserPassword).all();
        return encoder.matches(password,resultUsers.get(0).getPassword());
    }

    public Optional<UserPassword> getUserPasswordByLogin(String login)
    {
        return Optional.ofNullable(mongoTemplate.findOne(new Query( Criteria.where("login").is(login)),
                UserPassword.class));
    }

    public List<User> getUserByName(String forename, String surname) {

        Query searchUser = new Query(Criteria.where("forename").is(forename).and("surname").is(surname));
        List<User> resultUsers = mongoTemplate.query(User.class).matching(searchUser).all();
        return resultUsers;
    }

    public void deleteUserByLogin(String login) {
        User user = getUserByLogin(login);
        Query searchUserPassword = new Query(Criteria.where("login").is(login));
        UserPassword userPassword = mongoTemplate.findOne(searchUserPassword,UserPassword.class);
        mongoTemplate.remove(user);
        mongoTemplate.remove(userPassword);
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

    public void insertGroup(String name, String password, String user)
    {
        mongoTemplate.insert(new Group(name,user));
        if(getUsersByGroup(name).size() < 2) {
            mongoTemplate.insert(new GroupPassword(name,encoder.encode(password)));
        }
    }


    public Group findGroup(String user, String group)
    {
        Query searchGroup = new Query(Criteria.where("user").is(user).and("name").is(group));
        Group groupResult = mongoTemplate.findOne(searchGroup,Group.class);
        return groupResult;

    }


    public boolean groupExists(String name, String password)
    {
        Query searchGroupPassword = new Query(Criteria.where("name").is(name));
        List<GroupPassword> resultGroups = mongoTemplate.query(GroupPassword.class).matching(searchGroupPassword).all();
        if(resultGroups.size()>0) {
            return encoder.matches(password, resultGroups.get(0).getPassword());
        }
        else
        {
            return false;
        }
    }

    public boolean groupExists(String name)
    {
        Query groupExists = new Query(Criteria.where("name").is(name));
        List<Group> groups = mongoTemplate.query(Group.class).matching(groupExists).all();
        return groups.size()>0;
    }


    public List<Group> getGroupsByUser(String user)
    {
        Query searchGroup = new Query(Criteria.where("user").is(user));
        List<Group> resultGroups = mongoTemplate.query(Group.class).matching(searchGroup).all();
        return resultGroups;
    }

    public List<User> getUsersByGroup(String group)
    {
        Query searchGroup = new Query(Criteria.where("name").is(group));
        List<Group> resultGroup = mongoTemplate.query(Group.class).matching(searchGroup).all();
        List<User> users = new ArrayList<>();
        for (Group gr: resultGroup) {
            users.add(getUserByLogin(gr.getUser()));
        }
        return users;
    }

    public HashMap<User,List<Exercise>> inGroupGetExercisesByUsersAndType(String group,String type)
    {
        List<User> users = getUsersByGroup(group);
        HashMap<User, List<Exercise>> allUsersExercises = new HashMap<User, List<Exercise>>();
        for(User user:users){
            List<Exercise> exercises = getExercisesByTypeAndOwner(user.getLogin(),type);
            allUsersExercises.put(user,exercises);
        }
        return allUsersExercises;
    }


    //returns total time in minutes or total distance in km or total reps or total weight in kg
    public HashMap<String,Double> inGroupGetCumulativeValuesByUserAndType(String group, String type)
    {
        HashMap<User, List<Exercise>> allUsersExercises = inGroupGetExercisesByUsersAndType(group,type);
        HashMap<String,Double> cumulativeData = new HashMap<String,Double>();
        if(exerciseTypes.getDistanceExercises().contains(type))
        {
            for(User user:allUsersExercises.keySet())
            {
                double currentValue = 0;
                List<Exercise> exercises = allUsersExercises.get(user);
                for(Exercise exercise: exercises)
                {
                    currentValue+= ExerciseAdaptor.getDistanceValue(exercise.getInformation());
                }
                cumulativeData.put(user.getLogin(),currentValue);
            }
        }
        else if(exerciseTypes.getRepExercises().contains(type))
        {
            for(User user:allUsersExercises.keySet())
            {
                double currentValue = 0;
                List<Exercise> exercises = allUsersExercises.get(user);
                for(Exercise exercise: exercises)
                {
                    currentValue+= ExerciseAdaptor.getRepsValue(exercise.getInformation());
                }
                cumulativeData.put(user.getLogin(),currentValue);
            }
        }
        else if(exerciseTypes.getWeightExercises().contains(type))
        {
            for(User user:allUsersExercises.keySet())
            {
                double currentValue = 0;
                List<Exercise> exercises = allUsersExercises.get(user);
                for(Exercise exercise: exercises)
                {
                    currentValue+= ExerciseAdaptor.getWeightValue(exercise.getInformation());
                }
                cumulativeData.put(user.getLogin(),currentValue);
            }
        }
        else if(exerciseTypes.getTimeExercises().contains(type))
        {
            for(User user:allUsersExercises.keySet())
            {
                double currentValue = 0;
                List<Exercise> exercises = allUsersExercises.get(user);
                for(Exercise exercise: exercises)
                {
                    currentValue+= ExerciseAdaptor.getTimeValue(exercise.getInformation());
                }
                cumulativeData.put(user.getLogin(),currentValue);
            }
        }

        return  cumulativeData;
    }

    public void makeAdmin(String user, String group)
    {
        mongoTemplate.insert(new GroupAdmin(user,group));
    }

    public boolean checkIfAdmin(String user, String group)
    {
        Query checkAdmin = new Query(Criteria.where("admin").is(user).and("groupName").is(group));
        List<GroupAdmin> admins = mongoTemplate.query(GroupAdmin.class).matching(checkAdmin).all();
        return admins.size()>0;
    }

    public void removeUserFromGroup(String user, String group)
    {
        Group groupToRemove = this.findGroup(user,group);
        mongoTemplate.remove(groupToRemove);
    }


    public void deleteGroupFinal(String name)
    {
        Query findAdmins = new Query(Criteria.where("groupName").is(name));
        List<GroupAdmin> admins = mongoTemplate.query(GroupAdmin.class).matching(findAdmins).all();
        for(GroupAdmin admin : admins) {
            mongoTemplate.remove(admin);
        }
        Query searchGroupPassword = new Query(Criteria.where("name").is(name));
        GroupPassword password = mongoTemplate.findOne(searchGroupPassword,GroupPassword.class);
        mongoTemplate.remove(password);
    }
}
