package tw.com.lixin.wmcasino;

import tw.com.atromoby.utils.RegisterApplication;

public class App extends RegisterApplication {

    public static CasinoSocket lobbySocket;

    @Override
    public void onCreate() {
        super.onCreate();
        lobbySocket = new CasinoSocket();
    }
}
