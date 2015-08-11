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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.icss.bean.SortBean;
import com.icss.dao.sort.SortFactory;
import com.icss.mwing.MTable;
/**
 * 菜系管理窗体
 * @author 赵玉璐
 * version 1.0 2015-01-05
 *
 */
public class SortDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MTable table;
	
	private Vector<String> tableColumnV;
	
	private Vector<Vector<Object>> tableValueV; 

	private JTextField sortNameTextField;

	private final Vector<Object> columnNameV = new Vector<Object>();

	private final DefaultTableModel tableModel = new DefaultTableModel();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			SortDialog dialog = new SortDialog();
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
	public SortDialog() {
		super();
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("菜系管理");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel sortNameLabel = new JLabel();
		operatePanel.add(sortNameLabel);
		sortNameLabel.setText("菜系名称：");

		sortNameTextField = new JTextField();
		operatePanel.add(sortNameTextField);
		sortNameTextField.setColumns(20);

		final JLabel topPlaceholderLabel = new JLabel();
		topPlaceholderLabel.setPreferredSize(new Dimension(20, 40));
		operatePanel.add(topPlaceholderLabel);

		final JButton addButton = new JButton();  // 创建添加菜系名称按钮对象
		addButton.addActionListener(new ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				// 获得菜系名称
				String sortname = sortNameTextField.getText();
				// 查看菜系名称的长度是否超过了10个汉字
				if(sortname.length() > 10){
					JOptionPane.showMessageDialog(null, "菜系名不能超出10个字符!");
					return;
				}
				if(sortname.length() == 0){
					JOptionPane.showMessageDialog(null, "请输入菜系名!");
					return;
				}
				// 查看该菜系名称是否已经存在
				Vector<Vector<Object>> table1 = null;
				try {
					table1 = SortFactory.getInitialise().queryAllData();
				} catch (Exception e1) { 
					e1.printStackTrace();
				}
				for (int i=0; i<table1.size(); i++) {
					Vector<Object> row = table1.get(i);
					if (sortname.equals(row.get(2).toString().trim())) {
							JOptionPane.showMessageDialog(null, "菜系名已存在!");
							return;
					}
				}
				// 将新添加的菜系名称信息保存到数据库中
				SortBean bean = new SortBean();
				bean.setName(sortname);
				try{
					if(1 == SortFactory.getInitialise().addSort(bean)){
						// 将新菜系名称信息添加到表格中
						Vector<Object> rowV = new Vector<Object>();
						int row = tableValueV.size();
						rowV.add(row + 1);
						rowV.add(sortname);
						
						tableModel.addRow(rowV);
						// 设置新添加的菜系名称为选中的
						table.setRowSelectionInterval(row-1);
						// 将菜系名称文本框设置为空
						sortNameTextField.setText("");
						TableValueRefresh();
						table.setRowSelectionInterval(tableValueV.size()-1, tableValueV.size()-1);
						JOptionPane.showMessageDialog(null, "菜系添加完成!");
					}else{
						JOptionPane.showMessageDialog(null, "菜系添加失败!");
					}
				}catch(HeadlessException e1){
					e1.printStackTrace();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		addButton.setText("添加");
		operatePanel.add(addButton);

		final JButton delButton = new JButton();// 创建删除菜系名称按钮对象
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中的菜系
				int selectedRow = table.getSelectedRow();
				if (-1 == selectedRow) {
					return;
				}
				// 获得选中的菜系名称
				String sortname = table.getValueAt(selectedRow,1).toString();
				// 弹出确认提示
				int i = JOptionPane.showConfirmDialog(null, "确定要删除用户“" + sortname
						+ "”?", "友情提示", JOptionPane.YES_NO_OPTION);
				// 确认删除
				if(i == JOptionPane.YES_OPTION){
					Vector<Vector<Object>> tableSort;
					try {
						tableSort = SortFactory.getInitialise().queryAllData();
						for (i=0; i<tableSort.size(); i++) {
							Vector<Object> row = tableSort.get(i);
							String name = row.get(2).toString().trim();
							if (sortname.equals(name)) {
								String id = row.get(1).toString().trim();
								try {
								    // 从数据库中删除菜系
									SortFactory.getInitialise().deleteSort(Integer.parseInt(id));
									// 从表格中移除菜系信息
									tableValueV.remove(selectedRow);
									TableValueRefresh();
									JOptionPane.showMessageDialog(null, "成功删除!");
									return;
								} catch (Exception e2) {
									JOptionPane.showMessageDialog(null, "无法删除!");
									e2.printStackTrace();
									return;
								}
							}
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		delButton.setText("删除");
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		columnNameV.add("序    号");
		columnNameV.add("菜系名称");

		//---------- 初始化数据

		table = new MTable(tableModel);
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		scrollPane.setViewportView(table);

		final JLabel leftPlaceholderLabel = new JLabel();
		leftPlaceholderLabel.setPreferredSize(new Dimension(20, 20));
		getContentPane().add(leftPlaceholderLabel, BorderLayout.WEST);

		final JPanel exitPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		exitPanel.setLayout(flowLayout);
		getContentPane().add(exitPanel, BorderLayout.SOUTH);

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
		
		tableColumnV = new Vector<String>();
		String tableColumns[] = {"序号","菜系名"};
		for(int column = 0; column < tableColumns.length; column++){
			tableColumnV.add(tableColumns[column]);
		}
		try {
			//调用Dao全部查询回填数据
			tableValueV = SortFactory.getInitialise().queryAllData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (int i=0; i<tableValueV.size(); i++) {
			Vector<Object> row = tableValueV.get(i);
			String num = row.get(0).toString().trim();
			String name = row.get(2).toString().trim();
			row.clear();
			row.add(num);
			row.add(name);
			tableValueV.set(i, row);
		}
		tableModel.setDataVector(tableValueV, tableColumnV);
	}
	private void TableValueRefresh() {
		for (int j=0; j<tableValueV.size(); j++) {
			Vector<Object> tmp = tableValueV.get(j);
			tmp.set(0, j+1);
			//tableValueV.set(j, tmp);
		}
		tableModel.setDataVector(tableValueV, tableColumnV);
	}
}
