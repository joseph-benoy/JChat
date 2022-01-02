import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.time.LocalTime;
class ConnectionHandler{
    String addr,username;
    int port;
    DataInputStream din;
    DataOutputStream dout;
    Main ui;
    Socket soc;
    ConnectionHandler(Main u,String addr,int port,String username){
        this.addr = addr;
        this.port = port;
        this.username = username;
        this.ui = u;
    }
    public void start(){
        try{
            soc = new Socket(addr,port);
            din = new DataInputStream(soc.getInputStream());
            dout = new DataOutputStream(soc.getOutputStream());
            dout.writeUTF(this.username);
            dout.flush();
            while(Boolean.parseBoolean("true")){
                try{
                    String message = din.readUTF();
                    this.ui.addMessage(message);
                }
                catch(Exception err){
                    System.out.println(err.getLocalizedMessage());
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    public void stop()throws IOException{
        this.soc.close();
    }
    public void sendMessage(String message){
        try{
            this.ui.addOwnMessage(message);
            message = this.username+": "+message;
            dout.writeUTF(message);
            dout.flush();
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
public class Main extends JFrame{
    String username,addr,portNo;
    ConnectionHandler connection;
    JList<String> chatBox;
    JTextField messageBox ;
    JScrollPane scroll;
    JTextArea chats;
    public HashMap<String,String>  getUserData(){
        HashMap<String,String> data = new HashMap<>();
        data.put("username", this.username);
        data.put("address", this.addr);
        data.put("port", portNo);
        return data;
    }
    Main(){
        JDialog conForm = new JDialog(this,"Connection details",true);
        JLabel fname = new JLabel("Username");
        fname.setBounds(5,0,120,20);
        JLabel ad = new JLabel("Address");
        ad.setBounds(5,60,120,20);
        JLabel p = new JLabel("Port");
        p.setBounds(5,120,120,20);
        JButton startBtn = new JButton("connect");
        startBtn.setBounds(35,180,130,30);
        JLabel conFormError = new JLabel("",SwingConstants.CENTER);
        conFormError.setBounds(5,215,205,20);
        JTextField uname = new JTextField();
        uname.setBounds(5,25,200,30);
        JTextField address = new JTextField("localhost");
        address.setBounds(5,85,200,30);
        JTextField port = new JTextField();
        port.setBounds(5,145,200,30);
        startBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String u = uname.getText();
                String p = port.getText();
                String a = address.getText();
                if(!u.equals("")){
                    if(!p.equals("")){
                        if(!a.equals("")){
                            portNo = p;
                            username = u;
                            addr = a;
                            conForm.setVisible(false);
                        }
                        else{
                            conFormError.setText("Invalid address!");   
                        }
                    }
                    else{
                        conFormError.setText("Invalid port!");
                    }
                }
                else{
                    conFormError.setText("Invalid username!");
                }
            }
        });
        conForm.add(fname);
        conForm.add(uname);
        conForm.add(ad);
        conForm.add(address);
        conForm.add(p);
        conForm.add(port);
        conForm.add(startBtn);
        conForm.add(conFormError);
        conForm.setSize(210,270);
        conForm.setLayout(null);
        conForm.setVisible(true);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try{
                    connection.stop();
                    System.exit(0);
                }
                catch(Exception er){
                    System.out.println(er.getLocalizedMessage());
                }
            }
        });



        chats = new JTextArea();
        chats.setBounds(5,5,390,200);
        chats.setLineWrap(true);
        chats.setWrapStyleWord(true);
        chats.setEditable(false);
        chats.setCaretPosition(chats.getDocument().getLength());        


        messageBox = new JTextField();
        messageBox.setBounds(5,630,320,50);
        JButton sendBtn = new JButton("send");
        sendBtn.setBounds(325,630,70,50);
        sendBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                connection.sendMessage(messageBox.getText());
            }
        });
        add(chats);
        add(messageBox);
        add(sendBtn);
        scroll = new JScrollPane(chats,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(5, 5, 390, 610);
        this.scroll.getVerticalScrollBar().setValue(this.scroll.getVerticalScrollBar().getMaximum());
        add(scroll);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,getMaximumSize().height);  
        setLayout(null);
        setTitle("JChat");  
        setVisible(true);
        connection = new ConnectionHandler(this,this.addr,Integer.parseInt(this.portNo),this.username);
        connection.start();
    }
    public void addOwnMessage(String message){
        chats.append("You: "+message+"\n");
        this.messageBox.setText("");
        this.scroll.getVerticalScrollBar().setValue(this.scroll.getVerticalScrollBar().getMaximum());
    }
    public void addMessage(String message){
        chats.append(message+"\n");
        this.scroll.getVerticalScrollBar().setValue(this.scroll.getVerticalScrollBar().getMaximum());
    }
    public static void main(String args[]){
        new Main();
    }
}
