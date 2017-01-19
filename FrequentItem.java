public class FrequentItem {
    public String itemset;
    public int sup_count;
    public double sup=0;

    FrequentItem(String t, int s)
    {
        itemset = t;
        sup_count = s;
    }
}
