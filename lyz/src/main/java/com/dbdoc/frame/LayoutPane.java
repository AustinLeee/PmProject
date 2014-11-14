package com.dbdoc.frame;

import javax.swing.JDesktopPane;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class LayoutPane extends JDesktopPane {

	private static final long serialVersionUID = 1L;
	private JButton jButton = null;
	private JLabel jLabel = null;
	private JComboBox jComboBox = null;
	private JLabel jLabel1 = null;
	private JLabel jLabel2 = null;
	/**
	 * This is the default constructor
	 */
	public LayoutPane() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel2 = new JLabel();
		jLabel2.setBounds(new Rectangle(32, 165, 90, 18));
		jLabel2.setText("����");
		jLabel1 = new JLabel();
		jLabel1.setBounds(new Rectangle(33, 127, 88, 18));
		jLabel1.setText("�û���");
		jLabel = new JLabel();
		jLabel.setBounds(new Rectangle(36, 34, 88, 18));
		jLabel.setText("���ݿ�����");
		this.setSize(390, 320);

		this.add(getJButton(), null);
		this.add(jLabel, null);
		this.add(getJComboBox(), null);
		this.add(jLabel1, null);
		this.add(jLabel2, null);
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(267, 265, 94, 33));
			jButton.setText("ִ��");
		}
		return jButton;
	}

	/**
	 * This method initializes jComboBox	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox() {
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(136, 30, 211, 27));
		}
		return jComboBox;
	}

}  //  @jve:decl-index=0:visual-constraint="56,19"
