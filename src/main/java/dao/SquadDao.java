package dao;

import models.Squad;

import java.util.List;

public interface SquadDao {
    List<Squad> getAllSquads();

    //CREATE
    void addSquad (Squad squad);

    //READ
    Squad findById(int id);
    List<Squad> getAllHeroesBySquads(int squadId);

    //UPDATE
    void update(int id, String name, String cause, int maxSize);

    //DELETE
    void deleteById(int id);
    void clearAllSquads();

}
