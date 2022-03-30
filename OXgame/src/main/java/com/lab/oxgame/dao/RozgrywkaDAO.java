package com.lab.oxgame.dao;

import com.lab.oxgame.gamemodel.Rozgrywka;

import java.util.List;

public interface RozgrywkaDAO {

    void save(Rozgrywka rozgrywka);

    List<Rozgrywka> findAll();

    void deleteAll();
}
