package tw.com.lixin.wmcasino.jsonData;

import java.util.ArrayList;
import java.util.List;

public class CasinoData {
    public int protocol;

    public static List<Integer> divide(int val){

        List<Integer> powers = new ArrayList<>();
        for(int i = 8; i >= 0; i-- ){
            int boss = (int) Math.pow(2,i);
            if(val >= boss){
                powers.add(boss);
                val = val - boss;
                if(val == 0){
                    return powers;
                }
            }
        }
        return powers;
    }
}
