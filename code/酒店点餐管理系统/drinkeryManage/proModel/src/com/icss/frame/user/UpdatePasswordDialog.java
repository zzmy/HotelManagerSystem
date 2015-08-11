package com.icss.frame.user;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.MessageDigest;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.icss.bean.UserBean;
import com.icss.dao.user.UserFactory;

/**
 * 修改用户密码
 * @author 李振元
 * @version 1.1 2015-01-08
 */
public class UpdatePasswordDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPasswordField oldPasswordField;

	private JPasswordField repeatPasswordField;

	private JPasswordField newPasswordField;

	private Vector<Object> user;

	public void setUser(Vector<Object> user) {
		this.user = user;
	}
	public Vector<Object> getUser() {
		return user;
	}
	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			UpdatePasswordDialog dialog = new UpdatePasswordDialog();
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
	public UpdatePasswordDialog() {
		super();
		getContentPane().setLayout(new GridBagLayout());
		setModal(true);
		setTitle("修改密码");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 300) / 2, (screenSize.height - 200) / 2,
				500, 200);

		final JLabel oldPasswordLabel = new JLabel();
		oldPasswordLabel.setText("原 密 码：");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.gridx = 0;
		gridBagConstraints_7.gridy = 0;
		getContentPane().add(oldPasswordLabel, gridBagConstraints_7);

		oldPasswordField = new JPasswordField();
//		oldPasswordField.setText("      ");
		oldPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				oldPasswordField.setText("");
			}

			public void focusLost(FocusEvent e) {
			}
		});
		oldPasswordField.setColumns(25);
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.gridwidth = 3;
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 1;
		getContentPane().add(oldPasswordField, gridBagConstraints_8);

		final JLabel newPasswordLabel = new JLabel();
		newPasswordLabel.setText("新 密 码：");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridx = 0;
		getContentPane().add(newPasswordLabel, gridBagConstraints);

		newPasswordField = new JPasswordField();
//		newPasswordField.setText("      ");
		newPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				newPasswordField.setText("");
			}

			public void focusLost(FocusEvent e) {
			}
		});
		newPasswordField.setColumns(25);
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_1.gridwidth = 3;
		gridBagConstraints_1.gridy = 1;
		gridBagConstraints_1.gridx = 1;
		getContentPane().add(newPasswordField, gridBagConstraints_1);

		final JLabel repeatPasswordLabel = new JLabel();
		repeatPasswordLabel.setText("重新输入：");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_2.gridy = 2;
		gridBagConstraints_2.gridx = 0;
		getContentPane().add(repeatPasswordLabel, gridBagConstraints_2);

		repeatPasswordField = new JPasswordField();
//		repeatPasswordField.setText("      ");
		repeatPasswordField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				repeatPasswordField.setText("");
			}

			public void focusLost(FocusEvent e) {
			}
		});
		repeatPasswordField.setColumns(25);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridwidth = 3;
		gridBagConstraints_3.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_3.gridy = 2;
		gridBagConstraints_3.gridx = 1;
		getContentPane().add(repeatPasswordField, gridBagConstraints_3);

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(30, 20));
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridy = 3;
		gridBagConstraints_6.gridx = 1;
		getContentPane().add(label, gridBagConstraints_6);

		final JButton submitButton = new JButton();
		submitButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				String oldPassword = oldPasswordField.getText();
				String newPassword = newPasswordField.getText();
				String repeatPassword = repeatPasswordField.getText();
				// 判断用户是否输入密码，并提示"请输入密码！"
				if (0 == 
						oldPassword.length() * 
						newPassword.length() * 
						repeatPassword.length()) {
					JOptionPane.showMessageDialog(null,"密码为空，请输入密码!");
					return;
				}
				// 判断两次输入的新密码是否不一致 ，并提示
				if (newPassword.length()<6 || newPassword.length()>20)
				{
					JOptionPane.showMessageDialog(null,"密码长度应该在六到二十位之间!");
					return;
				}
				newPassword = Encode(newPassword);
				repeatPassword = Encode(repeatPassword);
				if (! newPassword.equals(repeatPassword)) {
					JOptionPane.showMessageDialog(null,"两次输入密码不一致，请确认后重新输入!");
					return;
				}
				// 验证用户原有密码是否正确
				oldPassword = Encode(oldPassword);
				String realPassword = user.get(6).toString().trim();
				if (!oldPassword.equals(realPassword)) {
					oldPasswordField.setText("");
					JOptionPane.showMessageDialog(null,"原密码错误，请确认后重新输入!");
					return;
				}
				// 修改用户密码
				UserBean bean = new UserBean();
				String idStr = user.get(1).toString();
				int id = Integer.parseInt(idStr);
				bean.setId(id);
				bean.setPassword(newPassword);
				try {
					UserFactory.getInitialise().updateUser(bean);
					user.set(6, newPassword);
					JOptionPane.showMessageDialog(null,"密码修改成功!");
					setVisible(false);// 设置窗口不可见
				} catch(Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"密码修改失败!");
				}
			}
		});
		submitButton.setText("确定");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_4.gridy = 3;
		gridBagConstraints_4.gridx = 2;
		getContentPane().add(submitButton, gridBagConstraints_4);

		final JButton exitButton = new JButton();
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		exitButton.setText("退出");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_5.gridy = 3;
		gridBagConstraints_5.gridx = 3;
		getContentPane().add(exitButton, gridBagConstraints_5);
		//
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
