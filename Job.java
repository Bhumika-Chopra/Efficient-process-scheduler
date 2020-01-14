package ProjectManagement;

public class Job implements Comparable<Job> {
	String jname;
	String pname;
	String user;
	int time;
	int completedtime;
	int status;
	int priority;
	int o;
	static int order = 0;
	
	public Job(String jname, String pname, String user, int time) {
		this.jname = jname;
		this.pname = pname;
		this.user = user;
		this.time = time;
		this.status = 0;
		this.completedtime = -1;
		order++;
		o = order;
		
	}
	
	public void setstatus(int status) {
		this.status = status;
	}
	 
	public int getStatus() {
		return this.status;
	}
	
	public String getname() {
		return this.jname;
	}
	
	public void setpriority(int p) {
		priority = p;
	}
	
    @Override
    public boolean equals(Object obj) {
    	if(this.getname().compareTo(obj.toString()) == 0) 
    		return true;
    	else
    		return false;
    }
    
    @Override
    public int hashCode() {
    	return this.o;
    }
	
    public String toString() {
    	//Job{user=’Rob’, project=’IITD.CS.ML.ICML’, jobstatus=COMPLETED, execution_time=10, end_time=10, name=’DeepLearning’
    	if(status == 1)
    		return "Job{user='" + user + "', project='" + pname + "', jobstatus=COMPLETED, execution_time=" + time + ", end_time=" + completedtime + ", name='" + jname + "'}";                               
    	else
    		return "Job{user='" + user + "', project='" + pname + "', jobstatus=REQUESTED, execution_time=" + time + ", end_time=null, name='" + jname + "'}";                              
    }
    
    @Override
    public int compareTo(Job job) {
        if(this.priority == job.priority) 
        	return 0;
        else if(this.priority > job.priority)
        	return 1;
        else
        	return 2;
    }
}