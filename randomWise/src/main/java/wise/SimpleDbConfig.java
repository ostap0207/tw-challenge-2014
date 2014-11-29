package wise;

import java.net.URI;
import java.net.URISyntaxException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SimpleDbConfig {

    @Bean
    public DataSource dataSource()  {    

        URI dbUri;
        try {
            String username = "postgres";
            String password = "12345678";
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String dbProperty = System.getenv("DATABASE_URL");
            if(dbProperty != null) {
                dbUri = new URI(dbProperty);
                String[] credentials = dbUri.getUserInfo().split(":");
                username = credentials[0];
                if (credentials.length == 2)
                	password = dbUri.getUserInfo().split(":")[1];
                else 
                	password = "";
                url = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
            }     

            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);

            return basicDataSource;

        } catch (URISyntaxException e) {
            return null;
        }
    }
}