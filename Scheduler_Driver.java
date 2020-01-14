package ProjectManagement;

import Trie.*;
import PriorityQueue.MaxHeap;
import PriorityQueue.PriorityQueueDriverCode;
import RedBlack.RBTree;
import RedBlack.RedBlackNode;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;

public class Scheduler_Driver extends Thread implements SchedulerInterface {
	@SuppressWarnings("rawtypes")
	Trie trie = new Trie();
	Trie mytrie = new Trie();
	ArrayList<JobReport> pjobs = new ArrayList<JobReport>();
	ArrayList<Job> myjobs = new ArrayList<Job>();
	MaxHeap<Job> jobs = new MaxHeap<>();
	ArrayList<User> users = new ArrayList<User>();
	RBTree<String,Job> notready = new RBTree<String, Job>();
	int globaltime = 0;
    public static void main(String[] args) throws IOException {
        Scheduler_Driver scheduler_driver = new Scheduler_Driver();

        File file;
        if (args.length == 0) {
            URL url = PriorityQueueDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        scheduler_driver.execute(file);
    }

    public void execute(File file) throws IOException {

        URL url = Scheduler_Driver.class.getResource("INP");
        file = new File(url.getPath());

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }
        String st;
        while ((st = br.readLine()) != null) {
            String[] cmd = st.split(" ");
            if (cmd.length == 0) {
                System.err.println("Error parsing: " + st);
                return;
            }

            switch (cmd[0]) {
                case "PROJECT":
                    handle_project(cmd);
                    break;
                case "JOB":
                    handle_job(cmd);
                    break;
                case "USER":
                    handle_user(cmd[1]);
                    break;
                case "QUERY":
                    handle_query(cmd[1]);
                    break;
                case "":
                    handle_empty_line();
                    break;
                case "ADD":
                    handle_add(cmd);
                    break;
                default:
                    System.err.println("Unknown command: " + cmd[0]);
            }
        }


        run_to_completion();

        print_stats();

    }




    @Override
    public void run() {
        while(jobs.size() != 0) {
        	schedule();
        	System.out.println("System execution completed");
        }
       //System.out.println("System execution completed");
    }


    @Override
    public void run_to_completion() {
    	run();
    }

    @Override
    public void handle_project(String[] cmd) {
    	int cmd3 = Integer.parseInt(cmd[3]);
    	int cmd2 = Integer.parseInt(cmd[2]);
    	Project p = new Project(cmd[1], cmd3, cmd2);
    	System.out.println("Creating project");
    	trie.insert(p.getname(), p);
    }

    @Override
    public void handle_job(String[] cmd) {
    	System.out.println("Creating job");
    	boolean found = false;
    	@SuppressWarnings("unchecked")
		TrieNode<Project> search = trie.search(cmd[2]);
    	for(int i=0; i<users.size(); i++) {
    		if(cmd[3].compareTo(users.get(i).name) == 0) {
    			found = true;
    			break;
    		}
    	}
    	if(search == null) 
    		System.out.println("No such project exists. " + cmd[2]);
    	else if(!found) 
    		System.out.println("No such user exists: " + cmd[3]);
    	else {
	    	int cmd4 = Integer.parseInt(cmd[4]);
	    	Job j = new Job(cmd[1], cmd[2], cmd[3], cmd4);
	    	j.setpriority(search.getValue().priority);
	    	myjobs.add(j);
	    	jobs.insert(j);
    	}
    }

    @Override
    public void handle_user(String name) {
    	User user = new User(name);
    	System.out.println("Creating user");
    	users.add(user);
    	
    }

    @SuppressWarnings("unlikely-arg-type")
	@Override
    public void handle_query(String key) {
    	System.out.println("Querying");
    	int size = myjobs.size();
    	Job j = null;
		for(int i=0; i<size; i++) {
    		if(myjobs.get(i).equals(key))
    			j = myjobs.get(i);
    	}
    	if(j != null) {
    		if(j.getStatus() == 0)
    			System.out.println(key + ": NOT FINISHED");
    		else
    			System.out.println(key + ": COMPLETED");
    	}
    	else
    		System.out.println(key + ": NO SUCH JOB");
    }

