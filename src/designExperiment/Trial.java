package designExperiment;

import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CPolyLine;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;

import java.awt.Point;
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
		CText text1 = experiment.getCanvas().newText(100, 100, "instructions", Experiment.INSTRUCTIONS_FONT);
        text1.addTag(experiment.getInstructions());

	}



	public void hideInstructions() {
		experiment.getCanvas().removeShapes(experiment.getInstructions());
	}

	public void start(double x_middle, double y_middle) {

        // display fullshapes
		fillGrid(nonTargetsCount, 30);
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


    public void fillGrid(int n, int size){
        Random rand = new Random();
        int r = rand.nextInt(n);
        int cont = 0;
		Point loc = new Point (0,0);
		for (int i =0; i<Math.sqrt(n); i++) {
			for (int j =0; j<Math.sqrt(n); j++) {
		CPolyLine triangle1 = createTriangle(loc,size,false,experiment.getCanvas());
		loc.x  += size;
		if(cont == r) {
			CPolyLine triangle2 = createTriangle(loc,size,true,experiment.getCanvas());
		}
		else {
			CPolyLine triangle2 = createTriangle(loc,size,false,experiment.getCanvas());
		}
		cont++;
		loc.x+=70;
			}
			loc.y+=70;
			loc.x=0;
		}
    }
    
    public void placeholderGrid(int n, int size){
		Point loc = new Point (0,0);
		for (int i =0; i<Math.sqrt(n); i++) {
			for (int j =0; j<Math.sqrt(n); j++) {
				CEllipse circle = experiment.getCanvas().newEllipse(loc.x,loc.y,size,size);
		        circle.addTag(placeholderShapes);

		loc.x+=100;
			}
			loc.y+=70;
			loc.x=0;
		}
    }
	
}