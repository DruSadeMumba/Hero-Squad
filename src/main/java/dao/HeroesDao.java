package dao;

import models.Heroes;


import java.util.List;

public interface HeroesDao {
    List<Heroes> getAll();

    void add(Heroes heroes);

    Heroes findById(int id);

    void update(int id, String name, int age, String powers, String weakness, int squadId);

    void deleteById(int id);

    void clearAllHeroes();

}
