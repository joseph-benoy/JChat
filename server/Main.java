import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Main extends JFrame{
    Main(){
        JDialog portForm = new JDialog(this,"Server details",true);
        JLabel pLabel = new JLabel("Port address");
        JTextField port = new JTextField();
        JLabel limitLabel = new JLabel("Clients limit");
        JTextField limit = new JTextField();
        JButton startBtn = new JButton("start server");
        JLabel portFormError = new JLabel("",SwingConstants.CENTER);
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
        JLabel limitClient = new JLabel("Client limits : "+limit.getText());
        limitClient.setBounds(195,20,190,30);
        JLabel logl = new JLabel("Logs");
        logl.setBounds(20,60,190,30);
        DefaultListModel<String> logListModel = new DefaultListModel<String>();
        JList<String> logList = new JList<String>();
        logList.setModel(logListModel);
        logList.setBounds(20,90,360,50);
        add(portAddr);
        add(limitClient);
        add(logl);
        add(logList);
        logListModel.addElement("this");
        logListModel.addElement("this");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        logListModel.addElement("dddd");
        JScrollPane scroll = new JScrollPane(logList,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(20, 90, 360, 230);
        add(scroll);
        JButton stopBtn = new JButton("shutdown");
        stopBtn.setBounds(100,330,190,30);
        add(stopBtn);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,430);  
        setLayout(null);  
        setVisible(true);  
    }
    public static void main(String[] args) {
        new Main();
    }
}