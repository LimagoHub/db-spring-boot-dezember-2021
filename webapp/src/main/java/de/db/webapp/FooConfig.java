package de.db.webapp;


import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


import java.util.List;

@Profile("production")
@Configuration
@PropertySource("classpath:foo.properties")
@ConfigurationProperties(prefix = "foo")
@Setter
public class FooConfig {

    private String stadt;
    private String land;
    private String fluss;

    @Bean
    @Qualifier("foo")
    public List<String> foos() {
        return List.of(stadt,land,fluss);
    }
}
