package dao;

import models.Heroes;
import models.Squad;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class SqlSquadDaoTest {
    private SqlSquadDao squadDao;
    private SqlHeroesDao heroesDao;
    private Connection conn;

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
        int originalCategoryId = squad.getId();
        squadDao.addSquad(squad);
        assertNotEquals(originalCategoryId, squad.getId());
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
        assertEquals(1, squadDao.getAllSquads().size());
    }

    @Test
    public void noSquadReturnsEmptyList() throws Exception {
        assertEquals(0, squadDao.getAllSquads().size());
    }

    @Test
    public void updateChangesSquadCause() throws Exception {
        String initialCause = "Fight Crime";
        Squad squad = new Squad ("","Rob banks", 5);
        squadDao.addSquad(squad);
        squadDao.update(squad.getId(),"", "", 5);
        Squad updatedSquad = squadDao.findById(squad.getId());
        assertNotEquals(initialCause, updatedSquad.getName());
    }

    @Test
    public void deleteByIdDeletesCorrectSquad() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.addSquad(squad);
        squadDao.deleteById(squad.getId());
        assertEquals(0, squadDao.getAllSquads().size());
    }

    @Test
    public void clearAllClearsAllCategories() throws Exception {
        Squad squad = setupNewSquad();
        Squad otherSquad = new Squad("","", 5);
        squadDao.addSquad(squad);
        squadDao.addSquad(otherSquad);
        int daoSize = squadDao.getAllSquads().size();
        squadDao.clearAllSquads();
        assertTrue(daoSize > 0 && daoSize > squadDao.getAllSquads().size());
    }

    /*@Test
    public void getAllTasksByCategoryReturnsTasksCorrectly() throws Exception {
        Squad squad = setupNewSquad();
        squadDao.addSquad(squad);
        int squadId = squad.getId();
        Heroes newHeroes = new Heroes("superman",1,"laser eyes", "kryptonite" ,squadId);
        Heroes otherHeroes = new Heroes("wonderwoman",2,"flight", "darkness" ,squadId);
        Heroes thirdHeroes = new Heroes("batman",3,"tbd", "bats" ,squadId);
        heroesDao.add(newHeroes);
        heroesDao.add(otherHeroes);
        assertEquals(2, SquadDao.getAllHeroesBySquad(squadId).size());
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(newHeroes));
        assertTrue(squadDao.getAllHeroesBySquad(squadId).contains(otherHeroes));
        assertFalse(squadDao.getAllHeroesBySquad(squadId).contains(thirdHeroes)); //things are accurate!
    }*/

    public Squad setupNewSquad(){
        return new Squad("Justice League", "Crime Fighting", 7);
    }

}