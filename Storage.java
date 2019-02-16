public class Storage {

	private int top;
	private int content[];

	public Storage(){
		this.top = 0;
		this.content = new int[10];	//Fixed buffer size that can store up to 10 integers

	}

	public synchronized boolean isFull(){  //Check if it's full
		return top==content.length;
	}

	public synchronized boolean isEmpty(){	//Check if it's empty
		return top==0;
	}

	public synchronized int size(){
		return top;

	}

	public synchronized void push(int x){
		while(isFull()){
			try {

				wait(); //push but full
			}
			catch(InterruptedException ie){
				ie.printStackTrace();

			}
		}
		content[top] = x;
		top++;
		notifyAll(); //for threads that are calling pop but empty
	}

	public synchronized int pop(){
		while(isEmpty()){
			try {
				wait(); //pop, but empty
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}

		int temp = content[--top];
		notifyAll();	//for threads that are calling push but full

		return temp;

	}
/*
	public static void main(String args[]){
		Storage s = new Storage();
		System.out.println(s.isEmpty());
		s.push(3);
		s.push(4);
		System.out.println(s.pop());
		System.out.println(s.pop());

	}
*/

}