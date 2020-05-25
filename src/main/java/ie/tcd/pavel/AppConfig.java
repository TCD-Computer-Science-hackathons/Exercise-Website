package ie.tcd.pavel;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import ie.tcd.pavel.utility.ExerciseTypes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class AppConfig {

    public @Bean
    MongoMappingContext mongoMappingContext()
    {
        MongoMappingContext mongoMC = new MongoMappingContext();
        mongoMC.setAutoIndexCreation(true);
        return mongoMC;
    }
    public @Bean
    MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://pavel:petrukhin@cluster0-fvceh.mongodb.net/test?retryWrites=true&w=majority");
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "application");
    }
}
