import java.awt.FileDialog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class reciever extends Thread
{
	OutputStream os;
	InputStream is;
	base_comp target;
	
	
	public void setOs(OutputStream os) {
		this.os = os;
	}
	
	public void setIs(InputStream is) {
		this.is = is;
	}
	public reciever(OutputStream os,InputStream is,base_comp target)
	{
		this.os=os;
		this.is=is;
		this.target=target;
	}
	public void run()
	{
		try {
			byte[] buf = new byte[256];
	    	is.read(buf);
	    	String str = new String(buf);
	    	
	    	while (true) {
			   
	    		is.read(buf);
	    		str = new String(buf);
			    
			    if(str.indexOf("f_t")!=-1)
			    	{
			    	System.out.println("here");
			    	String filename=str.substring(4,str.indexOf(">"));
			    	int fb=JOptionPane.showConfirmDialog(null, "stranger shares "+filename+" with you");
			    	
			    	if(fb==JOptionPane.YES_OPTION)
			    	{
			    		os.write("f_ok".getBytes());
			    		target.ff.setSelectedFile(new File(filename));
			    		target.ff.showOpenDialog(null);
			    		
			    		if(target.ff.getSelectedFile()!=null)
			    		{
			    			
			    			target.file_receive(target.ff.getSelectedFile().toString());
			    		}
			    		
			    		continue;
			    	}
			    	else 
			    		{
			    		os.write("f_no".getBytes());		
			    		continue;
			    		}
			    	}
			    	if(str.indexOf("f_ok")!=-1)
			    	{
			    	
			    	target.file_transport(target.ff.getSelectedFile().toString());
			    	continue;
			    	}
			    	if(str.indexOf("f_end")!=-1)
			    	{
			    		
			    		continue;
			    	}
			    	if(str.indexOf("c_end")!=-1)
			    	{
			    		os.close();
			    		is.close();
			    		break;
			    	}
			    	
			    target.jep.setText(target.jep.getText() + str.trim()+"\n");
			    

			   }
			   } catch (IOException e) {
			  }
	}

	

}