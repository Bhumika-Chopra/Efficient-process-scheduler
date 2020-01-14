package Trie;

import java.util.HashMap; 
import java.util.Map; 

public class Trie<T> implements TrieInterface {
	TrieNode<T> root;
	static String s = "";
	String pre = null;
	public Trie() {
		root = new TrieNode<T>(null);
	}
	
	public boolean getchildren(TrieNode<T> node) {
		for(int i=0; i<95; i++) {
			if(node.map[i] != null) 
				return true;
		}
		return false;
	}
	int height(TrieNode<T> node) {
	    if (node == null) 
	    	return 1;
	    int l = 0;
	    for (int i = 0 ; i < 95 ; i++) {
	        int h = height(node.map[i]);
	        if (h > l) {
	            l = h;
	        }
	    }
	    return l+1;
	}
	
    @Override
    public boolean delete(String word) {
    	if(search(word) != null) {
    		delete(word, root, 0);	
    		return true;
    	}
    	else
    		return false;
    }

    public boolean delete(String word, TrieNode<T> current, int level) {
    	boolean self = false;
    	if(level == word.length()) {
    		if(getchildren(current)) {
    			current.isend = false;
    			current.value = null;
    			self = false;
    		}
    		else {
    			current = null;
    			self = true;
    		}
    	}
    	else {
    		int index = word.charAt(level) - 32;
            TrieNode childNode = current.map[index];
            boolean childDeleted = delete(word, childNode, level + 1);
             
            if (childDeleted) {
                current.map[index] = null;
                if (current.isend){
                    self = false;
                }
                else if (getchildren(current)) {
                    self = false;
                }
                else {
                    current = null;
                    self = true;
                }
            }
            else{
                self = false;
            }
        }
        return self;
    }
    
    @Override
    public TrieNode<T> search(String word) {
        TrieNode<T> dummy = root;
    	for(int i=0; i<word.length(); i++) {
        	char c = word.charAt(i);
        	int index = c - 32;
        	if(dummy.map[index] == null) {
        		return null;
        	}
        	else {
        		dummy = dummy.map[index];
        	}
        		
        }
    	if(dummy == root) {
    		return null;
    	}
    	if(dummy.isend == true)
    		return dummy;
    	else
    		return null;
    }

    @Override
    public TrieNode<T> startsWith(String prefix) {
    	pre = prefix;
    	TrieNode<T> dummy = root;
     	for(int i=0; i<prefix.length(); i++) {
         	char c = prefix.charAt(i);
         	int index = c - 32;
         	if(dummy.map[index] == null) {
         		return null;
         	}
         	else {
         		dummy = dummy.map[index];
         	}
         		
         }
     	if(dummy == root) {
     		return null;
     	}
     	return dummy;
     }

    @Override
    public void printTrie(TrieNode trieNode) {
    	printTrie(trieNode, pre);
    }

    public void printTrie(TrieNode<T> node, String pre) {
    	if(node.isend == true) {
    		//[Name: Diljeet Singh, Phone=+91987654321]
    		System.out.println(node.getValue().toString());
    	}
    	if(!getchildren(node)) {
    		return;
    	}
    	for (int i = 0; i < 95; i++) 
        { 
            if (node.map[i] != null) 
            { 
            	pre = pre + (char)(i + 32);
                printTrie(node.map[i], pre);
            } 
        } 
    }
    
    @Override
    public boolean insert(String word, Object value) {
    	TrieNode<T> dummy = root;
    	int count = 0;
    	 for(int i=0; i<word.length(); i++){
             char c = word.charAt(i);
             int index = c- 32;
             TrieNode<T> temp;
             if(dummy.map[index] == null) {
            	 count++;
            	 if(i == word.length() - 1) {
            		 temp = new TrieNode<T>( (T) value);
            		 temp.isend = true;
            	 }
            	 else
            		 temp = new TrieNode<T>(null);
            	 dummy.map[index] = temp;
            	 dummy = temp;
             }
             else{
                 dummy = dummy.map[index];
             }             
         }
    	if(count != 0) {
    		return true;
    	}
    	else
    		return false;
    }

    @Override
    public void printLevel(int level) {
    	//Level 2: a,h,h,i,k
    	if(level == 0) 
    		return;
    	s = "";
    	printLevel(root, level, 0, -1);
    	String[] dets = s.split(" ");
    	for(int i=0; i<dets.length; i++) {
    		for(int j=0; j<dets.length-i-1; j++) {
    			if(dets[j].compareTo(dets[j+1]) > 0) {
    				String t; 
    				t = dets[j];
    				dets[j] = dets[j+1];
    				dets[j+1] = t;
    			}
    		}
    	}
    	s = "";
    	int k;
    	for(k = 0; k<dets.length-1; k++) {
    		s = s + dets[k] + ",";
    	}
    	s += dets[k]; 
    	System.out.println("Level " + level + ": " + s);
    }
    
    public void printLevel(TrieNode<T> node, int level, int l, int ind) {
    	if(l == level) {
    		if((char)(ind+32) != ' ') {
    			s = s + (char)(ind + 32) + " ";
    			//System.out.println((char)(ind + 32));
    		}
    	}
    	if(!getchildren(node) || l == level)
    		return;
    	for(int i=0; i<95; i++) {
    		if(node.map[i] != null) {
    			printLevel(node.map[i], level, l+1, i);
    		}
    	}
    }

    @Override
    public void print() {
    	System.out.println("-------------");
    	System.out.println("Printing Trie");
    	int levels = height(root);
    	for(int i=1; i<levels; i++) {
    		printLevel(i);
    	}
    	System.out.println("-------------");
    	return;
    }
}