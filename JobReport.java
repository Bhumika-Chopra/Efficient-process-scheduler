package ProjectManagement;

public class JobReport implements JobReport_ {
	String user;
	String pname;
	int bud;
	int atime;
	int ctime;
	public JobReport(String user, String pname, int budget, int arrival_time, int completion_time) {
		this.user = user;
		this.pname = pname;
		this.bud = budget;
		this.atime = arrival_time;
		this.ctime = completion_time;
	}
	@Override
	public String user() {
		return this.user;
	}
	@Override
	public String project_name() {
		return this.pname;
	}
	@Override
	public int budget() {
		return this.bud;
	}
	@Override
	public int arrival_time() {
		return this.atime;
	}
	@Override
	public int completion_time() {
		return this.ctime;
	}
//	public static void main(String[] args) {
//		JobReport j = new JobReport();
//		j.user();
//	}
}

