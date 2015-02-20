package designExperiment;

import fr.lri.swingstates.canvas.*;
import fr.lri.swingstates.canvas.Canvas;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Trial {
	protected int block;
	protected int trial;
	protected String targetChange;
	protected int nonTargetsCount;
	protected  Experiment experiment;
    protected CShape target = null;

    private int row = -1, column = -1;
	
	
	//static Canvas canvas;
    static CExtensionalTag experimentShapes = new CExtensionalTag() {
    };
    static CExtensionalTag placeholderShapes = new CExtensionalTag() {
    };


	public Trial(Experiment experiment2, int b, int t, String targetChange2, int objectsCount) {
		// TODO Auto-generated constructor stub
		experiment = experiment2;
		block = b;
		trial = t;
		targetChange = targetChange2;
		nonTargetsCount = objectsCount;

	}



	public void displayInstructions() {
		CText text1 = experiment.getCanvas().newText(100, 100, "STEP 1: YOU WILL SEE THE LIST OF OBJECTS, PLEASE DETECT DIFFERENT ONE AND USE MOUSE TO CLICK ON IT."
                , Experiment.INSTRUCTIONS_FONT);
        text1.addTag(experiment.getInstructions());
        CText text2 = experiment.getCanvas().newText(100, 200, "STEP 2: USING A SEQUENCE OF PRESSING < ENTER KEY > TO PROCEED AFTER SEEING THIS INSTRUCTION EACH TIME <CLICK MOUSE ON A DIFFERENT OBJECT IN THE PLACEHOLDER LIST."
                , Experiment.INSTRUCTIONS_FONT);
        text2.addTag(experiment.getInstructions());

        CText text3 = experiment.getCanvas().newText(100, 300, "STEP 3: < SPACE KEY > TO START THE TEST AFTER YOU'RE SURE OF DETECTING DIFFERENT OBJECT."
                , Experiment.INSTRUCTIONS_FONT);
        text3.addTag(experiment.getInstructions());

        CText text4 = experiment.getCanvas().newText(100, 400, "STEP 4: <CLICK MOUSE> ON A DIFFERENT OBJECT IN THE PLACEHOLDER LIST."
                , Experiment.INSTRUCTIONS_FONT);
        text4.addTag(experiment.getInstructions());

	}



	public void hideInstructions() {
		experiment.getCanvas().removeShapes(experiment.getInstructions());
	}

	public void start(double x_middle, double y_middle) {

        int isW2;
        // display fullshapes
        if(targetChange.equals("W1&W2")) isW2 = 0;
        else if(targetChange.equals("W1")) isW2 = -1;
        else isW2 = 1;
		fillGrid(nonTargetsCount, 30, isW2);
		double shapesCenterX = experimentShapes.getCenterX();
        double shapesCenterY = experimentShapes.getCenterY();
        

        experimentShapes.translateBy(x_middle-shapesCenterX, y_middle-shapesCenterY);
        
        
       
        
       

		// call experiment.trialCompleted(); when appropriate
        //experiment.trialCompleted();
	}

	public void stop(double x_middle, double y_middle) {
		// ...
        experimentShapes.setDrawable(false);



		placeholderGrid(nonTargetsCount, 30);
		 double placeholderCenterX = placeholderShapes.getCenterX();
	        double placeholderCenterY = placeholderShapes.getCenterY();

	        placeholderShapes.translateBy(x_middle-placeholderCenterX, y_middle-placeholderCenterY);

	}
	
	
	public CPolyLine createTriangle(Point loc, int size, boolean sym, Canvas canvas){
        double[] xCoords = new double[3];
        double[] yCoords = new double[3];

        if (!sym) {
            xCoords[0] = loc.x;
            xCoords[1] = loc.x+size-7;
            xCoords[2] = loc.x+size-7;
            yCoords[0] = loc.y+(size/2);
            yCoords[1] = loc.y;
            yCoords[2] = loc.y+size;

        }
        else {
            xCoords[0] = loc.x;
            xCoords[1] = loc.x+size-7;
            xCoords[2] = loc.x;
            yCoords[0] = loc.y;
            yCoords[1] = loc.y+(size/2);
            yCoords[2] = loc.y+size;
        }
        final CPolyLine triangle = canvas.newPolyLine(xCoords[0], yCoords[0]);
        triangle.lineTo(xCoords[1], yCoords[1]).lineTo(xCoords[2], yCoords[2]).close();
        triangle.addTag(experimentShapes);
        return triangle;
    }


    public void fillGrid(int n, int size, int isW2){
        System.out.println("Number of targets displayed: " + n);

        int size1 = (int) (size * 1.3);


        Random rand = new Random();
        int r = rand.nextInt(n);
        int cont = 0;
		Point loc = new Point (0,0);
		for (int i =0; i<Math.sqrt(n); i++) {
			for (int j =0; j<Math.sqrt(n); j++) {
                //in case both W1 and W2: random size distribution
                if(isW2 == 0) {
                    if(Math.random() < 0.5) {
                        CPolyLine triangle1 = createTriangle(loc,size1,false,experiment.getCanvas());
                        loc.x  += size1;


                        if(cont == r) {
                            CPolyLine triangle2 = createTriangle(loc,size1,true,experiment.getCanvas());
                            row = i;
                            column = j;
                        }
                        else {
                            CPolyLine triangle2 = createTriangle(loc,size1,false,experiment.getCanvas());
                        }
                        cont++;
                        loc.x+=70;
                    }
                    else {
                        if(cont == r) {
                            CPolyLine triangle1 = createTriangle(loc,size1,false,experiment.getCanvas());
                            row = i;
                            column = j;
                            loc.x  += size1;

                            //the right target
                            {
                                CPolyLine triangle2 = createTriangle(loc,size1,true,experiment.getCanvas());
                            }
                        }

                        else {
                            CPolyLine triangle1 = createTriangle(loc,size,false,experiment.getCanvas());
                            loc.x  += size1;

                            //the right target
                            {
                                CPolyLine triangle2 = createTriangle(loc,size,false,experiment.getCanvas());
                            }

                        }
                        cont++;
                        loc.x+=70;
                    }
                }

                //in case only W2: random size & no symmetric difference
                else if(isW2 == 1) {


                    //the right target
                    if(cont == r) {
                        CPolyLine triangle1 = createTriangle(loc,size1,false,experiment.getCanvas());
                        loc.x  += size1;
                        CPolyLine triangle2 = createTriangle(loc,size1,true,experiment.getCanvas());
                        row = i;
                        column = j;
                    }
                    else {
                        CPolyLine triangle1 = createTriangle(loc,size,false,experiment.getCanvas());
                        loc.x  += size1;
                        CPolyLine triangle2 = createTriangle(loc,size,false,experiment.getCanvas());
                    }
                    cont++;
                    loc.x+=70;
                }


                //default: in case only W1
                else {

                    CPolyLine triangle1 = createTriangle(loc,size,false,experiment.getCanvas());
                    loc.x  += size;

                    //the right target
                    if(cont == r) {
                        CPolyLine triangle2 = createTriangle(loc,size,true,experiment.getCanvas());
                        row = i;
                        column = j;
                    }
                    else {
                        CPolyLine triangle2 = createTriangle(loc,size,false,experiment.getCanvas());
                    }
                    cont++;
                    loc.x+=70;
                }

			}
			loc.y+=70;
			loc.x=0;
		}
    }
    
    public void placeholderGrid(int n, int size){
        CEllipse circle = null;
		Point loc = new Point (0,0);
		for (int i =0; i<Math.sqrt(n); i++) {
			for (int j =0; j<Math.sqrt(n); j++) {
				circle = experiment.getCanvas().newEllipse(loc.x,loc.y,size,size);
		        circle.addTag(placeholderShapes);
                circle.setFillPaint(Color.RED);


                loc.x+=100;

                if(row == i && column == j) {

                    target = circle;
                }
            }


            loc.y+=70;
            loc.x=0;



		}
    }
	
}