package com.lab.oxgame.engine;

import com.lab.oxgame.gamemodel.OXEnum;

public class OXGameImpl implements OXGame {
    private OXEnum kolej;
    private OXEnum zwyciezca;
    private OXEnum[] plansza;
    private int krok;
    private boolean koniec;
    private boolean wygrana;

    @Override
    public void initialize(){
        krok = 0;
        zwyciezca = OXEnum.BRAK;
        kolej = Math.random() < 0.5 ? OXEnum.X : OXEnum.O;
        plansza = new OXEnum[] {OXEnum.BRAK, OXEnum.BRAK, OXEnum.BRAK,
                                OXEnum.BRAK, OXEnum.BRAK, OXEnum.BRAK,
                                OXEnum.BRAK, OXEnum.BRAK, OXEnum.BRAK};

    }

    @Override
    public void setPole(int indeks) {
        plansza[indeks] = kolej;

        krok += 1;
        koniec = krok > 8;
        wygrana = (kolej.equals(plansza[0]) && kolej.equals(plansza[4]) && kolej.equals(plansza[8]))
                || (kolej.equals(plansza[2]) && kolej.equals(plansza[4]) && kolej.equals(plansza[6]))
                || (kolej.equals(plansza[0]) && kolej.equals(plansza[1]) && kolej.equals(plansza[2]))
                || (kolej.equals(plansza[3]) && kolej.equals(plansza[4]) && kolej.equals(plansza[5]))
                || (kolej.equals(plansza[6]) && kolej.equals(plansza[7]) && kolej.equals(plansza[8]))
                || (kolej.equals(plansza[0]) && kolej.equals(plansza[3]) && kolej.equals(plansza[6]))
                || (kolej.equals(plansza[1]) && kolej.equals(plansza[4]) && kolej.equals(plansza[7]))
                || (kolej.equals(plansza[2]) && kolej.equals(plansza[5]) && kolej.equals(plansza[8]));
    }

    @Override
    public OXEnum getPole(int index) {
        return plansza[index];
    }

    @Override
    public OXEnum getKolej() {
        if (kolej == OXEnum.O) kolej = OXEnum.X;
        else if (kolej == OXEnum.X) kolej = OXEnum.O;

        return kolej;
    }

    @Override
    public OXEnum getZwyciezca() {
        if (koniec && !wygrana)
            return OXEnum.BRAK;
        else if (wygrana) {
            zwyciezca = kolej;
            return zwyciezca;
        }
        return null;
    }
}
