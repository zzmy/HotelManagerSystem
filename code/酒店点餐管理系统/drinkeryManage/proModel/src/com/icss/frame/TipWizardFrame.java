package com.icss.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import com.icss.bean.Order_FormBean;
import com.icss.bean.Order_ItemBean;
import com.icss.dao.desk.DeskFactory;
import com.icss.dao.menu.MenuFactory;
import com.icss.dao.order_form.Order_FormFactory;
import com.icss.dao.order_item.Order_ItemFactory;
import com.icss.frame.check_out.DayDialog;
import com.icss.frame.check_out.MonthDialog;
import com.icss.frame.check_out.YearDialog;
import com.icss.frame.manage.DeskNumDialog;
import com.icss.frame.manage.MenuDialog;
import com.icss.frame.manage.SortDialog;
import com.icss.frame.user.UpdatePasswordDialog;
import com.icss.frame.user.UserManagerDialog;
import com.icss.mwing.MButton;
import com.icss.mwing.MTable;
import com.icss.tool.Today;
import com.icss.tool.Validate;

/**
 * �����湦��
 * @author ������ ��Ө �����
 * @version 1.0 2015-01-07
 */
public class TipWizardFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel timeLabel;

	private JTextField amountTextField;

	private JTextField unitTextField;

	private JTextField nameTextField;

	private JTextField codeTextField;

	private JComboBox<String> numComboBox;//ת����String����

	private JTextField changeTextField;

	private JTextField realWagesTextField;

	private JTextField expenditureTextField;

	private ButtonGroup buttonGroup = new ButtonGroup();

	private MTable rightTable;//��̨�б�

	private Vector<String> rightTableColumnV;

	private Vector<Vector<Object>> rightTableValueV;//��ά������

	private DefaultTableModel rightTableModel;

	private MTable leftTable;//ǩ���б�

	private Vector<String> leftTableColumnV;

	private Vector<Vector<Object>> leftTableValueV;

	private DefaultTableModel leftTableModel;

	private Vector<Vector<Vector<Object>>> menuOfDeskV;//��ά������

	private Dimension screenSize;
	
	private Vector<Object> user;

	public void setUser(final Vector<Object> user) {
		this.user = user;
	}
	/**
	 * ����������
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			TipWizardFrame frame = new TipWizardFrame(null);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ��������
	 * @author ������
	 * @version 1.0 2015-01-07
	 */
	private void makeOutAnInvoice()
	{
		//���̨��
		String deskNum = numComboBox.getSelectedItem().toString();
		//�����Ʒ����
		String menuName = nameTextField.getText();
		//�������
		String menuAmount = amountTextField.getText();
		//��ò�ѯ��ʽ
		String choice = codeTextField.getText();

		////////
		//��֤//
		///////
		//��֤�Ƿ�ѡ��̨��
		if(deskNum.equals("��ѡ��"))
		{
			JOptionPane.showMessageDialog(null, "��ѡ��̨�ţ�", "������ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;		
		}
		//��֤�Ƿ��Ѿ�ȷ����Ʒ
		if(menuName.length() == 0)
		{
			JOptionPane.showMessageDialog(null, "��ѡ����Ҫ�Ĳ�Ʒ��", "������ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;	
		}
		//��֤�����Ƿ���1����99֮��
		if(!Validate.execute("[1-9]{1}([0-9]{0,1})", menuAmount))
		{
			JOptionPane.showMessageDialog(null, "���������������������1����99֮�䣡",
					"������ʾ", JOptionPane.INFORMATION_MESSAGE);
			// �����ı������
			amountTextField.setText("1");
			return;
		}
		
		//����̨��Ϣ
		//��ÿ�̨�б�ѡ�е�̨��
		int rightSelectedRow = rightTable.getSelectedRow();
		//Ĭ�ϵ������Ϊ0
		int leftRowCount = 0;
		
		//û�б�ѡ�е�̨�ţ����¿�̨
		if(rightSelectedRow == -1)
		{
			//��ѡ�е�̨��Ϊ�¿���̨
			rightSelectedRow = rightTable.getRowCount();
			//����һ�������¿�̨����������deskV
			Vector<Object> deskV = new Vector<Object>();
			//��ӿ�̨���
			deskV.add(rightSelectedRow + 1);
			//��ӿ�̨��
			deskV.add(deskNum);
			//��ӿ�̨ʱ��Today.getTime()
			deskV.add(timeLabel.getText());
			//����̨��Ϣ��ӵ�����̨�б���
			rightTableModel.addRow(deskV);
			//ѡ���¿���̨
			rightTable.setRowSelectionInterval(rightSelectedRow);
			//���һ����Ӧ��ǩ���б�
			menuOfDeskV.add(new Vector<Vector<Object>>());		
		}
		//ѡ�е�̨���Ѿ���̨������Ӳ�Ʒ
		else
		{
			//����ѵ�˵�����
			leftRowCount = leftTable.getRowCount();		
		}
		
		//��������Ϣ
		Vector<Object> oneMenu = null;
		Vector<Vector<Object>> allMenu = null;
		try
		{
			//�ж�codeTextField�е�choice������
			if(!Validate.execute("\\d{8}", choice))//Ϊ��������ĸ����
			{
				//��ñ����Ʒ
				allMenu = MenuFactory.getInitialise().queryByCode(choice);
			}
			else//Ϊ��Ʒ�����������
			{
				allMenu = MenuFactory.getInitialise().queryByNum(Integer.parseInt(choice));
			}
			
			//����ѯ����ǰ��Ʒ
			if(allMenu.size() > 0)
			{
				oneMenu = allMenu.get(0);			
			}			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
						
		//����Ʒ����תΪint��
		int amount = Integer.parseInt(menuAmount);
		//����Ʒ����תΪdouble��
		double unitPrice = Double.parseDouble(oneMenu.get(6).toString());
		//�����Ʒ���Ѷ�
		double money = amount * unitPrice;
		//������ǰ��Ʒ���� menuV
		Vector<Object> menuV = new Vector<Object>();
		//����µ�˱�
		menuV.add("New");
		//��ӵ�����
		menuV.add(leftRowCount + 1);		
		//��Ӳ�Ʒ���
		menuV.add(oneMenu.get(1));
		
		//��Ӳ�Ʒ����
		menuV.add(menuName);
		//��Ӳ�Ʒ��λ
		menuV.add(oneMenu.get(5).toString().trim());
		//��Ӳ�Ʒ����
		menuV.add(amount);
		//��Ӳ�Ʒ����
		menuV.add(unitPrice);
		//��Ӳ�Ʒ���Ѷ�
		menuV.add(money);
		
		//�������Ϣ��ӵ���ǩ���б���
		leftTableModel.addRow(menuV);
		//���µ������Ϊѡ����
		leftTable.setRowSelectionInterval(leftRowCount);
		//���µ����Ϣ��ӵ���Ӧ��ǩ���б�
		menuOfDeskV.get(rightSelectedRow).add(menuV);
		
		//��������룬�����ȿؼ�
		codeTextField.setText(null);
		nameTextField.setText(null);
		unitTextField.setText(null);
		amountTextField.setText("1");
	}
	
	/**************************************************************************************/

	/**
	 * Create the frame
	 */
	public TipWizardFrame(final Vector<Object> user) {
		super();
		this.user = user;
		setTitle(" ICSS �Ƽ�");
		setResizable(false);
		setBounds(0, 0, 1024, 768);
		setExtendedState(TipWizardFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//���ö���ͼƬ
		final JLabel topLabel = new JLabel();
		topLabel.setPreferredSize(new Dimension(0, 100));
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		URL topUrl = this.getClass().getResource("/img/top.jpg");
		ImageIcon topIcon = new ImageIcon(topUrl);
		topLabel.setIcon(topIcon);
		getContentPane().add(topLabel, BorderLayout.NORTH);

		final JSplitPane splitPane = new JSplitPane();// �����ָ�������
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// ����Ϊˮƽ�ָ�
		splitPane.setDividerLocation(755);// �������Ĭ�ϵķָ�λ��
		splitPane.setDividerSize(10);// ���÷ָ����Ŀ��
		splitPane.setOneTouchExpandable(true);// ����Ϊ֧�ֿ���չ��/�۵��ָ���
		splitPane.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));// �������ı߿�
		getContentPane().add(splitPane, BorderLayout.CENTER);// ���ָ������ӵ��ϼ�������

		final JPanel leftPanel = new JPanel();// �������ڷָ����������ͨ������
		leftPanel.setLayout(new BorderLayout());// �������Ĳ��ֹ�����
		splitPane.setLeftComponent(leftPanel);// ����ͨ��������ӵ��ָ��������

		final JLabel leftTitleLabel = new JLabel();
		leftTitleLabel.setFont(new Font("", Font.BOLD, 14));
		leftTitleLabel.setPreferredSize(new Dimension(0, 25));
		leftTitleLabel.setText(" ǩ���б�");
		leftPanel.add(leftTitleLabel, BorderLayout.NORTH);

		final JScrollPane leftScrollPane = new JScrollPane();
		leftPanel.add(leftScrollPane);

		menuOfDeskV = new Vector<Vector<Vector<Object>>>();

		//����������ͷ��Ϣ
		leftTableColumnV = new Vector<String>();
		String leftTableColumns[] = { "  ", "��    ��", "��Ʒ���", "��Ʒ����", "��    λ",
				"��    ��", "��    ��", "��    ��" };
		for (int i = 0; i < leftTableColumns.length; i++) {
			leftTableColumnV.add(leftTableColumns[i]);
		}

		leftTableValueV = new Vector<Vector<Object>>();

		leftTableModel = new DefaultTableModel(leftTableValueV,
				leftTableColumnV);
		/**
		 * �������ѽ��
		 * @author �����
		 * @version 1.0 2015-01-07
		 */
		leftTableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {
					// ͨ�����ģ�ͼ�����ʵ���Զ�����
				    // ���ǩ���б��е�����
					int rowCount = leftTable.getRowCount();
					// Ĭ������ 0 Ԫ
					double expenditure = 0.0;
					// ͨ��ѭ���������ѽ��
					for(int i = 0; i < rowCount; i++){
						// �ۼ����ѽ��
						expenditure += Double.valueOf(leftTable.getValueAt(i,7)
								.toString());
					}	
					// ���¡����ѽ��ı���
					expenditureTextField.setText(expenditure + "0");			
			}
		});
		/**********************************************************************/

		leftTable = new MTable(leftTableModel);
		leftScrollPane.setViewportView(leftTable);

		final JPanel rightPanel = new JPanel();// �������ڷָ�����Ҳ����ͨ������
		rightPanel.setLayout(new BorderLayout());
		splitPane.setRightComponent(rightPanel);// ����ͨ��������ӵ��ָ������Ҳ�

		final JLabel rightTitleLabel = new JLabel();
		rightTitleLabel.setFont(new Font("", Font.BOLD, 14));
		rightTitleLabel.setPreferredSize(new Dimension(0, 25));
		rightTitleLabel.setText(" ��̨�б�");
		rightPanel.add(rightTitleLabel, BorderLayout.NORTH);

		final JScrollPane rightScrollPane = new JScrollPane();
		rightPanel.add(rightScrollPane);

		//�����Ҳ����ͷ��Ϣ
		rightTableColumnV = new Vector<String>();
		rightTableColumnV.add("��    ��");
		rightTableColumnV.add("̨    ��");
		rightTableColumnV.add("��̨ʱ��");

		rightTableValueV = new Vector<Vector<Object>>();

		rightTableModel = new DefaultTableModel(rightTableValueV,
				rightTableColumnV);

		/**
		 * ѡ�п�̨�б����ǩ���б�
		 * @author ��Ө
		 * @version 1.0 2015-01-07
		 */
		rightTable = new MTable(rightTableModel);
		//�Ҳ��������������ѡ��ĳ�к�������Զ���ʾ��̨����˿���Ϣ
		rightTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// ��á���̨�б��е�ѡ����
				int rSelectedRow= rightTable.getSelectedRow();
				// ��ա�ǩ���б��е�������
				leftTableValueV.removeAllElements();
				// ��ѡ��̨�ŵ�ǩ���б���ӵ���ǩ���б���
				leftTableValueV.addAll(menuOfDeskV.get(rSelectedRow));
				// ˢ�¡�ǩ���б�
				leftTableModel.setDataVector(leftTableValueV, leftTableColumnV);
				// ѡ�С�ǩ���б��еĵ�һ��
				leftTable.setRowSelectionInterval(0);
				// ͬ��ѡ�С�̨�š������˵��е���Ӧ̨��
				numComboBox.setSelectedItem(rightTable.getValueAt(rSelectedRow, 1));
			}
		});
		/**************************************************************************/
		
		rightScrollPane.setViewportView(rightTable);

		final JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(0, 230));
		bottomPanel.setLayout(new BorderLayout());
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		final JPanel orderDishesPanel = new JPanel();
		orderDishesPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		bottomPanel.add(orderDishesPanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		numLabel.setFont(new Font("", Font.BOLD, 12));
		numLabel.setText("̨�ţ�");
		orderDishesPanel.add(numLabel);
		
		/**
		 * ���ÿ�̨��̨�š������б�����
		 * @author ��Ө
		 * @version 1.0 2015-01-07
		 */
		//���ÿ�̨��̨�š������б�����
		numComboBox = new JComboBox<String>();
		numComboBox.addItem("��ѡ��");
		numComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��ÿ�̨�б��е����������ѿ�̨��
				int rowCount = rightTable.getRowCount();				
				if(0 < rowCount){	// ���Ѿ��п�̨
					// ��á�̨�š������˵���ѡ�е�̨��
					String selectedDeskNum = numComboBox.getSelectedItem()
							.toString();
					// Ĭ��ѡ�е�̨��δ��̨
					int needSelectedRow = -1;
					// ͨ��ѭ���鿴ѡ�е�̨���Ƿ��Ѿ���̨
					for(int row = 0;row < rowCount;row++){
						//����ѿ�̨��̨��
						String openedDeskNum = rightTable.getValueAt(row, 1)
								.toString();
						//�鿴ѡ�е�̨���Ƕ���
						if(selectedDeskNum.equals(openedDeskNum)){
							//ѡ�е�̨���ѿ�̨
							needSelectedRow = row;
							//rightTable.setRowSelectionInterval(needSelectedRow);
							break;
						}
					}
					if(needSelectedRow == -1){// ѡ�е�̨����δ��̨�����¿�̨
						// ȡ��ѡ�񡰿�̨�б��е�ѡ����
						rightTable.clearSelection();
						// ��ա�ǩ���б��е�������
						leftTableValueV.removeAllElements();
						// ˢ�¡�ǩ���б�
						leftTableModel
						.setDataVector(leftTableValueV, leftTableColumnV);
					} 
					else {// ѡ�е�̨���Ѿ���̨������Ӳ�Ʒ
						if(needSelectedRow != rightTable.getSelectedRow())
						{// ��̨�š������˵���ѡ�е�̨���ڡ���̨�б���δ��ѡ��
							// �ڡ���̨�б���ѡ�и�̨��
							rightTable.setRowSelectionInterval(needSelectedRow);
							// ��ա�ǩ���б��е�������
							leftTableValueV.removeAllElements();
							// ��ѡ��̨�ŵ�ǩ���б���ӵ���ǩ���б���
							leftTableValueV
							.addAll(menuOfDeskV.get(needSelectedRow));
							// ˢ�¡�ǩ���б�
							leftTableModel
							.setDataVector(leftTableValueV, leftTableColumnV);
							// ѡ�С�ǩ���б��еĵ�һ��
							leftTable.setRowSelectionInterval(0);
						}	
					}
				}
			}
		});
		//----------- ��ʼ����̨�����б�����
		try {
			Vector<Vector<Object>> TipTable = DeskFactory.getInitialise()
					.queryAllData();
			for (int i=0; i<TipTable.size(); i++) {
				Vector<Object> row = TipTable.get(i);
				String num  = row.get(1).toString();
				numComboBox.addItem(num);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//----------- �Դ˴�д����
		orderDishesPanel.add(numComboBox);
		/***********************************************************************/

		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		orderDishesPanel.add(panel);

		final JLabel codeALabel = new JLabel();
		codeALabel.setFont(new Font("", Font.BOLD, 12));
		codeALabel.setText("  ��Ʒ��");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		panel.add(codeALabel, gridBagConstraints);

		final JRadioButton numRadioButton = new JRadioButton();
		numRadioButton.setFont(new Font("", Font.BOLD, 12));
		buttonGroup.add(numRadioButton);
		numRadioButton.setText("���");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 1;
		panel.add(numRadioButton, gridBagConstraints_1);

		final JLabel codeBLabel = new JLabel();
		codeBLabel.setFont(new Font("", Font.BOLD, 12));
		codeBLabel.setText("/");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 2;
		panel.add(codeBLabel, gridBagConstraints_2);

		final JRadioButton codeRadioButton = new JRadioButton();
		codeRadioButton.setFont(new Font("", Font.BOLD, 12));
		buttonGroup.add(codeRadioButton);
		codeRadioButton.setSelected(true);
		codeRadioButton.setText("������");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 3;
		panel.add(codeRadioButton, gridBagConstraints_3);

		final JLabel codeCLabel = new JLabel();
		codeCLabel.setText("����");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 4;
		panel.add(codeCLabel, gridBagConstraints_4);
		
		/**
		 * ��Ʒ��ź��������ѯ
		 * @author ������
		 * @version 1.0 2015-01-07
		 */
		
		//ת������Ʒ��Ų�ѯ��ʽ
		numRadioButton.addFocusListener(new FocusListener() 
		{
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				// ���á���Ʒ���ơ��ı���Ϊ��
				nameTextField.setText(null);
				// ���á���λ���ı���Ϊ��
				unitTextField.setText(null);
			}
		});
		
		//ת�����������ѯ��ʽ
		codeRadioButton.addFocusListener(new FocusListener() 
		{
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				// TODO Auto-generated method stub
				// ���á���Ʒ���ơ��ı���Ϊ��
				nameTextField.setText(null);
				// ���á���λ���ı���Ϊ��
				unitTextField.setText(null);			
			}
		});
		
		//�������б��¼�����
		codeTextField = new JTextField();
		codeTextField.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ')// �ж��û�������Ƿ�Ϊ�ո�
					e.consume();// ����ǿո������ٴ˴ΰ����¼�
			}

			public void keyReleased(KeyEvent e) {// ͨ�����̼�����ʵ�����ܻ�ȡ��Ʒ
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {// ���»س���
				} else {
					String choice = (codeRadioButton.isSelected()? "code":"num");
					codeTextField.requestFocus();
					nameTextField.setText(null);
					unitTextField.setText(null);	
					
					//����ѡ�������뷽ʽ
					if(choice == "code")
					{
						// �����������
						String input = codeTextField.getText().trim();
						// �������ݲ�Ϊ��
						if(input.length() == 0)
						{
							//JOptionPane.showMessageDialog(null,
							//"������Ϊ��,�����������룡","������ʾ",
							//JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						
						// ���������ѯ����ѯ���������Ĳ�Ʒ
						Vector<Vector<Object>> vector = null;
						try
						{
							vector = MenuFactory.getInitialise().queryByCode(input);
						}catch(Exception e1)
						{
							e1.printStackTrace();
						}
									
						if(vector.size() != 0)// ���ڷ��������Ĳ�Ʒ
						{
							// ��õ�һ�����������Ĳ�Ʒ
							// ���á���Ʒ���ơ��ı���Ϊ���������Ĳ�Ʒ����
							nameTextField.setText(vector.get(0).
									get(3).toString().trim());
							// ���á���λ���ı���Ϊ���������Ĳ�Ʒ��λ
							unitTextField.setText(vector.get(0).
									get(5).toString().trim());	
						}
						else// �����ڷ��������Ĳ�Ʒ
						{
							//JOptionPane.showMessageDialog(null, 
							//"�ò�Ʒ��ʱ���ṩ��","������ʾ", 
							//JOptionPane.INFORMATION_MESSAGE);
							codeTextField.requestFocus();
							// ���á���Ʒ���ơ��ı���Ϊ��
							nameTextField.setText(null);
							// ���á���λ���ı���Ϊ��
							unitTextField.setText(null);	
									return;
						}				
					}
					//����ѡ����Ʒ��ŷ�ʽ
					else
					{
						// �����������
						String input = codeTextField.getText().trim();
						// �������ݲ�Ϊ��
						if(input.length() == 0)
						{
							//JOptionPane.showMessageDialog(null, 
							//"��Ʒ����Ϊ��,��������Ʒ��ţ�",
							//"������ʾ", JOptionPane.INFORMATION_MESSAGE);
						}
						
						// ����Ų�ѯ����ѯ���������Ĳ�Ʒ		
						if(input.length() == 8)//���Ϊ8λ
						{
							Vector<Vector<Object>> vector = null;
							try
							{
								vector = MenuFactory.getInitialise()
										.queryByNum(Integer.parseInt(input));
							}catch(Exception e1)
							{
								e1.printStackTrace();
								return;
							}
							if(vector.size() != 0)// ���ڷ��������Ĳ�Ʒ
							{
								// ��õ�һ�����������Ĳ�Ʒ
								// ���á���Ʒ���ơ��ı���Ϊ���������Ĳ�Ʒ����
								nameTextField.setText(vector.get(0)
										.get(3).toString().trim());
								// ���á���λ���ı���Ϊ���������Ĳ�Ʒ��λ
								unitTextField.setText(vector.get(0)
										.get(5).toString().trim());	
							}
							else// �����ڷ��������Ĳ�Ʒ
							{
								//JOptionPane.showMessageDialog(null, 
								//"�ò�Ʒ��ʱ���ṩ��","������ʾ", 
								//JOptionPane.INFORMATION_MESSAGE);
								codeTextField.requestFocus();
								// ���á���Ʒ���ơ��ı���Ϊ��
								nameTextField.setText(null);
								// ���á���λ���ı���Ϊ��
								unitTextField.setText(null);	
										return;
							}
						}
														
					}
							
				}
			}
			
		});
		/********************************************************************/
		
		codeTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.gridy = 0;
		gridBagConstraints_5.gridx = 5;
		panel.add(codeTextField, gridBagConstraints_5);

		final JLabel nameLabel = new JLabel();
		nameLabel.setFont(new Font("", Font.BOLD, 12));
		nameLabel.setText("  ��Ʒ���ƣ�");
		orderDishesPanel.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextField.setEditable(false);
		nameTextField.setFocusable(false);
		nameTextField.setColumns(20);
		orderDishesPanel.add(nameTextField);

		final JLabel unitLabel = new JLabel();
		unitLabel.setFont(new Font("", Font.BOLD, 12));
		unitLabel.setText("  ��λ��");
		orderDishesPanel.add(unitLabel);

		unitTextField = new JTextField();
		unitTextField.setHorizontalAlignment(SwingConstants.CENTER);
		unitTextField.setEditable(false);
		unitTextField.setFocusable(false);
		unitTextField.setColumns(5);
		orderDishesPanel.add(unitTextField);

		final JLabel amountLabel = new JLabel();
		amountLabel.setFont(new Font("", Font.BOLD, 12));
		amountLabel.setText("  ������");
		orderDishesPanel.add(amountLabel);

		// �������������ı���
		amountTextField = new JTextField();
		amountTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {// ���ı����ý���ʱִ��
				amountTextField.setText(null);// ���á��������ı���Ϊ��
			}

			public void focusLost(FocusEvent e) {// ���ı���ʧȥ����ʱִ��
				String amount = amountTextField.getText();// ������������
				if (amount.length() == 0)// δ��������
					amountTextField.setText("1"); // �ָ�ΪĬ������1
			}
		});
		amountTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int length = amountTextField.getText().length();// ��ȡ��ǰ������λ��
				if (length < 2) {// λ��С����λ
					 // ������������ַ�������ַ���
					String num = (length == 0 ? "123456789" : "0123456789");
					if (num.indexOf(e.getKeyChar()) < 0)// �鿴�����ַ��Ƿ����������������ַ���
						e.consume(); // ���������������������ַ��������ٴ˴ΰ����¼�
				} else {
					e.consume(); // �����С��������������λ�������ٴ˴ΰ����¼�
				}
			}
		});
		amountTextField.setText("1");// Ĭ������Ϊ1
		amountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		amountTextField.setColumns(3);
		orderDishesPanel.add(amountTextField);

		final JLabel emptyLabel = new JLabel();
		emptyLabel.setText(null);
		emptyLabel.setPreferredSize(new Dimension(10, 20));
		orderDishesPanel.add(emptyLabel);

		final JButton addButton = new JButton();
		
	
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		addButton.setFont(new Font("", Font.BOLD, 12));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeOutAnInvoice();
				codeTextField.requestFocus();
			}
		});
		addButton.setText("�� ��");
		orderDishesPanel.add(addButton);

		final JButton subButton = new JButton("ǩ ��");
		subButton.setFont(new Font("", Font.BOLD, 12));
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫǩ����̨�ţ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int row = leftTable.getRowCount() - 1;
				//�жϵ�һ���ֶβ���new ��ǩ��
				String NEW = leftTable.getValueAt(row, 0).toString();
				if (NEW.equals("New")) {
					leftTableValueV.removeAllElements();
					leftTableValueV.addAll(menuOfDeskV.get(selectedRow));
					for (; row >= 0; row--) {
						leftTableValueV.get(row).set(0, "");
					}
					leftTableModel.setDataVector(leftTableValueV,
							leftTableColumnV);
					leftTable.setRowSelectionInterval(0);
					//�����붩����
					Order_FormBean order_formBean = new Order_FormBean();
					String nextNum = null;
					try {
						nextNum = Order_FormFactory.getInitialise().queryMaxNum();
						
					} catch (Exception e1) {
						nextNum = null;
						e1.printStackTrace();
					}
					nextNum = getNextNum(nextNum);
					order_formBean.setNum(Long.parseLong(nextNum));
					order_formBean.setMoney(
							Double.parseDouble(expenditureTextField.getText()));
					order_formBean.setUser_id(
							Integer.parseInt(user.get(1).toString()));
					order_formBean.setDesk_num(
							Integer.parseInt(rightTable.getValueAt(selectedRow, 1).toString()));
					//����  order_item ��
					Vector<Order_ItemBean> order_itemBeanV = new Vector<Order_ItemBean>();
					for (int i=0; i<leftTableValueV.size(); i++) {
						Order_ItemBean order_itemBean = new Order_ItemBean();
						order_itemBean.setOrder_form_num(Long.valueOf(nextNum));
						order_itemBean.setMenu_num(
								Integer.parseInt(leftTable.getValueAt(i, 2).toString()));
						order_itemBean.setAmount(
								Integer.parseInt(leftTable.getValueAt(i, 5).toString()));
						order_itemBean.setTotal(
								Double.parseDouble(leftTable.getValueAt(i, 7).toString()));
						order_itemBeanV.add(order_itemBean);
					}
					try {
						Order_FormFactory.getInitialise().addOrder_Form(order_formBean);
						Order_ItemBean order_itemBean;
						for (int i=0; i<order_itemBeanV.size(); i++) {
							order_itemBean = order_itemBeanV.get(i);
							Order_ItemFactory.getInitialise().addOrder_Item(order_itemBean);
						}
						JOptionPane.showMessageDialog(null, "ǩ���ɹ�!");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "ǩ��ʧ��!");
						e1.printStackTrace();
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "�����ظ�ǩ��!");
					return;
				}
			}
		});
		/////////////////////////////////////////////////////////////////////
		orderDishesPanel.add(subButton);

		final JButton delButton = new JButton();
		delButton.setFont(new Font("", Font.BOLD, 12));
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lSelectedRow = leftTable.getSelectedRow();// ��á�ǩ���б��е�ѡ���У���Ҫȡ���Ĳ�Ʒ
				if (lSelectedRow == -1) {// δѡ���κ���
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫȡ������Ʒ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				String NEW = leftTable.getValueAt(lSelectedRow, 0)
						.toString();// ���ѡ�в�Ʒ���µ�˱��
				if (NEW.equals("")) {// û���µ�˱�ǣ�������ȡ��
					JOptionPane.showMessageDialog(null, "�ܱ�Ǹ������Ʒ�Ѿ�����ȡ����",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int rSelectedRow = rightTable.getSelectedRow();// ��á���̨�б��е�ѡ���У���ȡ����Ʒ��̨��
				int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫȡ��̨��"
						+ rightTable.getValueAt(rSelectedRow, 1)
						+ "�е���Ʒ��"
						+ leftTable.getValueAt(lSelectedRow, 3) + "����",
						"������ʾ", JOptionPane.YES_NO_OPTION);// ������ʾ��Ϣȷ���Ƿ�ȡ��
				if (i == 0) {// ȷ��ȡ��
					leftTableModel.removeRow(lSelectedRow);// �ӡ�ǩ���б���ȡ����Ʒ
					menuOfDeskV.get(rSelectedRow).remove(lSelectedRow);
					int rowCount = leftTable.getRowCount();// ���ȡ����ĵ������
					if (rowCount == 0) {// δ���κβ�Ʒ
						rightTableModel.removeRow(rSelectedRow);// ȡ����̨
						menuOfDeskV.remove(rSelectedRow);// �Ƴ�ǩ���б�
					} else {
						if (lSelectedRow == rowCount) {// ȡ����ƷΪ���һ��
							lSelectedRow -= 1;// �������һ����ƷΪѡ�е�
						} else {// ȡ����Ʒ�������һ��
							Vector<Vector<Object>> menus = menuOfDeskV
									.get(rSelectedRow);
							for (int row = lSelectedRow; row < rowCount; row++) {// �޸ĵ�����
								leftTable.setValueAt(row + 1, row, 1);
								menus.get(row).set(1, row + 1);
							}
						}
						leftTable.setRowSelectionInterval(lSelectedRow);// ����ѡ����
					}
				}
			}
		});
		delButton.setText("ȡ ��");
		orderDishesPanel.add(delButton);

		final JPanel clueOnPanel = new JPanel();
		clueOnPanel.setPreferredSize(new Dimension(220, 0));
		clueOnPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		clueOnPanel.setLayout(new GridLayout(0, 1));
		bottomPanel.add(clueOnPanel, BorderLayout.WEST);

		final JLabel aClueOnLabel = new JLabel();
		clueOnPanel.add(aClueOnLabel);
		aClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		aClueOnLabel.setText("  �����ǣ�");

		final JLabel bClueOnLabel = new JLabel();
		bClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(bClueOnLabel);
		bClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bClueOnLabel.setText(Today.getDateOfShow());

		final JLabel cClueOnLabel = new JLabel();
		cClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(cClueOnLabel);
		cClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cClueOnLabel.setText(Today.getDayOfWeek());

		timeLabel = new JLabel();// ����������ʾʱ��ı�ǩ����
		timeLabel.setFont(new Font("����", Font.BOLD, 14));// ���ñ�ǩ�е�����Ϊ���塢���塢14��
		timeLabel.setForeground(new Color(255, 0, 0));// ���ñ�ǩ�е�����Ϊ��ɫ
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);// ���ñ�ǩ�е����־�����ʾ
		clueOnPanel.add(timeLabel);// ����ǩ��ӵ��ϼ�������
		new Time().start();// �����߳�

		final JLabel eClueOnLabel = new JLabel();
		clueOnPanel.add(eClueOnLabel);
		eClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		eClueOnLabel.setText("  ��ǰ����Ա��");

		final JLabel fClueOnLabel = new JLabel();
		fClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(fClueOnLabel);
		fClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		if (user == null)
			fClueOnLabel.setText("ϵͳĬ���û�");
		else
			fClueOnLabel.setText(user.get(2).toString());

		final JPanel checkOutPanel = new JPanel();
		checkOutPanel.setPreferredSize(new Dimension(500, 0));
		bottomPanel.add(checkOutPanel);
		checkOutPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		checkOutPanel.setLayout(new GridBagLayout());

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(72, 70));
		URL rmbUrl = this.getClass().getResource("/img/rmb.jpg");
		ImageIcon rmbIcon = new ImageIcon(rmbUrl);
		label.setIcon(rmbIcon);
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(0, 0, 0, 15);
		gridBagConstraints_9.gridheight = 4;
		gridBagConstraints_9.gridy = 0;
		gridBagConstraints_9.gridx = 0;
		checkOutPanel.add(label, gridBagConstraints_9);

		final JLabel expenditureLabel = new JLabel();
		expenditureLabel.setFont(new Font("", Font.BOLD, 16));
		expenditureLabel.setText("���ѽ�");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.gridx = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.insets = new Insets(0, 10, 0, 0);
		checkOutPanel.add(expenditureLabel, gridBagConstraints_13);

		expenditureTextField = new JTextField();
		expenditureTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		expenditureTextField.setText("0.00");
		expenditureTextField.setForeground(new Color(255, 0, 0));
		expenditureTextField.setFont(new Font("", Font.BOLD, 15));
		expenditureTextField.setColumns(7);
		expenditureTextField.setEditable(false);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 2;
		checkOutPanel.add(expenditureTextField, gridBagConstraints_6);

		final JLabel expenditureUnitLabel = new JLabel();
		expenditureUnitLabel.setForeground(new Color(255, 0, 0));
		expenditureUnitLabel.setFont(new Font("", Font.BOLD, 15));
		expenditureUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 3;
		checkOutPanel.add(expenditureUnitLabel, gridBagConstraints_14);

		final JLabel realWagesLabel = new JLabel();
		realWagesLabel.setFont(new Font("", Font.BOLD, 16));
		realWagesLabel.setText("ʵ�ս�");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 1;
		checkOutPanel.add(realWagesLabel, gridBagConstraints_7);

		realWagesTextField = new JTextField();
		realWagesTextField.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO �Զ����ɷ������

			}

			public void keyReleased(KeyEvent e) {
				// TODO �Զ����ɷ������

			}

			public void keyTyped(KeyEvent e) {
				int length = realWagesTextField.getText().length();// ��ȡ��ǰ������λ��
				if (length < 8) {// λ��С����λ
					String num = (length == 4 ? "123456789" : "0123456789"); // ������������ַ�������ַ���
					if (num.indexOf(e.getKeyChar()) < 0)// �鿴�����ַ��Ƿ����������������ַ���
						e.consume(); // ���������������������ַ��������ٴ˴ΰ����¼�
				} else {
					e.consume(); // �����С��������������λ�������ٴ˴ΰ����¼�
				}
			}
		});
		realWagesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		realWagesTextField.setText("0.00");
		realWagesTextField.setForeground(new Color(0, 128, 0));
		realWagesTextField.setFont(new Font("", Font.BOLD, 15));
		realWagesTextField.setColumns(7);
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 2;
		checkOutPanel.add(realWagesTextField, gridBagConstraints_8);

		final JLabel realWagesUnitLabel = new JLabel();
		realWagesUnitLabel.setForeground(new Color(0, 128, 0));
		realWagesUnitLabel.setFont(new Font("", Font.BOLD, 15));
		realWagesUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_15.gridy = 1;
		gridBagConstraints_15.gridx = 3;
		checkOutPanel.add(realWagesUnitLabel, gridBagConstraints_15);

		final JButton checkOutButton = new JButton();
		checkOutButton.setFont(new Font("", Font.BOLD, 12));
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ���˵�̨�ţ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int rowCount = leftTable.getRowCount();// ��ý��˲�̨�ĵ������
				String NEW = leftTable.getValueAt(rowCount - 1, 0)
						.toString();// �������˵ı��
				if (NEW.equals("New")) {// �������˱����Ϊ��New��,�򵯳���ʾ
					JOptionPane.showMessageDialog(null, "����ȷ��δǩ����Ʒ�Ĵ���ʽ��",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
				} else {
					float expenditure = Float.valueOf(expenditureTextField
							.getText());// ������ѽ��
					float realWages = Float.valueOf(realWagesTextField
							.getText());// ���ʵ�ս��
					if (realWages < expenditure) {// ���ʵ�ս��С�����ѽ��򵯳���ʾ
						if (realWages == 0.0f)
							JOptionPane
									.showMessageDialog(null, "������ʵ�ս�",
											"������ʾ",
											JOptionPane.INFORMATION_MESSAGE);
						else
							JOptionPane.showMessageDialog(null,
									"ʵ�ս���С�����ѽ�", "������ʾ",
									JOptionPane.INFORMATION_MESSAGE);
						realWagesTextField.requestFocus();// ���ʵ�ս��ı����ý���
					} else {
						changeTextField.setText(realWages - expenditure
								+ "0");// ���㲢���á������
	//					String[] values = {
	//							getNum(),
	//							rightTable.getValueAt(selectedRow, 1)
	//									.toString(),
	//							Today.getDate()
	//									+ " "
	//									+ rightTable.getValueAt(
	//											selectedRow, 2),
	//							expenditureTextField.getText(),
	//							user.get(0).toString() };// ��֯���ѵ���Ϣ
	//					dao.iOrderForm(values);// �־û������ݿ�
	//					values[0] = dao.sOrderFormOfMaxId();// ������ѵ����
	//					for (int i = 0; i < rowCount; i++) {// ͨ��ѭ����ø���������Ŀ����Ϣ
	//						values[1] = leftTable.getValueAt(i, 2)
	//								.toString();// �����Ʒ���
	//						values[2] = leftTable.getValueAt(i, 5)
	//								.toString();// �����Ʒ����
	//						values[3] = leftTable.getValueAt(i, 7)
	//								.toString();// �����Ʒ���ѽ��
	//						dao.iOrderItem(values);// �־û������ݿ�
	//					}
						JOptionPane.showMessageDialog(null, rightTable
								.getValueAt(selectedRow, 1)
								+ " ������ɣ�", "������ʾ",
								JOptionPane.INFORMATION_MESSAGE);// �������������ʾ
						rightTableModel.removeRow(selectedRow);// �ӡ���̨�б���ȡ����̨
						leftTableValueV.removeAllElements();// ��ա�ǩ���б�
						leftTableModel.setDataVector(leftTableValueV,
								leftTableColumnV);// ˢ�¡�ǩ���б�
						realWagesTextField.setText("0.00");// ��ա�ʵ�ս��ı���
						changeTextField.setText("0.00");// ��ա�������ı���
						menuOfDeskV.remove(selectedRow);// �ӡ�ǩ���б��������Ƴ��ѽ��˵�ǩ���б�
						for (int i=0; i<rightTableModel.getRowCount(); i++) {
							rightTableModel.setValueAt(i+1, i, 0);
						}
					}
				}
				
			}
		});
		checkOutButton.setMargin(new Insets(2, 14, 2, 14));
		checkOutButton.setText("�� ��");
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.anchor = GridBagConstraints.EAST;
		gridBagConstraints_10.gridwidth = 2;
		gridBagConstraints_10.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 2;
		checkOutPanel.add(checkOutButton, gridBagConstraints_10);

		final JLabel changeLabel = new JLabel();
		changeLabel.setFont(new Font("", Font.BOLD, 16));
		changeLabel.setText("�����");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_11.gridy = 3;
		gridBagConstraints_11.gridx = 1;
		checkOutPanel.add(changeLabel, gridBagConstraints_11);

		changeTextField = new JTextField();
		changeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		changeTextField.setText("0.00");
		changeTextField.setForeground(new Color(255, 0, 255));
		changeTextField.setFont(new Font("", Font.BOLD, 15));
		changeTextField.setEditable(false);
		changeTextField.setColumns(7);
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 3;
		gridBagConstraints_12.gridx = 2;
		checkOutPanel.add(changeTextField, gridBagConstraints_12);

		final JLabel changeUnitLabel = new JLabel();
		changeUnitLabel.setForeground(new Color(255, 0, 255));
		changeUnitLabel.setFont(new Font("", Font.BOLD, 15));
		changeUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_16.gridy = 3;
		gridBagConstraints_16.gridx = 3;
		checkOutPanel.add(changeUnitLabel, gridBagConstraints_16);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 0));
		bottomPanel.add(buttonPanel, BorderLayout.EAST);

		final JPanel aButtonPanel = new JPanel();
		aButtonPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		aButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(aButtonPanel);

		final JButton aTopButton = new MButton();
		aTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuDialog menuDialog = new MenuDialog();
				menuDialog.setLocation((screenSize.width - menuDialog
						.getWidth()) / 2, (screenSize.height - menuDialog
						.getHeight()) / 2);
				menuDialog.setVisible(true);
			}
		});
		URL menuUrl = this.getClass().getResource("/img/menu.jpg");
		ImageIcon menuIcon = new ImageIcon(menuUrl);
		aTopButton.setIcon(menuIcon);
		aButtonPanel.add(aTopButton);

		final JButton aCenterButton = new MButton();
		aCenterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SortDialog sortDialog = new SortDialog();
				sortDialog.setLocation((screenSize.width - sortDialog
						.getWidth()) / 2, (screenSize.height - sortDialog
						.getHeight()) / 2);
				sortDialog.setVisible(true);
			}
		});
		URL sortUrl = this.getClass().getResource("/img/sort.jpg");
		ImageIcon sortIcon = new ImageIcon(sortUrl);
		aCenterButton.setIcon(sortIcon);
		aButtonPanel.add(aCenterButton);

		//��������
		final JButton aBottomButton = new MButton();
		aBottomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final DeskNumDialog deskNumDialog = new DeskNumDialog(rightTable);
				deskNumDialog.setLocation((screenSize.width - deskNumDialog
						.getWidth()) / 2, (screenSize.height - deskNumDialog
						.getHeight()) / 2);
				deskNumDialog.setVisible(true);
				//̨�Ź�����º�ͬ��ˢ�������б��е�̨��
				Vector<Vector<Object>> TipTable;
				try {
					TipTable = DeskFactory.getInitialise()
							.queryAllData();
					Vector<String> content = new Vector<String>();
					Vector<String> tables = new Vector<String>();
					for (int i=0; i<numComboBox.getItemCount(); i++) {
						content.add(numComboBox.getItemAt(i));
					}
					for (int i=0; i<TipTable.size(); i++) {
						Vector<Object> row = TipTable.get(i);
						String num  = row.get(1).toString();
						tables.add(num);
						if (content.indexOf(num) == -1)
							numComboBox.addItem(num);
					}
					for (int i=0; i<content.size(); i++) {
						String num = content.get(i);
						if (num.equals("��ѡ��")) {
							continue;
						}
						if (tables.indexOf(num) == -1) {
							numComboBox.removeItem(num);
						}
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				///////////////////////////////////////////////////////////////
			}
		});
		URL deskUrl = this.getClass().getResource("/img/desk.jpg");
		ImageIcon deskIcon = new ImageIcon(deskUrl);
		aBottomButton.setIcon(deskIcon);
		aButtonPanel.add(aBottomButton);

		final JPanel dButtonPanel = new JPanel();
		dButtonPanel.setPreferredSize(new Dimension(0, 0));
		dButtonPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		dButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(dButtonPanel);

		final JButton dTopButton = new MButton();
		dTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DayDialog dayDialog = new DayDialog();
				dayDialog.setVisible(true);
			}
		});
		URL dayUrl = this.getClass().getResource("/img/day.png");
		ImageIcon dayIcon = new ImageIcon(dayUrl);
		dTopButton.setIcon(dayIcon);
		dButtonPanel.add(dTopButton);

		final JButton dCenterButton = new MButton();
		dCenterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonthDialog monthDialog = new MonthDialog();
				monthDialog.setVisible(true);
			}
		});
		URL monthUrl = this.getClass().getResource("/img/month.png");
		ImageIcon monthIcon = new ImageIcon(monthUrl);
		dCenterButton.setIcon(monthIcon);
		dButtonPanel.add(dCenterButton);

		final JButton dBottomButton = new MButton();
		dBottomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YearDialog yearDialog = new YearDialog();
				yearDialog.setVisible(true);
			}
		});
		URL yearUrl = this.getClass().getResource("/img/year.png");
		ImageIcon yearIcon = new ImageIcon(yearUrl);
		dBottomButton.setIcon(yearIcon);

		dButtonPanel.add(dBottomButton);
		final JPanel cButtonPanel = new JPanel();
		cButtonPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		cButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(cButtonPanel);

		//���µ�ǰ�û����밴ť
		final JButton cTopButton = new MButton();
		//���������봰��
		final UpdatePasswordDialog upDialog = new UpdatePasswordDialog();
		upDialog.setUser(this.user);
		upDialog.setLocation(
				(screenSize.width - upDialog.getWidth()) / 2,
				(screenSize.height - upDialog.getHeight()) / 2);
		cTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���ݵ�ǰ�û���Ϣ
