


package dao;

import models.Heroes;
import models.Squad;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SqlSquadDaoTest {
    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    private SqlSquadDao squadDao = new SqlSquadDao(sql2o);
    private SqlHeroesDao heroesDao = new SqlHeroesDao(sql2o);
    private Connection conn = sql2o.open();

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        squadDao = new SqlSquadDao(sql2o);
        heroesDao = new SqlHeroesDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingSquadSetsId() throws Exception {
        Squad squad = setupNewSquad();
        int originalSquadId = squad.getId();
        squadDao.addSquad(squad);
        assertNotEquals(originalSquadId, squad.getId());
    }

    @Test
    public void existingSquadsCanBeFoundById() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.addSquad(squad);
        Squad foundSquad = squadDao.findById(squad.getId());
        assertEquals(squad, foundSquad);
    }

    @Test
    public void addedSquadsAreReturnedFromGetAll() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.addSquad(squad);
        assertEquals(2, squadDao.getAllSquads().size());
    }

    @Test
    public void noSquadReturnsEmptyList() throws Exception {
        assertEquals(2, squadDao.getAllSquads().size());
    }

    @Test
    public void updateChangesSquadCause() throws Exception {
        String initialCause = "Fight Crime";
        Squad squad = new Squad ("justice league","Rob banks", 7);
        squadDao.addSquad(squad);
        squadDao.update(squad.getId(),"justice league", "Rob banks", 7);
        Squad updatedSquad = squadDao.findById(squad.getId());
        assertNotEquals(initialCause, updatedSquad.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectSquad() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.addSquad(squad);
        squadDao.deleteById(squad.getId());
        assertEquals(2, squadDao.getAllSquads().size());
    }

    @Test
    public void clearAllClearsAllSquads() throws Exception {
        Squad squad = setupNewSquad();
        Squad otherSquad = new Squad("","", 5);
        squadDao.addSquad(squad);
        squadDao.addSquad(otherSquad);
        int daoSize = squadDao.getAllSquads().size();
        squadDao.clearAllSquads();
        assertTrue(daoSize > 0 && daoSize > squadDao.getAllSquads().size());
    }

    @Test
    public void getAllHeroesBySquadsReturnsHeroesCorrectly() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.addSquad(squad);
        int squadId = squad.getId();
        Heroes newHeroes = new Heroes("superman",1,"laser eyes", "kryptonite" ,squadId);
        Heroes otherHeroes = new Heroes("superman",2,"flight", "darkness" ,squadId);
        Heroes thirdHeroes = new Heroes("superman",3,"tbd", "bats" ,squadId);
        heroesDao.addHeroes(newHeroes);
        heroesDao.addHeroes(otherHeroes);
        assertEquals(2, squadDao.getAllHeroesBySquad(squadId).size());
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(newHeroes));
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(otherHeroes));
        assertFalse(squadDao.getAllHeroesBySquad(squadId).contains(thirdHeroes)); //things are accurate!
    }

    public Squad setupNewSquad(){
        return new Squad("Justice League", "Rob banks", 7);
    }

}


