public class StringParser {

        String line;
        String delimeter;
        int currentPosition;


        public StringParser()
        {
            line = "";
            currentPosition = 0;
            delimeter = ",";
        }

        public StringParser(String str)
        {
            line = str;
            currentPosition = 0;
            delimeter = ",";
        }

        public void SetString(String str)
        {
            line = str;
            currentPosition = 0;
        }

        public void SetDelimeter(String del)
        {
            delimeter = del;
        }

        public String GetNextItem()
        {
            if (currentPosition == -1)
                return "";

            int end = line.indexOf(delimeter, currentPosition);

            if (end > 0)
            {
                String returnStr = line.substring(currentPosition, end);
                currentPosition = end + 1;
                return returnStr;
            }
            else
            {
                String returnStr = line.substring(currentPosition);
                currentPosition = -1;
                return returnStr;
            }
        }

        public boolean IsEnd()
        {
            return currentPosition == -1;
        }

        public void ResetCurrentPosition()
        {
            currentPosition = 0;
        }


        public String GetItem(int idx)
        {
            String r_value ="";
            int oldCurrent = currentPosition;
            currentPosition = 0;
            for (int i = 0; i <= idx; i++)
                r_value = GetNextItem();

            currentPosition = oldCurrent;
            return r_value;
        }
}
