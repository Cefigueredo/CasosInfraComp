import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ActualizadorTPyMP extends Thread{

	private Queue<Integer> ref = new ArrayDeque<Integer>();
	private Queue<String> tipoRef = new ArrayDeque<String>();
	
	public void run() {
		
		
		
		
		try {
			sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void setRef(int ref, String tipoRef) {
		this.ref.add(ref);
		this.tipoRef.add(tipoRef);
	}
}
