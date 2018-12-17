package tw.com.lixin.wmcasino.Tools;

import android.os.Handler;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.Cmd;
import tw.com.lixin.wmcasino.Tools.CmdSocket;
import tw.com.lixin.wmcasino.jsonData.CasinoData;

public class CasinoSocket extends WebSocketListener {

    private WebSocket webSocket = null;
    private CmdSocket cmdSocket;
    private Cmd cmdOpen, cmdFail;
    private Handler handler;
    private CasinoData proData;

    public CasinoSocket(){
        handler = new Handler();
    }

    public void start(String url){
        OkHttpClient client = new OkHttpClient();
        webSocket = client.newWebSocket(new Request.Builder().url(url).build(), this);
        client.dispatcher().executorService().shutdown();
    }

    public void onSuccess(Cmd cmd){
        cmdOpen = cmd;
    }

    public void onFail(Cmd cmd){
        cmdFail = cmd;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        //   output(response.toString());
        //  webSocket.send("Hello, it's SSaurel !");
        //  webSocket.send("What's up ?");
        // webSocket.send(ByteString.decodeHex("deadbeef"));
        // webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        if(cmdOpen != null){
            handler.post(() -> cmdOpen.exec());
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        Log.e("onMessage", text);
        proData = Json.from(text,CasinoData.class);
        if(cmdSocket != null){
            handler.post(() -> cmdSocket.exec(text, proData.protocol));
        }
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
        cleanCallbacks();
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
     //   Log.e("onMessageByte", bytes.toString());
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
       // Log.e("onClosing", "bye bye");
       // webSocket.close(1000, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        this.webSocket = null;
        if(cmdFail != null){
            handler.post(() -> cmdFail.exec());
        }
    }

    public void cleanCallbacks(){
        cmdFail = null;
        cmdOpen = null;
        cmdSocket = null;
        handler.removeCallbacksAndMessages(null);
    }
}
