package ie.tcd.pavel;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig {

    /*
     * Use the standard Mongo driver API to create a com.mongodb.client.MongoClient instance.
     */
    public @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://pavel:petrukhin@cluster0-fvceh.mongodb.net/test?retryWrites=true&w=majority");
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "application");
    }
}
