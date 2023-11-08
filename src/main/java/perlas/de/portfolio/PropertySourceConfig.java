package perlas.de.portfolio;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/*
 * Class that allows to load config.properties to my application.properties
 * so no sensitive information will be tracked and submited to git as I will 
 * add config.properties to .gitignore and store there the user and password for 
 * the db connection
 */


@Configuration
@PropertySource("classpath:config.properties")
public class PropertySourceConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
