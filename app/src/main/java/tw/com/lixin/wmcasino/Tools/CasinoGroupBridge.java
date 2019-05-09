package tw.com.lixin.wmcasino.Tools;

import tw.com.lixin.wmcasino.jsonData.Server10;
import tw.com.lixin.wmcasino.jsonData.Server20;

public interface CasinoGroupBridge {

    void CardStatus(Server20.Data data);
    void loginStatus(Server10.Data data);
    void cardArea(int card_num);
    void balance(float value);
    void betOK();
}
