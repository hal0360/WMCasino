package tw.com.lixin.wmcasino;

import android.util.SparseArray;

import java.util.List;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.utils.RegisterApplication;
import tw.com.atromoby.widgets.Cmd;
import tw.com.lixin.wmcasino.Tools.CasinoSocket;
import tw.com.lixin.wmcasino.Tools.CmdStr;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.LoginData;
import tw.com.lixin.wmcasino.jsonData.LoginResData;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.models.Table;

public class App extends RegisterApplication {

    public static CasinoSocket lobbySocket;
    public static final int gameID = 101;
    public static int groupID = -1;
    public static List<Table> tables;


    @Override
    public void onCreate() {
        super.onCreate();
        lobbySocket = new CasinoSocket();
    }

    public static void cleanSocketCalls(){
        lobbySocket.cleanCallbacks();
    }

    public static Table findTable(int id){
        for(Table tt: tables){
            if(tt.groupID == id){

                return tt;
            }
        }
        return null;
    }

}
