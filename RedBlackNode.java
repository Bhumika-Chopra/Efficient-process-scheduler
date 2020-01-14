package RedBlack;

import Util.RBNodeInterface;

import java.util.LinkedList;
import java.util.List;

public class RedBlackNode<T extends Comparable, E> implements RBNodeInterface<E> {
	int c;
	T key;
	List<E> list;;
	RedBlackNode<T, E> left;
	RedBlackNode<T, E> right;
	RedBlackNode<T, E> parent;
	public RedBlackNode(T key, E data) {
		list = new LinkedList<E>();
		left = null;
		right = null;
		parent = null;
		this.key = key;
		list.add(data);
	}
	public RedBlackNode() {
		left = null;
		right = null;
		parent = null;
		key = null;
		list = null;
	}
	
	public void addvalues(E value) {
		list.add(value);
	}
	
    @Override
    public E getValue() {
        return list.get(0);
    }

    @Override
    public List<E> getValues() {
        return list;
    }
    
    public void emptylist() {
    	int size = list.size();
    	for(int i=0; i<size; i++) {
    		list.remove(0);
    	}
    }
}
