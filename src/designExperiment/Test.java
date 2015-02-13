package designExperiment;

import fr.lri.swingstates.canvas.*;
import fr.lri.swingstates.canvas.Canvas;
import fr.lri.swingstates.canvas.transitions.PressOnShape;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.KeyPress;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;

public class Test {
    static Canvas canvas;
    static CExtensionalTag experimentShapes = new CExtensionalTag() {
    };

    public static void main(String[] args) {

        File designFile = new File("experiment.csv");

        Experiment experiment = new Experiment("0", 5, 20, designFile);


        JFrame frame = new JFrame();
        canvas = new Canvas(800, 600);
        double x_middle = canvas.getPreferredSize().getWidth()/2;
        double y_middle = canvas.getPreferredSize().getHeight()/2;

        int number_of_el = 4;

        //final CEllipse target = canvas.newEllipse(x_middle-20, y_middle-20, 40, 40);
        //final CEllipse object = canvas.newEllipse(100, 100, 40, 40);

        Point loc = new Point(150,150);
        int size = 30;
        CPolyLine triangle1 = createTriangle(loc,size,false);
        loc = new Point(150+size,150);
        CPolyLine triangle2 = createTriangle(loc,size,false);


        loc = new Point(250,150);
        CPolyLine triangle3 = createTriangle(loc,size,false);
        loc = new Point(250+size,150);
        CPolyLine triangle4 = createTriangle(loc,size,true);


        loc = new Point(150,200);
        CPolyLine triangle5 = createTriangle(loc,size,false);
        loc = new Point(150+size,200);
        CPolyLine triangle6 = createTriangle(loc,size,false);

        loc = new Point(250,200);
        CPolyLine triangle7 = createTriangle(loc,size,false);
        loc = new Point(250+size,200);
        CPolyLine triangle8 = createTriangle(loc,size,false);

        double shapesCenterX = experimentShapes.getCenterX();
        double shapesCenterY = experimentShapes.getCenterY();

        experimentShapes.translateBy(x_middle-shapesCenterX, y_middle-shapesCenterY);

        //	object.setFillPaint(Color.RED);

        //	final CText text1 = canvas.newText(200, 200, "instruction1");
        //	final CText text2 = canvas.newText(200, 300, "instruction2");

        //	text1.addTag("instruction");
        //	text2.addTag("instruction");


        CStateMachine pressOnCircle = new CStateMachine() {
            State state = new State() {
                Transition pressOnShape = new PressOnShape() {
                    public void action() {
                        CShape shapePressed = getShape();
					/*	if(shapePressed == target) {
							System.out.println("press on target");
							shapePressed.setFillPaint(Color.WHITE);
						} else {*/
                        System.out.println("press on object");
                        shapePressed.setFillPaint(Color.RED);

                        //}
                    }
                };

                Transition pressSpaceBar = new KeyPress(KeyEvent.VK_SPACE) {
                    public void action() {
                        System.out.println("Space bar");
                        //target.setDrawable(false);

                        canvas.getTag("instruction").setFillPaint(Color.RED);
                    }
                };

            };
        };

        pressOnCircle.attachTo(canvas);



        frame.getContentPane().add(canvas);
        frame.pack();
        frame.setVisible(true);

        canvas.requestFocus();
    }

    public static CPolyLine createTriangle(Point loc, int size, boolean sym){
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


    public void fillGrid(int n, int size,double x_middle, double y_middle){
	/*	Point loc = new Point ()
		for (int i =0; i<n; i++) {
			
		CPolyLine triangle1 = createTriangle(loc,size,canvas,false);
		loc = new Point(150+size,150);
		CPolyLine triangle2 = createTriangle(loc,size,canvas,false);
		}*/
    }

}
