package tw.com.lixin.wmcasino.jsonData;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Server35 {

    public Data data;

    public class Data{
        public ArrayList<Game> gameArr;
    }

    public class Game{
        public int GameID;
        public JsonObject dtOdds;
        public ArrayList<Stage> groupArr;
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
        public ArrayList<Integer> historyArr;
        public int multiAreaID;
    }
}
