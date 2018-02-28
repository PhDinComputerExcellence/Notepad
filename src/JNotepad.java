import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.*;
public class JNotepad {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Notepad();
			}
		});
	}

}

class Notepad implements ActionListener{
	JFrame frame;
	JMenuItem New, open, save, saveas, exit;
	JTextArea text;
	boolean modified = false;
	public Notepad() {
		frame= new JFrame();
		frame.setTitle("Notepad");
		text = new JTextArea();
		JScrollPane textscroll = new JScrollPane(text);
		
		frame.add(textscroll);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
		
		JMenuBar menubar = new JMenuBar();
		JMenu file = new JMenu("File");
		New = new JMenuItem("New");
		New.addActionListener(this);
		open = new JMenuItem("Open");
		open.addActionListener(this);
		save = new JMenuItem("Save");
		save.addActionListener(this);
		saveas = new JMenuItem("Save As");
		saveas.addActionListener(this);
		file.add(New);
		file.add(open);
		file.add(save);
		file.add(saveas);
		file.addSeparator();
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);
		
		JMenu edit = new JMenu("Edit");
		
		
		JMenu format = new JMenu("Format");
		
		
		JMenu view = new JMenu("View");
		
		
		menubar.add(file);
		menubar.add(edit);
		menubar.add(format);
		menubar.add(view);
		
		
		
		frame.setJMenuBar(menubar);
		
		
		
		
		
		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae){
		if (ae.getSource()==New) {
			if (modified) {
				
			}
		} else if (ae.getSource()==exit) {
			frame.dispose();
		} else if(ae.getSource()==open) {
			if (modified) {
				
			} else {
				JFileChooser filechoose = new JFileChooser();
				int value = filechoose.showOpenDialog(null);
				if (value == JFileChooser.APPROVE_OPTION) {
					File file = filechoose.getSelectedFile();
					try {
						fileRead(file);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		} else if(ae.getSource()==saveas) {
			try {
				save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void fileRead(File x) throws IOException {
		text.setText("");
		BufferedReader read= new BufferedReader(new FileReader(x));
		String line = read.readLine();
		while (line!= null) {
			text.append(line);
			line = read.readLine();
			text.append("\n");
		}
		read.close();
		
	}
	
	public void save() throws IOException {
		Boolean exists = true;
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("/home/me/Documents"));
		while (exists) {
			int option = chooser.showSaveDialog(null);
			if (option == JFileChooser.APPROVE_OPTION) {
				String hold;
				File file = chooser.getSelectedFile();
				if (file.exists()) {
					int results = JOptionPane.showConfirmDialog(chooser, "Do you want to overwrite existing file?", "File Exists!",JOptionPane.YES_NO_CANCEL_OPTION);
					switch(results){
	                case JOptionPane.YES_OPTION:
	                    exists = false;
	                    break;
	                case JOptionPane.NO_OPTION:
	                    break;
	                case JOptionPane.CLOSED_OPTION:
	                    break;
	                case JOptionPane.CANCEL_OPTION:
	                    break;
	                    
	            }
				}
				if (!exists) {
					if (filetypecheck(chooser.getSelectedFile().getAbsolutePath())){
						hold = chooser.getSelectedFile().getAbsolutePath();
					} else {
						hold = chooser.getSelectedFile().getAbsolutePath() + ".txt";
					}
					
					FileWriter writer = new FileWriter(hold);
					writer.write(text.getText());
					writer.close();
				} else {
					
				}
				} else {
					exists = false;
				}
		}
	}
	
	public boolean filetypecheck(String x) {
		if (x.substring(x.length()-4, x.length()).equals(".txt")) {
			return true;
		} else {
			return false;
		}
	}
}