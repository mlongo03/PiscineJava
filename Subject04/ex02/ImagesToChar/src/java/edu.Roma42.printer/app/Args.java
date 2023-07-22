package edu.Roma42.printer.app;
import edu.Roma42.printer.logic.*;
import com.beust.jcommander.JCommander;
import	com.beust.jcommander.Parameter;
import	com.beust.jcommander.Parameters;
import	com.beust.jcommander.ParameterException;
import	com.beust.jcommander.IParameterValidator;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = {"--white"}, description = "white color")
    private String black = "WHITE";

    @Parameter(names = {"--black"}, description = "black color")
    private String white = "BLACK";

    public Args() {

    }

    public void run()
    {
        Img imgbmp = new Img();

		try {
			imgbmp.seeBMPImage("./target/resources/it.bmp", black, white);
		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
}
