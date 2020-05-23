package ie.tcd.pavel.security;

import ie.tcd.pavel.MongoDBOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {
    @Autowired
    private MongoDBOperations database;



    @Override
    public UserDetails loadUserByUsername(String login )throws UsernameNotFoundException {
        return database.getUserPasswordByLogin(login).orElseThrow(() ->
                new UsernameNotFoundException("user " + login + " was not found!"));
    }
}