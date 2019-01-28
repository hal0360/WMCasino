package tw.com.lixin.wmcasino.models;


import tw.com.lixin.wmcasino.Tools.CasinoRoad;
import tw.com.lixin.wmcasino.Tools.FourthRoad;
import tw.com.lixin.wmcasino.Tools.SecRoad;
import tw.com.lixin.wmcasino.Tools.ThirdRoad;

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

}
