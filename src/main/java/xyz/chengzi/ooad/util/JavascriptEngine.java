package xyz.chengzi.ooad.util;

import xyz.chengzi.ooad.server.ApplicationServer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavascriptEngine {
    private final ApplicationServer server;
    private final ScriptEngineManager manager;
    private ScriptEngine engine;

    public JavascriptEngine(ApplicationServer server) {
        this.server = server;
        this.manager = new ScriptEngineManager();
        reset();
    }

    public Object eval(String script) {
        try {
            return engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void reset() {
        engine = manager.getEngineByName("javascript");
        engine.put("server", server);
    }
}
