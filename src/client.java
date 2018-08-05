import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;




public class client extends base_comp {

	/**
	 * @param args
	 */
	
	public client()
	{
		//各变量的初始化
		super();
		this.setTitle(" client ");
		initial();
		rr=new reciever(os,is,this);
		rr.start();
		this.addWindowListener(new WindowAdapter(){
			
				
			public void windowClosing(WindowEvent e){
				try{
					os.write("c_end".getBytes());
					is.close();
				os.close();
				so.close();
				
				}catch(IOException ee){ee.printStackTrace();}
			}
			
			
		});
		note=new JLabel("");
		note.setBounds(90, 5, 240, 10);
		b=new JLabel("columns");
		b.setBounds(20, 40, 80, 40);
		base=new JTextField();
		base.setBounds(90, 40, 240, 40);
		base.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent ee)
			{
				
			}
			public void mouseClicked(MouseEvent ee){}
			public void mouseExited(MouseEvent ee){}
			public void mouseEntered(MouseEvent ee){}
			public void mousePressed(MouseEvent ee){}
		});
		file_tran=new JButton("file tran");
		file_tran.setBounds(180, 100, 60, 30);
		file_tran.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ee)
			{ 
				try{
				ff.showOpenDialog(null);
				os.write(("f_t:"+ff.getSelectedFile().getName()+">").getBytes());
				
				os.flush();
				
				}catch(IOException e){e.printStackTrace();}
			}
			
		});
		calculate=new JButton("send");
		calculate.setBounds(250, 100, 60, 30);
		calculate.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ee)
			{
				 
				try {
					   String str = "stranger:"+base.getText()+"\r\n";
					   byte[] buf = str.getBytes();
					   os.write(buf);
					   os.flush();
					   jep.setText(jep.getText()+ "you:"+base.getText()+"\n");
					  } catch (Exception eee) {
					  }
			}
			
		});
		j=new JLabel("result");
		j.setBounds(20, 150, 80, 40);
		
		
		jep=new JEditorPane();
		
		jep.setText("");
		
		jep.setEditable(false);
		
		jsp=new JScrollPane(jep);
		jsp.setBounds(90, 150, 240, 200);
		
		
		this.getContentPane().add(note);
		this.getContentPane().add(jsp);
		this.getContentPane().add(base);
		this.getContentPane().add(file_tran);
		this.getContentPane().add(j);
		this.getContentPane().add(b);
		
		this.getContentPane().add(calculate);
		
	}

	
	
	
	private void initial()
	{
		try{
			
			InetAddress ia=InetAddress.getByName("127.0.0.1");
			so=new Socket(ia,1234);
			os=so.getOutputStream();
			is=so.getInputStream();
			}catch(IOException e){e.printStackTrace();}

	}
	
	public static void main(String[] args) throws   IOException{
		// TODO Auto-generated method stub
		new client().setVisible(true);
		
		
		
	}

}
