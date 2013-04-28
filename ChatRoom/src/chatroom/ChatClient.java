/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatroom;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 *
 * @author abhijit
 */
class Client extends ChatClient
	{
	JTextArea ta;
	Socket cliet;
	InputStreamReader ir;
	BufferedReader reader;
	PrintWriter writer;
	Thread t2;
	int i;
	Client[] c;
	int myI;
	public void setDb(Client c[],int i)
	{
	this.c=c;
	this.i=i;
	}
	public Client(Socket s, JTextArea tas, int i)
	{
	try{
	myI=i;
	cliet=s;
	ta=tas;
	ir = new InputStreamReader(cliet.getInputStream());
        reader = new BufferedReader(ir);
        writer = new PrintWriter(cliet.getOutputStream());
	t2 = new Thread(this);
	t2.start();
	}
	catch(Exception ex){}
        }

	public void run()
	{
	String msg;
        try{
        while((msg=reader.readLine())!=null)
        {
	ta.append(msg + "\n");
	for(int j=0; j<i; j++)
	{
	if(j!=myI)
	{
	c[j].writer.println(msg);
        c[j].writer.flush();
	}
	}
	
	}
	for(int j=0; j<i; j++)
	{
	c[j].writer.println("Server : User left at IP "+cliet.getLocalAddress());
        c[j].writer.flush();
	}
	
	}
        catch(Exception ex){}
	}
	}
	
   	public class ChatClient extends JFrame implements Runnable, KeyListener, ActionListener, WindowListener{
	
	public void windowOpened(WindowEvent we){}
	public void windowClosing(WindowEvent we)
	{System.exit(0);}	
	public void windowClosed(WindowEvent we){}
	public void windowIconified(WindowEvent we){}
	public void windowDeiconified(WindowEvent we){}
	public void windowActivated(WindowEvent we){}
	public void windowDeactivated(WindowEvent we){}	

    	public ChatClient() {
       	initComponents();
    	}
    
    	private void initComponents() {
	setSize(400,400);
	setResizable(false);
        Panel1 = new JPanel();
	TextArea1 = new JTextArea();
	JScrollPane jscroll = new JScrollPane(TextArea1,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	jscroll.setPreferredSize(new Dimension(375,250));
	Panel2 = new JPanel();
        Label1 = new JLabel();
        TextField1 = new JTextField(30);
        Label2 = new JLabel();
        Label3 = new JLabel();
        Button1 = new JButton("Connect");
        addWindowListener(this);
	Panel1.add(jscroll);
        TextArea1.setColumns(20);
        TextArea1.setEditable(false);
        TextArea1.setRows(5);
        Label4 = new JLabel("User Name");
	TextField2 = new JTextField(10);

        Label1.setText("Chat");
        TextField1.addKeyListener(this);

        Panel2.add(Label1);
	Panel2.add(TextField1);

        Label2.setText("IP : ");

        Label3.setText("SERVER : ");
	Button1.addActionListener(this);
        
    
	add(Panel1);
	add(Panel2);
	add(Button1);
	CheckBox1 = new Checkbox("Client");
	add(CheckBox1);
	add(Label4);
	add(TextField2);
	setLayout(new FlowLayout(FlowLayout.LEFT));
	}

	public void keyPressed(KeyEvent evt)
	{
	if(evt.getSource()==TextField1)
	{	
        if ((evt.getKeyCode())== KeyEvent.VK_ENTER) 
        {
        TextArea1.append("Me : " + TextField1.getText() + "\n");
	for(int j=0;j<i;j++){
        c[j].writer.println( TextField2.getText() + " : "+TextField1.getText());
        c[j].writer.flush();
	}
	TextField1.setText("");
        }      
        }
    	}

	public void keyReleased(KeyEvent evt)
	{}
        
	public void keyTyped(KeyEvent evt)
	{}
         
	public void run()
	{
	try{
	ServerSocket s = new ServerSocket(5200);
	Socket sk;
	TextArea1.append("Server Created at Port "+s.getLocalPort()+" ..\n");
	while(i<=9)
	{
	sk=s.accept();
	c[i] = new Client(sk,TextArea1,i);
	i++;
	TextArea1.append("Server : New User Connected from IP "+sk.getInetAddress()+"\n");
	for(int j=0; j<i ;j++)
	{
	c[j].setDb(c,i);
	c[j].writer.println("Server : New User Connected from IP "+sk.getInetAddress());
	c[j].writer.flush();
	}
	}}catch(Exception ex){}
        }

   

    	public void actionPerformed(ActionEvent evt)
	{
	try{
	if(CheckBox1.getState())
	{
	TextArea1.append("Requested Connection to Server "+TextField1.getText()+" ..\n");
	Socket cl = new Socket(TextField1.getText(),5200);
	TextArea1.append("Request Accepted by Server "+TextField1.getText()+" ..\n");
	c[0]= new Client(cl,TextArea1,11);
	setTitle("Client");
	myI=i;
	i++;
	TextField1.setText("");
	}
	else
	{
	t1 = new Thread(this);
	t1.setName("Server");
	setTitle("Server");
	t1.start();
	}
		
        }
        catch(Exception ex)    
        {}
	}
	
	JLabel Label4;
	JTextArea TextArea2;
        Thread t1;      
	ServerSocket serverSckt;
	Client[] c = new Client[10];
	int i=0;
   	JLabel Label1;
   	JLabel Label2;
   	JLabel Label3;
   	JPanel Panel1;
   	JPanel Panel2;
   	JButton Button1;
   	JTextArea TextArea1;
   	JTextField TextField1;
	JTextField TextField2;
        Checkbox CheckBox1;
	int myI=0;
	public static void main(String args[]) 
	{
        new ChatClient().setVisible(true);
	}
    	}
