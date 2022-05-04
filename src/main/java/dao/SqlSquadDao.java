package dao;

import models.Heroes;
import models.Squad;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

public class SqlSquadDao implements SquadDao{
    private final Sql2o sql2o;
    public SqlSquadDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void addSquad(Squad squad) {
        String sql = "INSERT INTO squads (name, cause, maxSize) VALUES (:name, :cause, :maxSize)";
        try(Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(squad)
                    .executeUpdate()
                    .getKey();
            squad.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public List<Squad> getAllSquads() {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM squads")
                    .executeAndFetch(Squad.class);
        }
    }

    @Override
    public Squad findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM squads WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Squad.class);
        }
    }

    @Override
    public void update(int id, String newName, String newCause, int newMaxSize){
        String sql = "UPDATE squads SET (name, cause, maxSize) = (:name, :cause, :maxSize) WHERE id=:id";
        try(Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name", newName)
                    .addParameter("cause", newCause)
                    .addParameter("maxSize", newMaxSize)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM squads WHERE id=:id";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public void clearAllSquads() {
        String sql = "DELETE FROM squads";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }

    @Override
    public List<Heroes> getAllHeroesBySquad(int squadId) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM heroes WHERE squadId = :squadId")
                    .addParameter("squadId", squadId)
                    .executeAndFetch(Heroes.class);
        }
    }
}
