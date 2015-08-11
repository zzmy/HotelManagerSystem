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
 * �½��˴���
 * @author ����Ԫ
 * @version 1.1 2015-01-09
 */
public class MonthDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private Vector<String> tableColumnV;	//����ͷ��Ϣ

	private Vector<Vector<Object>> tableValueV;		//�������

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
		setTitle("�½���");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		//���ϵͳ��ǰ���ڵ�����
		int year = Today.getYEAR();
		int month = Today.getMONTH();
		int yearMin;

		//�����������б����
		yearComboBox = new JComboBox<Integer>();
		yearComboBox.setMaximumRowCount(10);
		/*------------  �������б��е�����Ҫ��ϵͳ���綨��������ݵ�ϵͳ��ǰʱ���
		 *------------  ���磺���綨������Ϊ2012-1-1,��ǰϵͳʱ��Ϊ2014-10-2,
		 *------------  ���������б�Ҫ��ʾ 2012,2013,2014
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
		//�趨��ǰ��Ϊѡ��״̬
		yearComboBox.setSelectedItem(year);
		judgeLeapYear(year);	//�ж��Ƿ�Ϊ����
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();
				judgeLeapYear(year);
			}
		});
		panel.add(yearComboBox);

		final JLabel yearLabel = new JLabel();
		yearLabel.setText("��");
		panel.add(yearLabel);

		//�������б�
		monthComboBox = new JComboBox<Integer>();
		monthComboBox.setMaximumRowCount(12);
		for (int m = 1; m < 13; m++) {
			monthComboBox.addItem(m);
		}
		monthComboBox.setSelectedItem(month);
		panel.add(monthComboBox);

		final JLabel monthLabel = new JLabel();
		monthLabel.setText("��    ");
		panel.add(monthLabel);

		//�ύ��ť
		final JButton submitButton = new JButton("ȷ��");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableValueV.clear();
				//-------- �ڴ�д����
				//��ȡ��
				String yearStr = yearComboBox.getSelectedItem().toString();
				int year = Integer.parseInt(yearStr);
				//��ȡ��
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
                    JOptionPane.showMessageDialog(null, "���� Excel �ɹ�!");
                } catch (IOException ex) { 
                    JOptionPane.showMessageDialog(null, "���� Excel ʧ��!");
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
		tableColumnV.add("����");
		tableColumnV.add("��̨����");
		tableColumnV.add("�����ܶ�");
		tableColumnV.add("ƽ�����Ѷ�");
		tableColumnV.add("������Ѷ�");
		tableColumnV.add("��С���Ѷ�");

		tableValueV = new Vector<Vector<Object>>();
		tableModel = new DefaultTableModel(tableValueV, tableColumnV);
		table = new MTable(tableModel);
		scrollPane.setViewportView(table);
		//
	}

	private Vector<Object> Summary() {
		Vector<Object> summary = new Vector<Object>();
		summary.add("�ܼ�");
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
            // �����ֱ���д������  
            WritableSheet ws = wwb.createSheet("����", 0);  
            // ��ӱ���  
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
            // �����  
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
            JOptionPane.showMessageDialog(null, "��������ǰ��رչ�����");  
        }  
    } 
}
