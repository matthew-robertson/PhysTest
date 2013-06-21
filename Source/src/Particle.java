public class Particle{
	protected float radius;
	protected float invMass;
	protected float elasticity;
	protected Vector3d position;
	protected Vector3d velocity;
	protected Vector3d acceleration;
	protected long ticksActive;
	protected boolean flippedX;
	protected boolean flippedY;
	protected boolean flippedZ;
	
	public Particle(Vector3d velocity, Vector3d acceleration, float radius){
		this.position = new Vector3d(0,0,0);
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.radius = radius;
		this.elasticity = 0.65f;
		this.invMass = 1;
		ticksActive = 0;
	}
	
	public Particle(Particle p){
		this.position = p.position;
		this.velocity = p.velocity;
		this.acceleration = p.acceleration;
		this.radius = p.radius;
		this.elasticity = p.elasticity;
		this.invMass = p.invMass;
		ticksActive = 0;
	}
	
	public Particle integrate(Engine engine){
		ticksActive++;
		this.position = this.position.Vector3dAdd(this.velocity);
		this.velocity = this.velocity.Vector3dAdd(this.acceleration);		
		return this;
	}
	
	public Particle wallCheck(Engine engine){
		if ((this.position.x - radius < -engine.roomsize || this.position.x + radius > engine.roomsize) && flippedX == false){
			this.setVelocity(new Vector3d(-this.velocity.x, this.velocity.y, this.velocity.z).scalarMultiple(elasticity));
			flippedX = true;
		}		
		if ((this.position.y - radius < -engine.roomsize || this.position.y  + radius > engine.roomsize) && flippedY == false){
			this.setVelocity(new Vector3d(this.velocity.x, -this.velocity.y, this.velocity.z).scalarMultiple(elasticity));
			flippedY = true;
		}		
		if ((this.position.z - radius < -engine.roomsize || this.position.z + radius > engine.roomsize) && flippedZ == false){
			this.setVelocity(new Vector3d(this.velocity.x, this.velocity.y, -this.velocity.z).scalarMultiple(elasticity));
			flippedZ = true;
		}
		
		if (this.position.z - radius > -engine.roomsize && this.position.z + radius < engine.roomsize){
			flippedZ = false;
		}
		if (this.position.y - radius > -engine.roomsize && this.position.y + radius < engine.roomsize){
			flippedY = false;
		}
		if (this.position.x - radius > -engine.roomsize && this.position.x + radius < engine.roomsize){
			flippedX = false;
		}
		
		if (this.velocity.x < 0.009f && this.velocity.x > -0.009f){
			this.velocity.x = 0;
		}		
		if (this.velocity.y < 0.009f && this.velocity.y > -0.009f){
			this.velocity.y = 0;
		}
		if (this.velocity.z < 0.009f && this.velocity.z > -0.009f){
			this.velocity.z = 0;
		}
		return this;
	}
	
	public void resolveCollision(Manifold m, Particle p){
		//Get relative velocity
		Vector3d rv = p.velocity.Vector3dSubtract(this.velocity);
		
		float velAlongNormal = rv.dotProduct(m.normal);
		
		if (velAlongNormal > 0){
			return;
		}
		//elasticity to use
		float e = Math.min(this.elasticity, p.elasticity);
		
		//impulse scalar
		float j = -(1 + e) * velAlongNormal;
		j /= this.invMass + p.invMass;
		
		//Apply impulse
		Vector3d impulse = m.normal.scalarMultiple(j);
				
		this.velocity = this.velocity.Vector3dSubtract(impulse.scalarMultiple(this.invMass));
		p.velocity = p.velocity.Vector3dAdd(impulse.scalarMultiple(p.invMass));
		
	}
	
	public boolean particleCollision(Manifold m){
		Particle a = m.first;
		Particle b = m.second;
		
		Vector3d distance = b.position.Vector3dSubtract(a.position);
		
		float radius = a.radius + b.radius;
		radius *= radius;
		
		//Check if overlapping. If not, stop
		if (distance.lengthSquared() > radius){
			return false;
		}
		float d = distance.length();
		//Spheres are colliding
		if (d != 0){
			m.penetration = radius - d;
			m.normal = distance.scalarMultiple(1/d);
		}
		else{
			m.penetration = a.radius;
			m.normal = new Vector3d(1, 0, 0);
		}
		
		
		return true;
	}
	
	/**
	 * setters
	 */
	
	public Particle setElasticity(float e){
		this.elasticity = e;
		return this;
	}
	
	
	public Particle setInvMass(float m){
		this.invMass = m;
		return this;
	}
	public Particle setPosition(Vector3d vec){
		this.position = vec;
		return this;
	}
	
	public Particle setVelocity(Vector3d vec){
		this.velocity = vec;
		return this;
	}
	
	public Particle seAcceleration(Vector3d vec){
		this.acceleration = vec;
		return this;
	}
	
	public final static Particle standard = new Particle(new Vector3d(0, 0, 0), new Vector3d(0, -0.01f, 0), 3f);
	
}