package Trie;


import Util.NodeInterface;

public class TrieNode<T> implements NodeInterface<T> {
	boolean isend;
	T value;
	TrieNode[] map;
	//HashMap<Character, TrieNode> map = new HashMap<Character, TrieNode>();
    public TrieNode() {
    	this.map = new TrieNode[95];
    	this.isend = false;
    	this.value = null;
    }
	public TrieNode(T value) {
    	this.value = value;
    	this.map = new TrieNode[95];
    	isend = false;
    }
	
	
    @Override
    public T getValue() {
        return this.value;
    }

}