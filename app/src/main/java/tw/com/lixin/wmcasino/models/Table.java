package tw.com.lixin.wmcasino.models;


import java.util.ArrayList;
import java.util.List;

import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.Tools.CasinoRoad;
import tw.com.lixin.wmcasino.Tools.FourthRoad;
import tw.com.lixin.wmcasino.Tools.SecRoad;
import tw.com.lixin.wmcasino.Tools.ThirdRoad;
import tw.com.lixin.wmcasino.global.Road;

public class Table {

    public CasinoRoad casinoRoad;
    public SecRoad secRoad;
    public SecRoad secRoadPreP;
    public SecRoad secRoadPreB;

    public ThirdRoad thirdRoad;
    public ThirdRoad thirdRoadPreP;
    public ThirdRoad thirdRoadPreB;

    public FourthRoad fourthRoad;
    public FourthRoad fourthRoadPreP;
    public FourthRoad fourthRoadPreB;

    public int score;
    public int number;
    public int stage;
    public int round;
    public int groupID;
    public int groupType;


    public int leftScore;
    public int rightScore;
    public int topScore;
    public int btLscore;
    public int btRscore;


    public List<List<Integer>> sortedRoad;
    public List<Integer> mainRoad;


    private List<Integer> tempRoad;
    private int preWin = 0;
    private int preRes = 0;


    public Table(List<Integer> arr){
        mainRoad = new ArrayList<>();
        sortedRoad = new ArrayList<>();
        for(int val: arr) divide(val);
    }

    private void divide(int rawVal){

        List<Integer> powers = new ArrayList<>();
        for(int i = 10; i >= 0; i-- ){
            int boss = (int) Math.pow(2,i);
            if(rawVal >= boss){
                powers.add(0,boss);
                rawVal = rawVal - boss;
                if(rawVal <= 0){
                    packRes(powers);
                    break;
                }
            }
        }

    }

    private void packRes(List<Integer> twos){

        int curRes;
        Integer curWin = twos.get(0);
        int curBigRes;

        if(curWin == 1){
            curRes = Road.Bank;
            curBigRes = R.drawable.casino_roadbank;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curRes = Road.Bank_B;
                    curBigRes = R.drawable.casino_roadbank_1;
                    if(twos.size() > 2 && twos.get(2) == 16){
                        curRes = Road.Bank_P_B;
                        curBigRes = R.drawable.casino_roadbank_3;
                    }
                }else if(twos.get(1) == 16){
                    curRes = Road.Bank_P;
                    curBigRes = R.drawable.casino_roadbank_2;
                }
            }
            mainRoad.add(curBigRes);
        }else if(curWin == 2){
            curRes = Road.Play;
            curBigRes = R.drawable.casino_roadplay;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curRes = Road.Play_B;
                    curBigRes = R.drawable.casino_roadplay_1;
                    if(twos.size() > 2 && twos.get(2) == 16) {
                        curRes = Road.Play_P_B;
                        curBigRes = R.drawable.casino_roadplay_3;
                    }
                }else if(twos.get(1) == 16){
                    curRes = Road.Play_P;
                    curBigRes = R.drawable.casino_roadplay_2;
                }
            }
            mainRoad.add(curBigRes);
        }else{
            curBigRes = R.drawable.casino_roadtie;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curBigRes = R.drawable.casino_roadtie_1;
                    if(twos.size() > 2 && twos.get(2) == 16) curBigRes = R.drawable.casino_roadtie_3;
                }else if(twos.get(1) == 16) curBigRes = R.drawable.casino_roadtie_2;
            }
            mainRoad.add(curBigRes);

            if(preWin == 0) return;

            switch(preRes) {
                case Road.Bank:
                    tempRoad.set(tempRoad.size() -1 ,Road.Bank_E);
                    break;
                case Road.Bank_B:
                    tempRoad.set(tempRoad.size() -1 ,Road.Bank_B_E);
                    break;
                case Road.Bank_P:
                    tempRoad.set(tempRoad.size() -1 ,Road.Bank_P_E);
                    break;
                case Road.Bank_P_B:
                    tempRoad.set(tempRoad.size() -1 ,Road.Bank_P_B_E);
                    break;
                case Road.Play:
                    tempRoad.set(tempRoad.size() -1 ,Road.Play_E);
                    break;
                case Road.Play_B:
                    tempRoad.set(tempRoad.size() -1 ,Road.Play_B_E);
                    break;
                case Road.Play_P:
                    tempRoad.set(tempRoad.size() -1 ,Road.Play_P_E);
                    break;
                case Road.Play_P_B:
                    tempRoad.set(tempRoad.size() -1 ,Road.Play_P_B_E);
                    break;
            }
            return;
        }

        if( (curWin - preWin) != 0){
            tempRoad = new ArrayList<>();
            sortedRoad.add(tempRoad);
        }
        tempRoad.add(curRes);
        preRes = curRes;
        preWin = curWin;
    }


    public void askRoad(int askWin){

        if(sortedRoad.size()>1){
            if(askWin ==  preWin) {

            }
        }

        if(askWin ==  preWin) {

        }
    }

}
