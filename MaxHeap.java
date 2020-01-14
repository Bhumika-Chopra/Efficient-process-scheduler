package PriorityQueue;
import java.util.ArrayList;

public class MaxHeap<T extends Comparable> implements PriorityQueueInterface<T> {
	static int count = 0;
	ArrayList<pair<T>> heap = new ArrayList<pair<T>>();

	boolean isleaf(int pos) 
    { 
        if(lchild(pos) >= count)  
        	return true;
        else
        	return false;
    } 
	
	int parent(int pos) 
    { 
        return (pos - 1) / 2; 
    } 
	
	int lchild(int pos) 
    { 
        return (2*pos + 1); 
    } 
	
	int rchild(int pos) 
    { 
        return (2*pos + 2); 
    } 
	
	void heapify(int pos) {
		if(isleaf(pos)) 
			return;
		
		pair<T> t = heap.get(pos);
		if(rchild(pos) < count) {
			if(t.compareTo(heap.get(lchild(pos))) == 0 || t.compareTo(heap.get(rchild(pos))) == 0) {
				if(heap.get(lchild(pos)).compareTo(heap.get(rchild(pos))) == 0) {
					pair<T> temp = heap.get(rchild(pos));
					heap.set(rchild(pos), heap.get(pos));
					heap.set(pos, temp);
					heapify(rchild(pos));
				}
				else {
					pair<T> temp = heap.get(lchild(pos));
					heap.set(lchild(pos), heap.get(pos));
					heap.set(pos, temp);
					heapify(lchild(pos));
				}
			}
		}
		else {
			if(t.compareTo(heap.get(lchild(pos))) == 0) {
					pair<T> temp = heap.get(lchild(pos));
					heap.set(lchild(pos), heap.get(pos));
					heap.set(pos, temp);
				}
			else {
				return;
			}
		}
	}
	
    @Override
    public void insert(T element) {
    	count++;
    	pair<T> e = new pair<T>(element, element.hashCode());
    	heap.add(e);
    	int index = count - 1;
    	if(count == 1) 
    		return;
    	while(heap.get(parent(index)).compareTo(e) == 0) {
    		pair<T> t = heap.get(parent(index));
    		heap.set(parent(index), e);
    		heap.set(index, t);
    		index = parent(index);
    	}
    }

    @Override
    public T extractMax() {
    	if(count == 0) 
    		return null;
    	pair<T> t = heap.get(0);
    	//Student s = new Student(t.getName(), t.getmarks());
	   	if(count == 1) {
	   		heap.remove(0);
	   		count--;
	   		return t.getStudent();
	   	}
	   	else {
	    	heap.set(0, heap.get(count-1));
		   	heap.remove(count - 1);
		   	count--;
		    heapify(0);
	        return t.getStudent();
	   	}
    }
    
    public T search(Object jname) {
    	for(int i=0; i<heap.size(); i++) {
//    		pair<T> t = heap.get(i);
//    		System.out.println(t.getStudent().toString());
			if(heap.get(i).equals(jname)) {
    			return heap.get(i).getStudent();
    		}
    	}
    	return null;
    }
    
    public int size() {
    	return count;
    }
    
    public static void main(String[] args) {
    	MaxHeap<Student> maxHeap = new MaxHeap<>();
    	Student s = new Student("A", 10);
    	maxHeap.insert(s);
    	Student s3 = new Student("D", 20);
    	Student s1 = new Student("B", 20);
    	Student s2 = new Student("C", 20);
    	maxHeap.insert(s3);
    	maxHeap.insert(s1);
    	maxHeap.insert(s2);
    	if(maxHeap.search("C") == null)
    		System.out.println("toto");
    	else
    		System.out.println("dkf");
    	System.out.println(maxHeap.extractMax());
    	System.out.println(maxHeap.extractMax());
    	maxHeap.insert(s3);
    	System.out.println(maxHeap.extractMax());
    	System.out.println(maxHeap.extractMax());
    }

}