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
 * 年结账窗体
 * @author 李振元
 * @version 1.1 2015-01-09
 */
public class YearDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private Vector<String> tableColumnV;

	private Vector<Vector<Object>> tableValueV;

	private DefaultTableModel tableModel;

	private JComboBox<Integer> yearComboBox;

	private final JScrollPane scrollPane;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			YearDialog dialog = new YearDialog();
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
	public YearDialog() {
		super();
		setModal(true);
		setTitle("年结账");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		int year = Today.getYEAR();
		int yearMin;
		
		yearComboBox = new JComboBox<Integer>();
		yearComboBox.setMaximumRowCount(10);
		
		/*------------  年下拉列表中的数据要求：系统最早定单日期年份到系统当前时间表
		 *------------  例如：最早定单日期为2012-1-1,当前系统时间为2014-10-2,
		 *------------  则：年下拉列表要显示 2012,2013,2014
		*/
		//------------  在此写代码 ------
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
		panel.add(yearComboBox);

		final JLabel yearLabel = new JLabel();
		yearLabel.setText("年    ");
		panel.add(yearLabel);

		final JButton submitButton = new JButton("确定");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableValueV.removeAllElements(); 
				//---------- 在此写代码
				String yearStr = yearComboBox.getSelectedItem().toString();
				for (int i=0; i<31; i++) {
					Vector<Object> rowInit = new Vector<Object>();
					rowInit.add(i+1);
					for (int j=0; j<13; j++) {
						rowInit.add("----");
					}
					tableValueV.add(rowInit);
				}
				try {
					Vector<Vector<Object>> tableYearDia = 
							Order_FormFactory.getInitialise().queryYearDia(yearStr);
					for (int i=0; i<tableYearDia.size(); i++) {
						Vector<Object> row = tableYearDia.get(i);
						String md = row.get(1).toString();
						String moneyStr = row.get(2).toString();
						double money = Double.parseDouble(moneyStr);
						
						String dayStr = md.substring(2, 4);
						int day = Integer.parseInt(dayStr);
						String monthStr = md.substring(0, 2);
						int month = Integer.parseInt(monthStr);
						
						Vector<Object> rowInTable = tableValueV.get(day-1);
						rowInTable.set(month, money);
						tableValueV.set(day-1, rowInTable);
					}
					//日度总计
					tableYearDia = 
							Order_FormFactory.getInitialise().queryDaySum(yearStr);
					for (int i=0; i<tableYearDia.size(); i++) {
						Vector<Object> row = tableYearDia.get(i);
						String dayStr = row.get(1).toString();
						int day = Integer.parseInt(dayStr);
						String sumStr = row.get(2).toString();
						
						Vector<Object> rowOfTable = tableValueV.get(day-1);
						rowOfTable.set(13, sumStr);
						
						tableValueV.set(day-1, rowOfTable);
					}
					//月度总计
					Vector<Object> rowSum = new Vector<Object>();
					rowSum.add("总结");
					for (int i=0; i<13; i++) {
						rowSum.add("----");
					}
					tableYearDia =
							Order_FormFactory.getInitialise().queryMonthSum(yearStr);
					for (int i=0; i<tableYearDia.size(); i++) {
						Vector<Object> row = tableYearDia.get(i);
						String monthStr = row.get(1).toString();
						int month = Integer.parseInt(monthStr);
						String sumStr = row.get(2).toString();
						
						rowSum.set(month, sumStr);
					}
					//日度总计的总计
					double sum = 0;
					for (int i=0; i<tableValueV.size(); i++) {
						Vector<Object> row = tableValueV.get(i);
						String valueStr = row.get(13).toString();
						if (valueStr.equals("----")) {
							continue;
						}
						double value = Double.parseDouble(valueStr);
						sum += value;
					}
					rowSum.set(rowSum.size()-1, sum);
					tableValueV.add(rowSum);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//显示数据
				tableModel.setDataVector(tableValueV, tableColumnV);
				/////////////////////////////////////////////////////////////
				//表格回填
//				Container contentPane = getContentPane();
//				contentPane.remove(1);
//				getContentPane().add(new FixedColumnTablePanel(tableColumnV,
//										tableValueV, 1), BorderLayout.CENTER);
//				SwingUtilities.updateComponentTreeUI(contentPane);
			}
		});
		final JButton printButton = new JButton("print");
		printButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				StringBuffer fileName = new StringBuffer()
				.append(yearComboBox.getSelectedItem().toString());
                try {
                    ExportTable(table, new File("Year_" + fileName.toString() + ".xls"));
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

		tableColumnV = new Vector<String>();
		tableColumnV.add("日期");
		for (int i = 1; i <= 12; i++) {
			tableColumnV.add(i + " 月");
		}
		tableColumnV.add("总计");

		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		tableValueV = new Vector<Vector<Object>>();
		tableModel = new DefaultTableModel(tableValueV, tableColumnV);
		table = new MTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(table);

//		getContentPane().add(
//				new FixedColumnTablePanel(tableColumnV, tableValueV, 1),
//				BorderLayout.CENTER);
		//
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
