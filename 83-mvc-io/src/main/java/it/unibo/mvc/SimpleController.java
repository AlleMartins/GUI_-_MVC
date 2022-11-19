package it.unibo.mvc;

import java.util.List;
import java.util.LinkedList;
import java.util.Objects;
/**
 *
 */
public final class SimpleController implements Controller {

    private final List<String> hystory = new LinkedList<>();
    private String nextString;

    @Override
    public void setNextString(final String nextString) {
        this.nextString = Objects.requireNonNull(nextString);
    }

    @Override
    public String getNextString() {
        return this.nextString;
    }

    @Override
    public List<String> hystoryPrintedString() {
        return hystory;
    }

    @Override
    public void currentString() {
        if (this.nextString.isEmpty()) {
            throw new IllegalStateException("Non è stata inserita nessuna stringa");
        }
        hystory.add(this.nextString);
        System.out.println(this.nextString); // NOPMD: allowed in exercises
    }
}
