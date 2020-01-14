package ProjectManagement;

public class User implements Comparable<User> {
	
	String name;
	public User(String name) {
		this.name = name;
	}
	public String getname() {
		return this.name;
	}
    @Override
    public int compareTo(User user) {
        return (this.name.compareTo(user.getname()));
    }
}
