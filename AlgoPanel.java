
 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;

 class AlgoPanel extends JPanel{

   JTextArea ta = new JTextArea();
   int algo=-1;
   String msv;

   public AlgoPanel(int algo) {
    setLayout(new BorderLayout());
    add(new JScrollPane(ta));
    ta.setEditable(false);
    this.algo = algo;
   }

   public void setText(String s) {
    ta.setText("");
    ta.setCaretPosition(ta.getText().length());
   }

   public String getText() {
    return ta.getText();
   }

   public void append(String text) {
    ta.append(text);
    ta.setCaretPosition(ta.getText().length());
   }

   public void append(char text) {
    ta.append(text+"");
    ta.setCaretPosition(ta.getText().length());
   }

   public void appendln(String text) {
    ta.append(text + "\n");
    ta.setCaretPosition(ta.getText().length());
   }

   public void appendln() {
    ta.append("\n");
    ta.setCaretPosition(ta.getText().length());
   }

   public void appendln(int data) {
    ta.append(data + "\n");
    ta.setCaretPosition(ta.getText().length());
   }

   public void execute(String msv) {
     this.msv = msv;
     (new AlgoThread(this)).start();
   }

   class AlgoThread extends Thread {
    AlgoPanel gui;
    AlgoThread(AlgoPanel gui) {
     this.gui = gui;
    }

    public void run() {
      if(algo==Algorithms.APRIORI) {
        long dur1 =  System.nanoTime();
       YAMI apriori = new YAMI(gui);
        Main.apFreqTrans = apriori.find_frequent_itemsets(Double.parseDouble(gui.msv));
        Main.apDur = System.nanoTime()-dur1;
      }
     /* else if(algo==Algorithms.BOOLEAN_MATRIX) {
        long dur1 =  System.nanoTime();
        BooleanMatrix bm = new BooleanMatrix(gui);
        Main.bmFreqTrans = bm.find_frequent_itemsets(Double.parseDouble(gui.msv));
        Main.bmDur = System.nanoTime()-dur1;
      }*/
    }
   }
 }
