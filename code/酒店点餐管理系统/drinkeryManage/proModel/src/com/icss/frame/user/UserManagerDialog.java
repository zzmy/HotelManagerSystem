package com.icss.frame.user;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.MessageDigest;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.icss.bean.UserBean;
import com.icss.dao.user.UserDao;
import com.icss.dao.user.UserFactory;
import com.icss.mwing.MTable;
import com.icss.tool.Validate;
/**
 * �û�������
 * @author ����Ԫ
 * @version 1.1 2015-01-08
 */
public class UserManagerDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MTable table;

	private Vector<Object> tableColumnV;

	private Vector<Vector<Object>> tableValueV;

	private DefaultTableModel tableModel;

	private ButtonGroup buttonGroup = new ButtonGroup();

	private ButtonGroup buttonGroup2 = new ButtonGroup();
	
	private JPasswordField passwordTextField;

	private JTextField idCardTextField;

	private JTextField birthdayTextField;

	private JTextField nameTextField;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		 try {
		 UserManagerDialog dialog = new UserManagerDialog();
		 dialog.addWindowListener(new WindowAdapter() {
		 public void windowClosing(WindowEvent e) {
		 System.exit(0);
		 }
		 });
		 dialog.setVisible(true);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	}

	/**
	 * Create the dialog
	 */
	public UserManagerDialog() {
		super();
		setModal(true);
		setTitle("�û�����");
		setBounds(100, 100, 800, 375);

		final JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridBagLayout());
		getContentPane().add(inputPanel, BorderLayout.NORTH);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("��    ����");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.gridx = 0;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.insets = new Insets(10, 0, 5, 5);
		inputPanel.add(nameLabel, gridBagConstraints_13);

		nameTextField = new JTextField();
		nameTextField.setColumns(12);
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 5);
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 1;
		inputPanel.add(nameTextField, gridBagConstraints);

		final JLabel sexLabel = new JLabel();
		sexLabel.setText("�Ա�");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(10, 15, 5, 5);
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 2;
		inputPanel.add(sexLabel, gridBagConstraints_1);

		final JRadioButton manRadioButton = new JRadioButton();
		buttonGroup.add(manRadioButton);
		manRadioButton.setText("��");
		manRadioButton.setSelected(true);
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 0, 5, 5);
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 3;
		inputPanel.add(manRadioButton, gridBagConstraints_2);

		final JRadioButton womanRadioButton = new JRadioButton();
		buttonGroup.add(womanRadioButton);
		womanRadioButton.setText("Ů");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(10, 0, 5, 5);
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 4;
		inputPanel.add(womanRadioButton, gridBagConstraints_3);

		final JLabel birthdayLabel = new JLabel();
		birthdayLabel.setText("�������ڣ�");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 15, 5, 5);
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 5;
		inputPanel.add(birthdayLabel, gridBagConstraints_4);

		birthdayTextField = new JTextField();
		birthdayTextField.setColumns(20);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 0, 5, 0);
		gridBagConstraints_5.gridy = 0;
		gridBagConstraints_5.gridx = 6;
		inputPanel.add(birthdayTextField, gridBagConstraints_5);

		final JLabel passwordLabel = new JLabel();
		passwordLabel.setText("��¼���룺");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(5, 0, 5, 5);
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 0;
		inputPanel.add(passwordLabel, gridBagConstraints_8);

		passwordTextField = new JPasswordField();
		passwordTextField.setColumns(33);
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.gridwidth = 4;
		gridBagConstraints_9.anchor = GridBagConstraints.WEST;
		gridBagConstraints_9.insets = new Insets(5, 0, 5, 5);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 1;
		inputPanel.add(passwordTextField, gridBagConstraints_9);

		final JLabel idCardLabel = new JLabel();
		idCardLabel.setText("���֤�ţ�");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(5, 16, 5, 5);
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.gridx = 5;
		inputPanel.add(idCardLabel, gridBagConstraints_6);

		idCardTextField = new JTextField();
		idCardTextField.setColumns(20);
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(5, 0, 5, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 6;
		inputPanel.add(idCardTextField, gridBagConstraints_7);
		
		final JRadioButton admRadioButton = new JRadioButton("\u7BA1\u7406\u5458");
		buttonGroup2.add(admRadioButton);
		GridBagConstraints gbc_admRadioButton = new GridBagConstraints();
		gbc_admRadioButton.insets = new Insets(0, 0, 0, 5);
		gbc_admRadioButton.gridx = 0;
		gbc_admRadioButton.gridy = 2;
		inputPanel.add(admRadioButton, gbc_admRadioButton);
		
		final JRadioButton accountantRadioButton = new JRadioButton("\u4F1A\u8BA1\u4EBA\u5458");
		buttonGroup2.add(accountantRadioButton);
		GridBagConstraints gbc_accountantButton = new GridBagConstraints();
		gbc_accountantButton.insets = new Insets(0, 0, 0, 5);
		gbc_accountantButton.gridx = 1;
		gbc_accountantButton.gridy = 2;
		inputPanel.add(accountantRadioButton, gbc_accountantButton);
		
		final JRadioButton serverRadioButton = new JRadioButton("\u670D\u52A1\u4EBA\u5458");
		buttonGroup2.add(serverRadioButton);
		serverRadioButton.setSelected(true);
		GridBagConstraints gbc_serverRadioButton = new GridBagConstraints();
		gbc_serverRadioButton.insets = new Insets(0, 0, 0, 5);
		gbc_serverRadioButton.gridx = 2;
		gbc_serverRadioButton.gridy = 2;
		inputPanel.add(serverRadioButton, gbc_serverRadioButton);

		final JPanel buttonPanel = new JPanel();
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.anchor = GridBagConstraints.EAST;
		gridBagConstraints_10.insets = new Insets(5, 0, 10, 0);
		gridBagConstraints_10.gridwidth = 2;
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 5;
		inputPanel.add(buttonPanel, gridBagConstraints_10);

		//������û�
		final JButton subButton = new JButton();
		subButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				// ��֤�������ֻ��Ϊ 4 �����֣�
				String name = nameTextField.getText().trim();
				if (name.length() > 4) {
					JOptionPane.showMessageDialog(null, "�������ֻ��Ϊ 4 ������!");
					return;
				}
				// ��֤���������Ƿ��������, "��ȷ��ʽΪ��2008-8-8"��
				String birthday = birthdayTextField.getText().trim();
				if (! Validate.execute("\\d{4}-\\d{2}-\\d{2}", birthday)) {
					JOptionPane.showMessageDialog(null, "���������������!\n��ȷ��ʽΪ2008-03-30");
					return;
				}
				// ��֤���֤���Ƿ��������", "���֤�ű���Ϊ15λ��18λ��"
				String idCard = idCardTextField.getText().trim();
				if (! Validate.execute("\\d{15}|\\d{18}", idCard)) {
					JOptionPane.showMessageDialog(null, "���֤���������!\n���֤�ű���Ϊ15��18λ");
					return;
				}
				// ��֤��������ܳ��� 20 ���ַ���
				String password = passwordTextField.getText().toString();
				if (20<password.length() || 6>password.length()) {
					JOptionPane.showMessageDialog(null, "���벻ӦС��6���ַ���20���ַ�!");
					return;
				}
				password = Encode(password);
				String sex = (manRadioButton.isSelected()? "��":"Ů");
				//Ȩ�޵ȼ�
				int powerlevel;
				if (admRadioButton.isSelected()) {
					powerlevel = 2;
				}
				else if (accountantRadioButton.isSelected()) {
					powerlevel = 4;
				}
				else {
					powerlevel = 6;
				}
				
				// ����û��ύ����
				// �����ݿ����������
				UserBean userBean = new UserBean();
				userBean.setName(name);
				userBean.setSex(sex);
				userBean.setBirthday(birthday);
				userBean.setId_card(idCard);
				userBean.setPassword(password);
				userBean.setPowerlevel(powerlevel);
				UserDao userDao = UserFactory.getInitialise();
				try {
					userDao.addUser(userBean);
					// ���±������
					Vector<Object> row = new Vector<Object>();
					row.add(tableValueV.size() + 1);
					row.add(name);
					row.add(sex);
					row.add(birthday);
					row.add(idCard);
					tableValueV.add(row);
					tableModel.setDataVector(tableValueV, tableColumnV);
					table.setRowSelectionInterval(tableValueV.size()-1);//���ý���
					JOptionPane.showMessageDialog(null, "����û��ɹ�!");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "����û�ʧ�ܣ��볢�Ը����û���!");
						e1.printStackTrace();
						return;
					}
					// ���
					nameTextField.setText("");
					manRadioButton.setSelected(true);
					birthdayTextField.setText("");
					idCardTextField.setText("");
					passwordTextField.setText("");
			}
		});
		subButton.setText("���");
		buttonPanel.add(subButton);

		// ɾ���û���Ϣ
		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���Ҫɾ���û�����
				int selectRowNum = table.getSelectedRow();
				if (-1 == selectRowNum) {
					return;
				}
				String name = table.getValueAt(selectRowNum, 1).toString();
				String idStr = table.getValueAt(selectRowNum, 0).toString();
				int id = Integer.parseInt(idStr);
				// ��ʾ "ȷ��Ҫɾ���û�"
				int result = JOptionPane.showConfirmDialog(
						null, 
						"ȷʵҪɾ���û�"+name+"?", 
						"", 
						2);
				if (result == 2) {
					return;
				}
				// �����ݿ��������û�״̬Ϊ "����"
				UserBean userBean = new UserBean();
				userBean.setId(id);
				try {
					UserFactory.getInitialise().deleteUser(userBean);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// �޸ı������
				tableValueV.remove(selectRowNum);
				tableModel.setDataVector(tableValueV, tableColumnV);
				table.setRowSelectionInterval(tableValueV.size()-1);//���ý���
				JOptionPane.showMessageDialog(null, "ɾ���û��ɹ�!");
			}
		});
		delButton.setText("ɾ��");
		buttonPanel.add(delButton);

		final JButton exitButton = new JButton();
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setText("�˳�");
		buttonPanel.add(exitButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		tableColumnV = new Vector<Object>();
		String tableColumns[] = { "��    ��", "��    ��", "��    ��", "��������", "���֤��" };
		for (int column = 0; column < tableColumns.length; column++) {
			tableColumnV.add(tableColumns[column]);
		}

		//------��ʾȫ������
		try {
			Vector<Vector<Object>> tmpTable = UserFactory.getInitialise().queryAllData();
			tableValueV = new Vector<Vector<Object>>();
			for (int i=0; i<tmpTable.size(); i++) {
				Vector<Object> rowOrigin = tmpTable.get(i);
				String freeze = rowOrigin.get(7).toString().trim();
				if (freeze.equals("����")) {
					continue;
				}
				Vector<Object> rowSelect = new Vector<Object>();
				rowSelect.add(rowOrigin.get(1));
				rowSelect.add(rowOrigin.get(2).toString().trim());
				rowSelect.add(rowOrigin.get(3).toString().trim());
				rowSelect.add(rowOrigin.get(4).toString().substring(0, 10));
				rowSelect.add(rowOrigin.get(5).toString().trim());
				tableValueV.add(rowSelect);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		tableModel = new DefaultTableModel(tableValueV, tableColumnV);
		table = new MTable(tableModel);
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0);
		scrollPane.setViewportView(table);
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
