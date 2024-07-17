package TTCS.Security.configuration;

import com.thoughtworks.xstream.XStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class AxonConfig {
 
    @Bean
    public XStream xStream() {
        XStream xStream = new XStream();
      
        xStream.allowTypesByWildcard(new String[] {
                "TTCS.ProfileService.**",
                "TTCS.Security.**",
                "KMA.TTCS.**",

        });
        return xStream;
    }
}
