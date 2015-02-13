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
//	protected File designFile;
//	protected String fileName = "experiment.csv";
//	protected ArrayList<Trial> allTrials = new ArrayList<Trial>();

	public Trial() {
		// ...
		
	}
	

	public Trial(Experiment experiment2, int b, int t, String targetChange2, int objectsCount) {
		// TODO Auto-generated constructor stub
		experiment = experiment2;
		block = b;
		trial = t;
		targetChange = targetChange2;
		nonTargetsCount = objectsCount;
	}



	public void displayInstructions() {
		// ...

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