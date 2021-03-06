package tools.widgets;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Dialog to create an instruction
 * @author David Lecoconnier david.lecoconnier@gmail.com
 * @version 1.0
 */
public class InstructionCreator extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField name;
	private JTextField request;
	private boolean sendData;

	/**
	 * Constructor
	 * @param parent the parent
	 * @param title a title
	 * @param modal true if it is modal
	 */
	public InstructionCreator(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(300, 160);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.initPanel();
	}
	
	private void initPanel() {
		JPanel grid = new JPanel(new GridLayout(2, 2));

		this.name = new JTextField();
		this.request = new JTextField();
		
		grid.add(new JLabel("Name"));
		grid.add(this.name);
		grid.add(new JLabel("Request"));
		grid.add(this.request);
		
		JPanel control = new JPanel();
		JButton okButton = new JButton("OK");
		JButton cancelButton = new JButton("Cancel");
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendData = !name.getText().isEmpty();
				setVisible(false);
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		control.add(okButton);
		control.add(cancelButton);
		this.add(grid, BorderLayout.CENTER);
		this.add(control, BorderLayout.SOUTH);
	}

	/**
	 * Show the dialog
	 * @return true if the user clicks on OK
	 */
	public boolean showDialog() {
		this.sendData = false;
		this.setVisible(true);
		return this.sendData;
	}
	
	/**
	 * Returns the name of the instruction
	 * @return the name
	 */
	public String getName() {
		return this.name.getText();
	}
	
	/**
	 * Returns the request value
	 * @return a request value
	 */
	public String getRequest() {
		return this.request.getText();
	}
	
	/**
	 * Modifies both name and request to update them
	 * @param name a name
	 * @param request a request
	 */
	public void setData(String name, String request) {
		this.name.setText(name);
		this.request.setText(request);
	}
}
