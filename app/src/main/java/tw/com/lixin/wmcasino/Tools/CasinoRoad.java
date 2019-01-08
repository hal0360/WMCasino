package tw.com.lixin.wmcasino.Tools;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

import tw.com.lixin.wmcasino.R;
import tw.com.lixin.wmcasino.global.Road;

public class CasinoRoad {

    private int posX = 0;
    private int posY = -1;
    private int next = -1;
    private int[][] gridNum;
    private CasinoGrid grid;
    private int preWin = 0;
    private int shift = 0;

    public CasinoRoad(CasinoGrid casinoGrid){
        grid = casinoGrid;
        gridNum = new int[80][7];

        for(int i=0; i<80; i++){
            gridNum[i][6] = 999;
        }
    }

    public void update(int val){
        divide(val);
        resetGrid();
    }

    private void resetGrid(){
        for(int x = 0; x < grid.width; x++){
            for(int y=0; y<6; y++){
                grid.insertImage(x,y,gridNum[x+shift][y]);
            }
        }
    }

    public void update(List<Integer> arr){
        for(int val: arr){
            divide(val);
        }
        resetGrid();
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

    private void packRes(List<Integer> twos){
        int curRes = 0;
        int curWin = twos.get(0);

        if(twos.get(0) == 1){
            curRes = Road.Bank;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curRes = Road.Bank_B;
                    if(twos.size() > 2 && twos.get(2) == 16) curRes = Road.Bank_P_B;
                }else if(twos.get(1) == 16) curRes = Road.Bank_P;
            }
        }else if(twos.get(0) == 2){
            curRes = Road.Play;
            if(twos.size() > 1){
                if(twos.get(1) == 8){
                    curRes = Road.Play_B;
                    if(twos.size() > 2 && twos.get(2) == 16) curRes = Road.Play_P_B;
                }else if(twos.get(1) == 16) curRes = Road.Play_P;
            }
        }else{
            switch(gridNum[posX][posY]) {
                case Road.Bank:
                    gridNum[posX][posY] = Road.Bank_E;
                    break;
                case Road.Bank_B:
                    gridNum[posX][posY] = Road.Bank_B_E;
                    break;
                case Road.Bank_P:
                    gridNum[posX][posY] = Road.Bank_P_E;
                    break;
                case Road.Bank_P_B:
                    gridNum[posX][posY] = Road.Bank_P_B_E;
                    break;
                case Road.Play:
                    gridNum[posX][posY] = Road.Play_E;
                    break;
                case Road.Play_B:
                    gridNum[posX][posY] = Road.Play_B_E;
                    break;
                case Road.Play_P:
                    gridNum[posX][posY] = Road.Play_P_E;
                    break;
                case Road.Play_P_B:
                    gridNum[posX][posY] = Road.Play_P_B_E;
                    break;

            }
        }

        if(curWin > 2)return;

        if( (curWin - preWin) != 0){
            next++;
            posX = next;
            posY = -1;
        }

        posY++;
        if(gridNum[posX][posY] != 0) posY--;
        while (gridNum[posX][posY] != 0) posX++;

        /*
        if(oddMode){
            posX++;
        }else{
            posY++;
            if(gridNum[posX][posY] != 0){
                oddMode = true;
                posY--;
                posX++;
            }
        }*/
        gridNum[posX][posY] = curRes;
        if(posX >= grid.width) shift++;
        preWin = curWin;
    }

}
