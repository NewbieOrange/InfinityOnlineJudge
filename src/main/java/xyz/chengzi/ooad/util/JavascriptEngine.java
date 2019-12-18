package xyz.chengzi.ooad.util;

import xyz.chengzi.ooad.server.ApplicationServer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavascriptEngine {
    private ScriptEngine engine;

    public JavascriptEngine(ApplicationServer server) {
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("javascript");
        engine.put("server", server);
    }

    public void eval(String script) {
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }
}
