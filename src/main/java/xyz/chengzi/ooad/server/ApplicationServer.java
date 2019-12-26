package xyz.chengzi.ooad.server;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import xyz.chengzi.ooad.service.*;
import xyz.chengzi.ooad.util.JavascriptEngine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ApplicationServer {
    private final RepositoryService repositoryService;
    private final SessionService sessionService;
    private final PropertiesService propertiesService;
    private final RestService restService;
    private final RabbitMQService messageQueueService;

    private final JedisPool jedisPool;
    private final TerminalThread terminalThread;
    private final JavascriptEngine javascriptEngine;
    private final List<Object> loadedModules;

    public ApplicationServer(int port) {
        jedisPool = new JedisPool(new JedisPoolConfig(), "10.20.16.7", 6379, 1000);

        repositoryService = new RepositoryService("hibernate.jpa");
        sessionService = new RedisSessionService(jedisPool);
        propertiesService = new RedisPropertiesService(jedisPool);
        messageQueueService = new RabbitMQService();
        restService = new RestService(this, port);

        terminalThread = new TerminalThread(this);
        terminalThread.setDaemon(true);
        javascriptEngine = new JavascriptEngine(this);
        loadedModules = new ArrayList<>();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop));
    }

    public void loadModules() {
        try {
            Files.list(Paths.get("./modules")).filter(Files::isRegularFile).forEach((p) -> {
                try {
                    String script = Files.readString(p);
                    Object plugin = javascriptEngine.eval(script);
                    loadedModules.add(plugin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void unloadModules() {
        javascriptEngine.reset();
        loadedModules.clear();
    }

    public void start() {
        loadModules();
        restService.start();
        terminalThread.start();
    }

    public void stop() {
        repositoryService.close();
        sessionService.close();
        restService.close();
        messageQueueService.close();
        jedisPool.close();
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    public SessionService getSessionService() {
        return sessionService;
    }

    public PropertiesService getPropertiesService() {
        return propertiesService;
    }

    public RabbitMQService getMessageQueueService() {
        return messageQueueService;
    }

    public RestService getRestService() {
        return restService;
    }

    public static void main(String[] args) {
        new ApplicationServer(8080).start();
    }
}
