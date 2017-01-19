
 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;
 import java.io.*;

 public class DataSourceSelection extends JFrame
                                  implements ActionListener
 {
  JLabel l = new JLabel("Select the data source: ");

  JTextField tf = new JTextField();

  JButton    bShow = new JButton("Browse");
  JButton    bAddT = new JButton("Add Transaction");
  JButton    bNext = new JButton("Next>>");

  JTextArea ta = new JTextArea();

  public DataSourceSelection()
  {
   JPanel p1 = new JPanel(new BorderLayout());
   JPanel p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));

   p1.add(l,"West");
   p1.add(tf);
   p1.add(bShow,"East");

   p2.add(bAddT);
   p2.add(bNext);

   add(p1,"North");
   add(new JScrollPane(ta));
   add(p2,"South");

   bShow.addActionListener(this);
   bAddT.addActionListener(this);
   bNext.addActionListener(this);

   tf.setEditable(false);
   ta.setEditable(false);
   
   setTitle("Data Source Selection");
   setBounds(40,40,500,500);
   setVisible(true);
   setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  public void actionPerformed(ActionEvent ae)
  {
    if(ae.getSource()==bNext)      {                                                 
     try{
        File f = new File(tf.getText());
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(ta.getText().trim().getBytes());
        fout.close();
        Main.fnm = tf.getText();
        new PatternMiningFrame();
        dispose();

     }
     catch(Exception e) {

     }    
    }    
    else if(ae.getSource()==bShow) {
     JFileChooser fchooser = new JFileChooser("../datasets/");

     if(fchooser.showOpenDialog(this)==JFileChooser.APPROVE_OPTION) {
       try{
         File f = fchooser.getSelectedFile();
         BufferedReader buf = new BufferedReader(
          new InputStreamReader(new FileInputStream(f)));
         tf.setText(f.getPath());
         ta.setText("");
         Main.trans.clear();     
         while(true) {
          String line = buf.readLine();
          if(line==null) break;
          ta.append("\n" + line);
          Main.trans.add(line);     
         }
       }
       catch(Exception e) {

       }
     }
    }
    else {
      String trans = JOptionPane.showInputDialog(this,"Enter a new transaction: ");
      if(trans!=null) {
       ta.append("\n"+trans);
       Main.trans.add(trans);
      }
    }
  }
 }


