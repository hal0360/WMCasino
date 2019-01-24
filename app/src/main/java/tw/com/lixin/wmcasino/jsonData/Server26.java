package tw.com.lixin.wmcasino.jsonData;

import java.util.List;

public class Server26 {
    public Data data;
  //  public int protocol;

    public interface CmdData {
        void exec();
    }


    public class Data{
        public int gameID;
        public int groupID;
        public List<Integer> historyArr;
        public int groupType;

    }
}