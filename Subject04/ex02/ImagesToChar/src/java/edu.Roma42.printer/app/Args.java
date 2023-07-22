package edu.Roma42.printer.app;
import edu.Roma42.printer.logic.*;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Args {
    @Parameter(names = {"--white"}, description = "white color")
    private String black = "WHITE";

    @Parameter(names = {"--black"}, description = "black color")
    private String white = "BLACK";

    public Args() {

    }

    public void run()
    {
        System.out.println(black);
        System.out.println(white);
    }
}
