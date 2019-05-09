import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

import javax.swing.ImageIcon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Color;
/**
 * 
 * GymnasieProjekt
 *
 * @author William Andersson
 * @version 21 okt. 2017
 *
 */
public class Window implements KeyListener {

	Serial main = new Serial();

	TimerTask task;

	static int width = 1200;
	static int height = 600;

	double C = 0;
	double A = 0;
	double B = 0;
	

	private static double x = 0;
	private static double y = 195;
	private static double z = 0;

	static double armLengt_3 = Math.sqrt((x*x) + (y*y));

	Panel_2D pan = new Panel_2D();
	JFrame frame = new JFrame();
	DecimalFormat dec = new DecimalFormat(".##");
	private JLabel labelStatus;
	private ImageIcon icon;

	boolean[] direction = new boolean[4]; //top0, down1, left2, right3
	boolean animationStatus = false;
	boolean servoStatus = true;

	static final int FPS = 500;

	static Controller controller;
	static boolean cancel = false;

	int i = 0;
	
	private float gripperValue = 95;
	
	String degreesTemp =  String.valueOf((int) (pan.angle_of_servo[0]));
	String degreesTemp_1 = String.valueOf((int) (pan.angle_of_servo[1]));
	String degreesTemp_2 = String.valueOf((int) (pan.angle_of_servo[2]));
	

	String degrees;
	String degrees_1;
	String degrees_2;
	String degrees_3;
	String degrees_4;

	Window() throws InterruptedException {

		main.start();
		arduino();
	}
	/**
	 * @wbp.parser.entryPoint
	 */
	void paintWindow() 
	{

		frame.getContentPane().add(pan);
		SpringLayout sl_pan = new SpringLayout();
		pan.setLayout(sl_pan);

		labelStatus = new JLabel("OFF");
		sl_pan.putConstraint(SpringLayout.NORTH, labelStatus, 13, SpringLayout.NORTH, pan);
		sl_pan.putConstraint(SpringLayout.SOUTH, labelStatus, 38, SpringLayout.NORTH, pan);
		labelStatus.setBackground(Color.LIGHT_GRAY);
		labelStatus.setForeground(Color.RED);
		labelStatus.setFont(new Font("Tahoma", Font.BOLD, 18));
		pan.add(labelStatus);

		JLabel lblNewLabel_1 = new JLabel("Z");
		sl_pan.putConstraint(SpringLayout.NORTH, lblNewLabel_1, 115, SpringLayout.NORTH, pan);
		sl_pan.putConstraint(SpringLayout.WEST, lblNewLabel_1, 21, SpringLayout.WEST, pan);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pan.add(lblNewLabel_1);

		JLabel lblX = new JLabel("Y");
		sl_pan.putConstraint(SpringLayout.NORTH, lblX, 10, SpringLayout.NORTH, pan);
		sl_pan.putConstraint(SpringLayout.WEST, lblX, 125, SpringLayout.WEST, pan);
		lblX.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pan.add(lblX);

		JLabel lblMotioncontrol = new JLabel("MotionControl");
		sl_pan.putConstraint(SpringLayout.EAST, lblMotioncontrol, -698, SpringLayout.EAST, pan);
		sl_pan.putConstraint(SpringLayout.WEST, labelStatus, 6, SpringLayout.EAST, lblMotioncontrol);
		sl_pan.putConstraint(SpringLayout.EAST, labelStatus, 47, SpringLayout.EAST, lblMotioncontrol);
		sl_pan.putConstraint(SpringLayout.NORTH, lblMotioncontrol, 5, SpringLayout.NORTH, lblX);
		lblMotioncontrol.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pan.add(lblMotioncontrol);

		JLabel lblY = new JLabel("Y");
		sl_pan.putConstraint(SpringLayout.NORTH, lblY, 0, SpringLayout.NORTH, lblNewLabel_1);
		sl_pan.putConstraint(SpringLayout.EAST, lblY, -567, SpringLayout.EAST, pan);
		lblY.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pan.add(lblY);

		JLabel lblX_1 = new JLabel("X");
		sl_pan.putConstraint(SpringLayout.NORTH, lblX_1, 0, SpringLayout.NORTH, lblX);
		sl_pan.putConstraint(SpringLayout.WEST, lblX_1, 177, SpringLayout.EAST, labelStatus);
		lblX_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		pan.add(lblX_1);

		icon = new ImageIcon("c:/Users/willi/Desktop/william.png");

		frame.setIconImage(icon.getImage());
		frame.addKeyListener(this);
		frame.setSize(width, height);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setAlwaysOnTop(true);
		frame.setTitle("GymnasieProjekt - Ver. 020171009");
		frame.setVisible(true);

	}

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, InterruptedException 
	{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Window().paintWindow();

	}


