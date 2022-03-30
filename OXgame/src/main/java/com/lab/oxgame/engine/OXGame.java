package com.lab.oxgame.engine;

import com.lab.oxgame.gamemodel.OXEnum;

public interface OXGame {

    void initialize();

    void setPole(int indeks);

    OXEnum getPole(int indeks);

    OXEnum getKolej();

    OXEnum getZwyciezca();
}
