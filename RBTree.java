package RedBlack;
import Trie.Person;


public class RBTree<T extends Comparable, E> implements RBTreeInterface<T, E>  {
	RedBlackNode<T, E> root;
	public RBTree() {
		root = null;
	}
    @Override
    public void insert(T key, E value) {
    	if(root == null) {
    		root = new RedBlackNode<T,E>(key,value);
    		root.c = 0;
    	}
    	else {
    		RedBlackNode<T, E> parent = null;
    		RedBlackNode<T, E> current = root;
    		while(current != null) {
    			parent = current;
    			if(current.key.compareTo(key) == 0) {
    				current.addvalues(value);
    				break;
    			}
    			else if(current.key.compareTo(key) > 0) 
    				current = current.left;
    			else
    				current = current.right;
    		}
    		if(parent.key.compareTo(key) > 0) {
    			parent.left = new RedBlackNode<T,E>(key, value);
    			parent.left.c = 1;
    			parent.left.parent = parent;
    			if(parent.c == 1) {
    				fixtree(parent.left);
    			}
    				
    		}
    		else if (parent.key.compareTo(key) < 0) {
    			parent.right = new RedBlackNode<T,E>(key, value);
    			parent.right.c = 1;
    			parent.right.parent = parent;
    			if(parent.c == 1) {
    				fixtree(parent.right);
    			}
    		}
    	}
    }

    public void fixtree(RedBlackNode<T, E> node) {
    	if(node == root) {
    		node.c = 0;
    		return;
    	}
    	if(node.parent.c == 0)
    		return;
    	RedBlackNode<T, E> grandparent = node.parent.parent;
    	RedBlackNode<T, E> uncle;
    	if(node.parent == grandparent.left)
    		uncle = grandparent.right;
    	else
    		uncle = grandparent.left;
    	if(uncle == null || uncle.c == 0) {
    		restructure(node, node.parent, grandparent);
    		//System.out.println("hello");
    		return;
    	}
    	else {
    		uncle.c = 0;
    		node.parent.c = 0;
    		grandparent.c = 1;
    		fixtree(grandparent);
    	}
    }
    
    public void restructure(RedBlackNode<T, E> node, RedBlackNode<T, E> parent, RedBlackNode<T, E> grandparent) {
    	if(node == parent.left) {
    		if(parent == grandparent.left) {
    			parent.c = 0;
    			node.c = 1;
    			grandparent.c = 1;
    			rightrotate(node, parent, grandparent);
    		}
    		else {
    			parent.left = node.right;
    			if(node.right != null) 
    				node.right.parent = parent;
    			grandparent.right = node;
    			node.right = parent;
    			node.parent = grandparent;
    			parent.parent = node;
    			node.c = 0;
    			parent.c = 1;
    			grandparent.c = 1;
    			leftrotate(parent,node,grandparent);
    		}
    	}
    	else {
    		if(parent == grandparent.right) {
    			parent.c = 0;
    			node.c = 1;
    			grandparent.c = 1;
    			leftrotate(node, parent, grandparent);
    		}
    		else {
    			parent.right = node.left;
    			if(node.left != null) 
    				node.left.parent = parent;
    			grandparent.left = node;
    			node.left = parent;
    			node.parent = grandparent;
    			parent.parent = node;
    			node.c = 0;
    			parent.c = 1;
    			grandparent.c = 1;
    			rightrotate(parent,node,grandparent);
    		}
    	}
    }
    
    public void rightrotate(RedBlackNode<T, E> node, RedBlackNode<T, E> parent, RedBlackNode<T, E> grandparent)
    {
    	grandparent.left = parent.right;
		if(parent.right != null) 
			parent.right.parent = grandparent;
		parent.parent = grandparent.parent;
		if(grandparent.parent == null) 
			root = parent;
		else if(grandparent == grandparent.parent.left) 
			grandparent.parent.left = parent;
		else
			grandparent.parent.right = parent;
		parent.right = grandparent;
		grandparent.parent = parent;
    }
    
    public void leftrotate(RedBlackNode<T, E> node, RedBlackNode<T, E> parent, RedBlackNode<T, E> grandparent)
    {
    	grandparent.right = parent.left;
		if(parent.left != null) 
			parent.left.parent = grandparent;
		parent.parent = grandparent.parent;
		if(grandparent.parent == null) {
			root = parent;
		}
		else if(grandparent == grandparent.parent.left) {
			grandparent.parent.left = parent;
		}
		else {
			grandparent.parent.right = parent;
		}
		//System.out.println(parent.getValue().toString());
		parent.left = grandparent;
		grandparent.parent = parent;
    }
    @Override
    public RedBlackNode<T, E> search(T key) {
        RedBlackNode<T, E> temp = new RedBlackNode<T,E>();
    	if(root == null) {
        	return root;
        }
        RedBlackNode<T, E> current = root;
        while(current != null) {
        	if(current.key.compareTo(key) == 0) 
        		return current;
        	else if(current.key.compareTo(key) > 0) 
        		current = current.left;
        	else
        		current = current.right;
        }
        return temp;
    }
    
//    public void print() {
//    	print(root);
//    }
//    
//    public void print(RedBlackNode<T, E> node) {
//    	if(node == null) 
//    		return;
//    	print(node.left);
//    	if(node.getValues() != null) {
//    		System.out.println(node.c);
//    		 for (Object person1 : node.getValues()) {
//                 System.out.println(person1);
//             }
//    	}
//    	print(node.right);
//    }
//    public static void main(String[] args) {
//    	RBTree<String, Person> rb = new RBTree<String, Person>();
//    	Person p = new Person("B", "1");
//
//    	Person p1 = new Person("A", "2");
//    	Person p2 = new Person("C", "2");
//    	Person p3 = new Person("F", "2");
//    	Person p5 = new Person("D", "2");
//    	Person p4 = new Person("G", "2");
//    	Person p6 = new Person("E", "2");
//    	rb.insert("A", p1);
//    	rb.insert("B", p);
//    	rb.insert("C", p2);
//    	rb.print();
//    } 
}