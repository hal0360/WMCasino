package tw.com.lixin.wmcasino.jsonData;

import com.google.gson.JsonObject;

import java.util.List;

public class Server35 {

    public Data data;

    public class Data{
        public List<Game> gameArr;
    }

    public class Game{
        public int gameID;
        public JsonObject dtOdds;
       // public List<Stage> groupArr;
    }

    public class Stage{
        public int bankerScore;
        public int betMilliSecond;
        public int dealerID;
        public int gameBo;
        public int gameNoRound;
        public int gameStage;
        public int groupID;
        public int groupType;
        public List<Integer> historyArr;
        public int multiAreaID;
    }
}
