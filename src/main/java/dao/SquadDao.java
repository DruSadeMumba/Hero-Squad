package dao;

import models.Heroes;
import models.Squad;

import java.util.List;

public interface SquadDao {
    List<Squad> getAllSquads();

    void addSquad (Squad squad);

    Squad findById(int id);
    List<Heroes> getAllHeroesBySquad(int squadId);

    void update(int id, String name, String cause, int maxSize);

    void deleteById(int id);
    void clearAllSquads();

}
