package geektime.spring.data.mybatisdemo.sequence;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnowflakeProperties {
    private long machineId;
    private long dataCenterId;
}