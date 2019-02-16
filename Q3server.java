import java.net.*;
import java.io.*;

class ClientHandler extends Thread{

	private Socket s;
	private Storage storage;

	public ClientHandler(Socket s, Storage storage){
		this.s = s;
		this.storage = storage;
	}

	public void run(){
		DataInputStream dis = null;
		DataOutputStream dos = null;

		try {
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());

			int type = dis.readInt();
			//For when client sends first integer of 0
			if(type==0){
				if(storage.size()>=2){	//Where there are 2 or more integers
					int op1 = storage.pop();
					int op2 = storage.pop();

					int result = op1+op2;
					dos.writeInt(result);
				}
				else {
					dos.writeInt(-9999);	//Return -9999 where there are less than 2 integers with buffer content unchanged

				}
			}
			else if(type==1){	//For when client sends first integer of 1

				int data = dis.readInt();
				if(!storage.isFull()){
					storage.push(data);
				}
			}
			else{
				System.out.println("0 or 1");
			}
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		finally{	//Close connection
			try{
				if(dis!=null){
					dis.close();
				}
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
			try{
				if(dos!=null){
					dos.close();
				}
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
		}

	}
}

public class Q3server {

	public static void main (String args[]){

		Storage storage = new Storage();

		try {
			ServerSocket ss = new ServerSocket(12345);	//Listens to request at port 12345

			while(true){
				Socket s = ss.accept();	//Serves new request
				ClientHandler ch = new ClientHandler(s,storage); //Pass thread to client handler
				ch.start();
			}
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

}