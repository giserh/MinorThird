package edu.cmu.minorthird.ui;

import edu.cmu.minorthird.text.*;
import edu.cmu.minorthird.text.mixup.*;
import edu.cmu.minorthird.util.gui.*;
import edu.cmu.minorthird.util.*;

import org.apache.log4j.Logger;
import java.util.*;
import java.io.*;

/**
 * Run a mixup program.
 *
 * @author William Cohen
 */

public class RunMixup extends UIMain
{
    private static Logger log = Logger.getLogger(RunMixup.class);

    // private data needed 
    private CommandLineUtil.SaveParams save = new CommandLineUtil.SaveParams();
    private CommandLineUtil.MixupParams mixup = new CommandLineUtil.MixupParams();
    private CommandLineUtil.AnnotatorOutputParams output = new CommandLineUtil.AnnotatorOutputParams();
    public MultiLevelTextLabels annotatedLabels = null;

    // for gui
    public CommandLineUtil.MixupParams getMixupParameters() { return mixup; }
    public void setMixupParameters(CommandLineUtil.MixupParams p) { mixup=p; }
    public CommandLineUtil.SaveParams getSaveParameters() { return save; }
    public void setSaveParameters(CommandLineUtil.SaveParams p) { save=p; }

    public String getRunMixupHelp() {
	return "<A HREF=\"http://minorthird.sourceforge.net/tutorials/Mixup%20Tutorial.htm\">Mixup Tutorial</A></html>";
    }

    public CommandLineProcessor getCLP()
    {
	return new JointCommandLineProcessor(new CommandLineProcessor[]{new GUIParams(),base,save,mixup,output});
    }

	//
	// run the mixup program
	// 

	public void doMain()
	{
		if (mixup.fileName==null) throw new IllegalArgumentException("need to specify -mixup");

		// load the mixup program
		MixupProgram program=null;
		try {
			program=new MixupProgram(new File(mixup.fileName));
		} catch (Mixup.ParseException ex) {
			System.out.println("can't parse file "+mixup.fileName+": "+ex);
		} catch (IOException ex) {
			System.out.println("can't load file "+mixup.fileName+": "+ex);
		}

		// check that inputs are valid
		if (program==null) {
			log.error("Cannot runMixup unless a valid mixup program is specified.");
			return;
		}

		// echo the program
		System.out.println("Will execute this program:\n"+program);

		// run the mixup on a copy
		annotatedLabels = new MultiLevelTextLabels(new NestedTextLabels(base.labels));
		program.eval(annotatedLabels);

		if (base.showResult) new ViewerFrame("Result of "+mixup.fileName, new SmartVanillaViewer(annotatedLabels));

		if (save.saveAs!=null) {
		    try {
			if ("minorthird".equals(output.format)) {
			    new TextLabelsLoader().saveTypesAsOps(annotatedLabels, save.saveAs);
			} else if ("strings".equals(output.format)) {
			    new TextLabelsLoader().saveTypesAsStrings(annotatedLabels, save.saveAs, true );
			} else {
			    throw new IllegalArgumentException("illegal output format "+output.format+" allowed values are "
							       +StringUtil.toString(output.getAllowedOutputFormatValues()));
			}

			System.out.println("saved to "+save.saveAs);

		    } catch (IOException e) {
			throw new IllegalArgumentException("can't save to "+save.saveAs+": "+e);
		    }
		}
	}

	public Object getMainResult() { return annotatedLabels; }

	public static void main(String args[])
	{
		new RunMixup().callMain(args);
	}
}
