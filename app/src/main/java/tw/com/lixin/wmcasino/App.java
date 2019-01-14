package tw.com.lixin.wmcasino;

import java.util.List;

import tw.com.atromoby.utils.RegisterApplication;
import tw.com.lixin.wmcasino.Tools.CasinoSocket;
import tw.com.lixin.wmcasino.jsonData.data.Game;

public class App extends RegisterApplication {

    public static CasinoSocket lobbySocket;
    public static CasinoSocket bacSocket;
    public static List<Game> games;
  //  public static List<>

    @Override
    public void onCreate() {
        super.onCreate();
        lobbySocket = new CasinoSocket();
        bacSocket = new CasinoSocket();
    }
}
