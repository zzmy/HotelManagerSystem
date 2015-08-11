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
 * �ս��˴���
 * @author ����Ԫ
 * @version 1.1 2015-01-08
 */
public class DayDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTable table;

	private Vector<String> tableColumnV;	//����ͷ��Ϣ

	private Vector<Vector<Object>> tableValueV; //�������

	private DefaultTableModel tableModel;
	
	private JComboBox<Integer> dayComboBox;	//�������б�

	private JComboBox<Integer> monthComboBox;//�������б�

	private JComboBox<Integer> yearComboBox;	//�������б�
	
	private final JScrollPane scrollPane;

	private int daysOfMonth[] = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
			30, 31 };   //ÿ�¶�Ӧ��������

	

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
		setTitle("�ս���");
		setBounds(60, 60, 860, 620);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);

		//���ϵͳ��ǰ���ڵ�������
		int year = Today.getYEAR();
		int month = Today.getMONTH();
		int day = Today.getDAY();
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
		judgeLeapYear(year);
		//Ϊ�������б�����¼���������ѡ�����º��Զ��޸����������б�
		yearComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int year = (Integer) yearComboBox.getSelectedItem();// ���ѡ�е����
				judgeLeapYear(year);// �ж��Ƿ�Ϊ���꣬��ȷ��2�·ݵ�����
				int month = (Integer) monthComboBox.getSelectedItem();// ���ѡ�е��·�
				if (month == 2) {// ���ѡ�е�Ϊ2��
					int itemCount = dayComboBox.getItemCount();// ����������˵���ǰ������
					if (itemCount != daysOfMonth[2]) {// ����������˵���ǰ������������2�·ݵ�����
						if (itemCount == 28)// ����������˵���ǰ������Ϊ28��
							dayComboBox.addItem(29);// �����Ϊ29��
						else
							// �����������˵���ǰ��������Ϊ29��
							dayComboBox.removeItem(29);// �����Ϊ28��
					}
				}
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
		monthComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int month = (Integer) monthComboBox.getSelectedItem();// ���ѡ�е��·�
				int itemCount = dayComboBox.getItemCount();// ����������˵���ǰ������
				while (itemCount != daysOfMonth[month]) {// ����������˵���ǰ������������ѡ���·ݵ�����
					if (itemCount > daysOfMonth[month]) {// �������ѡ���·ݵ�����
						dayComboBox.removeItem(itemCount);// ���Ƴ����һ��ѡ����
						itemCount--;// �����������˵���ǰ��������1
					} else {// ����С��ѡ���·ݵ�����
						itemCount++;// ���������˵���ǰ��������1
						dayComboBox.addItem(itemCount);// �����Ϊѡ����
					}
				}
			}
		});
		panel.add(monthComboBox);

		final JLabel monthLabel = new JLabel();
		monthLabel.setText("��");
		panel.add(monthLabel);

		//�����������б�
		dayComboBox = new JComboBox<Integer>();
		dayComboBox.setMaximumRowCount(10);
		int days = daysOfMonth[month];
		for (int d = 1; d <= days; d++) {
			dayComboBox.addItem(d);
		}
		dayComboBox.setSelectedItem(day);
		panel.add(dayComboBox);

		final JLabel dayLabel = new JLabel();
		dayLabel.setText("��    ");
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
                    JOptionPane.showMessageDialog(null, "���� Excel �ɹ�!");
                } catch (IOException ex) { 
                    JOptionPane.showMessageDialog(null, "���� Excel ʧ��!");
                    System.out.println(ex.getMessage());  
                    ex.printStackTrace();  
                }  
			}
		});
		
		//�ύ��ť
		final JButton submitButton = new JButton();
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//------------  �ڴ�д���� ------
				JOptionPane.showMessageDialog(null, "��ѯ�������������ʵ�");
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
				
				//��ѯ�������������嵥 sOrderFormOfDay
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
				//��ʾ����
				tableModel.setDataVector(tableValueV, tableColumnV);
				//������	
//				Container contentPane = getContentPane();
//				contentPane.remove(1);
//				contentPane.add(new FixedColumnTablePanel(tableColumnV,
//						tableValueV, 4), BorderLayout.CENTER);
//				SwingUtilities.updateComponentTreeUI(contentPane);
			}
		});
		submitButton.setText("ȷ��");
		panel.add(submitButton);
		panel.add(printButton);

		tableColumnV = new Vector<String>();
		tableColumnV.add("���");
		tableColumnV.add("̨��");
		tableColumnV.add("��̨ʱ��");
		tableColumnV.add("���ѽ��");
		//----------- �������Ӳ�Ʒѡ��
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
