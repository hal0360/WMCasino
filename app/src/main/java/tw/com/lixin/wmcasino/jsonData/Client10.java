package tw.com.lixin.wmcasino.jsonData;


public class Client10 {

    private Data data = new Data();
    int protocol = 10;

    public Client10(int num){
        data.groupID = num;
    }

    private class Data{
        DeLimt dtBetLimitSelectID = new DeLimt();
        int groupID = 0;
    }

    private class DeLimt{

    }

}
