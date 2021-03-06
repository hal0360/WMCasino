package tw.com.lixin.wmcasino.global;

import tw.com.lixin.wmcasino.App;

public class Setting {

    public static String remPass(){
        return App.getStr("remPass","");
    }

    public static void remPass(String val){
        App.putStr("remPass",val);
    }

    public static boolean savePassword(){
        return App.getBool("savePass",true);
    }

    public static void savePassword(boolean val){
        App.putBool("savePass",val);
    }

    public static int language(){
        return App.getInt("language",0);
    }

    public static void language(int val){
        App.putInt("language",val);
    }

}
