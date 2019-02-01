package tw.com.lixin.wmcasino.jsonData;

public class Server30 {
    public Data data;

    // public int protocol;

    public interface CmdData {
        void exec(Data data);
    }

    public class Data{
        public int balance;
        public int memberID;
    }
}
