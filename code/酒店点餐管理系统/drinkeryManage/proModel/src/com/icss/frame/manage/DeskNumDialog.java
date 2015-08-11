package com.icss.frame.manage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.icss.bean.DeskBean;
import com.icss.dao.desk.DeskFactory;
import com.icss.mwing.MTable;
import com.icss.tool.Validate;
/**
 * 餐桌管理窗体
 * @author 钟明媛
 * @version 1.3 2015-01-09
 */
public class DeskNumDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private JTextField seatingTextField;

	private JTextField numTextField;
	
	// 列
	private final Vector<Object> columnNameV = new Vector<Object>();
	
	// 行
	private Vector<Vector<Object>> tableValueV = new Vector<Vector<Object>>();

	private final DefaultTableModel tableModel;

	/**
	 * Launch the application
	 * 开始台号管理应用程序
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DeskNumDialog dialog = new DeskNumDialog(null);
			dialog.addWindowListener(new WindowAdapter() {
				/**
				 * 监听窗口
				 * @param WindowEvent e
				 */
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			System.out.println("窗体显示有误");
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog
	 * 建立台号管理的窗体
	 * @param rightTable
	 */
	public DeskNumDialog(final JTable rightTable) {
		super();
		setModal(true);
		/**
		 * 盒式布局
		 */
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("台号管理");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		operatePanel.add(numLabel);
		numLabel.setText("台  号：");
		
		numTextField = new JTextField();
		numTextField.setColumns(6);
		operatePanel.add(numTextField);// 台号文本框

		final JLabel seatingLabel = new JLabel();
		operatePanel.add(seatingLabel);
		seatingLabel.setText("  座位数：");
		
		seatingTextField = new JTextField();
		seatingTextField.setColumns(5);
		operatePanel.add(seatingTextField);// 座位数文本框

		final JLabel topPlaceholderLabel = new JLabel();
		topPlaceholderLabel.setPreferredSize(new Dimension(20, 40));
		operatePanel.add(topPlaceholderLabel);
		
		// 创建添加台号按钮对象
		final JButton addButton = new JButton();
		
		// 创建台号按钮监听器
		addButton.addActionListener(new ActionListener() {
			// 创建台号按钮事件
			public void actionPerformed(ActionEvent e) {
				
				// 获取台号，并去掉首尾空格
				String num = numTextField.getText().trim();
				
				// 获取座位数，并去掉首尾空格
				String seating = seatingTextField.getText().trim();
				
				// 查看用户是否输入了台号
				if(num.length() == 0)
				{
					JOptionPane.showMessageDialog(table, "台号不能为空！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					// 台号文本框清空
					numTextField.setText("");
					// 光标落在台号文本框
					numTextField.requestFocus();
					// 返回
					return;
				}
				
				// 查看用户是否输入了座位数
				if(seating.length() == 0)
				{
					JOptionPane.showMessageDialog(table, "座位数不能为空！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					// 座位数文本框清空
					seatingTextField.setText("");
					// 光标落在座位数文本框
					seatingTextField.requestFocus();
					// 返回
					return;
				}
				
				// 查看台号的长度是否超过了4位
				if(num.length() > 4)
				{
					JOptionPane.showMessageDialog(table, "台号的长度不得超过4位！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					// 台号文本框清空
					numTextField.setText("");
					// 光标落在台号文本框
					numTextField.requestFocus();
					return;
				}
				
				// 查看台号是否为数字
				if(!Validate.execute("[1-9]{1}([0-9]{0,1})([0-9]{0,1})([0-9]{0,1})", num))
				{
					JOptionPane.showMessageDialog(table, "首个数字不为0且请保证台号为数字类型！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					// 台号文本框清空
					numTextField.setText("");
					// 光标落在台号文本框
					numTextField.requestFocus();
					return;
				}
				
				
				// 验证座位数是否在1——99之间
				if(!Validate.execute("[1-9]{1}([0-9]{0,1})", seating))
				{
					JOptionPane.showMessageDialog(table, "请保证座位数在1——99之间！",
							"友情提示", JOptionPane.INFORMATION_MESSAGE);
					// 座位数文本框清空
					seatingTextField.setText("");
					// 光标落在座位数文本框
					seatingTextField.requestFocus();
					return;
				}
				
				// 查看该台号是否已经存在
				// 创建二维集
				Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
				try
				{
					// 查询Desk中已有的信息
					vector = DeskFactory.getInitialise().queryAllData();
				}catch(Exception e1)
				{
					System.out.println("查询有误！");
					e1.printStackTrace();
				}
				
				// 循环获取每行信息
				for(int i=0; i<vector.size(); i++)
				{
					Vector<Object> row = vector.get(i);
					// 比较当前输入的新台号与去掉首尾空格后的台号是否重复
					if(num.equals(row.get(1).toString().trim()))
					{
						JOptionPane.showMessageDialog(table, "台号已经存在");
						numTextField.requestFocus();
						return;
					}
				}
			
				// 获得当前拥有台号的个数
				int row = tableValueV.size();
				
				// 创建一个代表新台号的向量
				Vector<Object> rowsV = new Vector<Object>();
				
				// 添加添加序号
				rowsV.add(row + 1);
				
				// 添加台号
				rowsV.add(num);
				
				// 添加座位数
				rowsV.add(seating);
				
				// 将新台号信息添加到表格中
				tableValueV.add(rowsV);
				//显示表头
				tableModel.setDataVector(tableValueV, columnNameV);
						
				// 设置新添加的台号为选中的
				table.setRowSelectionInterval(row, row);
				
				// 将台号文本框设置为空
				numTextField.setText("");
				
				// 将座位数文本框设置为空
				seatingTextField.setText("");
				
				// 将新添加的台号信息保存到数据库中
				// 封装数据,记得将字符串重新转回整型
				DeskBean bean = new DeskBean();
				bean.setNum(Integer.parseInt(num));
				bean.setSeating(Integer.parseInt(seating));
				try {
					if(1 == DeskFactory.getInitialise().addDesk(bean))
					{
						JOptionPane.showMessageDialog(table, "台号添加完成！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
						table.setRowSelectionInterval(tableValueV.size()-1, tableValueV.size()-1);
					}else{
						JOptionPane.showMessageDialog(table, "台号添加失败，请确认餐台信息！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (HeadlessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		});
		
		addButton.setText("添加");
		operatePanel.add(addButton);
		
		//创建删除台号按钮对象
		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中的餐台
				int selectedRow = table.getSelectedRow();
				// 未选中任何餐台给友情提示
				if(selectedRow == -1)
				{
					JOptionPane.showMessageDialog(table, "未选中任何餐台", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String num = table.getValueAt(selectedRow, 1).toString().trim();
				String seating = table.getValueAt(selectedRow, 2).toString().trim();
						
				// 组织确认信息
				int i = JOptionPane.showConfirmDialog(table, "确定要删除餐台\n台号：" + num
						+ "\n座位数：" + seating, "友情提示", JOptionPane.YES_NO_OPTION);
				
				// 查看该餐台是否正在被使用
				if (i == JOptionPane.YES_OPTION) {
					TableModel rightTableModel = rightTable.getModel();
					for (int j=0; j<rightTable.getRowCount(); j++) {
						String usingTable = rightTableModel.getValueAt(j, 1).toString();
						// 该餐台正在被使用，不能删除，返回
						if (num.equals(usingTable)) {
							JOptionPane.showMessageDialog(null, "餐台正在使用!");
							return;
						}
					}
					//用户点击确认按钮，确认删除
					try {
						//调用Dao, 从数据库中删除
						if(1 == DeskFactory.getInitialise().deleteDesk(Integer.parseInt(num)))
						{
							JOptionPane.showMessageDialog(table, "删除餐台成功！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
							//从表格中删除当前行
							tableModel.removeRow(selectedRow);
						}
					}catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(table, "无法删除！", "友情提示",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// 刷新表格,重置序号
				for (i=0; i<tableValueV.size(); i++) {
					Vector<Object> row = tableValueV.get(i);
					row.set(0, i+1);
					tableValueV.set(i, row);					
				}
				//显示表头
				tableModel.setDataVector(tableValueV, columnNameV);
			}
		});
		
		delButton.setText("删除");
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "序  号", "台  号", "座位数" };
		for (int i = 0; i < columnNames.length; i++) {
			columnNameV.add(columnNames[i]);
		}
		
		//调用Dao全部查询回填数据
		Vector<Vector<Object>> columnValueV = new Vector<Vector<Object>>();
		tableModel = new DefaultTableModel(columnValueV, columnNameV);
		table = new MTable(tableModel);
		
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		scrollPane.setViewportView(table);
		try{
			tableValueV = DeskFactory.getInitialise().queryAllData();
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableModel.setDataVector(tableValueV, columnNameV);

		final JLabel leftPlaceholderLabel = new JLabel();
		leftPlaceholderLabel.setPreferredSize(new Dimension(20, 20));
		getContentPane().add(leftPlaceholderLabel, BorderLayout.WEST);

		//退出面板
		final JPanel exitPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		exitPanel.setLayout(flowLayout);
		getContentPane().add(exitPanel, BorderLayout.SOUTH);
		
		//退出按钮
		final JButton exitButton = new JButton();
		exitPanel.add(exitButton);
		exitButton.setText("退出");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		final JLabel bottomPlaceholderLabel = new JLabel();
		bottomPlaceholderLabel.setPreferredSize(new Dimension(10, 40));
		exitPanel.add(bottomPlaceholderLabel);

		final JLabel rightPlaceholderLabel = new JLabel();
		rightPlaceholderLabel.setPreferredSize(new Dimension(20, 20));
		getContentPane().add(rightPlaceholderLabel, BorderLayout.EAST);
		
	}
}
