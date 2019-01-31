package tw.com.lixin.wmcasino;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import tw.com.atromoby.utils.Json;
import tw.com.atromoby.utils.RegisterApplication;
import tw.com.atromoby.widgets.Cmd;
import tw.com.lixin.wmcasino.Tools.CasinoSocket;
import tw.com.lixin.wmcasino.Tools.CmdStr;
import tw.com.lixin.wmcasino.Tools.LobbySocket;
import tw.com.lixin.wmcasino.global.User;
import tw.com.lixin.wmcasino.jsonData.LoginData;
import tw.com.lixin.wmcasino.jsonData.LoginResData;
import tw.com.lixin.wmcasino.jsonData.Server35;
import tw.com.lixin.wmcasino.jsonData.data.Game;
import tw.com.lixin.wmcasino.models.Table;

public class App extends RegisterApplication {

    public static LobbySocket socket;
    public static final int gameID = 101;
    public static int groupID = -1;
    public static List<Table> tables;
    public static Table curTable;

    @Override
    public void onCreate() {
        super.onCreate();
        socket = new LobbySocket();
        tables = new ArrayList<>();
    }

    public static void logout(){
        socket.cleanCallbacks();
        socket.close();
    }

    public static void cleanSocketCalls(){
        socket.cleanCallbacks();
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
