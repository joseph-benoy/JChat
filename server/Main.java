import javax.swing.*;
public class Main extends JFrame{
    Main(){
        JDialog portForm = new JDialog(this,"Server details",true);
        JLabel pLabel = new JLabel("Port address");
        JTextField port = new JTextField();
        JLabel limitLabel = new JLabel("Clients limit");
        JTextField limit = new JTextField();
        JButton startBtn = new JButton("start server");
        pLabel.setBounds(5,0,120,20);
        limitLabel.setBounds(5,70,120,20);
        port.setBounds(5,30,200,30);
        limit.setBounds(5,100,200,30);
        startBtn.setBounds(30,140,130,30);
        portForm.add(pLabel);
        portForm.add(port);
        portForm.add(limitLabel);
        portForm.add(limit);
        portForm.add(startBtn);
        portForm.setSize(210,210);
        portForm.setLayout(null);
        portForm.setVisible(true);
        JLabel portAddr = new JLabel("Port number : "+port.getText());
        portAddr.setBounds(20,20,190,30);
        JLabel limitClient = new JLabel("Client limits : "+limit.getText());
        limitClient.setBounds(195,20,190,30);
        add(portAddr);
        add(limitClient);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,430);  
        setLayout(null);  
        setVisible(true);  
    }
    public static void main(String[] args) {
        new Main();
    }
}