package tw.com.lixin.wmcasino.jsonData;


import java.util.List;

import tw.com.lixin.wmcasino.jsonData.data.Game;

public class Server35 {

    // public int protocol;

    public interface CmdData {
        void exec(Data data);
    }
    public Data data;

    public class Data{
        public List<Game> gameArr;
    }


}
