package tw.com.lixin.wmcasino;

import android.os.Bundle;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;
import tw.com.atromoby.utils.Json;
import tw.com.atromoby.widgets.RootActivity;
import tw.com.lixin.wmcasino.JsonData.Protocol;

public class LoginActivity extends RootActivity {

    private OkHttpClient client;
    private WebSocket webSocket;
    private final class EchoWebSocketListener extends WebSocketListener {

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
            Log.e("onMessage", text);
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
            Log.e("onFailure", "failed");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://gameserver.a45.me:15109").build();
        EchoWebSocketListener listener = new EchoWebSocketListener();
        webSocket = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();

        Protocol protocol = new Protocol();

        clicked(R.id.loginBtn,v -> webSocket.send(Json.to(protocol)));
    }

}
