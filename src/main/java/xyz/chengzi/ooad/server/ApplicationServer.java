package xyz.chengzi.ooad.server;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.chengzi.ooad.service.RepositoryService;
import xyz.chengzi.ooad.service.RestService;
import xyz.chengzi.ooad.service.SessionService;
import xyz.chengzi.ooad.service.RedisSessionService;
import xyz.chengzi.ooad.util.JavascriptEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ApplicationServer {
    private final RepositoryService repositoryService;
    private final SessionService sessionService;
    private final RestService restService;
    private final JedisPool jedisPool;
    private final JavascriptEngine javascriptEngine;

    public ApplicationServer(int port) {
        jedisPool = new JedisPool(new JedisPoolConfig(), "10.20.16.7", 6379, 1000);
        repositoryService = new RepositoryService("hibernate.jpa");
        sessionService = new RedisSessionService(this, jedisPool);
        restService = new RestService(this, port);
        javascriptEngine = new JavascriptEngine(this);

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void loadModules() {
        try {
            Files.list(Paths.get("./modules")).filter(Files::isRegularFile).forEach((p) -> {
                try {
                    String script = Files.readString(p);
                    javascriptEngine.eval(script);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        loadModules();
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

    public RestService getRestService() {
        return restService;
    }

    public static void main(String[] args) {
        new ApplicationServer(8080).start();
    }
}
