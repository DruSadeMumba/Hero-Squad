package dao;

import models.Heroes;


import java.util.List;

public interface HeroesDao {
    List<Heroes> getAll();

    void addHeroes(Heroes heroes);

    Heroes findById(int id);

    void update(int id, String name, int age, String powers, String weakness, int squadId);

    void deleteById(int id);

    void clearAllHeroes();

}
