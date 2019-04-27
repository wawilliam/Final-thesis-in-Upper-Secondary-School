import org.lwjgl.LWJGLException;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;
/**
 * 
 * GymnasieProjekt
 *
 * @author William Andersson
 * @version 21 sep. 2017
 *
 */
public class Control {


	static Controller controller;
	static int i = 0;
	static int controllerIndex = 0;
	static boolean isButton = false;
	static boolean forsatt = true;

	void setupController() throws InterruptedException {

		try {
			Controllers.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		Controllers.poll();

		for(i = 0; i < Controllers.getControllerCount(); i++) {

			controller = Controllers.getController(i);
			System.out.println(controller.getName());
		}


		if(controller.getName().equals("Controller (Wireless Gamepad F710)")) {

			for(int n = 0; n < controller.getButtonCount(); n++) {

				System.out.println(n + ": " + controller.getButtonName(n));

			}

			for(int n = 0; n < controller.getAxisCount(); n++) {

				System.out.println(n + ":" + controller.getAxisName(n));

			}
			while(true) {
				Thread.sleep(25);
				controller.poll();
				if(!(controller.getAxisName(0).equals("Y-axeln"))) {
					break;
				}
				if(controller.getAxisName(1).equals("X-axeln")) {
					System.out.println(controller.getRXAxisValue());
					
					
				}
				else {
					isButton =  controller.isButtonPressed(0);
					
					if(isButton) {
						System.out.println("A");
					}
					
			
				}
			}
		}
	}
}