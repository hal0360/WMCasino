package tw.com.lixin.wmcasino.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import tw.com.lixin.wmcasino.Tools.CasinoSocket;
import tw.com.lixin.wmcasino.global.Url;

public class CasinoService extends Service {

    private CasinoSocket lobbySocket;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        lobbySocket = new CasinoSocket();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        lobbySocket.start(Url.Lobby);

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

lobbySocket.close();
        super.onDestroy();
    }
}
