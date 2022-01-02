import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.time.LocalTime;
class ClientReader implements Runnable{
    Server server;
    Socket soc;
    DataInputStream sin;
    int key;
    ClientReader(Server s,Socket soc,int key) throws IOException{
        this.server = s;
        this.soc = soc;
        this.sin = new DataInputStream(soc.getInputStream());
        this.key = key;
    }
    public void run(){
        try{
            String message="";
            while(Boolean.parseBoolean("true")){
                message = this.sin.readUTF();
                synchronized(this.server){
                    this.server.sendMessage(message,this.key);
                }
                System.out.println(message);
            }
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
}
class Server{
    Main ui;
    HashMap<Integer,DataOutputStream>outputs = new HashMap<>();
    ServerSocket server;
    Server(Main u){
        this.ui = u;
    }
    int count=0;
    public void startServer(int port,int limit){
        try{
            server = new ServerSocket(port);
            ExecutorService readerPool = Executors.newFixedThreadPool(limit);
            System.out.println("Server started!");
            while(Boolean.parseBoolean("true")){
                Socket s = server.accept();
                count++;
                ClientReader c = new ClientReader(this,s,count);
                outputs.put(count,new DataOutputStream(s.getOutputStream()));
                String uname = new DataInputStream(s.getInputStream()).readUTF();
                this.ui.addNewConnection(uname);
                this.sendMessage(uname+" joined chat!",888888);
                readerPool.execute(c);
            }
            readerPool.shutdown();
            server.close();
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    public void sendMessage(String message,int key){
        try{
            for(Map.Entry m:outputs.entrySet()){
                DataOutputStream out = (DataOutputStream)m.getValue();
                try{
                    Integer x = key;
                    if(m.getKey()!=x){
                        out.writeUTF(message);
                        out.flush();
                    }
                }
                catch(Exception e){
                }
            }
        }
        catch(Exception e){
            System.out.println(e.getLocalizedMessage());
        }
    }
    public void stopServer() throws IOException{
        this.server.close();
    }
}
public class Main extends JFrame{
    DefaultListModel<String> logListModel = new DefaultListModel<String>();
    public void addNewConnection(String name){
        logListModel.addElement(name+" joined on "+LocalTime.now().getHour()+":"+LocalTime.now().getMinute());
    }
    Main(){
        JDialog portForm = new JDialog(this,"Server details",true);
        JLabel pLabel = new JLabel("Port address");
        JTextField port = new JTextField();
        JLabel limitLabel = new JLabel("Clients limit");
        JTextField limit = new JTextField();
        JButton startBtn = new JButton("start server");
        JLabel portFormError = new JLabel("",SwingConstants.CENTER);
        Server server = new Server(this);
        portFormError.setBounds(30,180,120,20);
        portForm.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        startBtn.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String lim = limit.getText();
                String p = port.getText();
                if(!lim.equals("")){
                    if(!p.equals("")){
                        portForm.setVisible(false);
                    }
                    else{
                        portFormError.setText("Invalid port!");
                    }
                }
                else{
                    portFormError.setText("Invalid limit!");
                }
            }
        });
        pLabel.setBounds(5,0,120,20);
        limitLabel.setBounds(5,70,120,20);
        port.setBounds(5,30,200,30);
        limit.setBounds(5,100,200,30);
        startBtn.setBounds(30,140,130,30);
        portForm.add(portFormError);
        portForm.add(pLabel);
        portForm.add(port);
        portForm.add(limitLabel);
        portForm.add(limit);
        portForm.add(startBtn);
        portForm.setSize(210,230);
        portForm.setLayout(null);
        portForm.setVisible(true);
        JLabel portAddr = new JLabel("Port number : "+port.getText());
        portAddr.setBounds(20,20,190,30);
        JLabel limitClient = new JLabel("Client limit : "+limit.getText());
        limitClient.setBounds(195,20,190,30);
        JLabel logl = new JLabel("Logs");
        logl.setBounds(20,60,190,30);
        JList<String> logList = new JList<String>();
        logList.setModel(this.logListModel);
        logList.setBounds(20,90,360,50);
        add(portAddr);
        add(limitClient);
        add(logl);
        add(logList);
        JScrollPane scroll = new JScrollPane(logList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(20, 90, 360, 230);
        add(scroll);
        JButton stopBtn = new JButton("shutdown");
        stopBtn.setBounds(100,330,190,30);
        add(stopBtn);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                try{
                    server.stopServer();
                    System.exit(0);
                }
                catch(Exception er){
                    System.out.println(er.getLocalizedMessage());
                }
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,430);  
        setLayout(null);  
        setVisible(true);
        server.startServer(Integer.parseInt(port.getText()), Integer.parseInt(limit.getText()));  
    }
    public static void main(String[] args) {
        new Main();
    }
}