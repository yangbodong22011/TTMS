package src.xupt.se.ttms.view.datadict;

import javax.swing.JDialog;








import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;






//import view.studioUI.ImageJPanel;
import src.xupt.se.ttms.model.DataDict;
import src.xupt.se.ttms.model.Schedule;
import src.xupt.se.ttms.service.DataDictSrv;
import src.xupt.se.ttms.view.tmpl.*;

public class DataDictAddUI extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnCancel, btnSave; 	//取消，保存按鈕

	protected boolean rst=false; 				//操作结果
	protected JLabel lblSuperId,lblIndex, lblName, lblValue;
	protected JComboBox<String> txtSuperId;

	protected JTextField txtIndex, txtName, txtValue;

	public DataDictAddUI() {
		initContent();
	}

//	@Override
	protected void initContent(){
		this.setTitle("添加数据字典");
		this.setBackground(Color.WHITE);
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();//得到屏幕的大小 
		setBounds((screen.width-800)/2,(screen.height-600)/2,800,600);
		
		DataDictSrv dataDictSrv = new DataDictSrv();
		List<DataDict> listsAllDataDict = dataDictSrv.FetchAll();
		String [] parentNode = new String[listsAllDataDict.size()];
		DataDict dataDict;
		for(int i=0; i<listsAllDataDict.size(); i++) 
		{    
			dataDict = listsAllDataDict.get(i);
			parentNode[i] = dataDictSrv.Fetch("dict_parent_id = 1").get(0).getName();
		} 
		
		lblSuperId = new JLabel("父类型：");
		lblSuperId.setBounds(60, 30, 80, 30);
		this.add(lblSuperId);
		txtSuperId = new JComboBox<String>(parentNode);
		txtSuperId.setBounds(150, 30, 120, 30);
		this.add(txtSuperId);
		
//		lblIndex = new JLabel("序号：");
//		lblIndex.setBounds(60, 80, 80, 30);
//		this.add(lblIndex);
//		txtIndex = new JTextField();
//		txtIndex.setBounds(150, 80, 120, 30);
//		this.add(txtIndex);

		lblName = new JLabel("字典名称：");
		lblName.setBounds(60, 80, 80, 30);
		this.add(lblName);
		txtName = new JTextField();
		txtName.setBounds(150, 80, 120, 30);
		this.add(txtName);

		lblValue = new JLabel("字典值：");
		lblValue.setBounds(60, 130, 80, 30);
		this.add(lblValue);
		txtValue = new JTextField();
		txtValue.setBounds(150, 130, 120, 30);
		this.add(txtValue);

		btnSave = new JButton("保存");

		btnSave.addActionListener(this);
		btnSave.setBounds(60, 180, 80, 30);
		this.add(btnSave);

		btnCancel = new JButton("取消");
		btnCancel.addActionListener(this);
		btnCancel.setBounds(180, 180, 80, 30);
		this.add(btnCancel);

		JLabel jLabel = new JLabel();
		jLabel.setBounds(360, 160, 100, 100);
	
		this.add(jLabel);
	}
	
	
	public boolean getReturnStatus(){
		   return rst;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnCancel) {
			rst=false;
			this.dispose();
			getParent().setVisible(true);

		} else if (e.getSource() == btnSave) {
			System.out.println(1);
			btnSaveClicked();
			
		}

	}
	
	protected void btnSaveClicked(){
		if (txtIndex.getText() != null && txtName.getText() != null
				&& txtValue.getText() != null) {
			DataDictSrv dictSrv = new DataDictSrv();
			DataDict ddict=new DataDict();
			int super_id = new DataDictSrv().Fetch("dict_name = '" + txtSuperId.getSelectedItem() + "'").get(0).getId();
			ddict.setSuperId(super_id);
			ddict.setIndex(new DataDictSrv().Fetch("dict_parent_id = " +super_id).get((new DataDictSrv().Fetch("dict_parent_id = " +super_id).size())).getIndex() + 1);
			
			ddict.setName(txtName.getText());
			ddict.setValue(txtValue.getText());
			dictSrv.add(ddict);
			this.dispose();
			rst=true;
			getParent().setVisible(true);
		} else {
			JOptionPane.showMessageDialog(null, "数据不完整");
		}		
	}
}
