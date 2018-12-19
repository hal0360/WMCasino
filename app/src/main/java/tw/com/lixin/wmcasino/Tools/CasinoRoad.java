package tw.com.lixin.wmcasino.Tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.com.lixin.wmcasino.R;

public class CasinoRoad {

    private int posX = 0;
    private int posY = -1;
    private int shift = -1;
    private boolean oddMode = false;
   // private int preX = 0;
   // private int preY = 0;
    private int[][] gridNum;
    private CasinoGrid grid;


    public CasinoRoad(CasinoGrid casinoGrid){
        grid = casinoGrid;
        gridNum = new int[80][7];

        for(int i=0; i<50; i++){
            gridNum[i][6] = 999;
        }
    }

    public void setyy(){

        List<Integer> tList = Arrays.asList(1,1,1,2,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);

        int preArr = 0;
        int res = 0;
        int maxX = 0;

        for(int hiss: tList){

            if(preArr != hiss){
                shift++;
                posX = shift;
                posY = -1;
                oddMode = false;
            }
            preArr = hiss;

            switch(hiss) {
                case 1 :
                    res = R.drawable.play_1;
                    break;
                case 2 :
                    res = R.drawable.bank_1;
                    break;

                default :
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

            gridNum[posX][posY] = res;
            if (maxX < posX) maxX = posX;


        }

        for(int x=0; x <= 26; x++) {
            for (int y = 0; y < 6; y++)
                grid.insertImage(x,y, gridNum[x][y]);
        }

    }

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
