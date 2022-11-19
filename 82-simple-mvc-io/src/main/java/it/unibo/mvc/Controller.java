package it.unibo.mvc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Application controller. Performs the I/O.
 */
public class Controller {

    private File file = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "output.txt");

    public void setFile (final File file_setting) {
        this.file = file_setting;
    }

    public File getFile () {
        return this.file;
    }

    public String Path () {
        return this.file.getPath();
    }

    public void setString (String content) throws IOException {
        try (PrintStream ps = new PrintStream(this.Path(), StandardCharsets.UTF_8)) {
            ps.print(content);
        } catch (IOException e1) {
            throw new IOException("Error on write on file");
        }
    }   
}
