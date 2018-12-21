package tw.com.lixin.wmcasino.Tools;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.com.lixin.wmcasino.R;

public class CasinoRoad {

    private int posX = 0;
    private int posY = -1;
    private int next = -1;
    private boolean oddMode = false;
   // private int preX = 0;
   // private int preY = 0;
    private int[][] gridNum;
    private CasinoGrid grid;


    public CasinoRoad(CasinoGrid casinoGrid){
        grid = casinoGrid;
        gridNum = new int[85][7];
        for(int i=0; i<85; i++){
            gridNum[i][6] = 999;
        }
    }

    public void setGrid(){

        List<Integer> tList = Arrays.asList(1,1,1,2,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1);
        int preArr = 0;
        int res = 0;
        int shift = 0;

        for(int hiss: tList){

            if(preArr != hiss){
                next++;
                posX = next;
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

           // if(posX > 79) break;

            gridNum[posX][posY] = res;
            if(posX < grid.width){
                grid.insertImage(posX,posY, res);
            }else{
                shift++;
            }

        }

/*
        for(int x=0; x < maxX; x++) {
            for (int y = 0; y < 6; y++)
                grid.insertImage(x,y, gridNum[x+shift][y]);
        }*/

    }

    public void update(int sht){

    }

    public void shift(){

    }

    public static List<Integer> divide(List<Integer> arr){

        List<Integer> powers = new ArrayList<>();

        for(int val: arr){

            for(int i = 8; i >= 0; i-- ){
                int boss = (int) Math.pow(2,i);
                if(val >= boss){
                    powers.add(0,boss);
                    val = val - boss;
                    if(val == 0){
                        return powers;
                    }
                }
            }

        }

        return powers;
    }
}
