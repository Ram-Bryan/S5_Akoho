/*
 * Decompiled with CFR 0.152.
 */
package chatbot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AntRunner {
    private final String antExecutable;
    private static final Logger logger = Logger.getLogger(AntRunner.class.getName());

    public AntRunner(String antExecutable) {
        this.antExecutable = antExecutable;
    }

    public int runAnt(String projectDir, String target, String buildFile) {
        int exitCode = -1;
        try {
            ProcessBuilder pb = buildFile != null && !buildFile.isEmpty() ? (target != null && !target.isEmpty() ? new ProcessBuilder(this.antExecutable, "-f", buildFile, target) : new ProcessBuilder(this.antExecutable, "-f", buildFile)) : (target != null && !target.isEmpty() ? new ProcessBuilder(this.antExecutable, target) : new ProcessBuilder(this.antExecutable));
            pb.directory(new File(projectDir));
            pb.redirectErrorStream(true);
            logger.info("Running: " + String.join((CharSequence)" ", pb.command()) + " in " + projectDir);
            Process process = pb.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));){
                String line;
                while ((line = reader.readLine()) != null) {
                    logger.fine("[ANT] " + line);
                }
            }
            exitCode = process.waitFor();
            logger.info("Ant finished with exit code: " + exitCode);
        }
        catch (IOException e) {
            logger.log(Level.SEVERE, "Ant execution failed", e);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, "Ant execution interrupted", e);
        }
        return exitCode;
    }
}

