package roadrunnergui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

class RoadRunnerGUI implements ActionListener, ItemListener {
	boolean[] selected = new boolean[7];
	GridBagConstraints gbc = new GridBagConstraints();
	JButton jbtnOut, jbtnReset, jbtnRun, jbtnSelect;
	JCheckBox[] jcb;
	JFileChooser jfc;
	JFrame jfrm;
	JLabel jlab;
	JMenu jmFile, jmHelp, jmTool;
	JMenuBar jmb;
	JMenuItem jmiAbout, jmiDoc, jmiExit, jmiGet, jmiOut, jmiReset, jmiRun, jmiSelect;
	JPanel jpnl1, jpnl2, jpnl3, jpnlBot, jpnlTop;
	JScrollPane jsp;
	JTextArea jta;
	String command = "-tools:";

	RoadRunnerGUI() {
		// Frame configurations
		jfrm = new JFrame("RoadRunner Dynamic Analysis Framework - GUI");
		jfrm.setSize(750, 500);
		jfrm.setResizable(false);
		jfrm.getContentPane().setLayout(new GridLayout(2, 1));
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfrm.setLocationRelativeTo(null);
		jfrm.setVisible(true);

		// Other variable declarations
		jcb = new JCheckBox[11];
		jfc = new JFileChooser();
		jfc.setFileFilter(new JavaFileFilter());
		jmb = new JMenuBar();

		// Menubar - File
		jmFile = new JMenu("File");
		jmiSelect = new JMenuItem("Select Java File...", KeyEvent.VK_S);
		jmiSelect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
		jmiRun = new JMenuItem("Run Program", KeyEvent.VK_R);
		jmiRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.ALT_MASK));
		jmiRun.setEnabled(false);
		jmiOut = new JMenuItem("Output File", KeyEvent.VK_O);
		jmiOut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.ALT_MASK));
		jmiOut.setEnabled(false);
		jmiReset = new JMenuItem("Reset GUI", KeyEvent.VK_G);
		jmiReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_MASK));
		jmiExit = new JMenuItem("Exit", KeyEvent.VK_E);
		jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
		jmFile.add(jmiSelect);
		jmFile.add(jmiRun);
		jmFile.add(jmiOut);
		jmFile.addSeparator();
		jmFile.add(jmiReset);
		jmFile.addSeparator();
		jmFile.add(jmiExit);
		jmb.add(jmFile);

		// Menubar - Help
		jmHelp = new JMenu("Help");
		jmiGet = new JMenuItem("Getting Started", KeyEvent.VK_G);
		jmiGet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.ALT_MASK));
		jmiDoc = new JMenuItem("Documentation", KeyEvent.VK_D);
		jmiDoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.ALT_MASK));
		jmiAbout = new JMenuItem("About", KeyEvent.VK_A);
		jmiAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
		jmHelp.add(jmiGet);
		jmHelp.add(jmiDoc);
		jmHelp.add(jmiAbout);
		jmb.add(jmHelp);

		jmiSelect.addActionListener(this);
		jmiRun.addActionListener(this);
		jmiOut.addActionListener(this);
		jmiExit.addActionListener(this);
		jmiReset.addActionListener(this);
		jmiGet.addActionListener(this);
		jmiDoc.addActionListener(this);
		jmiAbout.addActionListener(this);

		// Top panel
		jpnlTop = new JPanel();
		jpnlTop.setLayout(new GridLayout(1, 3));
		jpnlTop.setOpaque(true);

		// Tool grid
		jpnl1 = new JPanel();
		jpnl1.setLayout(new GridLayout(4, 2));
		jpnl1.setBorder(BorderFactory.createTitledBorder("Step 1"));
		jpnl1.setOpaque(true);
		jcb[0] = new JCheckBox("Atomizer");
		jcb[1] = new JCheckBox("BarrierEraser");
		jcb[2] = new JCheckBox("FastTrack");
		jcb[3] = new JCheckBox("HappensBefore");
		jcb[4] = new JCheckBox("LockSet");
		jcb[5] = new JCheckBox("ProtectingLock");
		jcb[6] = new JCheckBox("ReadShared");
		for (int i = 0; i < 7; i++) {
			jcb[i].addItemListener(this);
			jpnl1.add(jcb[i]);
		}

		// Option grid
		jpnl2 = new JPanel();
		jpnl2.setLayout(new GridLayout(2, 2));
		jpnl2.setBorder(BorderFactory.createTitledBorder("Step 2"));
		jpnl2.setOpaque(true);
		jcb[7] = new JCheckBox("Array");
		jcb[8] = new JCheckBox("Classpath");
		jcb[9] = new JCheckBox("MaxTID");
		jcb[10] = new JCheckBox("NoBarrier");
		for (int i = 7; i < 11; i++) {
			jcb[i].addItemListener(this);
			jpnl2.add(jcb[i]);
		}

		// Button grid
		jpnl3 = new JPanel();
		jpnl3.setLayout(new GridBagLayout());
		jpnl3.setBorder(BorderFactory.createTitledBorder("Step 3"));
		jpnl3.setOpaque(true);
		jbtnSelect = new JButton("Select Java File...");
		jbtnSelect.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpnl3.add(jbtnSelect, gbc);
		jlab = new JLabel("No file selected.");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		jpnl3.add(jlab, gbc);
		jbtnRun = new JButton("Run Program");
		jbtnRun.setEnabled(false);
		jbtnRun.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(20, 0, 5, 0);
		jpnl3.add(jbtnRun, gbc);
		jbtnOut = new JButton("Output File");
		jbtnOut.setEnabled(false);
		jbtnOut.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 20, 0);
		jpnl3.add(jbtnOut, gbc);
		jbtnReset = new JButton("Reset GUI");
		jbtnReset.setEnabled(true);
		jbtnReset.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(0, 0, 0, 0);
		jpnl3.add(jbtnReset, gbc);

		// Bottom panel
		jpnlBot = new JPanel();
		jta = new JTextArea();
		jta.setLineWrap(true);
		jta.setWrapStyleWord(true);
		jsp = new JScrollPane(jta);
		jsp.setPreferredSize(new Dimension(720, 210));

		jpnlTop.add(jpnl1);
		jpnlTop.add(jpnl2);
		jpnlTop.add(jpnl3);
		jpnlBot.add(jsp);

		jfrm.setJMenuBar(jmb);
		jfrm.getContentPane().add(jpnlTop);
		jfrm.getContentPane().add(jpnlBot);
	}

	public void actionPerformed(ActionEvent ae) {
		String comStr = ae.getActionCommand();

		if (comStr.equals("Select Java File...")) {
			int result = jfc.showOpenDialog(null);

			if (result == JFileChooser.APPROVE_OPTION) {
				jlab.setText("Selected file is: " + jfc.getSelectedFile().getName());
				jmiRun.setEnabled(true);
				jbtnRun.setEnabled(true);
				jmiOut.setEnabled(true);
				jbtnOut.setEnabled(true);
			} else {
				jlab.setText("No file selected.");
			}
		}

		if (comStr.equals("Run Program")) {
			try {
				getSelected(selected);
				command = appendTools(command, selected);
				Runtime run = Runtime.getRuntime();
				Process pr = run.exec(new String[]{"rrrun", command, jlab.getText()});
				pr.waitFor();
				BufferedReader buf = new BufferedReader(new InputStreamReader(pr.getInputStream()));
				String line = buf.readLine();
				while (line != null) {
					System.out.println(line);
					line = buf.readLine();
				}
			} catch (Exception error) {
				error.printStackTrace();
			}
		}

		if (comStr.equals("Output File")) {
			// Code here
		}

		if (comStr.equals("Exit")) {
			System.exit(0);
		}

		if (comStr.equals("Reset GUI")) {
			for (int i = 0; i < 11; i++) {
				jcb[i].setSelected(false);
			}

			jlab.setText("No file selected.");
			jmiRun.setEnabled(false);
			jbtnRun.setEnabled(false);
			jmiOut.setEnabled(false);
			jbtnOut.setEnabled(false);
			jta.setText("");
		}

		if (comStr.equals("Getting Started")) {
			JOptionPane.showMessageDialog(jfrm, "To use this GUI, simply follow the steps below: "
					+ "\n\n\n"
					+ "Step 1: Choose the desired tools."
					+ "\n\n"
					+ "Step 2: Choose the desired options."
					+ "\n\n"
					+ "Step 3a: Choose which java file to run."
					+ "\n\n"
					+ "Step 3b: Press \"Run Program\" to run the program."
					+ "\n\n"
					+ "Step 4: Press \"Output File\" to show the results in XML format.",
					"How to - RoadRunnerGUI", JOptionPane.INFORMATION_MESSAGE);
		}

		if (comStr.equals("Documentation")) {
			try {
				// Change path to pdf according to laptop.
				Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler C:\\Users\\Danny\\Documents\\NetBeansProjects\\RoadrunnerGUI\\RDAF.pdf");
				p.waitFor();
			} catch (Exception error) {
				error.printStackTrace();
			}
		}

		if (comStr.equals("About")) {
			JOptionPane.showMessageDialog(jfrm, "This GUI was made by:"
					+ "\n\n"
					+ "- Chuc, Jonathan"
					+ "\n"
					+ "- Ho, Duy"
					+ "\n"
					+ "- Jung, Jay"
					+ "\n"
					+ "- Lu, Curtis"
					+ "\n\n"
					+ "© 2014",
					"Credits", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void itemStateChanged(ItemEvent ie) {
		JCheckBox cb = (JCheckBox) ie.getItem();

		if (ie.getStateChange() == ItemEvent.SELECTED) {
			jta.append(cb.getText() + " was selected.\n");
		} else {
			jta.append(cb.getText() + " was unselected.\n");
		}
	}

	public boolean[] getSelected(boolean selected[]) {
		for (int i = 0; i < 7; i++) {
			if (jcb[i].isSelected()) {
				selected[i] = true;
			}
		}
		
		return selected;
	}

	public String appendTools(String command, boolean selected[]) {
		for (int i = 0; i < selected.length; i++) {
			if (selected[i]) {
				if (i == 0) { command += "A:";
				} else if (i == 1) { command += "BE:";
				} else if (i == 2) { command += "FT:";
				} else if (i == 3) { command += "HB:";
				} else if (i == 4) { command += "LS:";
				} else if (i == 5) { command += "PL:";
				} else if (i == 6) { command += "RS:";
				}
			}
		}
		
		command = command.substring(0, command.length() - 1);
		return command;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RoadRunnerGUI();
			}
		});
	}
}

class JavaFileFilter extends FileFilter {

	public boolean accept(File file) {
		if (file.getName().endsWith(".java")) {
			return true;
		}

		if (file.isDirectory()) {
			return true;
		}

		return false;
	}

	public String getDescription() {
		return "Java Files (*.java)";
	}
}
