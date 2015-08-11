package com.icss.frame.check_out;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.icss.dao.menu.MenuFactory;
import com.icss.dao.order_form.Order_FormFactory;
import com.icss.dao.order_item.Order_ItemFactory;
import com.icss.mwing.MTable;
import com.icss.tool.Today;

import jxl.*;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
/**
 * 日结账窗体
 * @author 李振元
 * @version 1.1 2015-01-08
 */
public class DayDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private Vector<String> tableColumnV;	//表格表头信息

	private Vector<Vector<Object>> tableValueV; //表格数据

	private DefaultTableModel tableModel;
	
	private JComboBox<Integer> dayComboBox;	//日下拉列表

	private JComboBox<Integer> monthComboBox;//月下拉列表

	private JComboBox<Integer> yearComboBox;	//年下拉列表
	
	private final JScrollPane scrollPane;

	private int daysOfMonth[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };   //每月对应天数数组

	

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DayDialog dialog = new DayDialog();
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
	public DayDialog() {
		super();
		setModal(true);
		setTitle("日结账");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		//获得系统当前日期的年月日
		int year = Today.getYEAR();
		int month = Today.getMONTH();
		int day = Today.getDAY();
		int yearMin;

		//创建年下拉列表对象
		yearComboBox = new JComboBox<Integer>();
		yearComboBox.setMaximumRowCount(10);
		/*------------  年下拉列表中的数据要求：系统最早定单日期年份到系统当前时间表
		 *------------  例如：最早定单日期为2012-1-1,当前系统时间为2014-10-2,
		 *------------  则：年下拉列表要显示 2012,2013,2014
		*/
		try {
			Vector<Vector<Object>> tableMinYear = Order_FormFactory.getInitialise().queryMinYear();
			String tmpY = tableMinYear.get(0).get(1).toString().substring(0, 4);
			yearMin = Integer.parseInt(tmpY);
		} catch (Exception e) {
			yearMin = year;
			e.printStackTrace();
		}
		for (int i=yearMin; i<=year; i++) {
			yearComboBox.addItem(i);
		}
	
		//设定当前年为选中状态
		yearComboBox.setSelectedItem(year);
		judgeLeapYear(year);
		//为年下拉列表添加事件监听：当选中年月后自动修改日期下拉列表
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();// 获得选中的年度
				judgeLeapYear(year);// 判断是否为闰年，以确定2月份的天数
				int month = (Integer) monthComboBox.getSelectedItem();// 获得选中的月份
				if (month == 2) {// 如果选中的为2月
					int itemCount = dayComboBox.getItemCount();// 获得日下拉菜单当前的天数
					if (itemCount != daysOfMonth[2]) {// 如果日下拉菜单当前的天数不等于2月份的天数
						if (itemCount == 28)// 如果日下拉菜单当前的天数为28天
							dayComboBox.addItem(29);// 则添加为29天
						else
							// 否则日下拉菜单当前的天数则为29天
							dayComboBox.removeItem(29);// 则减少为28天
					}
				}
			}
		});
		panel.add(yearComboBox);

		final JLabel yearLabel = new JLabel();
		yearLabel.setText("年");
		panel.add(yearLabel);

		//月下拉列表
		monthComboBox = new JComboBox<Integer>();
		monthComboBox.setMaximumRowCount(12);
		for (int m = 1; m < 13; m++) {
			monthComboBox.addItem(m);
		}
		monthComboBox.setSelectedItem(month);
		monthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int month = (Integer) monthComboBox.getSelectedItem();// 获得选中的月份
				int itemCount = dayComboBox.getItemCount();// 获得日下拉菜单当前的天数
				while (itemCount != daysOfMonth[month]) {// 如果日下拉菜单当前的天数不等于选中月份的天数
					if (itemCount > daysOfMonth[month]) {// 如果大于选中月份的天数
						dayComboBox.removeItem(itemCount);// 则移除最后一个选择项
						itemCount--;// 并将日下拉菜单当前的天数减1
					} else {// 否则小于选中月份的天数
						itemCount++;// 将日下拉菜单当前的天数加1
						dayComboBox.addItem(itemCount);// 并添加为选择项
					}
				}
			}
		});
		panel.add(monthComboBox);

		final JLabel monthLabel = new JLabel();
		monthLabel.setText("月");
		panel.add(monthLabel);

		//创建月下拉列表
		dayComboBox = new JComboBox<Integer>();
		dayComboBox.setMaximumRowCount(10);
		int days = daysOfMonth[month];
		for (int d = 1; d <= days; d++) {
			dayComboBox.addItem(d);
		}
		dayComboBox.setSelectedItem(day);
		panel.add(dayComboBox);

		final JLabel dayLabel = new JLabel();
		dayLabel.setText("日    ");
		panel.add(dayLabel);
		
		final JButton printButton = new JButton("print");
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StringBuffer fileName = new StringBuffer()
				.append(yearComboBox.getSelectedItem().toString());
				String monthStr = monthComboBox.getSelectedItem().toString();
				if (monthStr.length() < 2) {
					fileName.append("0");
				}
				fileName.append(monthComboBox.getSelectedItem().toString());
				String dayStr = dayComboBox.getSelectedItem().toString();
				if (dayStr.length() < 2) {
					fileName.append("0");
				}
				fileName.append(dayComboBox.getSelectedItem().toString());
                try {
                    ExportTable(table, new File("Day_" + fileName.toString() + ".xls"));
                    JOptionPane.showMessageDialog(null, "生成 Excel 成功!");
                } catch (IOException ex) { 
                    JOptionPane.showMessageDialog(null, "生成 Excel 失败!");
                    System.out.println(ex.getMessage());  
                    ex.printStackTrace();  
                }  
			}
		});
		
		//提交按钮
		final JButton submitButton = new JButton();
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//------------  在此写代码 ------
				JOptionPane.showMessageDialog(null, "查询本日所有消费帐单");
				tableValueV.removeAllElements();
				StringBuffer time = new StringBuffer();
				int year = (Integer) yearComboBox.getSelectedItem();
				time.append(year);
				int month = (Integer) monthComboBox.getSelectedItem();
				if (10 > month) {
					time.append(0);
				}
				time.append(month);
				int day = (Integer) dayComboBox.getSelectedItem();
				if (10 > day) {
					time.append(0);
				}
				time.append(day);
				//int columnCount = tableColumnV.size();
				
				//查询本日所有消费清单 sOrderFormOfDay
				try {
					Vector<Vector<Object>> tableOrderForm = null;
					Vector<Vector<Object>> tableOrderItem = null;
					
					tableOrderForm = Order_FormFactory.getInitialise().queryDayDia(time.toString());
					for (int i=0; i<tableOrderForm.size(); i++) {
						Vector<Object> rowNew = tableOrderForm.get(i);
						rowNew.remove(0);
						String timeSub = rowNew.get(2).toString().substring(11);
						rowNew.set(2, timeSub);
						for (int j=0; j<tableColumnV.size(); j++) {
							rowNew.add(0);
						}
						tableOrderItem = Order_ItemFactory.getInitialise().queryMenuAmount(rowNew.get(0).toString());
						for (int j=0; j<tableOrderItem.size(); j++) {
							Vector<Object> rowMenuAmount = tableOrderItem.get(j);
							String dishName = rowMenuAmount.get(1).toString().trim();
							String dishAmountS = rowMenuAmount.get(2).toString().trim();
							int dishAmount = Integer.parseInt(dishAmountS);
							int index = tableColumnV.indexOf(dishName);
							int amount = Integer.parseInt(rowNew.get(index).toString());
							amount += dishAmount;
							rowNew.set(index, amount);
						}
						for (int j=0; j<rowNew.size(); j++) {
							try {
								String value = rowNew.get(j).toString();
								if (value.equals("0")) {
									rowNew.set(j, "----");
								}
							} catch (Exception e1) {
								rowNew.set(j, "----");
							}
						}
						tableValueV.add(rowNew);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				//显示数据
				tableModel.setDataVector(tableValueV, tableColumnV);
				//回填表格	
//				Container contentPane = getContentPane();
//				contentPane.remove(1);
//				contentPane.add(new FixedColumnTablePanel(tableColumnV,
//						tableValueV, 4), BorderLayout.CENTER);
//				SwingUtilities.updateComponentTreeUI(contentPane);
			}
		});
		submitButton.setText("确定");
		panel.add(submitButton);
		panel.add(printButton);

		tableColumnV = new Vector<String>();
		tableColumnV.add("编号");
		tableColumnV.add("台号");
		tableColumnV.add("开台时间");
		tableColumnV.add("消费金额");
		//----------- 表格中添加菜品选项
		try {
			Vector<Vector<Object>> tableMenu = MenuFactory.getInitialise().queryAllData();
			for (int i=0; i<tableMenu.size(); i++) {
				Vector<Object> row = tableMenu.get(i);
				tableColumnV.add(row.get(3).toString().trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		tableValueV = new Vector<Vector<Object>>();
		tableModel = new DefaultTableModel(tableValueV, tableColumnV);
		table = new MTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);
		
//		getContentPane().add(
//				new FixedColumnTablePanel(tableColumnV, tableValueV, 4),
//				BorderLayout.CENTER);
	}

	private void judgeLeapYear(int year) {
		if (year % 100 == 0) {
			if (year % 400 == 0)
				daysOfMonth[2] = 29;
			else
				daysOfMonth[2] = 28;
		} else {
			if (year % 4 == 0)
				daysOfMonth[2] = 29;
			else
				daysOfMonth[2] = 28;
		}
	}
    public void ExportTable(JTable table, File file) throws IOException {  
        try {  
            OutputStream out = new FileOutputStream(file);  
            TableModel model = table.getModel();  
            WritableWorkbook wwb = Workbook.createWorkbook(out);  
            // 创建字表，并写入数据  
            WritableSheet ws = wwb.createSheet("中文", 0);  
            // 添加标题  
            for (int i = 0; i < model.getColumnCount(); i++) {  
                jxl.write.Label labelN = new jxl.write.Label(i, 0, model.getColumnName(i));  
                try {  
                    ws.addCell(labelN);  
                } catch (RowsExceededException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                } catch (WriteException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
            // 添加列  
            for (int i = 0; i < model.getColumnCount(); i++) {  
                for (int j = 1; j <= model.getRowCount(); j++) {  
                    jxl.write.Label labelN = new jxl.write.Label(i, j, model.getValueAt(j - 1, i).toString());  
                    try {  
                        ws.addCell(labelN);  
                    } catch (RowsExceededException e) {  
                        e.printStackTrace();  
                    } catch (WriteException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
            wwb.write();  
            try {  
                wwb.close();  
            } catch (WriteException e) {  
                e.printStackTrace();  
            }  
        } catch (FileNotFoundException e) {  
            JOptionPane.showMessageDialog(null, "导入数据前请关闭工作表");  
        }  
    } 

}
