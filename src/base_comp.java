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
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

class tran_process extends JFrame
{
	JLabel board;
	public tran_process()
	{
		this.setSize(400,300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		board=new JLabel();
		board.setBounds(0, 0, 200, 100);
		this.getContentPane().add(board);
	}

}

public class base_comp extends JFrame{
	static JEditorPane jep;    		//结果显示框
	JTextField base;			//行数
	JLabel b,j,note;			//提示标签
	JButton calculate,file_tran;			//运行按钮
	JScrollPane jsp;
	OutputStream os;
	InputStream is;
	Socket so;
	DataOutputStream dos;
	DataInputStream dis;
	JFileChooser ff;
	ServerSocket ss;
	tran_process tp;
	String ip="127.0.0.1";
	reciever rr;
	public base_comp()
	{
		this.setSize(400, 400);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tp=new tran_process();
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		}catch(Exception e){e.printStackTrace();}
	    
		ff=new JFileChooser();		
	}
	public  void file_receive(String path){
		System.out.println("receive");
		try{
			int content,per=0;			
			String len="";
			byte[] line=new byte[256];
		dis=new DataInputStream(so.getInputStream());
		String filename=dis.readUTF();
		File f=new File(path);
		
		dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
		len = Long.toString(dis.readLong());
		tp.setVisible(true);
		
		while((content=dis.read(line))!=-1)
		{
			dos.write(line,0,content);
			per+=content;	
			tp.board.setText(filename+" transporting:"+per+"/"+len);			
		}
		dos.flush();
		dos.close();
		
		JOptionPane.showMessageDialog(null, " the file has been transported");
		tp.setVisible(false);
		so=ss.accept();
		os=so.getOutputStream();
		is=so.getInputStream();
		rr.setIs(is);
		rr.setOs(os);
		os.write("f_end".getBytes());
		}catch(IOException e){e.printStackTrace();}	
	}
	
	public void file_transport(String path){
		
		int per=0;
		try{
			int content;
			byte[] line=new byte[256];
		File f=new File(path);
		dis = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
		dos=new DataOutputStream(so.getOutputStream());
		
		dos.writeUTF(f.getName());
		dos.flush();
		dos.writeLong((long)f.length());
		tp.setVisible(true);
		while((content=dis.read(line))!=-1)
		{
			dos.write(line,0,content);
			per+=content;
			tp.board.setText(f.getName()+" transporting:"+per+"/"+f.length());	
		}
		dos.flush();
		dis.close();
		dos.close();
		JOptionPane.showMessageDialog(null, " the file has been transported");
		tp.setVisible(false);
		so=new Socket(ip,1234);
		os=so.getOutputStream();
		is=so.getInputStream();
		rr.setIs(is);
		rr.setOs(os);
		os.write("f_end".getBytes());
		}catch(IOException e){e.printStackTrace();}
	}

}
