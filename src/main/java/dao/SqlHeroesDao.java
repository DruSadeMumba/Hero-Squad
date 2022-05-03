package dao;

import models.Heroes;
import org.sql2o.*;
import java.util.List;

public class SqlHeroesDao implements HeroesDao{
    private final Sql2o sql2o;

    public SqlHeroesDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void addHeroes(Heroes heroes) {
        String sql = "INSERT INTO heroes (name, age, powers, weakness, squadId) VALUES (:name, :age, :powers, :weakness, :squadId)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(heroes)
                    .executeUpdate()
                    .getKey();
            heroes.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Heroes> getAll() {
        try(Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM heroes")
                    .executeAndFetch(Heroes.class);
        }
    }

    @Override
    public Heroes findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM heroes WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Heroes.class);
        }
    }

    @Override
    public void update(int id, String newName, int newAge, String newPowers, String newWeakness, int newSquadId){
        String sql = "UPDATE heroes SET (name, age, powers, weakness, squadId) = (:name, :age, :powers, :weakness, :squadId) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("age", newAge)
                    .addParameter("powers", newPowers)
                    .addParameter("weakness", newWeakness)
                    .addParameter("squadId", newSquadId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from heroes WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override 
    public void clearAllHeroes() {
        String sql = "DELETE from heroes";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }
}

