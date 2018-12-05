package tw.com.lixin.wmcasino.global;

import tw.com.lixin.wmcasino.App;

public class User {
    public static String account(){
        return App.getStr("account",null);
    }

    public static void account(String val){
        App.putStr("account",val);
    }

    public static String userName(){
        return App.getStr("userName",null);
    }

    public static void userName(String val){
        App.putStr("userName",val);
    }

    public static int memberID(){

        return App.getInt("memberID",0);
    }

    public static void memberID(int val){
        App.putInt("memberID",val);
    }

    public static int gameID(){

        return App.getInt("gameID",0);
    }

    public static void gameID(int val){
        App.putInt("gameID",val);
    }

    public static void logout(){
        account(null);
        memberID(0);
    }
}
