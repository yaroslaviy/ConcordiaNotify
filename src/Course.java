
public class Course {
	protected String name;
	protected String section;

	public Course(String name, String section) {
		super();
		this.name = name;
		this.section = section;
	}

	
	public String toString() {
		return name  + " " + section ;
	}
	
	

}
