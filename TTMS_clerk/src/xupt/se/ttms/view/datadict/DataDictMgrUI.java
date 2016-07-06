package xupt.se.ttms.view.datadict;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import xupt.se.ttms.dao.DataDictDAO;
import xupt.se.ttms.model.DataDict;
import xupt.se.ttms.service.DataDictSrv;
import xupt.se.ttms.view.tmpl.*;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;


class DataDictTableMouseListener extends MouseAdapter {

	private JTable jt;
	private static DataDict ddict;

	public DataDict getDict() {
		return ddict;
	}

	public DataDictTableMouseListener(JTable jt, Object[] number, DataDict ddict) {
		this.ddict = ddict;
		this.jt = jt;
		
	}

	// 监听到行号，将所选行的内容依次赋到ddict对象，以便传有值对象到修改面板进行修改
	public void mouseClicked(MouseEvent event) {
		int row = jt.getSelectedRow();
		ddict.setId(Integer.parseInt(jt.getValueAt(row, 0).toString()));

		ddict.setSuperId(Integer.parseInt(jt.getValueAt(row, 1).toString()));
		ddict.setIndex(Integer.parseInt(jt.getValueAt(row, 2).toString())); 
		ddict.setName(jt.getValueAt(row, 3).toString());
		if (jt.getValueAt(row, 4) != null)
			ddict.setValue(jt.getValueAt(row, 4).toString());
		else
			ddict.setValue("");
		System.out.println(jt.getValueAt(row, 0).toString());
	}
}

class DataDictTable {

	private DataDict ddict;
	//private JTable jt = null;

	public DataDictTable(DataDict ddict) {
		this.ddict = ddict;
		
	}

