import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHandler{
	long lastTime;
	int x = 0;
	
	public void updateCamera(Engine engine, Camera cam){
		
		int dx = engine.width/2 - Mouse.getX();
		int dy = engine.height/2 - Mouse.getY();
		if (dx < 0){
			cam.setRotation(cam.getRotation().Vector3dAdd(new Vector3d(0f, 2f, 0f)));
		}
		else if (dx > 0){
			cam.setRotation(cam.getRotation().Vector3dSubtract(new Vector3d(0f, 2f, 0f)));
		}
		
		if (dy > 0 && cam.getRotation().x < cam.getMaxAngle()){
			cam.setRotation(cam.getRotation().Vector3dAdd(new Vector3d(2f, 0f, 0f)));
		}
		else if (dy < 0 && cam.getRotation().x > -cam.getMaxAngle()){
			cam.setRotation(cam.getRotation().Vector3dSubtract(new Vector3d(2f, 0f, 0f)));
		}
		
		
		Mouse.setCursorPosition(engine.width / 2, engine.height / 2);
	}
	
	public void pollInput(Engine engine, Camera cam){
		updateCamera(engine, cam);
		/*if (Mouse.isButtonDown(0)){
			if (System.nanoTime() - lastTime > 100000000){
				if (x > 20){
					x = -20;
				}
				
				x++;			
				engine.particleList.add(new Particle(Particle.standard).setPosition(new Vector3d(x, 0, -60f)).setVelocity(new Vector3d(1, 1, 0)));
				System.out.println("Made at: " + x + " Mouse at: " + Mouse.getX());
				lastTime = System.nanoTime();
			}
		}*/
		
       if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
    	   cam.move(0.75f, 0);
	    }
       if (Keyboard.isKeyDown(Keyboard.KEY_W)){
	    	cam.move(0.75f, 1);
	    }
       if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
    	   cam.move(-0.75f, 1);
       }
       if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
    	   cam.move(-0.75f, 0);
	    }
       if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
    	   System.exit(0);
       }
       if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
    	   cam.setPosition(cam.getPosition().Vector3dSubtract(new Vector3d(0, 0.5f, 0f)));
       }
       if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
    	   cam.setPosition(cam.getPosition().Vector3dAdd(new Vector3d(0, 0.5f, 0f)));
       }
       
       if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
	    	cam.setRotation(cam.getRotation().Vector3dSubtract(new Vector3d(0f, 5f, 0f)));
	    }
       
       if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
	    	cam.setRotation(cam.getRotation().Vector3dAdd(new Vector3d(0f, 5f, 0f)));
	    }
	}
}