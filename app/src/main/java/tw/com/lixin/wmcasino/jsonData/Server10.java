package tw.com.lixin.wmcasino.jsonData;

import java.util.Map;

public class Server10 {
    public int protocol;
    public Data data;

    public class Data{
        public Map<Integer, String> dtOdds;
        public int gameID;
        public int groupID;
        public Boolean bOk;
    }
}
