package ie.tcd.pavel;

import ie.tcd.pavel.documents.Challenge;
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

    public void insertChallenge(String owner, String type, String information, long date) {
        mongoTemplate.insert(new Challenge(owner, type, information, date));
    }

    public List<Challenge> getChallengesByOwner(String owner)
    {
        Query searchChallenge = new Query(Criteria.where("owner").is(owner));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByType(String type)
    {
        Query searchChallenge = new Query(Criteria.where("type").is(type));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByTypeAndOwner(String owner,String type)
    {
        Query searchChallenge = new Query(Criteria.where("type").is(type).and("owner").is(owner));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public Challenge getChallengeByOwnerAndTypeAndDate(String owner, String type, Date date)
    {
        Query searchChallenge = new Query(Criteria.where("type").is(type).and("owner").is(owner).and("date").
                is(date.getTime()));
        Challenge challenge = mongoTemplate.findOne(searchChallenge, Challenge.class);

        return  challenge;
    }

    public List<Challenge> getChallengesByOwnerAndDateGt(String owner, Date date)
    {
        Query searchChallenge = new Query(Criteria.where("owner").is(owner).and("date").gt(date.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByOwnerAndDateLt(String owner, Date date)
    {
        Query searchChallenge = new Query(Criteria.where("owner").is(owner).and("date").lt(date.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByOwnerAndDateInterval(String owner, Date date1, Date date2)
    {
        Query searchChallenge = new Query(Criteria.where("owner").is(owner).and("date").gt(date1.getTime()).
                and("date").lt(date2.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByTypeAndDateGt(String type, Date date)
    {
        Query searchChallenge = new Query(Criteria.where("type").is(type).and("date").gt(date.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByTypeAndDateLt(String type, Date date)
    {
        Query searchChallenge = new Query(Criteria.where("type").is(type).and("date").lt(date.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByTypeAndDateInterval(String type, Date date1, Date date2)
    {
        Query searchChallenge = new Query(Criteria.where("type").is(type).and("date").gt(date1.getTime()).
                and("date").lt(date2.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByOwnerAndTypeAndDateGt(String owner, String type, Date date)
    {
        Query searchChallenge = new Query(Criteria.where("owner").is(owner).and("type").is(type).and("date").
                gt(date.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByOwnerAndTypeAndDateLt(String owner, String type, Date date)
    {
        Query searchChallenge = new Query(Criteria.where("owner").is(owner).and("type").is(type).and("date").
                lt(date.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public List<Challenge> getChallengesByOwnerAndTypeAndDateInterval(String owner, String type, Date date1, Date date2)
    {
        Query searchChallenge = new Query(Criteria.where("owner").is(owner).and("type").is(type).and("date").
                gt(date1.getTime()).and("date").lt(date2.getTime()));
        List<Challenge> resultChallenges = mongoTemplate.query(Challenge.class).matching(searchChallenge).all();
        return resultChallenges;
    }

    public void deleteChallenge(String owner, String type, Date date)
    {
        Challenge challenge = getChallengeByOwnerAndTypeAndDate(owner, type, date);
        mongoTemplate.remove(challenge);
    }












}
