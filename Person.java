package Trie;

public class Person{
	String name;
	String num;
    public Person(String name, String phone_number) {
    	this.num = phone_number;
    	this.name = name;
    }

    public String getName() {
        return this.name;
    }
    
    public String toString() {
    	//[Name: Diljeet Singh, Phone=+91987654321]
    	return "[Name: " + this.getName() + ", Phone=" + this.num + "]"; 
    }

}