    @Override
    public void handle_empty_line() {
//    	System.out.println("Running code");
//    	System.out.println("Remaining jobs: " + jobs.size());
    	schedule();
    	System.out.println("Execution cycle completed");
    }

    @Override
    public void handle_add(String[] cmd) {
    	System.out.println("ADDING Budget");
    	@SuppressWarnings("unchecked")
		TrieNode<Project> search = trie.search(cmd[1]);
    	//if project does not exist in the trie
    	if(search != null) {
    		int addvalue = Integer.parseInt(cmd[2]);
    		int budget = search.getValue().budget + addvalue;
    		Project p = new Project(cmd[1], budget, search.getValue().priority);
    		trie.delete(cmd[1]);
    		trie.insert(cmd[1], p);
    		RedBlackNode<String, Job> node = notready.search(cmd[1]); 
    		LinkedList<Job> list = (LinkedList<Job>) node.getValues();
    		if (list != null) {
    			int size = list.size();
                for(int i=0; i<size; i++) {
                	jobs.insert(list.remove(0));
                }
    		}
    		//node.emptylist();
    	}
    	else
    		System.out.println("No such project exists. " + cmd[1]);
    	
    }

    @Override
    public void print_stats() {
    	int index = 0;
    	int index2 = 0;
    	System.out.println("--------------STATS---------------");
    	//bubble sort on the basis of completed time
    	for(int i=0; i<myjobs.size(); i++) {
    		for(int j=0; j<myjobs.size()-i-1; j++) {
    			if(myjobs.get(j).completedtime > myjobs.get(j+1).completedtime) {
    				Job temp = myjobs.get(j);
    				myjobs.set(j, myjobs.get(j+1));
    				myjobs.set(j+1, temp);
    			}
    		}
    	}
    	MaxHeap<Job> sorting = new MaxHeap<Job>();
    	for(int i=0; i<myjobs.size(); i++) {
    		if(myjobs.get(i).completedtime == -1) {
    			sorting.insert(myjobs.get(i));
    		}
    	}
    	for(int i=0; i<myjobs.size(); i++) {
    		if(myjobs.get(i).getStatus() == 1)
    			index2++;
    	}
    	System.out.println("Total jobs done: " + index2);
    	for(int i=0; i<myjobs.size(); i++) {
    		if(myjobs.get(i).getStatus() == 1)
    			System.out.println(myjobs.get(i).toString());
    	}
    	System.out.println("------------------------");
    	System.out.println("Unfinished jobs: ");
    	
    	while(sorting.size() != 0) {
    		System.out.println(sorting.extractMax());
    		index++;
    	}
    	System.out.println("Total unfinished jobs: " + index);
    	System.out.println("--------------STATS DONE---------------");
    }

    @SuppressWarnings("unlikely-arg-type")
	@Override
    public void schedule() {
    	System.out.println("Running code");
    	System.out.println("Remaining jobs: " + jobs.size());
    	Job j = jobs.extractMax();
    	while(j != null) {
    		System.out.println("Executing: " + j.jname + " from: " + j.pname);
    		@SuppressWarnings("unchecked")
			TrieNode<Project> search = trie.search(j.pname);
    		int budget = search.getValue().budget;
    		if(budget >= j.time) {
    			j.setstatus(1);
    			globaltime += j.time;
    			j.completedtime = globaltime;
    			budget -= j.time;
    			trie.delete(search.getValue().pname);
    			Project p = new Project(j.pname, budget, search.getValue().priority);
    			trie.insert(j.pname, p);
    			System.out.println("Project: " + j.pname + " budget remaining: " + budget);
    			//System.out.println("Execution cycle completed");
    			int size = myjobs.size();
    			for(int i=0; i<size; i++) {
    				if(myjobs.get(i).equals(j.jname)) {
    					if(myjobs.get(i).o == j.o)
    						myjobs.set(i,j);
    				}
    			}
    			return;
    		}
    		else {
    			System.out.println("Un-sufficient budget.");
    			notready.insert(j.pname, j);
    			j = jobs.extractMax();
    		}
    	}
    	if(j == null) {
    		return;
    	}
    }
}

