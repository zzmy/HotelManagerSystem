package com.icss.frame.manage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import com.icss.bean.MenuBean;
import com.icss.dao.menu.MenuFactory;
import com.icss.dao.sort.SortFactory;
import com.icss.mwing.MTable;
import com.icss.tool.Today;
/**
 * 菜品管理窗体
 * @author 华莹 李振元
 *
 */
public class MenuDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Vector<Object> rowV;

	private JTextField numTextField;

	private JTextField nameTextField;

	private JTextField unitTextField;

	private JTextField codeTextField;

	private JComboBox<String> sortComboBox;

	private JTextField unitPriceTextField;

	private JTable table;

	private final Vector<String> tableColumnV = new Vector<String>();

	private DefaultTableModel tableModel = new DefaultTableModel();
	// 列
	
	// 行
	private Vector<Vector<Object>> tableValueV = new Vector<Vector<Object>>();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MenuDialog dialog = new MenuDialog();
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
	public MenuDialog() {
		super();
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("菜品管理");
		setBounds(100, 100, 800, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		numLabel.setText("编  号：");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 0;
		operatePanel.add(numLabel, gridBagConstraints_6);

		numTextField = new JTextField();
		numTextField.setHorizontalAlignment(SwingConstants.CENTER);
		numTextField.setEditable(false);
		numTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 1;
		operatePanel.add(numTextField, gridBagConstraints_15);

		final JLabel nameLabel = new JLabel();
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 15, 0, 0);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		operatePanel.add(nameLabel, gridBagConstraints);
		nameLabel.setText("名称：");

		nameTextField = new JTextField();
		// nameTextField.setName("名称");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_1.gridx = 3;
		gridBagConstraints_1.gridy = 0;
		operatePanel.add(nameTextField, gridBagConstraints_1);
		nameTextField.setColumns(21);

		final JLabel unitPriceLabel = new JLabel();
		unitPriceLabel.setText("单价：");
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 4;
		operatePanel.add(unitPriceLabel, gridBagConstraints_9);

		final JLabel unitLabel = new JLabel();
		unitLabel.setText("单位：");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 4;
		operatePanel.add(unitLabel, gridBagConstraints_8);

		unitTextField = new JTextField();
		unitTextField.setName("单位");
		unitTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 5;
		operatePanel.add(unitTextField, gridBagConstraints_11);

		final JLabel codeLabel = new JLabel();
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_2.gridx = 0;
		gridBagConstraints_2.gridy = 1;
		operatePanel.add(codeLabel, gridBagConstraints_2);
		codeLabel.setText("助记码：");

		codeTextField = new JTextField();
		codeTextField.setName("助记码");
		codeTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_3.gridx = 1;
		gridBagConstraints_3.gridy = 1;
		operatePanel.add(codeTextField, gridBagConstraints_3);

		final JLabel sortLabel = new JLabel();
		sortLabel.setText("菜系：");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_4.gridy = 1;
		gridBagConstraints_4.gridx = 2;
		operatePanel.add(sortLabel, gridBagConstraints_4);

		sortComboBox = new JComboBox<String>();
		sortComboBox.addItem("请选择");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.anchor = GridBagConstraints.WEST;
		gridBagConstraints_7.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 3;
		operatePanel.add(sortComboBox, gridBagConstraints_7);

		unitPriceTextField = new JTextField();
		unitPriceTextField.setName("单价");
		unitPriceTextField.setColumns(8);
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 1;
		gridBagConstraints_12.gridx = 5;
		operatePanel.add(unitPriceTextField, gridBagConstraints_12);

		final JLabel label = new JLabel();
		label.setText("元");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_5.gridy = 1;
		gridBagConstraints_5.gridx = 6;
		operatePanel.add(label, gridBagConstraints_5);

		final JPanel panel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		panel.setLayout(flowLayout_1);
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.anchor = GridBagConstraints.EAST;
		gridBagConstraints_14.insets = new Insets(5, 0, 10, 0);
		gridBagConstraints_14.gridwidth = 7;
		gridBagConstraints_14.gridy = 2;
		gridBagConstraints_14.gridx = 0;
		operatePanel.add(panel, gridBagConstraints_14);

		//添加新菜品按钮
		final JButton addButton = new JButton();
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得菜品名称
				String name = nameTextField.getText();
				// 验证菜品名称最多只能为 10 个汉字
				if (10 < name.length()) {
					JOptionPane.showMessageDialog(null, "菜品名最多为10个汉字!");
					return;
				}
				// 验证菜品文本框为空
				if (0 == name.length()) {
					JOptionPane.showMessageDialog(null, "菜品名不能为空!");
					return;
				}
				try {
					Vector<Vector<Object>> menuTable = MenuFactory.getInitialise().queryAllData();
					for (int i=0; i<menuTable.size(); i++) {
						Vector<Object> row = menuTable.get(i);
						String tmpName = row.get(3).toString().trim();
						if (!name.equals(tmpName)) {
							continue;
						}
						// 判断当前菜品是否已经存在并且状态为“销售”，是则提示 "该菜品已经存在！
						String state = row.get(7).toString().trim();
						if (state.equals("销售")) {
							JOptionPane.showMessageDialog(null, "菜品已存在");
							return;
						}
						else {
							MenuBean menuBean = new MenuBean();
							menuBean.setState("销售");
							try {
								int num;
								String str;
								Vector<Object> rowNew = new Vector<Object>();
								//序号
								rowNew.add(tableValueV.size());
								//编号
								num = Integer.parseInt(row.get(1).toString().trim());
								menuBean.setNum(num);
								rowNew.add(num);
								//菜系号
								str = row.get(2).toString().trim();
								num = Integer.parseInt(str);
								menuBean.setSort_id(num);
								//根据菜系号取得菜系名
								str = GetSortNamtById(str);
								rowNew.add(str);
								//菜名
								str = row.get(3).toString().trim();
								menuBean.setName(str);
								rowNew.add(str);
								//助记码
								str = row.get(4).toString().trim();
								menuBean.setCode(str);
								rowNew.add(str);
								//单位
								str = row.get(5).toString().trim();
								menuBean.setUnit(str);
								rowNew.add(str);
								//单价
								num = Integer.parseInt(row.get(6).toString().trim());
								menuBean.setUnit_price(num);
								rowNew.add(num);
								tableValueV.add(rowNew);
								TableValueRefresh();
								tableModel.setDataVector(tableValueV, tableColumnV);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "插入失败!");
								e1.printStackTrace();
								return;
							}
							try {
								MenuFactory.getInitialise().updateMenu(menuBean);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "插入数据库失败!");
								e1.printStackTrace();
								return;
							}
							JOptionPane.showMessageDialog(null, "插入成功!");
							return;
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// 获得菜品所属菜系
				String sort = sortComboBox.getSelectedItem().toString();
				// 验证菜系下拉菜单
				if (sort.equals("请选择")) {
					JOptionPane.showMessageDialog(null, "请选择菜系!");
					return;
				}
				int sortId = -1;
				try {
					Vector<Vector<Object>> tableSort = SortFactory.getInitialise().queryAllData();
					for (int i=0; i<tableSort.size(); i++) {
						Vector<Object> row = tableSort.get(i);
						String tmp = row.get(2).toString().trim();
						if (sort.equals(tmp)) {
							tmp = row.get(1).toString().trim();
							sortId = Integer.parseInt(tmp);
							break;
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// 获得菜品助记码
				String code = codeTextField.getText();
				// 验证助记码最多只能为 10 个字符
				if (10 < code.length()) {
					JOptionPane.showMessageDialog(null, "助记码最多为10个汉字!");
					return;
				}
				if (0 == code.length()) {
					JOptionPane.showMessageDialog(null, "助记码不能为空!");
					return;
				}
				// 获得菜品单位
				String unit = unitTextField.getText();
				// 验证单位最多只能为 2 个汉字
				if (2 < unit.length()) {
					JOptionPane.showMessageDialog(null, "单位最多为2个汉字!");
					return;
				}
				if (0 == unit.length()) {
					JOptionPane.showMessageDialog(null, "单位不能为空!");
					return;
				}
				// 获得菜品单价
				String unitPriceStr = unitPriceTextField.getText();
				double unitPrice = 0;
				try {
					unitPrice = Double.parseDouble(unitPriceStr);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "单价必须为数字");
					e1.printStackTrace();
					return;
				}
				// 验证单价必须在 1——9999
				if (unitPrice<1 || unitPrice>9999) {
					JOptionPane.showMessageDialog(null, "单价必须在1－9999!");
					return;
				}
				// 获得菜品编号
				String num = null;
				try {
					num = MenuFactory.getInitialise().queryMaxNum();
					num = getNextNum(num);
				} catch (Exception e1) {
					num = getNextNum(null);
					e1.printStackTrace();
				}
				// 将新菜品信息保存到数据库
				MenuBean bean = new MenuBean();
				bean.setNum(Integer.parseInt(num));
				bean.setName(name);
				bean.setCode(code);
				bean.setSort_id(sortId);				
				bean.setUnit(unit);
				bean.setUnit_price(unitPrice);
				//调用Dao
				try {
					MenuFactory.getInitialise().addMenu(bean);
					rowV = new Vector<Object>();
					int row = table.getRowCount();
					//添加行号
					rowV.add(row + 1);
					//添加用户数据
					rowV.add(num);
					rowV.add(sort);
					rowV.add(name);	
					rowV.add(code);		
					rowV.add(unit);
					rowV.add(unitPrice);
					rowV.add("销售");
					//向表中添加行数据
					tableValueV.add(rowV);
					TableValueRefresh();
					tableModel.setDataVector(tableValueV, tableColumnV);
					//设置为选中状态
					table.setRowSelectionInterval(row, row);
					
					// 清空表格数据
					nameTextField.setText("");
					codeTextField.setText("");
					unitTextField.setText("");
					unitPriceTextField.setText("");
					sortComboBox.setToolTipText("");
					JOptionPane.showMessageDialog(null, "菜品添加完成！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "菜品添加失败！", "友情提示",
							JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
					return;
				}
			}
		});
		addButton.setText("添加");
		panel.add(addButton);

		//删除按钮
		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 获得选中的菜品
				int selectedRow = table.getSelectedRow();
				table.getValueAt(selectedRow, 0);
				String name = table.getValueAt(selectedRow, 3).toString();
				String num = table.getValueAt(selectedRow, 1).toString();
				// 弹出确认提示框
				int i = JOptionPane.showConfirmDialog(null, "确定要删除菜品“" + name
						+ "”?", "友情提示", JOptionPane.YES_NO_OPTION);
				// 确认删除
				if(i == JOptionPane.YES_OPTION){
					//数据库更新
					try {
						MenuFactory.getInitialise().deleteMenu(Integer.parseInt(num));
						for (i=0; i<tableValueV.size(); i++) {
							Vector<Object> row = tableValueV.get(i);
							row.set(0, i+1);
							tableValueV.set(i, row);					
						}
						//从表格中删除当前行
						tableValueV.remove(selectedRow);
						TableValueRefresh();
						
						table.repaint();
						//调用Dao
						JOptionPane.showMessageDialog(null, "删除菜品成功！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "删除菜品失败！", "友情提示",
								JOptionPane.INFORMATION_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});
		delButton.setText("删除");
		panel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "序 号", "编 号", "菜 系","名 称" ,
				"助记码", "单 位", "单 价"};
		for (int i = 0; i < columnNames.length; i++) {
			tableColumnV.add(columnNames[i]);
		}
		try{
			Vector<Vector<Object>>tableMenu = MenuFactory.getInitialise().queryAllData();
			Vector<Vector<Object>> tableSort = SortFactory.getInitialise().queryAllData();
			int index = 1;
			for (int i=0; i<tableMenu.size(); i++) {
				Vector<Object> row = tableMenu.get(i);
				String tmp = row.get(7).toString().trim();
				//如果菜品处于删除状态
				if (tmp.equals("删除")) {
					continue;
				}
				row.set(0, index ++);
				row.remove(7);
				for (int j=0; j<row.size(); j++) {
					String column = row.get(j).toString().trim();
					if (2 == j) {
						for (int k=0; k<tableSort.size(); k++) {
							Vector<Object> rowSort = tableSort.get(k);
							String sort = rowSort.get(1).toString().trim();
							if (column.equals(sort)) {
								column = rowSort.get(2).toString().trim();
							}
						}
					}
					row.set(j, column);
				}
				tableValueV.add(row);
			}
		}catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tableModel.setDataVector(tableValueV, tableColumnV);

		
		
		//---------- 获得菜系下拉列表数据
		try {
			Vector<Vector<Object>> menuTable = SortFactory.getInitialise().queryAllData();
			for (int i=0; i<menuTable.size(); i++) {
				Vector<Object> row = menuTable.get(i);
				String sort = row.get(2).toString().trim();
				sortComboBox.addItem(sort);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		table = new MTable(tableModel);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int selected = table.getSelectedRow();
				if (selected == -1) {
					return;
				}
				String num = table.getValueAt(selected, 1).toString();
				numTextField.setText(num);
			}
		});
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		scrollPane.setViewportView(table);
		
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
	}

	private void TableValueRefresh() {
		for (int j=0; j<tableValueV.size(); j++) {
			Vector<Object> tmp = tableValueV.get(j);
			tmp.set(0, j+1);
			tableValueV.set(j, tmp);
		}
	}
	private String GetSortNamtById (String id) {
		String name = null;
		try {
		Vector<Vector<Object>> tableSort = SortFactory.getInitialise().queryAllData();
			for (int j=0; j<tableSort.size(); j++) {
				Vector<Object> rowTmp = tableSort.get(j);
				String tmp = rowTmp.get(1).toString().trim();
				if (id.equals(tmp)) {
					name = rowTmp.get(2).toString().trim();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}
	
	//获取下一个菜单编号
	private String getNextNum(String maxNum) {
		String date = Today.getDateOfNum().substring(2);
		if (maxNum == null) {
			maxNum = date + "01";
		} else {
			if (maxNum.subSequence(0, 6).equals(date)) {
				maxNum = maxNum.substring(6);
				int nextNum = Integer.valueOf(maxNum) + 1;
				if (nextNum < 10)
					maxNum = date + "0" + nextNum;
				else
					maxNum = date + nextNum;
			} else {
				maxNum = date + "01";
			}
		}
		return maxNum;
	}
}