	// 创建JTable
	public void createTable(JTable jDictTable, JScrollPane jp, Object[] columnNames, List<DataDict> dictList) {
		try {

			Object data[][] = new Object[dictList.size()][columnNames.length];

			Iterator<DataDict> itr = dictList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				DataDict ddict = itr.next();
				data[i][0] = Integer.toString(ddict.getId());
				
				data[i][1] = Integer.toString(ddict.getSuperId());
				data[i][2] = Integer.toString(ddict.getIndex());
				data[i][3] = ddict.getName();
				data[i][4] = ddict.getValue();
				i++;
			}

			// 生成JTable
		     //jt = new JTable(data, columnNames);
			
			javax.swing.table.DefaultTableModel t = new javax.swing.table.DefaultTableModel(data,columnNames){
			
	           /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
	            {
	                return false;
	            }
	        }; 
	        
	        t.setDataVector(data, columnNames);
	       
	        //DefaultTableModel model = (DefaultTableModel)(jDictTable.getModel());
	       // model.setDataVector(data, columnNames);
	        jDictTable.repaint();   
	        //jDictTable.setBounds(0, 0, 700, 450);

			// 添加鼠标监听，监听到所选行
			DataDictTableMouseListener tml = new  DataDictTableMouseListener(jDictTable, columnNames, ddict);
			jDictTable.addMouseListener(tml);

			// 设置可调整列宽
			
			jDictTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

			jp.add(jDictTable);
			jp.setViewportView(jDictTable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showTable(JTable jDictTable, JScrollPane jp, Object[] columnNames, List<DataDict> dictList) {
		try {

			Object data[][] = new Object[dictList.size()][columnNames.length];

			Iterator<DataDict> itr = dictList.iterator();
			int i = 0;
			while (itr.hasNext()) {
				DataDict ddict = itr.next();
				data[i][0] = Integer.toString(ddict.getId());
				
				data[i][1] = Integer.toString(ddict.getSuperId());
				data[i][2] = Integer.toString(ddict.getIndex());
				data[i][3] = ddict.getName();
				data[i][4] = ddict.getValue();
				i++;
			}
  
	        DefaultTableModel model = (DefaultTableModel)(jDictTable.getModel());
	        model.setDataVector(data, columnNames);
	        jDictTable.repaint();   
	      
			//jp.setViewportView(jDictTable);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class DataDictMgrUI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataDict ddict=new DataDict();
	private JLabel ca1 = null; // 界面提示
	// 用来放表格的滚动控件
	
	JSplitPane splitPane = new JSplitPane();// 创建一个分割容器类
	private JScrollPane jsc;
	// 查找的提示和输出
	private JLabel hint;
	private JTextField input;

	// 查找，编辑和删除按钮
	private JButton btnAdd, btnEdit, btnDel, btnQuery;
	
	private JTable jDictTable=null;

	public DataDictMgrUI() {
		initContent();
		this.setVisible(true);
	}

	// To be override by the detailed business block interface
//	@Override
	private void initContent() {
		
		Rectangle rect = this.getBounds();
		setLayout(new BorderLayout());
		splitPane = new JSplitPane();
		JPanel right = new JPanel ();
		JPanel left = new JPanel ();
		
		
		jDictTable = new JTable();
		right.setLayout(new BorderLayout());
		right.setBounds(200,0, 824, 700);
		
		left.setLayout(null);
		left.setBounds(200,0, 200, 700);
		
        final JTree tree = createTree();
        
        DataDictTreeCellRenderer render = new DataDictTreeCellRenderer();
        tree.setCellRenderer(render);
        
        left.setLayout(new BorderLayout());
        
        left.add(tree);
        // 添加选择事件
        tree.addTreeSelectionListener(new TreeSelectionListener() {
 
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
                        .getLastSelectedPathComponent();
 
                List<DataDict> leafList =null;
                if (node == null)
                    return;
 
                Object object = node.getUserObject();
                if (node.isRoot()) {
                	
                	System.out.println("你选择了：" +"根节点");
                    leafList =  new ArrayList<DataDict>();
                    leafList = new DataDictSrv().findByID(1);
                    
                    showAllLeafTable(leafList);
                   
                }else if(node.isLeaf())
                {
                	System.out.println("你选择了：" + "叶子节点");
                	DataDict dict = (DataDict) object; 
                	leafList =  new ArrayList<DataDict>();
               
                    leafList = new DataDictSrv().findSelfByID(dict.getId());
                    showAllLeafTable(leafList);
                }else{
                	System.out.println("你选择了：" + "双亲节点");
                	DataDict dict = (DataDict) object; 
                    leafList =  new ArrayList<DataDict>();
                    new DataDictSrv().findAllSonByID(leafList, dict.getId());
                    
                    showAllLeafTable(leafList);
                }
                
                
                
 
            }
        });
		
	
		jsc = new JScrollPane();
		jsc.setBounds(0, 40, rect.width-30, rect.height - 290);
		right.add(jsc,BorderLayout.CENTER);

		JPanel topJPanel = new JPanel();
		right.add(topJPanel, BorderLayout.NORTH);
		
		ca1 = new JLabel("数据字典管理", JLabel.CENTER);
		ca1.setBounds(0, 5, rect.width, 30);
		ca1.setFont(new java.awt.Font("宋体", 1, 20));
		ca1.setForeground(Color.blue);
		topJPanel.add(ca1);
		
		hint = new JLabel("请输入数据字典名称:", JLabel.RIGHT);
		hint.setBounds(0, rect.height - 160, 150, 30);
		topJPanel.add(hint);

		input = new JTextField(10);
		input.setBounds(200, rect.height - 160, 200, 30);
		topJPanel.add(input);

		// 查找 ，删除和编辑的按钮，其中含有相关的事件处理！
		btnQuery = new JButton("查找");
		btnQuery.setBounds(440, rect.height - 160, 60, 30);
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnQueryClicked();
			}
		});
		topJPanel.add(btnQuery);

		btnAdd = new JButton("添加");
		btnAdd.setBounds(rect.width - 220, rect.height - 160, 60, 30);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnAddClicked();
			}
			
		});
		topJPanel.add(btnAdd);

		btnEdit = new JButton("修改");
		btnEdit.setBounds(rect.width - 150, rect.height - 160, 60, 30);
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnModClicked();
			}
		});
		topJPanel.add(btnEdit);

		btnDel = new JButton("删除");
		btnDel.setBounds(rect.width - 80, rect.height - 160, 60, 30);
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent Event) {
				btnDelClicked();
			}
		});
		topJPanel.add(btnDel);
				
		splitPane.setBounds(0,0, 1024,700);
		splitPane.setOneTouchExpandable(true);// 让分割线显示出箭头
		splitPane.setContinuousLayout(true);// 操作箭头，重绘图形
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// 设置分割线方向
		splitPane.setLeftComponent(left);
		splitPane.setRightComponent(right);
		splitPane.setDividerSize(1);
		splitPane.setDividerLocation(200);// 设置分割线位于中央
		
		
		this.add(splitPane, BorderLayout.CENTER);
		
		//初次为数据字典表格添加事件，不能添加多次，添加多次事件会出现会
		
		Object[] in = { "id", "superid", "index", "name", "value" };
		DataDictTable tms = new DataDictTable(ddict);
		List<DataDict> leafList =new ArrayList<DataDict>();
		new DataDictSrv().findAllSonByID(leafList, 1);
//		leafList = new DataDictSrv().findByID(1);
		showAllLeafTable(leafList);
		tms.createTable(jDictTable,jsc, in, leafList);
		jsc.repaint();
		splitPane.repaint();
	
		
	}

    private JTree createTree(){ 
    	
    	
        DefaultMutableTreeNode   root   =   new   DefaultMutableTreeNode( "数据字典 "); 
        DefaultTreeModel   treeModel   =   new   DefaultTreeModel(root); 
        JTree   tree   =   new   JTree(treeModel); 
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION); 
        
