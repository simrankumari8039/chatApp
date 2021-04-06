package chatApp;
import java.net.*;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.JFrame;

import java.io.*;

public class client extends JFrame{
	    Socket socket;
	    BufferedReader br;
		PrintWriter out;
		
		private JLabel heading=new JLabel("CLient Area");
		private JTextArea messageArea=new JTextArea();
		private JTextField messageInput=new JTextField();
		private Font font=new Font("Roboto",Font.PLAIN,20);
		
	 
	public client() {
		try {
			System.out.println("sending request to server...");
			 socket =new Socket("192.168.43.20",7777);
			 System.out.println("connection done.. ");
			 br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out=new PrintWriter(socket.getOutputStream());
				createGUI();
				handleEvents();
			    startReading();
				//startWriting();
				
		}catch(Exception e) {
			
		}
	}
	
	private void createGUI()
	{
		this.setTitle("client Message[END]");
		this.setSize(500,500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		heading.setFont(font);
		messageArea.setFont(font);
		messageInput.setFont(font);
		
		heading.setHorizontalAlignment(SwingConstants.CENTER);
		heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		messageArea.setEditable(false);
		
		this.setLayout(new BorderLayout());
		
		this.add(heading,BorderLayout.NORTH);
		JScrollPane jScrollPane=new JScrollPane(messageArea);
		this.add(jScrollPane,BorderLayout.CENTER);
		this.add(messageInput,BorderLayout.SOUTH);
		
		
		this.setVisible(true);
	}
	
	private void handleEvents() {
		messageInput.addKeyListener(new KeyListener() {
			
			public void keyTyped(KeyEvent e) {
				
			}
           public void keyPressed(KeyEvent e) {
				
			}

           public void keyReleased(KeyEvent e) {
        	   if(e.getKeyCode()==10) {
        		   String contentToSend=messageInput.getText();
        		   messageArea.append("Me  :"+contentToSend+"\n");
        		   out.println(contentToSend);
        		   out.flush();
        		   messageInput.setText("");
        	   }
	
}
		


                 			
		});
	}
	public void startReading() {
		Runnable r1=()->{
			System.out.println("reader started");
			 try {

			while(true) {
		     
				String msg=br.readLine();
				
				  if(msg.equals("exit"))
				     {
					     System.out.println("client terminated the chat");
					     JOptionPane.showMessageDialog(this,"client terminated the chat");
					     messageInput.setEnabled(false);
					     socket.close();
					     break;
				     }
				//System.out.println("client:"+msg);
				  messageArea.append("server : "+msg+"\n");
			
			}
			 }catch(Exception e) {
				//e.printStackTrace();
				 System.out.println("connection closed");
			    }
			
		    };
		    new Thread(r1).start();

		}
		
	
	
	public void startWriting() {
		Runnable r2=()->{
			System.out.println("writer started...");
			try {
			while(!socket.isClosed()) {
		     
				BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
				String content=br1.readLine();
				out.println(content);
				out.flush();
				 
			}
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("connection closed");
			    }
			
		    };
		    new Thread(r2).start();
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("client");
   new client();
	}

}
