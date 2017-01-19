
import java.util.*;
import java.util.logging.*;

public class YAMI {

   public static ArrayList isets=new ArrayList();
  	
    double min_sup=0;
    int trans_count=0;
    public static AlgoPanel gui;
    
    public YAMI(AlgoPanel gui)     {
     Main.apPatCount = 0;
     this.gui=gui;
     gui.setText("");
    }

    public LinkedList<FrequentItem> find_frequent_itemsets(double m_sup)
    {
        int i=1;
        min_sup = m_sup;

        LinkedList<FrequentItem> frequent_itemsets = new LinkedList<FrequentItem>();
        LinkedList<FrequentItem> L;
        LinkedList<FrequentItem> C=null;

        L = Find_frequent_1_itemsets();    
        PrintTable("Level" + i++, L);
        
        while(L.size() > 1)
        {
            C = apriori_gen(L);
            L=C;

            if(C.size() > 1) {             
             frequent_itemsets.addAll(C);
             PrintTable("L" + i++, C);
            }
        }

        return frequent_itemsets;
    }            

    public LinkedList<FrequentItem> apriori_gen(LinkedList<FrequentItem> L)
    {
        LinkedList<FrequentItem> C = new LinkedList<FrequentItem>();

        int k = L.get(0).itemset.length()/2; 
        if(k==0)
        {
            for(int i=0;i<L.size();i++)
            {
                for(int j=i+1; j<L.size();j++)
                {
                    FrequentItem c = new FrequentItem(L.get(i).itemset+","+ L.get(j).itemset,-1);
                    if(IsFrequentItem(c))
                        C.addLast(c);
                }
            }
        }
        else
        {
            for(int i=0;i<L.size();i++)
            {
                for(int j=i+1; j<L.size();j++)
                {
                    
                    if(IsPosibleInter(L.get(i).itemset, L.get(j).itemset, k))
                    {
                        FrequentItem c = new FrequentItem(L.get(i).itemset+","+ L.get(j).itemset.substring(k*2),-1);
                        if(IsFrequentItem(c))
                            C.addLast(c);
                    }
                }
            }
        }
        return C;
    }

    private boolean IsFrequentItem(FrequentItem c) {
        boolean r_value = false;
        boolean check=false;
        LinkedList<String> trans = null;
        StringParser sp = new StringParser();
        try {
            trans = Main.trans;
        } catch (Exception ex) {
            Logger.getLogger(YAMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        int current_sup = 0;

        for(int i=0; i<trans.size();i++)
        {
            sp.SetString(c.itemset);
            check = true;
            while(!sp.IsEnd())
            {
                String tmp = sp.GetNextItem();
                if(trans.get(i).indexOf(tmp) == -1) 
                {
                    check = false;
                    break;
                }
            }
            if(check)
                current_sup++;
        }
        if((current_sup / (double)trans_count) >= min_sup)
            r_value = true;
        c.sup_count = current_sup;
        return r_value;
    }

    private boolean IsPosibleInter(String s1, String s2, int k)
    {
        boolean r_value = true;
        StringParser sp1 = new StringParser(s1);
        StringParser sp2 = new StringParser(s2);

        for(int i=0; i<k ; i++)
        {
            if(sp1.GetNextItem().compareTo(sp2.GetNextItem()) != 0)
            {
                r_value = false;
                i=k;
            }
        }
        return r_value;
    }
    public LinkedList<FrequentItem> Find_frequent_1_itemsets()
    {
        LinkedList<FrequentItem> r_value = new LinkedList<FrequentItem>();
        LinkedList<String> trans = null;
        StringParser sp = new StringParser();
        try {
            trans = Main.trans;
        } catch (Exception ex) {
            Logger.getLogger(YAMI.class.getName()).log(Level.SEVERE, null, ex);
        }

        LinkedList<String> inter = new LinkedList<String>();
        String tmp = "@";
        int i=0;
        gui.append("\n=====Transaction Table=====\n");
        for(i=0; i<trans.size() ; i++)
        {
            tmp = trans.get(i);
            gui.append(tmp+"\n");
            sp.SetString(tmp);
            while(!sp.IsEnd())
            {
                String item = sp.GetNextItem();
                if(!IsExistItem(inter, item))
                {
                    inter.addLast(item);
                }
            }
        }
        
        String[] s = inter.toArray(new String[inter.size()]);
        Arrays.sort(s, String.CASE_INSENSITIVE_ORDER);
        List<String> m = Arrays.asList(s);

        trans_count = i;
        int sup = 0;
        for(i=0; i<m.size() ; i++)
        {
            sup=0;
            for(int j=0; j<trans.size(); j++)
            {
                tmp = trans.get(j);
                sp.SetString(tmp);
                while(!sp.IsEnd())
                {
                    if(m.get(i).compareTo(sp.GetNextItem()) == 0)
                    {
                        sup++;
                    }
                }
            }
            if( (sup / (double)trans_count) >= min_sup)
            {
                FrequentItem fi = new FrequentItem(m.get(i), sup);
                r_value.addLast(fi);
            }
        }

        return r_value;
    }
		
		int level=0;
    private void PrintTable(String table_name, LinkedList<FrequentItem> t)
    {
		level++;
		String level1="L"+level;
	 Vector iset1=new Vector();
	   	iset1.add(level1);
		isets.add(iset1);
        gui.append("\n======" + table_name + "=====\n");
        for(int i=0; i<t.size(); i++)
        {
		 Vector iset=new Vector();
            gui.append(t.get(i).itemset + " [SupCount=" + t.get(i).sup_count + ", Sup="+(t.get(i).sup_count / (double)trans_count)+"]\n");
	         iset.add(t.get(i).itemset);
		 iset.add(t.get(i).sup_count);  	
		 iset.add(t.get(i).sup_count / (double)trans_count);
		 isets.add(iset);	
        }
        Main.apPatCount += t.size();
    }

    public boolean IsExistItem(LinkedList<String> list, String item)
    {
        for(int i=0; i<list.size() ; i++)
        {
            if(list.get(i).compareTo(item) == 0)
                return true;
        }
        return false;
    }
}
