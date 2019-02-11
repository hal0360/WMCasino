package tw.com.lixin.wmcasino.models;

import java.util.List;

public class GridRoad {

    public int posX;
    public int posY;
    public int next = -1;

  //  private int next;
    public int[][] road;

    public GridRoad(){
        road = new int[80][6];
    }

    public void setFirst(List<List<Integer>> arrs){

        for(List<Integer> arr : arrs){
            next++;
            posX = next;
            for(int res: arr){
                if(posY > 5 || road[posX][posY] != 0) posY--;
                while (road[posX][posY] != 0) posX++;
                road[posX][posY] = res;
                posY++;
            }
        }

    }

  //  public

  //  public void setSecond(List<List<Integer>> arrs){

   // }
}
