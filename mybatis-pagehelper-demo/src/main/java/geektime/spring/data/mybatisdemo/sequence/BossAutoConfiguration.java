package geektime.spring.data.mybatisdemo.sequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SnakeConfig.class)
public class BossAutoConfiguration {
    @Autowired
    private SnakeConfig properties;

    @Bean
    @ConditionalOnMissingBean
    public SnowflakeManager snowflakeManager() {
        return new SnowflakeManager(this.properties.getSnowflake().getMachineId(), this.properties.getSnowflake().getDataCenterId());
    }
}