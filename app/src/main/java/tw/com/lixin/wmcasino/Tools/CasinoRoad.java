package tw.com.lixin.wmcasino.Tools;

import java.util.ArrayList;
import java.util.List;

import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.global.Road;

public class CasinoRoad {

    public int posX;
    private int posY;
    private int next;
    public int[][] smallRoad;
    private int preWin = 0;
    public List<Integer> bigRoad;
    public List<List<Integer>> sortedRoad;
    private List<Integer> tempRoad;

    public List<List<Integer>> sortedRoadP;
    public List<List<Integer>> sortedRoadB;

    public int bankCount;
    public int playerCount;
    public int tieCount;

    public CasinoRoad(List<Integer> arr){
        posX = 0;
        posY = -1;
        next = -1;
        bigRoad = new ArrayList<>();
        sortedRoad = new ArrayList<>();
        sortedRoadB = new ArrayList<>();
        sortedRoadP = new ArrayList<>();

        smallRoad = new int[80][7];
        for(int i=0; i<80; i++)smallRoad[i][6] = 999;

        for(int val: arr) divide(val);
        addSortedPre();
    }

    private void divide(int rawVal){
        List<Integer> powers = new ArrayList<>();
        for(int i = 8; i >= 0; i-- ){
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

    private void addSortedPre(){
        if(preWin == 0) return;
        if(preWin == 1){
            sortedRoadB.get(sortedRoadB.size()-1).add(1);
            List<Integer> nxtLine = new ArrayList<>();
            nxtLine.add(1);
            sortedRoadP.add(nxtLine);
        }else{
            sortedRoadP.get(sortedRoadP.size()-1).add(2);
            List<Integer> nxtLine = new ArrayList<>();
            nxtLine.add(2);
            sortedRoadB.add(nxtLine);
        }
    }

    private void packRes(List<Integer> twos){
        int curRes;
        int curWin = twos.get(0);
        int curBigRes;

        if(curWin == 1){
            bankCount++;
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
                }else if(twos.get(1) == 16) {
                    curRes = Road.Bank_P;
                    curBigRes = R.drawable.casino_roadbank_2;
                }
            }
            bigRoad.add(curBigRes);
        }else if(curWin == 2){
            playerCount++;
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
                }else if(twos.get(1) == 16) {
                    curRes = Road.Play_P;
                    curBigRes = R.drawable.casino_roadplay_2;
                }
            }
            bigRoad.add(curBigRes);
        }else{
            tieCount++;
            curBigRes = R.drawable.casino_roadtie;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curBigRes = R.drawable.casino_roadtie_1;
                    if(twos.size() > 2 && twos.get(2) == 16) curBigRes = R.drawable.casino_roadtie_3;
                }else if(twos.get(1) == 16) curBigRes = R.drawable.casino_roadtie_2;
            }
            bigRoad.add(curBigRes);
            if(smallRoad[0][0] == 0) return;
            switch(smallRoad[posX][posY]) {
                case Road.Bank:
                    smallRoad[posX][posY] = Road.Bank_E;
                    break;
                case Road.Bank_B:
                    smallRoad[posX][posY] = Road.Bank_B_E;
                    break;
                case Road.Bank_P:
                    smallRoad[posX][posY] = Road.Bank_P_E;
                    break;
                case Road.Bank_P_B:
                    smallRoad[posX][posY] = Road.Bank_P_B_E;
                    break;
                case Road.Play:
                    smallRoad[posX][posY] = Road.Play_E;
                    break;
                case Road.Play_B:
                    smallRoad[posX][posY] = Road.Play_B_E;
                    break;
                case Road.Play_P:
                    smallRoad[posX][posY] = Road.Play_P_E;
                    break;
                case Road.Play_P_B:
                    smallRoad[posX][posY] = Road.Play_P_B_E;
                    break;
            }
            return;
        }

        if( (curWin - preWin) != 0){
            tempRoad = new ArrayList<>();
            sortedRoad.add(tempRoad);
            sortedRoadB.add(tempRoad);
            sortedRoadP.add(tempRoad);
            next++;
            posX = next;
            posY = -1;
        }
        tempRoad.add(curWin);

        posY++;
        if(smallRoad[posX][posY] != 0 && posY > 0) posY--;
        while (smallRoad[posX][posY] != 0) posX++;
        smallRoad[posX][posY] = curRes;
        preWin = curWin;
    }

}
