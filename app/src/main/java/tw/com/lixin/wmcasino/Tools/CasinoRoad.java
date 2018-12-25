package tw.com.lixin.wmcasino.Tools;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.global.Road;

import static tw.com.lixin.wmcasino.global.Road.Bank;

public class CasinoRoad {

    private int posX = 0;
    private int posY = -1;
    private int next = -1;
    private boolean oddMode = false;
   // private int preX = 0;
   // private int preY = 0;
    private int[][] gridNum;
    private CasinoGrid grid;
    private  int preWin = 0;
    private int shift = 0;
    private int preRes = 0;
    private View preView = null;


    public CasinoRoad(CasinoGrid casinoGrid){
        grid = casinoGrid;
        gridNum = new int[90][7];

        for(int i=0; i<90; i++){
            gridNum[i][6] = 999;
        }
    }

    public void update(int sht){

    }

    public void shift(){

    }

    public void divide(List<Integer> arr){
        for(int val: arr){
            List<Integer> powers = new ArrayList<>();
            for(int i = 8; i >= 0; i-- ){
                int boss = (int) Math.pow(2,i);
                if(val >= boss){
                    powers.add(0,boss);
                    val = val - boss;
                    if(val <= 0){
                        packRes(powers);
                        break;
                    }
                }
            }
        }
    }


    private void packRes(List<Integer> twos){
        int curRes = 0;
        int curWin = twos.get(0);

        if(twos.get(0) == 1){
            curRes = Bank;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curRes = Road.Bank_B;
                    if(twos.size() > 2 && twos.get(2) == 16) curRes = Road.Bank_P_B;
                }else if(twos.get(1) == 16) curRes = Road.Bank_P;
            }
        }else if(twos.get(0) == 2){
            curRes = Road.Player;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curRes = Road.Player_B;
                    if(twos.size() > 2 && twos.get(2) == 16) curRes = Road.Player_P_B;
                }else if(twos.get(1) == 16) curRes = Road.Player_P;
            }
        }else{

            switch(preRes) {
                case Road.Bank:
                    preRes = Road.Bank_E;
                    break;
                case Road.Bank_B:
                    preRes = Road.Bank_B_E;
                    break;
                case Road.Bank_P:
                    preRes = Road.Bank_P_E;
                    break;
                case Road.Bank_P_B:
                    preRes = Road.Bank_P_B_E;
                    break;
                case Road.Player:
                    preRes = Road.Player_E;
                    break;
                case Road.Player_B:
                    preRes = Road.Player_B_E;
                    break;
                case Road.Player_P:
                    preRes = Road.Player_P_E;
                    break;
                case Road.Player_P_B:
                    preRes = Road.Player_P_B_E;
                    break;

            }
            preView.setBackgroundResource(preRes);
        }

        if(curWin > 2)return;

        if( (curWin - preWin) != 0){
            next++;
            posX = next;
            posY = -1;
            oddMode = false;
        }

        if(oddMode){
            posX++;
        }else{
            posY++;
            if(gridNum[posX][posY] != 0){
                oddMode = true;
                posY--;
                posX++;
            }
        }

        gridNum[posX][posY] = curRes;
        if(posX < grid.width){
            preView = grid.insertImage(posX,posY, curRes);
        }else{
            shift++;
        }

        preRes = curRes;
        preWin = curWin;
    }

}
