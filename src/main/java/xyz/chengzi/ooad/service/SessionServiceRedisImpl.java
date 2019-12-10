package xyz.chengzi.ooad.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.google.common.primitives.Ints;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import xyz.chengzi.ooad.entity.User;
import xyz.chengzi.ooad.repository.JpaRepository;
import xyz.chengzi.ooad.repository.user.UserRepository;
import xyz.chengzi.ooad.server.ApplicationServer;

import java.security.SecureRandom;
import java.util.UUID;

public class SessionServiceRedisImpl implements SessionService {
    private static final int TIME_OUT_SECS = 60 * 60 * 12; // Token expires in 12 hours after generation.
    private final SecureRandom secureRandom = new SecureRandom();
    private final ApplicationServer server;
    private final JedisPool jedisPool;

    public SessionServiceRedisImpl(ApplicationServer server, JedisPool jedisPool) {
        this.server = server;
        this.jedisPool = jedisPool;
    }

    @NotNull
    @Override
    public String hashPassword(@NotNull String plainPassword) {
        return BCrypt.with(secureRandom).hashToString(10, plainPassword.toCharArray());
    }

    @Override
    public boolean checkPassword(@NotNull User user, @NotNull String plainPassword) {
        return BCrypt.verifyer().verify(plainPassword.toCharArray(), user.getPassword()).verified;
    }

    @Nullable
    @Override
    public User findTokenOwner(@NotNull byte[] token) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] id = jedis.get(token);
            if (id != null) {
                try (UserRepository userRepository = server.getRepositoryService().createUserRepository()) {
                    return userRepository.findById(Ints.fromByteArray(id));
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public byte[] getTokenIfPresent(@NotNull User user) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(Ints.toByteArray(user.getId()));
        }
    }

    @NotNull
    @Override
    public byte[] generateToken(@NotNull User user) {
        byte[] token = UUID.randomUUID().toString().getBytes();
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(Ints.toByteArray(user.getId()), TIME_OUT_SECS, token);
            jedis.setex(token, TIME_OUT_SECS, Ints.toByteArray(user.getId()));
            return token;
        }
    }

    @Override
    public void invalidateToken(@Nullable User user) {
        if (user != null) {
            try (Jedis jedis = jedisPool.getResource()) {
                byte[] token = getTokenIfPresent(user);
                if (token != null) {
                    jedis.del(token);
                }
                jedis.del(Ints.toByteArray(user.getId()));
            }
        }
    }

    @Override
    public void close() {
        jedisPool.close();
    }
}
