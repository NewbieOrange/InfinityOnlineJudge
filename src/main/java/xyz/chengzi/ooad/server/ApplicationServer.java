package xyz.chengzi.ooad.server;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.chengzi.ooad.service.RepositoryService;
import xyz.chengzi.ooad.service.RestService;
import xyz.chengzi.ooad.service.SessionService;
import xyz.chengzi.ooad.service.SessionServiceRedisImpl;

public class ApplicationServer {
    private final RepositoryService repositoryService;
    private final SessionService sessionService;
    private final RestService restService;
    private final JedisPool jedisPool;

    public ApplicationServer(int port) {
        jedisPool = new JedisPool(new JedisPoolConfig(), "10.20.16.7", 6379, 1000);
        repositoryService = new RepositoryService("hibernate.jpa");
        sessionService = new SessionServiceRedisImpl(this, jedisPool);
        restService = new RestService(this, port);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void start() {
        restService.start();
    }

    public void stop() {
        repositoryService.close();
        sessionService.close();
        restService.close();
        jedisPool.close();
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public static void main(String[] args) {
        new ApplicationServer(8080).start();
    }
}
