package designExperiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Trial {
	protected int block;
	protected int trial;
	protected String targetChange;
	protected int nonTargetsCount;
	protected Experiment experiment;
	protected File designFile;
	protected String fileName = "experiment.csv";
	protected ArrayList<Trial> allTrials = new ArrayList<Trial>();

	public Trial() {
		// ...
		designFile = new File(fileName);
	}
	

	public Trial(Trial trial2, int b, int t, String targetChange2, int objectsCount) {
		// TODO Auto-generated constructor stub
		block = b;
		trial = t;
		targetChange = targetChange2;
		nonTargetsCount = objectsCount;
	}

	public void displayInstructions() {
		// ...
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



	public void hideInstructions() {
	//	experiment.getCanvas().removeShapes(experiment.getInstructions());
	}

	public void start() {
		// ...
		// install the graphical listener and the user input listener
		// call experiment.trialCompleted(); when appropriate
	}

	public void stop() {
		// ...
	}
}