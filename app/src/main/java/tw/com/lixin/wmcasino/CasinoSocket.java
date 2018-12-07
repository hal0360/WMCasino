package tw.com.lixin.wmcasino;

import android.os.Handler;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import tw.com.atromoby.utils.Json;
import tw.com.lixin.wmcasino.jsonData.CasinoData;

public class CasinoSocket extends WebSocketListener {

    private WebSocket webSocket;
    private CmdSocket cmdSocket;
    private Handler handler;
    private CasinoData proData;

    public CasinoSocket(){
        handler = new Handler();
    }

    public void start(String url){
        if(webSocket != null) close();
        OkHttpClient client = new OkHttpClient();
        webSocket = client.newWebSocket(new Request.Builder().url(url).build(), this);
        client.dispatcher().executorService().shutdown();
    }

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
        Log.e("onMessage", text);
        proData = Json.from(text,CasinoData.class);
        handler.post(() -> cmdSocket.exec(text, proData.protocol));

    }

    public void send(String message){
        webSocket.send(message);
    }

    public void onReceive(CmdSocket cmd){
        cmdSocket = cmd;
    }

    public void close(){
        if(webSocket == null) return;
        webSocket.close(1000,null);
        webSocket = null;
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.e("onMessageByte", bytes.toString());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(1000, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        Log.e("onFailure", "bad");
    }
}
