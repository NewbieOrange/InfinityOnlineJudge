package xyz.chengzi.ooad.server;

import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.UserInterruptException;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class TerminalThread extends Thread {
    private ApplicationServer server;

    public TerminalThread(ApplicationServer server) {
        this.server = server;
    }

    @Override
    public void run() {
        try {
            Terminal terminal = TerminalBuilder.terminal();
            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
            String prompt = "> ";
            while (true) {
                try {
                    String line = lineReader.readLine(prompt);
                    if (line.isBlank()) {
                        continue;
                    }
                    switch (line.toLowerCase()) {
                        case "reload":
                            server.unloadModules();
                            server.loadModules();
                            break;
                        case "stop":
                            server.stop();
                            return;
                        default:
                            Logger.getGlobal().info("Unknown command");
                            break;
                    }
                } catch (UserInterruptException e) {
                    // Ignore
                } catch (EndOfFileException e) {
                    server.stop();
                    return;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
