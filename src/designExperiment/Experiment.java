package designExperiment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Experiment {
	// input file (design): "experiment.csv"
	protected File designFile = null;
	// output file: logs
	protected PrintWriter pwLog = null;
	protected ArrayList<Trial> allTrials = new ArrayList<Trial>();
	protected int currentTrial = 0;

	public Experiment(String participant, int block, int trial, File designFile) {
		// ...
		loadTrials();
		initLog();
		nextTrial();
	}

	public void trialCompleted() {
		Trial trial = allTrials.get(currentTrial);
		trial.stop();
		log(trial);
		currentTrial++;
		nextTrial();
	}

	public void log(Trial trial) {
		// ...
	}

	public void initLog() {
		String logFileName = "log_S" + participant + "_"
				+ (new Date()).toString() + ".csv";
		File logFile = new File(logFileName);
		try {
			pwLog = new PrintWriter(logFile);
			String header = "Block\t" + "Trial\t" + "TargetChange\t"
					+ "NonTargetsCount\t" + "Duration\t" + "Hit\n";

			pwLog.print(header);
			pwLog.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		// display a "thank you" message
	}

	public void nextTrial() {
		if (currentTrial >= allTrials.size()) {
			stop();
		}
		Trial trial = allTrials.get(currentTrial);
		trial.displayInstructions();
	}

	public void loadTrials() {
		allTrials.clear();
		// read the design file and keep only the trials to run
		try {
			BufferedReader br = new BufferedReader(new FileReader(designFile));
			String line = br.readLine();
			// ...
			while (line != null) {
				String[] parts = line.split(",");
				// ...
				// allTrials.add(new Trial(...));
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}