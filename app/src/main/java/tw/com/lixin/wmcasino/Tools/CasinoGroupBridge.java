package tw.com.lixin.wmcasino.Tools;

import tw.com.lixin.wmcasino.jsonData.Server10;
import tw.com.lixin.wmcasino.jsonData.Server20;
import tw.com.lixin.wmcasino.jsonData.Server25;
import tw.com.lixin.wmcasino.jsonData.Server26;
import tw.com.lixin.wmcasino.jsonData.Server31;

public interface CasinoGroupBridge {

    void CardStatus(Server20.Data data);
    void loginStatus(Server10.Data data);
    void cardArea(int card_num);
    void balance(float value);
    void betOK();
    void gridUpdate(Server26.Data data);
    void winLossResult(Server25.Data data);
    void moneWon(Server31.Data data);
    void betCountdown(int sec);
}