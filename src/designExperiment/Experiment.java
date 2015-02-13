package designExperiment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class Experiment {
	// output file: logs
	protected PrintWriter pwLog = null;
	protected ArrayList<Trial> allTrials = new ArrayList<Trial>();
	protected int currentTrial = 0;
	
	protected String participant;
	protected int block;
	protected int trial;
	// input file (design): "experiment.csv"
	protected File designFile = null;

	public Experiment(String participant, int block, int trial, File designFile) {
		// ...
		this.participant = participant;
		this.block = block;
		this.trial = trial;
		this.designFile = designFile;

	
		loadTrials(participant, block, trial);
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
        System.out.println("Thank you for your cooperation!");
        return;
	}

	public void nextTrial() {
		if (currentTrial >= allTrials.size()) {
			stop();
            return;
		}
		Trial trial = allTrials.get(currentTrial);
		trial.displayInstructions();
	}

	
	public void loadTrials(String participant, int block, int trial) {
		allTrials.clear();
		try {
			BufferedReader br = new BufferedReader(new FileReader(designFile));
			String line = br.readLine();
			line = br.readLine();
			while(line != null) {
				String[] parts = line.split(",");
				String p = parts[0];
				int b = Integer.parseInt(parts[2]);
				int t = Integer.parseInt(parts[3]);
				String targetChange = parts[4];
				int objectsCount = Integer.parseInt(parts[5]);
				if(p.compareTo(participant) == 0) {
					if(b > block || (b == block && t >= trial)) {
						allTrials.add(new Trial(this, b, t, targetChange, objectsCount));
					}
				}
				line = br.readLine();
				System.out.println(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}