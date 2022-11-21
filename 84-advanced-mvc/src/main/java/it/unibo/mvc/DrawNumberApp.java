package it.unibo.mvc;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final String conf_name, final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }

        final String conf_file = ClassLoader.getSystemResource(conf_name).getPath();
        /*final String conf_file = "./resources/config.yml";*/
        final Configuration.Builder conf_builder = new Configuration.Builder();
        
        try {

            for (String lines : Files.readAllLines(Paths.get(conf_file))) {
                
                final String val = lines.split(": ")[1];
                final String name = lines.split(": ")[0];
                switch(name) {
                    case "maximum":
                        conf_builder.setMax(Integer.parseInt(val));
                        break;
                    case "minimum":
                        conf_builder.setMin(Integer.parseInt(val));
                        break;
                    case "attempts":
                        conf_builder.setAttempts(Integer.parseInt(val));
                        break;
                }
            }

            
            
        } catch(Exception e) {
            for(DrawNumberView view : views) {
                view.displayError(e.toString());
            }
        }
       
        this.model = new DrawNumberImpl(conf_builder.build());
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
         new DrawNumberApp("config.yml", // res is part of the classpath!
                new DrawNumberViewImpl(),
                new DrawNumberViewImpl(),
                new PrintStreamView(System.out),
                new PrintStreamView("output.log"));
    }

}
