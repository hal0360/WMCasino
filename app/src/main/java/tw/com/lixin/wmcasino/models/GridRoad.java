package tw.com.lixin.wmcasino.models;

import java.util.List;

import tw.com.lixin.wmcasino.global.Road;

public class GridRoad {

    public int posX = -1;
    public int posY = -1;
    public int next = -1;
    public int preWin = 0;

  //  private int next;
    public int[][] road;

    public GridRoad(){
        road = new int[80][6];
    }


    public void setFirst(List<List<Integer>> arrs){

        for(List<Integer> arr : arrs){
            next++;
            posX = next;
            posY = -1;
            for(int res: arr){
                posY++;
                if(posY > 5 || road[posX][posY] != 0) posY--;
                while (road[posX][posY] != 0) posX++;
                road[posX][posY] = res;
            }
        }

    }

    public void setSec(List<List<Integer>> arrs){
        boolean blueWillWin = true;
        for(int i = 0; i < arrs.size() - 1; i++ ){
            List<Integer> curLine = arrs.get(i+1);
            List<Integer> preLine = arrs.get(i);
            if(i > 0){
                if(blueWillWin) drawReal(Road.Bank);
                else drawReal(Road.Play);
            }
            for(int k = 2; k <= curLine.size(); k++ ){
                if(k - preLine.size() == 1) drawReal(Road.Play);
                else drawReal(Road.Bank);
            }
            int lastK = curLine.size() + 1;
            blueWillWin = lastK - preLine.size() == 1;
        }
    }

    public void setThird(List<List<Integer>> arrs){
        boolean blueWillWin = true;
        for(int i = 0; i < arrs.size() - 2; i++ ){
            List<Integer> curLine = arrs.get(i+2);
            List<Integer> preLine = arrs.get(i);
            if(i > 0){
                if(blueWillWin) drawReal(Road.Bank_S);
                else drawReal(Road.Play_S);
            }
            for(int k = 2; k <= curLine.size(); k++ ){
                if(k - preLine.size() == 1) drawReal(Road.Play_S);
                else drawReal(Road.Bank_S);
            }
            int lastK = curLine.size() + 1;
            blueWillWin = lastK - preLine.size() == 1;
        }
    }

    public void setFourth(List<List<Integer>> arrs){
        boolean blueWillWin = true;
        for(int i = 0; i < arrs.size() - 3; i++ ){
            List<Integer> curLine = arrs.get(i+3);
            List<Integer> preLine = arrs.get(i);
            if(i > 0){
                if(blueWillWin) drawReal(Road.Bank_I);
                else drawReal(Road.Play_I);
            }
            for(int k = 2; k <= curLine.size(); k++ ){
                if(k - preLine.size() == 1) drawReal(Road.Play_I);
                else drawReal(Road.Bank_I);
            }
            int lastK = curLine.size() + 1;
            blueWillWin = lastK - preLine.size() == 1;
        }
    }

    private void drawReal(int rid){

        if(preWin != rid){
            next++;
            posX = next;
            posY = -1;
        }


        posY++;
        if(posY > 5 || road[posX][posY] != 0 && posY > 0) posY--;
        while (road[posX][posY] != 0) posX++;

        road[posX][posY] = rid;
        preWin = rid;
    }
}
