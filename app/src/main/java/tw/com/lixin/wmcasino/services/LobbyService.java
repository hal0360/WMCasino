package tw.com.lixin.wmcasino.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class LobbyService extends Service {

    private OkHttpClient client;
    private WebSocket webSocket;
    private final class MySocketListener extends WebSocketListener {

        private static final int NORMAL_CLOSURE_STATUS = 1000;
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            //   output(response.toString());
            //  webSocket.send("Hello, it's SSaurel !");
            //  webSocket.send("What's up ?");
            // webSocket.send(ByteString.decodeHex("deadbeef"));
            // webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {


        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            Log.e("onMessageByte", bytes.toString());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            Log.e("onFailure", reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            Log.e("onFailure", "bad");
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {


        super.onDestroy();
    }
}
