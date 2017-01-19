
 import java.util.ArrayList;
 import java.util.Vector;	
 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;

 public class PatternMiningFrame extends JFrame
                      implements ActionListener  {

      
   JLabel l = new JLabel("Enter Minimum Support Value: ");
   JTextField tf = new JTextField(10);
   JButton bExec = new JButton("Execute");
   JButton brules1 = new JButton("Rules for YAMI");	
   JButton brules2 = new JButton("Rules for Apriori");
   JButton bNew = new JButton("Update DataBase"); 

   JTabbedPane tabp = new JTabbedPane();

   JTextArea ta = new JTextArea();

   AlgoPanel[] algoPanels = new AlgoPanel[3];
   
   	

   

   public PatternMiningFrame() {

    for(int i=0;i<3;i++)
     algoPanels[i] = new AlgoPanel(i);
	 

    tabp.addTab("",algoPanels[0]);
   
    tf.setText("0.08");

    JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

    p1.add(l);
    p1.add(tf);
    p1.add(bExec);
p1.add(brules1);   
p1.add(brules2); 
   	
    p2.add(bNew);	
    

    add(p1,"North");
    add(tabp);
    add(p2,"South");

    bExec.addActionListener(this);
    	
    brules1.addActionListener(this);
    brules2.addActionListener(this);
    bNew.addActionListener(this);	

    setTitle("Master Console");
    setVisible(true);
    setExtendedState(MAXIMIZED_BOTH);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   }

   public void actionPerformed(ActionEvent ae) {

     if(ae.getSource()==bNew)	
    {                      
	new DataSourceSelection();                           
     dispose();
    }	
    else if(ae.getSource()==bExec)
    {
     
      algoPanels[0].execute(tf.getText());

     }
    else if(ae.getSource()==brules1)
    {
	AlgoPanel gui=YAMI.gui;
	String rules="";
	String ruleSet="";
	int ii=100;	
	gui.setText("New Rules\n");
	gui.append("New Rules\n--------------------------------\n\n");	
	gui.append("Rules		Confidence\n");	
	
     	for(int i=0;i<YAMI.isets.size();i++)
	 {
		Vector item=(Vector)YAMI.isets.get(i);
		String L=item.get(0).toString().trim();
		
		if(L.equals("L2"))
			{
				ii=i;
				continue;
			}
			
			if(L.equals("L3"))
			{
				ii=i;
				continue;
			}
			if(i>ii)
			{
			  item=(Vector)YAMI.isets.get(i);
			   	
			String data1=item.get(0).toString().trim();
			
			
			  		
			  String[] abc=data1.split(",");
			  for(int k=0;k<abc.length;k++)
			  {
				String e1=abc[k].toString().trim();
				String e22="";
			     	for(int kk=0;kk<abc.length;kk++)
			 	{
					if(kk==k)  continue;
					String e2=abc[kk].toString().trim();
					e22+=e2+",";
					float sup1=getSupCount(e1,e2);
					
					rules=e1+"--->"+e2;
					
					if(sup1!=0.0)
					{
					if(ruleSet.indexOf(rules)==-1)
					 {
						ruleSet+=rules+" ";
						gui.append("\n"+rules+"		"+sup1);
					  }
					}
						
			  	}
					
					String e222=e22.substring(0,e22.length()-1);
					
					String rul1=e1+"--->"+e222;
					String rul2=e222+"--->"+e1;
					float sup2=getSupCount(e1,e222);	
					if(sup2!=0.0)
					{
						if(ruleSet.indexOf(rul1)==-1)
						{
						gui.append("\n"+e1+"--->"+e222+"		"+sup2);
						ruleSet+=rul1+" ";
						}
					}
					float sup3=getSupCount(e222,e1);	
					if(sup3!=0.0)
					{
						if(ruleSet.indexOf(rul2)==-1)
						{
						gui.append("\n"+e222+"--->"+e1+"		"+sup3);
						ruleSet+=rul2+" ";
						}	
					}
						
			  }
			  
			}
			
		
	 }
     }
    else if(ae.getSource()==brules2){
        AlgoPanel gui=YAMI.gui;
	String rules="";
	String ruleSet="";
	int ii=100;	
	gui.setText("New Rules\n");
	gui.append("New Rules\n--------------------------------\n\n");	
	gui.append("Rules		Confidence\n");	
	//System.out.println(YAMI.isets.size());
     	for(int i=0;i<YAMI.isets.size();i++)
	 {
		Vector item=(Vector)YAMI.isets.get(i);
		String L=item.get(0).toString().trim();
		
		
			
			if(L.equals("L3"))
			{
				ii=i;
				continue;
			}
			if(i>ii)
			{
			  item=(Vector)YAMI.isets.get(i);
			   	
			String data1=item.get(0).toString().trim();
			
			
			  		
			  String[] abc=data1.split(",");
			  for(int k=0;k<abc.length;k++)
			  {
				String e1=abc[k].toString().trim();
				String e22="";
			     	for(int kk=0;kk<abc.length;kk++)
			 	{
					if(kk==k)  continue;
					String e2=abc[kk].toString().trim();
					e22+=e2+",";
					float sup1=getSupCount(e1,e2);
					
					rules=e1+"--->"+e2;
					
					if(sup1!=0.0)
					{
					if(ruleSet.indexOf(rules)==-1)
					 {
						ruleSet+=rules+" ";
						gui.append("\n"+rules+"		"+sup1);
					  }
					}
						
			  	}
					
					String e222=e22.substring(0,e22.length()-1);
					
					String rul1=e1+"--->"+e222;
					String rul2=e222+"--->"+e1;
					float sup2=getSupCount(e1,e222);	
					if(sup2!=0.0)
					{
						if(ruleSet.indexOf(rul1)==-1)
						{
						gui.append("\n"+e1+"--->"+e222+"		"+sup2);
						ruleSet+=rul1+" ";
						}
					}
					float sup3=getSupCount(e222,e1);	
					if(sup3!=0.0)
					{
						if(ruleSet.indexOf(rul2)==-1)
						{
						gui.append("\n"+e222+"--->"+e1+"		"+sup3);
						ruleSet+=rul2+" ";
						}	
					}
						
			  }
			  
			}
			
		
	 }
    }
     try {
     
     }
     catch(Exception e) {
      System.out.println(e);
     }
     }
   public float getSupCount(String a,String b)
	{
		String abc=a+","+b;
		float sup=0,x=0,y=0,xy=0;	
		for(int i=0;i<YAMI.isets.size();i++)
	   	 {
			Vector item=(Vector)YAMI.isets.get(i);
			String L=item.get(0).toString().trim();
			if(L.equals(a))
			{
			x=Float.parseFloat(item.get(1).toString());
			
			}
			
			else if(L.equals(abc))
			{
				xy=Float.parseFloat(item.get(1).toString());
				
			}
			
		  }
			sup=xy/x;
			
		return sup;				
		
	}
 }



