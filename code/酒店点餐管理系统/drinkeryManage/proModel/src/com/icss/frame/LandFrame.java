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
 * 登录窗体
 * @author 李振元
 * @version 1.1 2015-01-08
 */
public class LandFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPasswordField passwordField;// 密码框

	private JComboBox<String> usernameComboBox;// 用户名下拉菜单

	public static void main(String args[]) {
		try {
			LandFrame frame = new LandFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LandFrame() {
		// 首先设置窗口的相关信息
		super();// 调用父类的构造方法
		setTitle(" T 科技");// 设置窗口的标题
		setResizable(false);// 设置窗口不可以改变大小
//		setAlwaysOnTop(true);// 设置窗口总在最前方
		setBounds(100, 100, 428, 292);// 设置窗口的大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 设置当关闭窗口时执行的动作

		// 下面将创建一个面板对象并添加到窗口的容器中
		final MPanel panel = new MPanel(this.getClass().getResource(
				"/img/land_background.jpg"));// 创建一个面板对象
		panel.setLayout(new GridBagLayout());// 设置面板的布局管理器为网格组布局
		getContentPane().add(panel, BorderLayout.CENTER);// 将面板添加到窗体中

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

		// 创建并设置用户名下拉菜单
//		usernameComboBox = new JComboBox();// 创建用户名下拉菜单组件对象
//		usernameComboBox.setMaximumRowCount(5);// 设置下拉菜单最多可显示的选项数
//		usernameComboBox.addItem("请选择");// 为下拉菜单添加提示项
//		usernameComboBox
//				.addActionListener(new UsernameComboBoxActionListener());// 为下拉菜单添加事件监听器
//		final GridBagConstraints gridBagConstraints = new GridBagConstraints();// 创建网格组布局管理器对象
//		gridBagConstraints.anchor = GridBagConstraints.WEST;// 设置为靠左侧显示
//		gridBagConstraints.gridy = 1;// 设置行索引为1
//		gridBagConstraints.gridx = 2;// 设置列索引为2
//		panel.add(usernameComboBox, gridBagConstraints);// 将组件按指定的布局管理器添加到面板中

		// 创建并设置密码框
		passwordField = new JPasswordField();// 创建密码框组件对象
		passwordField.setColumns(20);// 设置密码框可显示的字符数
		passwordField.setText("      ");// 设置密码框默认显示6个空格
		passwordField.addFocusListener(new PasswordFieldFocusListener());// 为密码框添加焦点监听器
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();// 创建网格组布局管理器对象
		gridBagConstraints_1.insets = new Insets(5, 0, 0, 0);// 设置组件外部上方的填充量为5像素
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;// 设置为靠左侧显示
		gridBagConstraints_1.gridy = 2;// 设置行索引为2
		gridBagConstraints_1.gridx = 2;// 设置列索引为2
		panel.add(passwordField, gridBagConstraints_1);// 将组件按指定的布局管理器添加到面板中

		// 创建并设置一个用来添加三个按钮的面板
		final JPanel buttonPanel = new JPanel();// 创建一个用来添加按钮的面板
		buttonPanel.setOpaque(false);// 设置面板的背景为透明
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));// 设置面板采用水平箱布局
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();// 创建网格组布局管理器对象
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);// 设置组件外部上方的填充量为10像素
		gridBagConstraints_4.gridwidth = 2;// 设置其占两列
		gridBagConstraints_4.gridy = 3;// 设置行索引为3
		gridBagConstraints_4.gridx = 1;// 设置列索引为1
		panel.add(buttonPanel, gridBagConstraints_4);// 将组件按指定的布局管理器添加到面板中

		// 创建并设置一个登录按钮，并将其添加到用来添加按钮的面板中
		final JButton landButton = new JButton();// 创建登录按钮组件对象
		landButton.setMargin(new Insets(0, 0, 0, 0));// 设置按钮边框和标签之间的间隔
		landButton.setContentAreaFilled(false);// 设置不绘制按钮的内容区域
		landButton.setBorderPainted(false);// 设置不绘制按钮的边框
		URL landUrl = this.getClass().getResource("/img/land_submit.png");// 获得默认情况下登录按钮显示图片的URL
		landButton.setIcon(new ImageIcon(landUrl));// 设置默认情况下登录按钮显示的图片
		URL landOverUrl = this.getClass().getResource(
				"/img/land_submit_over.png");// 获得当鼠标经过登录按钮时显示图片的URL
		landButton.setRolloverIcon(new ImageIcon(landOverUrl));// 设置当鼠标经过登录按钮时显示的图片
		URL landPressedUrl = this.getClass().getResource(
				"/img/land_submit_pressed.png");// 获得当登录按钮被按下时显示图片的URL
		landButton.setPressedIcon(new ImageIcon(landPressedUrl));// 设置当登录按钮被按下时显示的图片
		landButton.addActionListener(new LandButtonActionListener());// 为登录按钮添加事件监听器
		buttonPanel.add(landButton);// 将登录按钮添加到用来添加按钮的面板中

		final JButton resetButton = new JButton();//创建重置按钮组建对象
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

		final JButton exitButton = new JButton();//创建退出按钮组建对象
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
		//------- 初始化用户名下拉菜单
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
			if (freeze.equals("禁用")) {
				continue;
			}
			users_name.addElement(users.get(i).get(2).toString().trim());
		}
		usernameComboBox = new JComboBox<String>(users_name);// 创建用户名下拉菜单组件对象
		usernameComboBox.setMaximumRowCount(5);// 设置下拉菜单最多可显示的选项数
		usernameComboBox.addItem("请选择");// 为下拉菜单添加提示项
		usernameComboBox.setSelectedItem("请选择");
		usernameComboBox
				.addActionListener(new UsernameComboBoxActionListener());// 为下拉菜单添加事件监听器
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();// 创建网格组布局管理器对象
		gridBagConstraints.anchor = GridBagConstraints.WEST;// 设置为靠左侧显示
		gridBagConstraints.gridy = 1;// 设置行索引为1
		gridBagConstraints.gridx = 2;// 设置列索引为2
		panel.add(usernameComboBox, gridBagConstraints);// 将组件按指定的布局管理器添加到面板中
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
			// 获得登录用户的名称
			// 查看是否选择了登录用户
			// 未选择用户，给出“友情提示”
			String userName = usernameComboBox.getSelectedItem().toString();
			if (userName.equals("请选择")) {
				JOptionPane.showMessageDialog(null,"请选择用户名!");
				return;
			}
			// 获得登录用户的密码
			// 用户未输入登录密码，给出"友情提示"
			String userPWD = String.valueOf(passwordField.getPassword());
			userPWD = Encode(userPWD);
			if (userPWD.equalsIgnoreCase("      ")) {
				JOptionPane.showMessageDialog(null,"请输入密码!");
				return;
			}
			// 验证用户名密码是否匹配
			// 密码错误，给出"友情提示"
			Vector<Vector<Object>> tableUser = null;
			try {
				UserDao userDao = UserFactory.getInitialise();
				tableUser = userDao.queryUser(userName, userPWD);
				Vector<Object> row = tableUser.get(0);
				if (null != row) {
					// 用户名密码正确，登陆系统
					// 创建主窗体对象
					// 设置主窗体可见
					// 设置登录窗口不可见
					TipWizardFrame tipWizard = new TipWizardFrame(row);// 创建主窗体对象
					tipWizard.setVisible(true);// 设置主窗体可见
					setVisible(false);// 设置登录窗口不可见
					return;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				// 恢复登录用户和登录密码
				// 恢复选中的登录用户为“请选择”项
				// 恢复密码框的默认值为6个空格
				JOptionPane.showMessageDialog(null,"密码错误，请确认后重新输入!");
				usernameComboBox.setSelectedItem("请选择");
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
