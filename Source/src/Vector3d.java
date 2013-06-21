public class Vector3d{
	public float x;
	public float y;
	public float z;
	
	public Vector3d(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float dotProduct(Vector3d vec){
		float sum = this.x * vec.x + this.y * vec.y + this.z * vec.z;
		return sum;
	}
	
	public Vector3d Vector3dSubtract(Vector3d vec){
		return (new Vector3d(x - vec.x, y - vec.y, z - vec.z));
	}
	
	public Vector3d Vector3dAdd(Vector3d vec){
		return (new Vector3d(x + vec.x, y + vec.y, z + vec.z));
	}
	
	public float lengthSquared(){
		return (float)(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z,  2));
	}
	
	public float length(){
		return (float) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z,  2)));
	}
	
	public Vector3d scalarMultiple(float scalar){
		return (new Vector3d(x * scalar, y * scalar, z * scalar));
	}
	
	public String toString()
	{
		return "<" + x + "," + y + "," + z + ">";
	}
}