//        DefaultMutableTreeNode   node   =   null; 
//        node   =   new   DefaultMutableTreeNode(new DataDictDAO().findSelfByID(1).get(0)); 
//        root.add(node); 
        
        addTreeNode(root, 1); 
        tree.expandRow(0); 
        tree.setSelectionRow(0); 
        return   tree; 
    } 
    
    //生成节点的递归调用 
    private   void   addTreeNode(DefaultMutableTreeNode  treeNode,int superID)   { 
    	DataDictDAO dictDAO = new DataDictDAO();
    	List<DataDict> list = dictDAO.findByID(superID);
  
    	if(list.size()>0) {
             DefaultMutableTreeNode   node   =   null; 
	         for   (int  i   =   0   ;   i < list.size();   i++){ 
	        	 	if(dictDAO.hasChildren(list.get(i).getId())) {     
		                node   =   new   DefaultMutableTreeNode(list.get(i)); 
		                addTreeNode(node,list.get(i).getId()); 
		                treeNode.add(node); 
		              }else{
		            	  node   =   new   DefaultMutableTreeNode(list.get(i));
		            	  treeNode.add(node);
		              }
	          } 
          }
       } 
    
	private void btnAddClicked() {
		DataDictAddUI addDict = new DataDictAddUI();
//		addDict.setWindowName("添加数据字典");
		addDict.toFront();
		addDict.setModal(true);
		addDict.setVisible(true);

		if (addDict.getReturnStatus()) {
			showTable();
		}
		
		DataDictTable tms = new DataDictTable(ddict);
		Object[] in = { "id", "superid", "index", "name", "value" };
		List<DataDict> dictList = new LinkedList<DataDict>();
		new DataDictSrv().Fetch("");
		tms.showTable(jDictTable,jsc, in, dictList);
		jsc.repaint();
		splitPane.repaint();
	}

	private void btnModClicked() {
			
		DataDictEditUI modDict = new DataDictEditUI(ddict);
	//	modDict.setWindowName("修改数据字典");
		modDict.toFront();
		modDict.setModal(true);
		modDict.setVisible(true);
		if (modDict.getReturnStatus()) {
			showTable();
		}
		
		DataDictTable tms = new DataDictTable(ddict);
		Object[] in = { "id", "superid", "index", "name", "value" };
		List<DataDict> dictList = new LinkedList<DataDict>();
		new DataDictSrv().Fetch("");
		tms.showTable(jDictTable,jsc, in, dictList);
		jsc.repaint();
		splitPane.repaint();
	}

	private void btnDelClicked() {
		int confirm = JOptionPane.showConfirmDialog(null, "确认删除所选？", "删除", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			DataDictSrv dictSrv = new DataDictSrv();
			dictSrv.delete(ddict.getId());
			showTable();
			
		}
	}

	private void btnQueryClicked() {
		if (!input.getText().equals("")) {
			
			Object[] in = { "id", "superid", "index", "name", "value" };
			DataDictTable tms = new DataDictTable(ddict);
			List<DataDict> leafList =new ArrayList<DataDict>();
	//		new DataDictSrv().findAllSonByID(leafList, 1);
			leafList = new DataDictSrv().findByID(Integer.parseInt(input.getText()));
			showAllLeafTable(leafList);
			tms.createTable(jDictTable,jsc, in, leafList);
			jsc.repaint();
			splitPane.repaint();

		} else {
			JOptionPane.showMessageDialog(null, "请输入检索条件");
		}
	}

	
	public void showAllLeafTable(List<DataDict> leafList ) {
		DataDictTable tms = new DataDictTable(ddict);
		Object[] in = { "id", "superid", "index", "name", "value" };
		
		tms.showTable(jDictTable,jsc, in,leafList );
		jsc.repaint();
		splitPane.repaint();
	}
	
	public void showTable() {
		DataDictTable tms = new DataDictTable(ddict);
		Object[] in = { "id", "superid", "index", "name", "value" };
		List<DataDict> dictList = new LinkedList<DataDict>();
		new DataDictSrv().findAllSonByID(dictList, 0);

		
		tms.showTable(jDictTable,jsc, in, dictList);
		jsc.repaint();
		splitPane.repaint();
	}

//	public static void main(String[] args) {
//		DataDictMgrUI frmStuMgr = new DataDictMgrUI();
//		frmStuMgr.setVisible(true);
//	}
}

class DataDictTreeCellRenderer extends DefaultTreeCellRenderer  
{  
   /** 
    * ID 
    */  
   private static final long   serialVersionUID    = 1L;  
 
   /** 
    * 重写父类DefaultTreeCellRenderer的方法 
    */  
   @Override  
   public Component getTreeCellRendererComponent(JTree tree, Object value,  
           boolean sel, boolean expanded, boolean leaf, int row,  
           boolean hasFocus)  
   {  
 
       //执行父类原型操作  
       super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,  
               row, hasFocus);  
 
       setText(value.toString());  
         
       if (sel)  
       {  
           setForeground(getTextSelectionColor());  
       }  
       else  
       {  
           setForeground(getTextNonSelectionColor());  
       }  
         
       //得到每个节点的TreeNode  
       DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;           
       //得到每个节点的text        
       this.setIcon(new ImageIcon("resource/image/dict.gif"));  
 
       return this;  
   }  
 
} 
