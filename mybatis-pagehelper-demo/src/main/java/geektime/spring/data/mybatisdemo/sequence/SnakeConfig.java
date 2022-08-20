package geektime.spring.data.mybatisdemo.sequence;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "com.linshan.demo1")
public class SnakeConfig {

    private SnowflakeProperties snowflake;

}