package ProjectManagement;


public class Project {
	String pname;
	int budget;
	int priority;
	public Project(String name, int budget, int priority) {
		pname = name;
		this.budget = budget;
		this.priority = priority;
	}
	
	public String getname() {
		return this.pname;
	}
	
}
