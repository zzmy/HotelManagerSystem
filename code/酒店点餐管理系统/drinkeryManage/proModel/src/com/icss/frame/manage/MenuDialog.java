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
 * ��Ʒ������
 * @author ��Ө ����Ԫ
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
	// ��
	
	// ��
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
		setTitle("��Ʒ����");
		setBounds(100, 100, 800, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		numLabel.setText("��  �ţ�");
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
		nameLabel.setText("���ƣ�");

		nameTextField = new JTextField();
		// nameTextField.setName("����");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_1.gridx = 3;
		gridBagConstraints_1.gridy = 0;
		operatePanel.add(nameTextField, gridBagConstraints_1);
		nameTextField.setColumns(21);

		final JLabel unitPriceLabel = new JLabel();
		unitPriceLabel.setText("���ۣ�");
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 4;
		operatePanel.add(unitPriceLabel, gridBagConstraints_9);

		final JLabel unitLabel = new JLabel();
		unitLabel.setText("��λ��");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 4;
		operatePanel.add(unitLabel, gridBagConstraints_8);

		unitTextField = new JTextField();
		unitTextField.setName("��λ");
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
		codeLabel.setText("�����룺");

		codeTextField = new JTextField();
		codeTextField.setName("������");
		codeTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_3.gridx = 1;
		gridBagConstraints_3.gridy = 1;
		operatePanel.add(codeTextField, gridBagConstraints_3);

		final JLabel sortLabel = new JLabel();
		sortLabel.setText("��ϵ��");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_4.gridy = 1;
		gridBagConstraints_4.gridx = 2;
		operatePanel.add(sortLabel, gridBagConstraints_4);

		sortComboBox = new JComboBox<String>();
		sortComboBox.addItem("��ѡ��");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.anchor = GridBagConstraints.WEST;
		gridBagConstraints_7.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 3;
		operatePanel.add(sortComboBox, gridBagConstraints_7);

		unitPriceTextField = new JTextField();
		unitPriceTextField.setName("����");
		unitPriceTextField.setColumns(8);
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 1;
		gridBagConstraints_12.gridx = 5;
		operatePanel.add(unitPriceTextField, gridBagConstraints_12);

		final JLabel label = new JLabel();
		label.setText("Ԫ");
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

		//����²�Ʒ��ť
		final JButton addButton = new JButton();
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ò�Ʒ����
				String name = nameTextField.getText();
				// ��֤��Ʒ�������ֻ��Ϊ 10 ������
				if (10 < name.length()) {
					JOptionPane.showMessageDialog(null, "��Ʒ�����Ϊ10������!");
					return;
				}
				// ��֤��Ʒ�ı���Ϊ��
				if (0 == name.length()) {
					JOptionPane.showMessageDialog(null, "��Ʒ������Ϊ��!");
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
						// �жϵ�ǰ��Ʒ�Ƿ��Ѿ����ڲ���״̬Ϊ�����ۡ���������ʾ "�ò�Ʒ�Ѿ����ڣ�
						String state = row.get(7).toString().trim();
						if (state.equals("����")) {
							JOptionPane.showMessageDialog(null, "��Ʒ�Ѵ���");
							return;
						}
						else {
							MenuBean menuBean = new MenuBean();
							menuBean.setState("����");
							try {
								int num;
								String str;
								Vector<Object> rowNew = new Vector<Object>();
								//���
								rowNew.add(tableValueV.size());
								//���
								num = Integer.parseInt(row.get(1).toString().trim());
								menuBean.setNum(num);
								rowNew.add(num);
								//��ϵ��
								str = row.get(2).toString().trim();
								num = Integer.parseInt(str);
								menuBean.setSort_id(num);
								//���ݲ�ϵ��ȡ�ò�ϵ��
								str = GetSortNamtById(str);
								rowNew.add(str);
								//����
								str = row.get(3).toString().trim();
								menuBean.setName(str);
								rowNew.add(str);
								//������
								str = row.get(4).toString().trim();
								menuBean.setCode(str);
								rowNew.add(str);
								//��λ
								str = row.get(5).toString().trim();
								menuBean.setUnit(str);
								rowNew.add(str);
								//����
								num = Integer.parseInt(row.get(6).toString().trim());
								menuBean.setUnit_price(num);
								rowNew.add(num);
								tableValueV.add(rowNew);
								TableValueRefresh();
								tableModel.setDataVector(tableValueV, tableColumnV);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "����ʧ��!");
								e1.printStackTrace();
								return;
							}
							try {
								MenuFactory.getInitialise().updateMenu(menuBean);
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(null, "�������ݿ�ʧ��!");
								e1.printStackTrace();
								return;
							}
							JOptionPane.showMessageDialog(null, "����ɹ�!");
							return;
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// ��ò�Ʒ������ϵ
				String sort = sortComboBox.getSelectedItem().toString();
				// ��֤��ϵ�����˵�
				if (sort.equals("��ѡ��")) {
					JOptionPane.showMessageDialog(null, "��ѡ���ϵ!");
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
				// ��ò�Ʒ������
				String code = codeTextField.getText();
				// ��֤���������ֻ��Ϊ 10 ���ַ�
				if (10 < code.length()) {
					JOptionPane.showMessageDialog(null, "���������Ϊ10������!");
					return;
				}
				if (0 == code.length()) {
					JOptionPane.showMessageDialog(null, "�����벻��Ϊ��!");
					return;
				}
				// ��ò�Ʒ��λ
				String unit = unitTextField.getText();
				// ��֤��λ���ֻ��Ϊ 2 ������
				if (2 < unit.length()) {
					JOptionPane.showMessageDialog(null, "��λ���Ϊ2������!");
					return;
				}
				if (0 == unit.length()) {
					JOptionPane.showMessageDialog(null, "��λ����Ϊ��!");
					return;
				}
				// ��ò�Ʒ����
				String unitPriceStr = unitPriceTextField.getText();
				double unitPrice = 0;
				try {
					unitPrice = Double.parseDouble(unitPriceStr);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "���۱���Ϊ����");
					e1.printStackTrace();
					return;
				}
				// ��֤���۱����� 1����9999
				if (unitPrice<1 || unitPrice>9999) {
					JOptionPane.showMessageDialog(null, "���۱�����1��9999!");
					return;
				}
				// ��ò�Ʒ���
				String num = null;
				try {
					num = MenuFactory.getInitialise().queryMaxNum();
					num = getNextNum(num);
				} catch (Exception e1) {
					num = getNextNum(null);
					e1.printStackTrace();
				}
				// ���²�Ʒ��Ϣ���浽���ݿ�
				MenuBean bean = new MenuBean();
				bean.setNum(Integer.parseInt(num));
				bean.setName(name);
				bean.setCode(code);
				bean.setSort_id(sortId);				
				bean.setUnit(unit);
				bean.setUnit_price(unitPrice);
				//����Dao
				try {
					MenuFactory.getInitialise().addMenu(bean);
					rowV = new Vector<Object>();
					int row = table.getRowCount();
					//����к�
					rowV.add(row + 1);
					//����û�����
					rowV.add(num);
					rowV.add(sort);
					rowV.add(name);	
					rowV.add(code);		
					rowV.add(unit);
					rowV.add(unitPrice);
					rowV.add("����");
					//��������������
					tableValueV.add(rowV);
					TableValueRefresh();
					tableModel.setDataVector(tableValueV, tableColumnV);
					//����Ϊѡ��״̬
					table.setRowSelectionInterval(row, row);
					
					// ��ձ������
					nameTextField.setText("");
					codeTextField.setText("");
					unitTextField.setText("");
					unitPriceTextField.setText("");
					sortComboBox.setToolTipText("");
					JOptionPane.showMessageDialog(null, "��Ʒ�����ɣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "��Ʒ���ʧ�ܣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					e1.printStackTrace();
					return;
				}
			}
		});
		addButton.setText("���");
		panel.add(addButton);

		//ɾ����ť
		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�еĲ�Ʒ
				int selectedRow = table.getSelectedRow();
				table.getValueAt(selectedRow, 0);
				String name = table.getValueAt(selectedRow, 3).toString();
				String num = table.getValueAt(selectedRow, 1).toString();
				// ����ȷ����ʾ��
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ����Ʒ��" + name
						+ "��?", "������ʾ", JOptionPane.YES_NO_OPTION);
				// ȷ��ɾ��
				if(i == JOptionPane.YES_OPTION){
					//���ݿ����
					try {
						MenuFactory.getInitialise().deleteMenu(Integer.parseInt(num));
						for (i=0; i<tableValueV.size(); i++) {
							Vector<Object> row = tableValueV.get(i);
							row.set(0, i+1);
							tableValueV.set(i, row);					
						}
						//�ӱ����ɾ����ǰ��
						tableValueV.remove(selectedRow);
						TableValueRefresh();
						
						table.repaint();
						//����Dao
						JOptionPane.showMessageDialog(null, "ɾ����Ʒ�ɹ���", "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "ɾ����Ʒʧ�ܣ�", "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);
						e1.printStackTrace();
					}
				}
			}
		});
		delButton.setText("ɾ��");
		panel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "�� ��", "�� ��", "�� ϵ","�� ��" ,
				"������", "�� λ", "�� ��"};
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
				//�����Ʒ����ɾ��״̬
				if (tmp.equals("ɾ��")) {
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

		
		
		//---------- ��ò�ϵ�����б�����
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
	
	//��ȡ��һ���˵����
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