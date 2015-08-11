package com.icss.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.security.MessageDigest;

import com.icss.dao.user.UserDao;
import com.icss.dao.user.UserFactory;
import com.icss.mwing.MPanel;
/**
 * ��¼����
 * @author ����Ԫ
 * @version 1.1 2015-01-08
 */
public class LandFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPasswordField passwordField;// �����

	private JComboBox<String> usernameComboBox;// �û��������˵�

	public static void main(String args[]) {
		try {
			LandFrame frame = new LandFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LandFrame() {
		// �������ô��ڵ������Ϣ
		super();// ���ø���Ĺ��췽��
		setTitle(" T �Ƽ�");// ���ô��ڵı���
		setResizable(false);// ���ô��ڲ����Ըı��С
//		setAlwaysOnTop(true);// ���ô���������ǰ��
		setBounds(100, 100, 428, 292);// ���ô��ڵĴ�С
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ���õ��رմ���ʱִ�еĶ���

		// ���潫����һ����������ӵ����ڵ�������
		final MPanel panel = new MPanel(this.getClass().getResource(
				"/img/land_background.jpg"));// ����һ��������
		panel.setLayout(new GridBagLayout());// �������Ĳ��ֹ�����Ϊ�����鲼��
		getContentPane().add(panel, BorderLayout.CENTER);// �������ӵ�������

		final JLabel topLabel = new JLabel();
		topLabel.setPreferredSize(new Dimension(0, 126));
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.gridx = 0;
		gridBagConstraints_5.gridy = 0;
		panel.add(topLabel, gridBagConstraints_5);

		final JLabel leftLabel = new JLabel();
		leftLabel.setPreferredSize(new Dimension(140, 0));
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 0;
		panel.add(leftLabel, gridBagConstraints_3);

		final JLabel rightLabel = new JLabel();
		rightLabel.setPreferredSize(new Dimension(55, 0));
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridy = 1;
		gridBagConstraints_2.gridx = 1;
		panel.add(rightLabel, gridBagConstraints_2);

		// �����������û��������˵�
//		usernameComboBox = new JComboBox();// �����û��������˵��������
//		usernameComboBox.setMaximumRowCount(5);// ���������˵�������ʾ��ѡ����
//		usernameComboBox.addItem("��ѡ��");// Ϊ�����˵������ʾ��
//		usernameComboBox
//				.addActionListener(new UsernameComboBoxActionListener());// Ϊ�����˵�����¼�������
//		final GridBagConstraints gridBagConstraints = new GridBagConstraints();// ���������鲼�ֹ���������
//		gridBagConstraints.anchor = GridBagConstraints.WEST;// ����Ϊ�������ʾ
//		gridBagConstraints.gridy = 1;// ����������Ϊ1
//		gridBagConstraints.gridx = 2;// ����������Ϊ2
//		panel.add(usernameComboBox, gridBagConstraints);// �������ָ���Ĳ��ֹ�������ӵ������

		// ���������������
		passwordField = new JPasswordField();// ����������������
		passwordField.setColumns(20);// ������������ʾ���ַ���
		passwordField.setText("      ");// ���������Ĭ����ʾ6���ո�
		passwordField.addFocusListener(new PasswordFieldFocusListener());// Ϊ�������ӽ��������
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();// ���������鲼�ֹ���������
		gridBagConstraints_1.insets = new Insets(5, 0, 0, 0);// ��������ⲿ�Ϸ��������Ϊ5����
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;// ����Ϊ�������ʾ
		gridBagConstraints_1.gridy = 2;// ����������Ϊ2
		gridBagConstraints_1.gridx = 2;// ����������Ϊ2
		panel.add(passwordField, gridBagConstraints_1);// �������ָ���Ĳ��ֹ�������ӵ������

		// ����������һ���������������ť�����
		final JPanel buttonPanel = new JPanel();// ����һ��������Ӱ�ť�����
		buttonPanel.setOpaque(false);// �������ı���Ϊ͸��
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));// ����������ˮƽ�䲼��
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();// ���������鲼�ֹ���������
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);// ��������ⲿ�Ϸ��������Ϊ10����
		gridBagConstraints_4.gridwidth = 2;// ������ռ����
		gridBagConstraints_4.gridy = 3;// ����������Ϊ3
		gridBagConstraints_4.gridx = 1;// ����������Ϊ1
		panel.add(buttonPanel, gridBagConstraints_4);// �������ָ���Ĳ��ֹ�������ӵ������

		// ����������һ����¼��ť����������ӵ�������Ӱ�ť�������
		final JButton landButton = new JButton();// ������¼��ť�������
		landButton.setMargin(new Insets(0, 0, 0, 0));// ���ð�ť�߿�ͱ�ǩ֮��ļ��
		landButton.setContentAreaFilled(false);// ���ò����ư�ť����������
		landButton.setBorderPainted(false);// ���ò����ư�ť�ı߿�
		URL landUrl = this.getClass().getResource("/img/land_submit.png");// ���Ĭ������µ�¼��ť��ʾͼƬ��URL
		landButton.setIcon(new ImageIcon(landUrl));// ����Ĭ������µ�¼��ť��ʾ��ͼƬ
		URL landOverUrl = this.getClass().getResource(
				"/img/land_submit_over.png");// ��õ���꾭����¼��ťʱ��ʾͼƬ��URL
		landButton.setRolloverIcon(new ImageIcon(landOverUrl));// ���õ���꾭����¼��ťʱ��ʾ��ͼƬ
		URL landPressedUrl = this.getClass().getResource(
				"/img/land_submit_pressed.png");// ��õ���¼��ť������ʱ��ʾͼƬ��URL
		landButton.setPressedIcon(new ImageIcon(landPressedUrl));// ���õ���¼��ť������ʱ��ʾ��ͼƬ
		landButton.addActionListener(new LandButtonActionListener());// Ϊ��¼��ť����¼�������
		buttonPanel.add(landButton);// ����¼��ť��ӵ�������Ӱ�ť�������

		final JButton resetButton = new JButton();//�������ð�ť�齨����
		resetButton.setMargin(new Insets(0, 0, 0, 0));
		resetButton.setContentAreaFilled(false);
		resetButton.setBorderPainted(false);
		URL resetUrl = this.getClass().getResource("/img/land_reset.png");
		resetButton.setIcon(new ImageIcon(resetUrl));
		URL resetOverUrl = this.getClass().getResource(
				"/img/land_reset_over.png");
		resetButton.setRolloverIcon(new ImageIcon(resetOverUrl));
		URL resetPressedUrl = this.getClass().getResource(
				"/img/land_reset_pressed.png");
		resetButton.setPressedIcon(new ImageIcon(resetPressedUrl));
		resetButton.addActionListener(new ResetButtonActionListener());
		buttonPanel.add(resetButton);

		final JButton exitButton = new JButton();//�����˳���ť�齨����
		exitButton.setMargin(new Insets(0, 0, 0, 0));
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		URL exitUrl = this.getClass().getResource("/img/land_exit.png");
		exitButton.setIcon(new ImageIcon(exitUrl));
		URL exitOverUrl = this.getClass()
				.getResource("/img/land_exit_over.png");
		exitButton.setRolloverIcon(new ImageIcon(exitOverUrl));
		URL exitPressedUrl = this.getClass().getResource(
				"/img/land_exit_pressed.png");
		exitButton.setPressedIcon(new ImageIcon(exitPressedUrl));
		exitButton.addActionListener(new ExitButtonActionListener());
		buttonPanel.add(exitButton);
		//------- ��ʼ���û��������˵�
		/////////////////////////////////////
		UserDao userDao = UserFactory.getInitialise();
		Vector<Vector<Object>> users = null;
		Vector<String> users_name = new Vector<String>();
		try {
			users = userDao.queryAllData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i=0; i<users.size(); i++) {
			Vector<Object> row = users.get(i);
			String freeze = row.get(7).toString().trim();
			if (freeze.equals("����")) {
				continue;
			}
			users_name.addElement(users.get(i).get(2).toString().trim());
		}
		usernameComboBox = new JComboBox<String>(users_name);// �����û��������˵��������
		usernameComboBox.setMaximumRowCount(5);// ���������˵�������ʾ��ѡ����
		usernameComboBox.addItem("��ѡ��");// Ϊ�����˵������ʾ��
		usernameComboBox.setSelectedItem("��ѡ��");
		usernameComboBox
				.addActionListener(new UsernameComboBoxActionListener());// Ϊ�����˵�����¼�������
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();// ���������鲼�ֹ���������
		gridBagConstraints.anchor = GridBagConstraints.WEST;// ����Ϊ�������ʾ
		gridBagConstraints.gridy = 1;// ����������Ϊ1
		gridBagConstraints.gridx = 2;// ����������Ϊ2
		panel.add(usernameComboBox, gridBagConstraints);// �������ָ���Ĳ��ֹ�������ӵ������
		/////////////////////////////////////
	}

	class UsernameComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userName = (String) usernameComboBox.getSelectedItem();
			if (userName.equals("TSoft"))
				passwordField.setText("111");
		}
	}

	class PasswordFieldFocusListener implements FocusListener {
		public void focusGained(FocusEvent e) {
			passwordField.setText("");
		}

		public void focusLost(FocusEvent e) {
		}
	}

	class ExitButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	class ResetButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			usernameComboBox.setSelectedIndex(0);
			passwordField.setText("      ");
		}
	}

	class LandButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// ��õ�¼�û�������
			// �鿴�Ƿ�ѡ���˵�¼�û�
			// δѡ���û���������������ʾ��
			String userName = usernameComboBox.getSelectedItem().toString();
			if (userName.equals("��ѡ��")) {
				JOptionPane.showMessageDialog(null,"��ѡ���û���!");
				return;
			}
			// ��õ�¼�û�������
			// �û�δ�����¼���룬����"������ʾ"
			String userPWD = String.valueOf(passwordField.getPassword());
			userPWD = Encode(userPWD);
			if (userPWD.equalsIgnoreCase("      ")) {
				JOptionPane.showMessageDialog(null,"����������!");
				return;
			}
			// ��֤�û��������Ƿ�ƥ��
			// ������󣬸���"������ʾ"
			Vector<Vector<Object>> tableUser = null;
			try {
				UserDao userDao = UserFactory.getInitialise();
				tableUser = userDao.queryUser(userName, userPWD);
				Vector<Object> row = tableUser.get(0);
				if (null != row) {
					// �û���������ȷ����½ϵͳ
					// �������������
					// ����������ɼ�
					// ���õ�¼���ڲ��ɼ�
					TipWizardFrame tipWizard = new TipWizardFrame(row);// �������������
					tipWizard.setVisible(true);// ����������ɼ�
					setVisible(false);// ���õ�¼���ڲ��ɼ�
					return;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				// �ָ���¼�û��͵�¼����
				// �ָ�ѡ�еĵ�¼�û�Ϊ����ѡ����
				// �ָ�������Ĭ��ֵΪ6���ո�
				JOptionPane.showMessageDialog(null,"���������ȷ�Ϻ���������!");
				usernameComboBox.setSelectedItem("��ѡ��");
				passwordField.setText("      ");
			}
		}
	    private String Encode(String origin) {
	        String resultString = null;

	        try {
	            resultString=new String(origin);
	            MessageDigest md = MessageDigest.getInstance("MD5");
	            resultString=ByteArrayToHexString(md.digest(resultString.getBytes()));
	        }
	        catch (Exception ex) {
	        }
	        return resultString;
	    }
	    public String ByteArrayToHexString(byte[] b) {
	        StringBuffer result = new StringBuffer();
	        for (int i=0; i<b.length; i++) {
	            result.append(Character.forDigit((b[i]&240)>>4, 16));
	            result.append(Character.forDigit((b[i]&15), 16));
	        }
	        return result.toString();
	    }

	}
}
