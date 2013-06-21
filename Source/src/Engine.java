import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Main Physics engine, controls the renderer and so on
 * @author Matt
 *
 */
public class Engine{
	int width;
	int height;
	int depth;
	long lastFrame;
	long lastball;
	int roomsize;
	int fps;
	long lastFPS;
	InputHandler input;
	Renderer render;
	Camera camera;
	public List<Particle> particleList;
	
	public Engine(int width, int height, int depth, int roomsize){
		this.width = width;
		this.height = height;
		this.roomsize = roomsize;
		input = new InputHandler();
		render = new Renderer();
		particleList = new ArrayList<Particle>(255);		
		this.start(render);		
	}
	
	public void start(Renderer render){
		try{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("Derp 2.0");
			Display.create();
		} 
		catch(LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		this.gameLoop();
	}
	
	/**
	 * Game loop.
	 * Check if there are enough particles
	 * Step each particle forward in time
	 * Check for and resolve collisions
	 */
	public void gameLoop(){
		camera = new Camera(this, new Vector3d(0,0,0), new Vector3d(0,0,0), 45.0f, 0.1f, 100f);
		float x = 0;
		float y = 0;
		float z = 0;
		float vx = 0;
		float vy = 0;
		float vz = 0;
		float ma = 0;
		float el = 0;
		
		lastball = getTime();
		Particle first;
		Particle second;		
		
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer
		
		/*particleList.add(new Particle(Particle.standard.setPosition(new Vector3d(7f, -0.5f, -5f))).setVelocity(new Vector3d(0.2f, 0.3f, 0.2f)));
		particleList.add(new Particle(Particle.standard.setPosition(new Vector3d(-3f, 5f, 2f))).setVelocity(new Vector3d(-0.5f, 0.1f, 0.2f)));
		particleList.add(new Particle(Particle.standard.setPosition(new Vector3d(2f, -4.6f, -3f))).setVelocity(new Vector3d(0.3f, -0.2f, -0.4f)));
		particleList.add(new Particle(Particle.standard.setPosition(new Vector3d(-6f, 2f, 6f))).setVelocity(new Vector3d(-0.1f, 0.1f, 0.0f)));
		*/
		
		// The actual game loop
		while (!Display.isCloseRequested()){
			//If there aren't too many particles to kill your computer, and it's not too soon
			if (particleList.size() <= 15 && getTime() - lastball > 100){
				float am = roomsize -1;
				float amm = am / 2;
				
				//Get some random values for position and velocity
				x = (float) (Math.random() * am - amm);
				y = (float) (Math.random() * am - amm);
				z = (float) (Math.random() * am - amm);
				
				vx = (float) (Math.random() * 2 - 1);
				vy = (float) (Math.random() * 2 - 1);
				vz = (float) (Math.random() * 2 - 1);
				ma = (float) (Math.random() + Math.random() * 3);
				el = (float) (Math.random() * 0.35 + 0.65);				
				
				particleList.add(new Particle(Particle.standard.setPosition(new Vector3d(x, y, z))).setElasticity(el).setVelocity(new Vector3d(vx, vy, vz)).setInvMass(ma));
			
				lastball = getTime();
			}
			
			input.pollInput(this, camera);
			render.update(this, camera);
			
			//Actually make the particles do stuff.
			for (int i = 0; i < particleList.size(); i++){
				first = particleList.get(i);
				first.integrate(this);
				if (first.ticksActive > 1000){
					particleList.remove(i);
				}
				
				for (int j = i + 1; j < particleList.size(); j++){
					second = particleList.get(j);
					if (Math.abs(first.position.x - second.position.x) < 2 * first.radius + 0.1f){
						if (Math.abs(first.position.y - second.position.y) < 2 * first.radius + 0.1f){
							if (Math.abs(first.position.z - second.position.z) < 2 * first.radius + 0.1f){
								Manifold m = new Manifold(first, second);
								if (first.particleCollision(m)){
									first.resolveCollision(m, second);
								}
							}
						}
					}				
				}
				first.wallCheck(this);
			}
			updateFPS();
		}
		Display.destroy();
	}	
	
	/**
	 * Get the time in milliseconds
	 * 
	 * @return The system time in milliseconds
	 */	
	public long getTime() {
	    return System.nanoTime() / 1000000;
	}
	
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	 
	    return delta;
	}
	
	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
	    if (getTime() - lastFPS > 1000) {
	        Display.setTitle("FPS: " + fps);
		fps = 0;
		lastFPS += 1000;
	    }
	    fps++;
	}
}