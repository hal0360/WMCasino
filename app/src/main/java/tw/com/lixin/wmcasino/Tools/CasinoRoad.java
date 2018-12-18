package tw.com.lixin.wmcasino.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.com.lixin.wmcasino.R;

public class CasinoRoad {

    private int posX = 0;
    private int posY = 0;
    private int shift = -1;
    private boolean oddMode = false;
   // private int preX = 0;
   // private int preY = 0;
    private int[][] gridNum;
    private CasinoGrid grid;


    public CasinoRoad(CasinoGrid casinoGrid){
        grid = casinoGrid;
        gridNum = new int[50][7];

        for(int i=0; i<50; i++){
            gridNum[i][6] = 999;
        }
    }

    public void setyy(){

        List<Integer> tList = Arrays.asList(1,1,1,2,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2);

        int preArr = 0;
        int res = 0;
        int maxX = 0;

        for(int hiss: tList){

            if(preArr != hiss){
                shift++;
                posX = shift;
                posY = 0;
                oddMode = false;
            }

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
                if(gridNum[posX][posY] == 0){
                    posY++;
                }else{
                    oddMode = true;
                    posY--;
                    posX++;
                }
            }

            gridNum[posX][posY] = res;

        }

        for(int x=0; x <= posX; x++) {
            for (int y = 0; y < 6; y++)
                gridNum[x][y] = 0;
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
