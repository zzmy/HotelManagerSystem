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
 * ����������
 * @author ������
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
	
	// ��
	private final Vector<Object> columnNameV = new Vector<Object>();
	
	// ��
	private Vector<Vector<Object>> tableValueV = new Vector<Vector<Object>>();

	private final DefaultTableModel tableModel;

	/**
	 * Launch the application
	 * ��ʼ̨�Ź���Ӧ�ó���
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DeskNumDialog dialog = new DeskNumDialog(null);
			dialog.addWindowListener(new WindowAdapter() {
				/**
				 * ��������
				 * @param WindowEvent e
				 */
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			System.out.println("������ʾ����");
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog
	 * ����̨�Ź���Ĵ���
	 * @param rightTable
	 */
	public DeskNumDialog(final JTable rightTable) {
		super();
		setModal(true);
		/**
		 * ��ʽ����
		 */
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("̨�Ź���");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		operatePanel.add(numLabel);
		numLabel.setText("̨  �ţ�");
		
		numTextField = new JTextField();
		numTextField.setColumns(6);
		operatePanel.add(numTextField);// ̨���ı���

		final JLabel seatingLabel = new JLabel();
		operatePanel.add(seatingLabel);
		seatingLabel.setText("  ��λ����");
		
		seatingTextField = new JTextField();
		seatingTextField.setColumns(5);
		operatePanel.add(seatingTextField);// ��λ���ı���

		final JLabel topPlaceholderLabel = new JLabel();
		topPlaceholderLabel.setPreferredSize(new Dimension(20, 40));
		operatePanel.add(topPlaceholderLabel);
		
		// �������̨�Ű�ť����
		final JButton addButton = new JButton();
		
		// ����̨�Ű�ť������
		addButton.addActionListener(new ActionListener() {
			// ����̨�Ű�ť�¼�
			public void actionPerformed(ActionEvent e) {
				
				// ��ȡ̨�ţ���ȥ����β�ո�
				String num = numTextField.getText().trim();
				
				// ��ȡ��λ������ȥ����β�ո�
				String seating = seatingTextField.getText().trim();
				
				// �鿴�û��Ƿ�������̨��
				if(num.length() == 0)
				{
					JOptionPane.showMessageDialog(table, "̨�Ų���Ϊ�գ�",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					// ̨���ı������
					numTextField.setText("");
					// �������̨���ı���
					numTextField.requestFocus();
					// ����
					return;
				}
				
				// �鿴�û��Ƿ���������λ��
				if(seating.length() == 0)
				{
					JOptionPane.showMessageDialog(table, "��λ������Ϊ�գ�",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					// ��λ���ı������
					seatingTextField.setText("");
					// ���������λ���ı���
					seatingTextField.requestFocus();
					// ����
					return;
				}
				
				// �鿴̨�ŵĳ����Ƿ񳬹���4λ
				if(num.length() > 4)
				{
					JOptionPane.showMessageDialog(table, "̨�ŵĳ��Ȳ��ó���4λ��",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					// ̨���ı������
					numTextField.setText("");
					// �������̨���ı���
					numTextField.requestFocus();
					return;
				}
				
				// �鿴̨���Ƿ�Ϊ����
				if(!Validate.execute("[1-9]{1}([0-9]{0,1})([0-9]{0,1})([0-9]{0,1})", num))
				{
					JOptionPane.showMessageDialog(table, "�׸����ֲ�Ϊ0���뱣̨֤��Ϊ�������ͣ�",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					// ̨���ı������
					numTextField.setText("");
					// �������̨���ı���
					numTextField.requestFocus();
					return;
				}
				
				
				// ��֤��λ���Ƿ���1����99֮��
				if(!Validate.execute("[1-9]{1}([0-9]{0,1})", seating))
				{
					JOptionPane.showMessageDialog(table, "�뱣֤��λ����1����99֮�䣡",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					// ��λ���ı������
					seatingTextField.setText("");
					// ���������λ���ı���
					seatingTextField.requestFocus();
					return;
				}
				
				// �鿴��̨���Ƿ��Ѿ�����
				// ������ά��
				Vector<Vector<Object>> vector = new Vector<Vector<Object>>();
				try
				{
					// ��ѯDesk�����е���Ϣ
					vector = DeskFactory.getInitialise().queryAllData();
				}catch(Exception e1)
				{
					System.out.println("��ѯ����");
					e1.printStackTrace();
				}
				
				// ѭ����ȡÿ����Ϣ
				for(int i=0; i<vector.size(); i++)
				{
					Vector<Object> row = vector.get(i);
					// �Ƚϵ�ǰ�������̨����ȥ����β�ո���̨���Ƿ��ظ�
					if(num.equals(row.get(1).toString().trim()))
					{
						JOptionPane.showMessageDialog(table, "̨���Ѿ�����");
						numTextField.requestFocus();
						return;
					}
				}
			
				// ��õ�ǰӵ��̨�ŵĸ���
				int row = tableValueV.size();
				
				// ����һ��������̨�ŵ�����
				Vector<Object> rowsV = new Vector<Object>();
				
				// ���������
				rowsV.add(row + 1);
				
				// ���̨��
				rowsV.add(num);
				
				// �����λ��
				rowsV.add(seating);
				
				// ����̨����Ϣ��ӵ������
				tableValueV.add(rowsV);
				//��ʾ��ͷ
				tableModel.setDataVector(tableValueV, columnNameV);
						
				// ��������ӵ�̨��Ϊѡ�е�
				table.setRowSelectionInterval(row, row);
				
				// ��̨���ı�������Ϊ��
				numTextField.setText("");
				
				// ����λ���ı�������Ϊ��
				seatingTextField.setText("");
				
				// ������ӵ�̨����Ϣ���浽���ݿ���
				// ��װ����,�ǵý��ַ�������ת������
				DeskBean bean = new DeskBean();
				bean.setNum(Integer.parseInt(num));
				bean.setSeating(Integer.parseInt(seating));
				try {
					if(1 == DeskFactory.getInitialise().addDesk(bean))
					{
						JOptionPane.showMessageDialog(table, "̨�������ɣ�", "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);
						table.setRowSelectionInterval(tableValueV.size()-1, tableValueV.size()-1);
					}else{
						JOptionPane.showMessageDialog(table, "̨�����ʧ�ܣ���ȷ�ϲ�̨��Ϣ��", "������ʾ",
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
		
		addButton.setText("���");
		operatePanel.add(addButton);
		
		//����ɾ��̨�Ű�ť����
		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ���ѡ�еĲ�̨
				int selectedRow = table.getSelectedRow();
				// δѡ���κβ�̨��������ʾ
				if(selectedRow == -1)
				{
					JOptionPane.showMessageDialog(table, "δѡ���κβ�̨", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String num = table.getValueAt(selectedRow, 1).toString().trim();
				String seating = table.getValueAt(selectedRow, 2).toString().trim();
						
				// ��֯ȷ����Ϣ
				int i = JOptionPane.showConfirmDialog(table, "ȷ��Ҫɾ����̨\n̨�ţ�" + num
						+ "\n��λ����" + seating, "������ʾ", JOptionPane.YES_NO_OPTION);
				
				// �鿴�ò�̨�Ƿ����ڱ�ʹ��
				if (i == JOptionPane.YES_OPTION) {
					TableModel rightTableModel = rightTable.getModel();
					for (int j=0; j<rightTable.getRowCount(); j++) {
						String usingTable = rightTableModel.getValueAt(j, 1).toString();
						// �ò�̨���ڱ�ʹ�ã�����ɾ��������
						if (num.equals(usingTable)) {
							JOptionPane.showMessageDialog(null, "��̨����ʹ��!");
							return;
						}
					}
					//�û����ȷ�ϰ�ť��ȷ��ɾ��
					try {
						//����Dao, �����ݿ���ɾ��
						if(1 == DeskFactory.getInitialise().deleteDesk(Integer.parseInt(num)))
						{
							JOptionPane.showMessageDialog(table, "ɾ����̨�ɹ���", "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);
							//�ӱ����ɾ����ǰ��
							tableModel.removeRow(selectedRow);
						}
					}catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(table, "�޷�ɾ����", "������ʾ",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				
				// ˢ�±��,�������
				for (i=0; i<tableValueV.size(); i++) {
					Vector<Object> row = tableValueV.get(i);
					row.set(0, i+1);
					tableValueV.set(i, row);					
				}
				//��ʾ��ͷ
				tableModel.setDataVector(tableValueV, columnNameV);
			}
		});
		
		delButton.setText("ɾ��");
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "��  ��", "̨  ��", "��λ��" };
		for (int i = 0; i < columnNames.length; i++) {
			columnNameV.add(columnNames[i]);
		}
		
		//����Daoȫ����ѯ��������
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

		//�˳����
		final JPanel exitPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		exitPanel.setLayout(flowLayout);
		getContentPane().add(exitPanel, BorderLayout.SOUTH);
		
		//�˳���ť
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
}
