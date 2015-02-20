package designExperiment;

import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.ClickOnShape;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.Click;
import fr.lri.swingstates.sm.transitions.KeyPress;

import javax.swing.*;
import javax.swing.text.Position;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.*;
import java.util.Timer;

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
    protected double currentTime = 0;

    protected Canvas canvas;
//    protected Position

    protected CExtensionalTag instructions = new CExtensionalTag() { };

    public static Font INSTRUCTIONS_FONT = new Font("Helvetica", Font.PLAIN, 20);

	public Experiment(String participant, int block, final int trial, File designFile) {
		// ...
		this.participant = participant;
		this.block = block;
		this.trial = trial;
		this.designFile = designFile;

	
		loadTrials(participant, block, trial);

        JFrame frame = new JFrame();
        canvas = new Canvas(800, 600);
        frame.getContentPane().add(canvas);
        frame.pack();
        frame.setVisible(true);

        canvas.requestFocus();

        CStateMachine interaction = new CStateMachine() {
          State instructionsShown = new State() {
            Transition enterKey = new KeyPress(KeyEvent.VK_ENTER, ">> fullShapesShown") {
                public void action() {
                    System.out.println("TESTING NUMBER " + (currentTrial + 1) + " START");
//                    System.out.printf("YOU WILL SEE THE LIST OF OBJECTS, PLEASE DETECT DIFFERENT ONE AND USE MOUSE TO CLICK ON IT. \n USING A SEQUENCE OF PRESSING \n < ENTER KEY > TO PROCEED AFTER SEEING THIS INSTRUCTION EACH TIME \n < SPACE KEY > TO START THE TEST AFTER YOU\'RE SURE OF DETECTING DIFFERENT OBJECT <CLICK MOUSE ON A DIFFERENT OBJECT IN THE PLACEHOLDER LIST");

                    double x_middle = canvas.getPreferredSize().getWidth()/2;
                    double y_middle = canvas.getPreferredSize().getHeight()/2;

                    // hide instructions
                     allTrials.get(currentTrial).start(x_middle,y_middle);
                    
                    allTrials.get(currentTrial).hideInstructions();

                }
            };
          };

            State fullShapesShown = new State() {
                Transition spaceBar = new KeyPress(KeyEvent.VK_SPACE, ">> placeholdersShown") {
                    public void action() {
                        currentTime = (double) System.currentTimeMillis();
//                        new Date(System.currentTimeMillis()).getTime()
                        System.out.println("full shapes "+ System.currentTimeMillis());
                        // hide shapes, show placeholders


                        double x_middle = canvas.getPreferredSize().getWidth()/2;
                        double y_middle = canvas.getPreferredSize().getHeight()/2;
                        allTrials.get(currentTrial).stop(x_middle,y_middle);

                    }
                };
            };
            State placeholdersShown = new State() {
                boolean hit = false;
                Transition clickShape = new ClickOnShape(BUTTON1, ">> instructionsShown") {
                    public void action() {
                        if(getShape() == allTrials.get(currentTrial).target){
                            System.out.println("click");
                            hit = true;
                        }else{
                            System.out.println("noclick");
                            hit = false;
                        }

                        //String data = allTrials.get(currentTrial).block+";\t"
                        //+currentTrial+";\t"
                        //+allTrials.get(currentTrial).targetChange+";\t"
                        //+allTrials.get(currentTrial).objectsCount+";\t"
                        //+time_used[currentTrial]+";\t"
                        //+hit+";\t"
                        ////+allTrials.get(currentTrial).delta+";"
                        //+ "\n";
                        ////pwLog.print(data);
                        //pwLog.flush();
                        // check if the click was on the target or not
                        // log
                        currentTime = ((System.currentTimeMillis() - currentTime) / 1000.0);
                        System.out.println("click on shape " + currentTime + " seconds");

                        // test if the click happened on the target or not


                        // log success and time

                        // hide all shapes
                        canvas.removeAllShapes();

                        // currentTrial++ and then call nextTrial
                        currentTrial++;

                        nextTrial();

                    }
                };
            };

        };
        interaction.attachTo(canvas);

		initLog();
		nextTrial();
	}

    public CExtensionalTag getInstructions() {
        return instructions;
    }

	public void trialCompleted() {
		Trial trial = allTrials.get(currentTrial);
		//trial.stop();
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

    public Canvas getCanvas() {
        return canvas;
    }
	
	public void loadTrials(String participant, int block, int trial) {
		allTrials.clear(); //delete all previous trials stored in the array

        //reading csv trial file and store each trial data to allTrials as a Trial object
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

    public static void main(String[] args) {
        new Experiment("0", 1, 0, new File("experiment.csv"));
    }

}