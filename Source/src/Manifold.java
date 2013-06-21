public class Manifold{
	Particle first;
	Particle second;
	float penetration;
	Vector3d normal;
	
	public Manifold(Particle a, Particle b){
		this.first = a;
		this.second = b;
	}
}