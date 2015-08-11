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

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.icss.dao.order_form.Order_FormFactory;
import com.icss.mwing.MTable;
import com.icss.tool.Today;
/**
 * 月结账窗体
 * @author 李振元
 * @version 1.1 2015-01-09
 */
public class MonthDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private Vector<String> tableColumnV;	//表格表头信息

	private Vector<Vector<Object>> tableValueV;		//表格数据

	private DefaultTableModel tableModel;

	private JComboBox<Integer> monthComboBox;

	private JComboBox<Integer> yearComboBox;

	private int daysOfMonth[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MonthDialog dialog = new MonthDialog();
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
	public MonthDialog() {
		super();
		setModal(true);
		setTitle("月结账");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		//获得系统当前日期的年月
		int year = Today.getYEAR();
		int month = Today.getMONTH();
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
		judgeLeapYear(year);	//判断是否为闰年
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();
				judgeLeapYear(year);
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
		panel.add(monthComboBox);

		final JLabel monthLabel = new JLabel();
		monthLabel.setText("月    ");
		panel.add(monthLabel);

		//提交按钮
		final JButton submitButton = new JButton("确定");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableValueV.clear();
				//-------- 在此写代码
				//获取年
				String yearStr = yearComboBox.getSelectedItem().toString();
				int year = Integer.parseInt(yearStr);
				//获取月
				String monthStr = monthComboBox.getSelectedItem().toString();
				int month = Integer.parseInt(monthStr);
				for (int i=0; i<daysOfMonth[month]; i++) {
					Vector<Object> rowInit = new Vector<Object>();
					rowInit.add(i+1);
					for (int j=0; j<5; j++) {
						rowInit.add("----");
					}
					tableValueV.add(rowInit);
				}
				try {
					StringBuffer time = new StringBuffer()
					.append(year);
					if (10 > month) {
						time.append("0");
					}
					time.append(month);
					Vector<Vector<Object>> tableMonthDai = 
							Order_FormFactory.getInitialise().queryMonthDia(time.toString());
					for (int i=0; i<tableMonthDai.size(); i++) {
						Vector<Object> row = tableMonthDai.get(i);
						row.remove(0);
						String timeValue = row.get(0).toString();
						if (!time.toString().equals(timeValue.subSequence(0, 6))) {
							continue;
						}
						String dayValue = timeValue.substring(6, 8);
						int day = Integer.parseInt(dayValue);
						row.set(0, day);
						String avgStr = row.get(3).toString();
						int indexOfDot = avgStr.indexOf(".");
						avgStr = indexOfDot!=-1? avgStr.substring(0, indexOfDot):avgStr;
						row.set(3, avgStr);
						tableValueV.set(day-1, row);
					}
					Vector<Object> summary = Summary();
					tableValueV.add(summary);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				tableModel.setDataVector(tableValueV, tableColumnV);
			}
		});
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
                try {
                    ExportTable(table, new File("Month_" + fileName.toString() + ".xls"));
                    JOptionPane.showMessageDialog(null, "生成 Excel 成功!");
                } catch (IOException ex) { 
                    JOptionPane.showMessageDialog(null, "生成 Excel 失败!");
                    System.out.println(ex.getMessage());  
                    ex.printStackTrace();  
                }  
			}
		});
		panel.add(submitButton);
		panel.add(printButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		tableColumnV = new Vector<String>();
		tableColumnV.add("日期");
		tableColumnV.add("开台总数");
		tableColumnV.add("消费总额");
		tableColumnV.add("平均消费额");
		tableColumnV.add("最大消费额");
		tableColumnV.add("最小消费额");

		tableValueV = new Vector<Vector<Object>>();
		tableModel = new DefaultTableModel(tableValueV, tableColumnV);
		table = new MTable(tableModel);
		scrollPane.setViewportView(table);
		//
	}

	private Vector<Object> Summary() {
		Vector<Object> summary = new Vector<Object>();
		summary.add("总计");
		for (int i=1; i<tableValueV.get(0).size(); i++) {
			double sum = 0;
			for (int j=0; j<tableValueV.size(); j++) {
				Vector<Object> row = tableValueV.get(j);
				String value = row.get(i).toString();
				if (value.equals("----")) {
					continue;
				}
				sum += Double.parseDouble(value);
			}
			summary.add(sum);
		}
		return summary;
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
