import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

public class Renderer{	
	public void update(Engine engine, Camera cam){
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glLoadIdentity();
		cam.updateView();
		//drawRoom(engine.roomsize);    
		drawParticles(engine);
		Display.sync(70);
		Display.update();
		
	}
	
	private void drawRoom(int roomsize){
		GL11.glPushMatrix();
		GL11.glColor3d(5.0f, 0.5f, 0f);
		GL11.glTranslatef(0, 0, 0);
		GL11.glBegin(GL11.GL_QUADS);
		{
			//Back face
			GL11.glColor3f(1, 0, 0);
			GL11.glVertex3f(-roomsize, roomsize, -roomsize);
			GL11.glVertex3f(roomsize, roomsize, -roomsize);
			GL11.glVertex3f(roomsize, -roomsize, -roomsize);
			GL11.glVertex3f(-roomsize, -roomsize, -roomsize);
			
			//Front face
			/*GL11.glColor3f(0, 1, 1);
			GL11.glVertex3f(-roomsize, roomsize, roomsize);
			GL11.glVertex3f(roomsize, roomsize, roomsize);
			GL11.glVertex3f(roomsize, -roomsize, roomsize);
			GL11.glVertex3f(-roomsize, -roomsize, roomsize);*/
			
			//Left face
			GL11.glColor3f(0, 1, 0);
			GL11.glVertex3f(-roomsize, roomsize, -roomsize);
			GL11.glVertex3f(-roomsize, roomsize, roomsize);
			GL11.glVertex3f(-roomsize, -roomsize, roomsize);
			GL11.glVertex3f(-roomsize, -roomsize, -roomsize);
			
			//Right face
			GL11.glColor3f(0, 0, 1);
			GL11.glVertex3f(roomsize, roomsize, -roomsize);
			GL11.glVertex3f(roomsize, roomsize, roomsize);
			GL11.glVertex3f(roomsize, -roomsize, roomsize);
			GL11.glVertex3f(roomsize, -roomsize, -roomsize);
			
			//Top face
			GL11.glColor3f(1, 1, 0);
			GL11.glVertex3f(roomsize, roomsize, roomsize);
			GL11.glVertex3f(-roomsize,roomsize, roomsize);
			GL11.glVertex3f(-roomsize, roomsize, -roomsize);
			GL11.glVertex3f(roomsize, roomsize, -roomsize);
			
			//Bottom face
			GL11.glColor3f(1, 0, 1);
			GL11.glVertex3f(roomsize, -roomsize, roomsize);
			GL11.glVertex3f(-roomsize, -roomsize, roomsize);
			GL11.glVertex3f(-roomsize, -roomsize, -roomsize);
			GL11.glVertex3f(roomsize, -roomsize, -roomsize);
			
		}
		GL11.glEnd();
		GL11.glPopMatrix();
		
	}
	
	
	private void drawSphere(float x, float y, float z, float radius, Vector3d vec) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, z);
        //GL11.glColor3f(vec.x, vec.y, vec.z);
        Sphere s = new Sphere();
        s.draw(radius, 50, 50);
        GL11.glPopMatrix();
    }	
	public void drawParticles(Engine engine){
		for (int i = 0; i < engine.particleList.size(); i++){
			drawSphere(engine.particleList.get(i).position.x
					, engine.particleList.get(i).position.y
					, engine.particleList.get(i).position.z
					, engine.particleList.get(i).radius
					, new Vector3d(0, 0.5f, 0.5f));
		}
	}	
}