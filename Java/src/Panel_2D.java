import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;
/**
 * 
 * GymnasieProjekt
 *
 * @author William Andersson
 * @version 9 okt. 2017
 *
 */
public class Panel_2D extends JPanel {

	int width_length = 3;
	int armLengthTotal;
	int lengthCube;

	double [] [] position = new double[5] [2];
	double [] angle_of_servo = new double[4];
	double [] armLength = new double[5];


	Panel_2D() {
		

		position [0] [0] = (Window.width/2 - Window.width/3 - Window.width/50);
		position [0] [1] = (Window.height/2 + (Window.height/4));

		angle_of_servo[0] = 45; //Grader
		angle_of_servo[1] = (180 + angle_of_servo[0] + 150); //Grader
		angle_of_servo[2] = (180 + angle_of_servo[1] + 165); //Grader

		angle_of_servo[3] = 0; //Grader

		armLength[0] = 130; //mm
		armLength[1] = 130; //mm
		armLength[2] = 135; //mm

		armLength[3] = 100; //mm

		lengthCube = 100; //mm

		armLengthTotal = (int) ( armLength[0] + armLength[1] + armLength[2]);
	}


	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Base-line
		g.fillRect( (int) position[0] [0], (int) position[0] [1] - 90, 2, 90);

		//Center-line
		g.fillRect((Window.width / 2), 0, 1, Window.height);

		//Horizontal-line
		g.fillRect( (int) position [0] [0] , (int) position [0] [1] , (Window.width) - ((Window.width/2 - Window.width/3 - Window.width/50)), 2 );

		/**
		 * TOP VIEW
		 */

		//A4 Papper - Top
		int A4_Width = 297;
		int A4_Height = 210;
		g.setColor(Color.WHITE);
		g.fillRect((Window.width/2) + (Window.width/4) - (A4_Width/2), (Window.height/2) + (Window.height/4) - (A4_Height) - lengthCube/2 - 135 , A4_Width, A4_Height);
		g.setColor(Color.BLACK);
		g.drawRect((Window.width/2) + (Window.width/4) - (A4_Width/2), (Window.height/2) + (Window.height/4) - (A4_Height) - lengthCube/2 - 135, A4_Width, A4_Height);


		//Foundation
		g.setColor(new Color(228, 201, 140));
		g.fillRect((Window.width/2) + (Window.width/4) - (lengthCube/2), (Window.height/2) + (Window.height/4) - (lengthCube/2),lengthCube, lengthCube);
		g.setColor(Color.BLACK);
		g.drawRect((Window.width/2) + (Window.width/4) - (lengthCube/2), (Window.height/2) + (Window.height/4) -(lengthCube/2), lengthCube, lengthCube);


		g.setColor(Color.GRAY);
		for(int i = 1; i <= armLength[3]; i++) {

			g.fillRect((int) (Window.width/2 + Window.width/4 - (3) + (i*Math.cos(Math.toRadians(angle_of_servo[3]+90)))), (int) ((Window.height/2) + (Window.height/4) - 3 - (i*Math.sin(Math.toRadians(angle_of_servo[3]+90)))), 5, 5);

		}

		g.setColor(Color.BLACK);


		/**
		 * SIDE VIEW
		 */


		for (int arm = 0; arm < 3; arm++) {

			g.setColor(Color.GRAY);

			for (int i = 1; i <= armLength[arm]; i++) {


				if(arm == 0) {

					g.fillRect( (int) ( position[arm+1] [0] = ((position[arm] [0] + (i*Math.cos(Math.toRadians(angle_of_servo[arm])))))), 
							(int) ( position[arm+1] [1] = ((position [arm] [1] - 90 - (i*Math.sin(Math.toRadians(angle_of_servo[arm])))))), width_length, width_length );

				}

				else {

					g.setColor(Color.GRAY);
					g.fillRect((int) ( position[arm+1] [0] =  ((position[arm] [0] + (i*Math.cos(Math.toRadians(angle_of_servo[arm])))))), 
							(int) ( position[arm+1] [1] = ((position [arm] [1] - (i*Math.sin(Math.toRadians(angle_of_servo[arm])))))), width_length, width_length );


				}

			}

			//Arm 1 rotation circle
			if((arm == 0)) {
				g.setColor(new Color(255, 170, 2));	
				g.fillArc((int) position[arm] [0] - (50/2), (int) position[arm] [1] - 90 - (50/2), 50, 50, (int) 180, 180);
				g.fillOval((int) position[arm] [0] - (25/2), (int) position[arm] [1] - 90 - (25/2), 25, 25);
				g.setColor(Color.BLACK);
				g.drawOval((int) position[arm] [0] - (50/2), (int) position[arm] [1] - 90 - (50/2), 50, 50);
			}
			//Arm 2 rotation circle
			else if(arm == 1) {
				g.setColor(new Color(255, 170, 2));	
				g.fillArc((int) position[arm] [0] - (50/2), (int) position[arm] [1] - (50/2), 50, 50, (int) angle_of_servo[arm-1], 180);
				g.fillOval((int) position[arm] [0] - (25/2), (int) position[arm] [1] - (25/2), 25, 25);
				g.setColor(Color.BLACK);
				g.drawOval((int) position[arm] [0] - (50/2), (int) position[arm] [1] - (50/2), 50, 50);
			}
			//Arm 3 rotation circle
			else if(arm == 2) {

				g.setColor(new Color(255, 170, 2));	
				g.fillArc((int) position[arm] [0] - (50/2), (int) position[arm] [1] - (50/2), 50, 50, (int) (angle_of_servo[arm-1] + 90), 180);
				g.fillOval((int) position[arm] [0] - (25/2), (int) position[arm] [1] - (25/2), 25, 25);
				g.setColor(Color.BLACK);
				g.drawOval((int) position[arm] [0] - (50/2), (int) position[arm] [1] - (50/2), 50, 50);

			}

		}

