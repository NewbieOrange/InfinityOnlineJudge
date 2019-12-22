package xyz.chengzi.ooad.service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class RedisPropertiesService implements PropertiesService {
    private final JedisPool jedisPool;

    public RedisPropertiesService(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Nullable
    @Override
    public String getProperty(@Nonnull String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public void setProperty(@Nonnull String key, @Nonnull Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, String.valueOf(value));
        }
    }

    @Override
    public void removeProperty(@Nonnull String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    @Override
    public void loadProperties() {
    }

    @Override
    public void saveProperties() {
    }
}
