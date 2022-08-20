package geektime.spring.data.mybatisdemo.bitmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

public class util {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 设置标记位
     *
     * @param key
     * @param offset
     * @param tag
     * @return
     */
    public Boolean mark(String key, long offset, boolean tag) {
        return redisTemplate.opsForValue().setBit(key, offset, tag);
    }

    public Boolean mark2(String key, long offset, boolean tag) {
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.setBit(key.getBytes(), offset, tag);
            }
        });
    }

    /**
     * 判断是否标记过
     *
     * @param key
     * @param offest
     * @return
     */
    public Boolean container(String key, long offest) {
        return redisTemplate.opsForValue().getBit(key, offest);
    }

    /**
     * 统计计数
     *
     * @param key
     * @return
     */
    public long bitCount(String key) {
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.bitCount(key.getBytes());
            }
        });
    }

}
