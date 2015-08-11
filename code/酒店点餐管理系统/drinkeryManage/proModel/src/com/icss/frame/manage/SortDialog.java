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
 * ��ϵ������
 * @author �����
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
		setTitle("��ϵ����");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel sortNameLabel = new JLabel();
		operatePanel.add(sortNameLabel);
		sortNameLabel.setText("��ϵ���ƣ�");

		sortNameTextField = new JTextField();
		operatePanel.add(sortNameTextField);
		sortNameTextField.setColumns(20);

		final JLabel topPlaceholderLabel = new JLabel();
		topPlaceholderLabel.setPreferredSize(new Dimension(20, 40));
		operatePanel.add(topPlaceholderLabel);

		final JButton addButton = new JButton();  // ������Ӳ�ϵ���ư�ť����
		addButton.addActionListener(new ActionListener() {
			/* (non-Javadoc)
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			public void actionPerformed(ActionEvent e) {
				// ��ò�ϵ����
				String sortname = sortNameTextField.getText();
				// �鿴��ϵ���Ƶĳ����Ƿ񳬹���10������
				if(sortname.length() > 10){
					JOptionPane.showMessageDialog(null, "��ϵ�����ܳ���10���ַ�!");
					return;
				}
				if(sortname.length() == 0){
					JOptionPane.showMessageDialog(null, "�������ϵ��!");
					return;
				}
				// �鿴�ò�ϵ�����Ƿ��Ѿ�����
				Vector<Vector<Object>> table1 = null;
				try {
					table1 = SortFactory.getInitialise().queryAllData();
				} catch (Exception e1) { 
					e1.printStackTrace();
				}
				for (int i=0; i<table1.size(); i++) {
					Vector<Object> row = table1.get(i);
					if (sortname.equals(row.get(2).toString().trim())) {
							JOptionPane.showMessageDialog(null, "��ϵ���Ѵ���!");
							return;
					}
				}
				// ������ӵĲ�ϵ������Ϣ���浽���ݿ���
				SortBean bean = new SortBean();
				bean.setName(sortname);
				try{
					if(1 == SortFactory.getInitialise().addSort(bean)){
						// ���²�ϵ������Ϣ��ӵ������
						Vector<Object> rowV = new Vector<Object>();
						int row = tableValueV.size();
						rowV.add(row + 1);
						rowV.add(sortname);
						
						tableModel.addRow(rowV);
						// ��������ӵĲ�ϵ����Ϊѡ�е�
						table.setRowSelectionInterval(row-1);
						// ����ϵ�����ı�������Ϊ��
						sortNameTextField.setText("");
						TableValueRefresh();
						table.setRowSelectionInterval(tableValueV.size()-1, tableValueV.size()-1);
						JOptionPane.showMessageDialog(null, "��ϵ������!");
					}else{
						JOptionPane.showMessageDialog(null, "��ϵ���ʧ��!");
					}
				}catch(HeadlessException e1){
					e1.printStackTrace();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		addButton.setText("���");
		operatePanel.add(addButton);

		final JButton delButton = new JButton();// ����ɾ����ϵ���ư�ť����
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�еĲ�ϵ
				int selectedRow = table.getSelectedRow();
				if (-1 == selectedRow) {
					return;
				}
				// ���ѡ�еĲ�ϵ����
				String sortname = table.getValueAt(selectedRow,1).toString();
				// ����ȷ����ʾ
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ���û���" + sortname
						+ "��?", "������ʾ", JOptionPane.YES_NO_OPTION);
				// ȷ��ɾ��
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
								    // �����ݿ���ɾ����ϵ
									SortFactory.getInitialise().deleteSort(Integer.parseInt(id));
									// �ӱ�����Ƴ���ϵ��Ϣ
									tableValueV.remove(selectedRow);
									TableValueRefresh();
									JOptionPane.showMessageDialog(null, "�ɹ�ɾ��!");
									return;
								} catch (Exception e2) {
									JOptionPane.showMessageDialog(null, "�޷�ɾ��!");
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
		delButton.setText("ɾ��");
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		columnNameV.add("��    ��");
		columnNameV.add("��ϵ����");

		//---------- ��ʼ������

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
		exitButton.setText("�˳�");
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
		String tableColumns[] = {"���","��ϵ��"};
		for(int column = 0; column < tableColumns.length; column++){
			tableColumnV.add(tableColumns[column]);
		}
		try {
			//����Daoȫ����ѯ��������
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
