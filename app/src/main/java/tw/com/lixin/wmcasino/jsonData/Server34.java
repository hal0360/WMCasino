package tw.com.lixin.wmcasino.jsonData;

public class Server34 {
    public int protocol;
    public Data data;

    public interface Cmd {
        void exec(Server34 server, int protocol);
    }

    public class Data{
        public int gameID;
        public int onlinePeople;
    }
}
