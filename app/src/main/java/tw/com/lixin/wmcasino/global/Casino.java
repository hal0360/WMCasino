package tw.com.lixin.wmcasino.global;

import tw.com.atromoby.utils.Json;
import tw.com.lixin.wmcasino.App;
import tw.com.lixin.wmcasino.jsonData.CasinoData;
import tw.com.lixin.wmcasino.jsonData.User;

public class Casino {

    public static int protocol(String mss){
        CasinoData proData = Json.from(mss,CasinoData.class);
        return proData.protocol;
    }

    public static User user(){
        String json = App.getStr("userJson",null);
        if(json == null) return null;
        return Json.from(json, User.class);
    }

    public static void user(User val){
        if(val == null){
            App.putStr("userJson", null);
        }
        App.putStr("userJson", Json.to(val));
    }

}