	void inverseKinematics4df() throws InterruptedException 
	{


		double lengthArmSideView = Math.sqrt(( (x*x) + (y*y) )) - pan.armLength[2];

		double a = pan.armLength[1];
		double b = pan.armLength[0];
		double c = Math.sqrt(((lengthArmSideView)*(lengthArmSideView)) + ((z)*(z)));

		double D1 = Math.toDegrees(Math.atan(z/lengthArmSideView));

		C = (((a*a) + (b*b) - (c*c)) / (2*a*b));
		A = (((b*b) + (c*c) - (a*a)) / (2*b*c));


		C = Math.acos(C);
		A = Math.acos(A);


		C = Math.toDegrees(C); //Second servo
		A = (D1 + Math.toDegrees(A)); //First servo
		B = A + C;


		pan.angle_of_servo[0] = A;
		pan.angle_of_servo[1] = (180 + pan.angle_of_servo[0] + C);
		pan.angle_of_servo[2] = (0);
		pan.angle_of_servo[3] = -(Math.toDegrees((Math.atan(x/y))));
		pan.armLength[3] = Math.sqrt((x*x) + (y*y));

		pan.repaint();


		degrees = String.valueOf(dec.format((float) (180 - (90 - pan.angle_of_servo[3])))).replace(",", ".");
		degrees_1 = String.valueOf(dec.format((float) (pan.angle_of_servo[0]))).replace(",", ".");
		degrees_2 = String.valueOf(dec.format((float) (360- pan.angle_of_servo[1]) + A).replaceAll(",", "."));
		degrees_3 = "90";  //String.valueOf(dec.format((float) (B)).replaceAll(",", "."));
		degrees_4 = String.valueOf(dec.format(gripperValue).replaceAll(",", "."));

		if(!degrees.equals(degreesTemp)) {
			
			System.out.println("s1 " + degrees);
			System.out.println("s2 " + degrees_1);
			System.out.println("s3 " + degrees_2);
			System.out.println("s4" + degrees_3);
			System.out.println(degrees_4);
			
			arduino();
			degreesTemp = degrees;
		}
		
		else{
			arduino();
			
		}


	}


	public void arduino() throws InterruptedException {



		Serial.writeData(String.valueOf(degrees) + "D" + String.valueOf(degrees_1) + "A" + String.valueOf(degrees_2) + "D" + String.valueOf(degrees_3) + "W" + String.valueOf(degrees_4) + "V");

	}
	void run() 
	{
		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}


		animationStatus = false;

		frame.setResizable(false);

		task = new TimerTask() 
		{

			public void run() 
			{

				Controllers.poll();


				for(i = 0; i < Controllers.getControllerCount(); i++) {

					controller = Controllers.getController(i);
				}

				if(controller.getName().equals("Controller (Wireless Gamepad F710)")) {


					z = z - controller.getPovY();



					if(controller.isButtonPressed(0)) {
						z = z + 2;
					}

					if(controller.isButtonPressed(1)) {
						z = z - 2;
					}
					
					if(controller.getAxisName(4).equals("Z-axeln")) {
						
						
						gripperValue = gripperValue + controller.getZAxisValue();
						
						
					}

					if(controller.getAxisName(1).equals("X-axeln") ) {



						if(!cancel) {
							if(controller.getRXAxisValue() != -1) {
								cancel = true;
							}
						}
						



						else {
							x = x + (controller.getRXAxisValue()*2);
							y = y - (controller.getRYAxisValue()*2);

						}

						try {
							inverseKinematics4df();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						pan.repaint();


					}

					if(animationStatus) {
						Controllers.destroy();
						task.cancel();
					}


				}


			}

		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, (1000/FPS));


	}


	@Override
	public void keyPressed(KeyEvent e) 
	{

		switch (e.getKeyCode()) 
		{

		case KeyEvent.VK_ENTER:
			Arrays.fill(direction, false);
			labelStatus.setText("ON");
			labelStatus.setForeground(Color.GREEN);
			run();
			break;

		case KeyEvent.VK_ESCAPE:
			animationStatus = true;
			Arrays.fill(direction, false);
			labelStatus.setText("OFF");
			labelStatus.setForeground(Color.RED);
		}

	}


	@Override
	public void keyReleased(KeyEvent e) 
	{


	}

	@Override
	public void keyTyped(KeyEvent e) 
	{


	}
}