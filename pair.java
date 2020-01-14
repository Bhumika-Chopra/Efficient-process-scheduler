package PriorityQueue;

public class pair<T extends Comparable> implements Comparable<pair<T>> {
	T student;
	int p;
	public pair(T element, int order) {
		this.student = element;
		p = order;
	}
	
	public T getStudent() {
		return this.student;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.getStudent().equals(obj))
			return true;
		else
			return false;
	}
	
	@Override
	public int compareTo(pair<T> obj) {
		if(this.student.compareTo(obj.student) == 0) {
			if(this.p > obj.p) 
				return 0;
			else
				return 1;
		}
		else if(this.student.compareTo(obj.student) == 1) 
			return 1;
		else
			return 0;
	}
}
