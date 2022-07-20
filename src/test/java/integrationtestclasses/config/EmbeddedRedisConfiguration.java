package integrationtestclasses.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Profile("test")
@TestConfiguration
public class EmbeddedRedisConfiguration {

    public static int REDIS_PORT = 6370;

    private RedisServer redisServer;

    public EmbeddedRedisConfiguration() {
        this.redisServer = new RedisServer(REDIS_PORT);
    }

    @PostConstruct
    public void postConstruct() {
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
        redisServer.stop();
    }
}