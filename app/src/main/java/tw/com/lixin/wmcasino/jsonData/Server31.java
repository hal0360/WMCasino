package tw.com.lixin.wmcasino.jsonData;

import java.util.Map;

public class Server31 {
    public int protocol;
    public Data data;

    public interface Cmd {
        void exec(Server31 server, int protocol);
    }

    public class Data{
        public int gameID;
        public int groupID;
        public int memberID;
        public int moneyWinLoss;
        public int moneyWin;
        public Map<Integer, String> dtMoneyWin;
    }

}
