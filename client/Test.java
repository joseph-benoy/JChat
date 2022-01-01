import java.io.*;
import java.net.*;
class Test{
    public static void main(String args[]){
        try{
            int port;
            System.out.print("PORT : ");
            port = Integer.parseInt(System.console().readLine());
            Socket s=new Socket("localhost",port);  
            DataInputStream din=new DataInputStream(s.getInputStream());  
            DataOutputStream dout=new DataOutputStream(s.getOutputStream()); 
            dout.writeUTF("Joseph Benoy:-");
            dout.flush();
            dout.close(); 
            s.close();
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}