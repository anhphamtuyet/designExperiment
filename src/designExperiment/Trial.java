package designExperiment;

import fr.lri.swingstates.canvas.CText;

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


	public Trial(Experiment experiment2, int b, int t, String targetChange2, int objectsCount) {
		// TODO Auto-generated constructor stub
		experiment = experiment2;
		block = b;
		trial = t;
		targetChange = targetChange2;
		nonTargetsCount = objectsCount;
	}



	public void displayInstructions() {
		CText text1 = experiment.getCanvas().newText(100, 100, "instructions", Experiment.INSTRUCTIONS_FONT);
        text1.addTag(experiment.getInstructions());

	}



	public void hideInstructions() {
		experiment.getCanvas().removeShapes(experiment.getInstructions());
	}

	public void start() {

        // display fullshapes

		// call experiment.trialCompleted(); when appropriate
        experiment.trialCompleted();
	}

	public void stop() {
		// ...

	}
}