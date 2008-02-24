import java.io.File;
import java.io.IOException;

import edu.cmu.minorthird.classify.DatasetLoader;
import edu.cmu.minorthird.classify.Example;
import edu.cmu.minorthird.classify.Splitter;
import edu.cmu.minorthird.classify.experiments.Evaluation;
import edu.cmu.minorthird.classify.experiments.RandomSplitter;
import edu.cmu.minorthird.classify.experiments.Tester;
import edu.cmu.minorthird.classify.relational.RealRelationalDataset;
import edu.cmu.minorthird.classify.relational.StackedBatchClassifierLearner;
import edu.cmu.minorthird.classify.relational.StackedGraphicalLearner;
import edu.cmu.minorthird.util.gui.ViewerFrame;

/**
 * Example of how to load relational data and run SGM learning
 *
 * There is a sample set of data under demo/SGMsample
 *
 *
 * @author Zhenzhen Kou
 */
public class NumericDemo_SGM{

	public static void main(String[] args){
		
		// usage check
		if(args.length<3){
			usage();
			return;
		}

		try{
			
			//aquire the files
			String datafl=args[0];
			String linkfl=args[1];
			String relTempfl=args[2];
			RealRelationalDataset data=new RealRelationalDataset();
			DatasetLoader.loadRelFile(new File(datafl),data);
			DatasetLoader.loadLinkFile(new File(linkfl),data);
			DatasetLoader.loadRelTempFile(new File(relTempfl),data);

			Splitter<Example> splitter=new RandomSplitter<Example>();

			//Construct a  learner
			StackedBatchClassifierLearner learner=new StackedGraphicalLearner();

			//Test and evaluate the learner:
			Evaluation eval=Tester.evaluate(learner,data,splitter,"stacked");

			//The constructor of ViewerFrame displays the frame
			//Classes which implement Visible have a toGUI() method which produces a Viewer component.
			//The ViewerFrame - obviously - displays the Viewer component
			new ViewerFrame("numeric demo",eval.toGUI());

		}catch(IOException e){
			e.printStackTrace(); //To change body of catch statement use Options | File Templates.
		}

	}

	private static void usage(){
		System.out.println("usage: NumericDemo_SGM [data file] [link file] [relational template]");
		System.out.println("both files must be in standard SVM format");
	}
}