		//Pen
		g.setColor(Color.WHITE);
		g.fillRect((int) ( position[2] [0] + armLength[2]) - 20/2,(int) position[2] [1] - 50, 20, 140);
		g.setColor(Color.BLACK);
		g.drawRect((int) ( position[2] [0] + armLength[2]) - 20/2,(int) position[2] [1] - 50, 20, 140);


		/**
		 * TOP-VIEW COMP
		 * 
		 */
		g.drawOval((Window.width/2) + (Window.width/4) - 70/2,(Window.height/2) + (Window.height/4) - 70/2, 70, 70);
		g.setColor(new Color(255, 170, 2));


		//Konsoll 1

		for(int j = 0; j <= 25; j++) {

			for(int i = 0; i <= 25; i++) {

				if(j <= 13) {
					g.setColor(Color.WHITE);
				}

				else {

					g.setColor(new Color(255, 170, 2));
				}
				//Right-up-side
				g.fillRect( (int) (Window.width/2 + (Window.width/4) + (j*(Math.cos(Math.toRadians(angle_of_servo[3]))) + (i*Math.cos(Math.toRadians(angle_of_servo[3] + 90))))), (int) ((Window.height/2) + (Window.height/4) - j*(Math.sin(Math.toRadians(angle_of_servo[3]))) - (i*Math.sin(Math.toRadians(angle_of_servo[3] + 90)))) , 1, 1);
				//Left-up-side
				g.fillRect( (int) (Window.width/2 + (Window.width/4) + (-j*(Math.cos(Math.toRadians(angle_of_servo[3]))) + (i*Math.cos(Math.toRadians(angle_of_servo[3] + 90))))),(int) ((Window.height/2) + (Window.height/4) + j*(Math.sin(Math.toRadians(angle_of_servo[3]))) - (i*Math.sin(Math.toRadians(angle_of_servo[3] + 90)))) , 1, 1);


				//Rigt-down-side
				g.fillRect( (int) ( Window.width/2 + (Window.width/4) + (j*(Math.cos(Math.toRadians(angle_of_servo[3]))) - (i*Math.cos(Math.toRadians(angle_of_servo[3] + 90))))),(int) ((Window.height/2) + (Window.height/4) - j*(Math.sin(Math.toRadians(angle_of_servo[3]))) + (i*Math.sin(Math.toRadians(angle_of_servo[3] + 90)))) , 1, 1);
				//Left-down-side
				g.fillRect( (int) ( Window.width/2 + (Window.width/4) + (-j*(Math.cos(Math.toRadians(angle_of_servo[3]))) - (i*Math.cos(Math.toRadians(angle_of_servo[3] + 90))))),(int) ((Window.height/2) + (Window.height/4) + j*(Math.sin(Math.toRadians(angle_of_servo[3]))) + (i*Math.sin(Math.toRadians(angle_of_servo[3] + 90)))) , 1, 1);
			}


		}

		//TODO
		//Konsoll 2
		for(int j = 0; j <= 25; j++) {

			for(int i = 0; i <= 25; i++) {

				if(j <= 13) {

					g.setColor(Color.WHITE);
				}

				else {

					g.setColor(new Color(255, 170, 2));
				}
				//Right-up-side
				g.fillRect( (int) (Window.width/2 + (Window.width/4) + (j*(Math.cos(Math.toRadians(angle_of_servo[3]))) + (i*Math.cos(Math.toRadians(angle_of_servo[3] + 90))))), 
						(int) ((Window.height/2) + (Window.height/4) - j*(Math.sin(Math.toRadians(angle_of_servo[3]))) - (i*Math.sin(Math.toRadians(angle_of_servo[3] + 90)))) , 1, 1);
				//Left-up-side
			}

		}

		//TODO
		//Kontroll 3



		/**
		 * CORDS (SIDE-VIEW)
		 */

		//Z
		g.setColor(new Color(66, 134, 244));
		g.fillRect(20, 20-10, 2, 100);
		//Y
		g.setColor(new Color(111, 229, 84));
		g.fillRect(20-10, 20, 100, 2);


		/**
		 * CORDS (TOP-VIEW)
		 */

		//Y
		g.setColor(new Color(111, 229, 84));
		g.fillRect(20 + Window.width/2 , 20-10, 2, 100);
		//X
		g.setColor(new Color(234, 46, 46));
		g.fillRect(20-10 + Window.width/2, 20, 100, 2);


	}


}