//				Vector<Object> tmp = new Vector<Object>();
//				tmp.add("abcdefg");
				upDialog.setVisible(true);
			}
		});
		URL passwordUrl = this.getClass().getResource("/img/password.jpg");
		ImageIcon passwordIcon = new ImageIcon(passwordUrl);
		cTopButton.setIcon(passwordIcon);
		cButtonPanel.add(cTopButton);

		//�û�����ť
		final JButton cCenterButton = new MButton();
		cCenterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManagerDialog umDialog = new UserManagerDialog();
				umDialog.setLocation(
						(screenSize.width - umDialog.getWidth()) / 2,
						(screenSize.height - umDialog.getHeight()) / 2);
				umDialog.setVisible(true);
			}
		});
		URL userUrl = this.getClass().getResource("/img/user.jpg");
		ImageIcon userIcon = new ImageIcon(userUrl);
		cCenterButton.setIcon(userIcon);
		cButtonPanel.add(cCenterButton);

		//�˳�ϵͳ��ť
		final JButton cBottomButton = new MButton();
		cBottomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		URL exitUrl = this.getClass().getResource("/img/exit.jpg");
		ImageIcon exitIcon = new ImageIcon(exitUrl);
		cBottomButton.setIcon(exitIcon);
		cButtonPanel.add(cBottomButton);

		String powerLevelStr = user.get(7).toString();
		int powerLevel = Integer.parseInt(powerLevelStr);
		if (4 == powerLevel) {
			numComboBox.setEnabled(false);
			numRadioButton.setEnabled(false);
			codeRadioButton.setEnabled(false);
			codeTextField.setEnabled(false);
			amountTextField.setEnabled(false);
			addButton.setEnabled(false);
			subButton.setEnabled(false);
			delButton.setEnabled(false);
			realWagesTextField.setEnabled(false);
			checkOutButton.setEnabled(false);
			aTopButton.setEnabled(false);
			aCenterButton.setEnabled(false);
			aBottomButton.setEnabled(false);
			cCenterButton.setEnabled(false);
		}
		if (6 == powerLevel) {
			aTopButton.setEnabled(false);
			aCenterButton.setEnabled(false);
			aBottomButton.setEnabled(false);
			cCenterButton.setEnabled(false);
			dTopButton.setEnabled(false);
			dCenterButton.setEnabled(false);
			dBottomButton.setEnabled(false);
		}
	}
	
	class Time extends Thread {// �����ڲ���
		public void run() {// �ع�����ķ���
			while (true) {
				Date date = new Date();// �������ڶ���
				timeLabel.setText(date.toString().substring(11, 19));// ��ȡ��ǰʱ�䣬����ʾ��ʱ���ǩ��
				try {
					Thread.sleep(1000);// ���߳�����1��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * @author ������
	 * @param maxNum
	 * @return
	 */
	
	//ȡ��һ���������
	private String getNextNum(String maxNum) {
		String date = Today.getDateOfNum();
		if (maxNum == null) {
			maxNum = date + "001";
		} else {
			if (maxNum.subSequence(0, 8).equals(date)) {
				maxNum = maxNum.substring(8);
				int nextNum = Integer.valueOf(maxNum) + 1;
				if (nextNum < 100)
					date = date + "0";
				if (nextNum < 10)
					date = date + "0";
				maxNum = date + nextNum;
			} else {
				maxNum = date + "001";
			}
		}
		return maxNum;
	}
	////////////////////////////////////////////////////////////
}
