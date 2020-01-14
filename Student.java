package PriorityQueue;

public class Student implements Comparable<Student> {
    private String name;
    private Integer marks;
    static int order = 0;
    int o;

    public Student(String trim, int parseInt) {
    	this.name = trim;
    	this.marks = parseInt;
    	order++;
    	o = order;
    }

    public int getmarks() {
    	return this.marks;
    }
    
    @Override
    public int hashCode() {
    	return this.o;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if(this.getName().compareTo(obj.toString()) == 0) 
    		return true;
    	else
    		return false;
    }
    
    @Override
    public int compareTo(Student student) {
        if(this.getmarks() == student.getmarks()) 
        	return 0;
        else if(this.getmarks() > student.getmarks())
        	return 1;
        else
        	return 2;
    }
    
    public String getName() {
        return this.name;
    }
    public String toString() {
    	return ("Student{name='" + this.getName() + "', marks=" + this.marks + "}");
    }
}
