package tw.com.lixin.wmcasino.Tools;

import java.util.ArrayList;
import java.util.List;

import tw.com.lixin.wmcasino.CoinHolder;

public class CoinStackBack {

    public boolean backUp = false;

    public int hit = 0;
    public int value = 0;
    public int maxValue = 999;

    public List<CoinHolder> addedCoin = new ArrayList<>();
    public List<CoinHolder> tempAddedCoin = new ArrayList<>();
}
