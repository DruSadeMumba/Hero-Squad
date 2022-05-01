package dao;

import models.Heroes;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class SqlHeroesDaoTest {

    private SqlHeroesDao heroesDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        heroesDao = new SqlHeroesDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingHeroesSetsId() throws Exception {
        Heroes heroes = setupNewHeroes();
        int originalHeroesId = heroes.getId();
        heroesDao.add(heroes);
        assertNotEquals(originalHeroesId, heroes.getId()); //how does this work?
    }

    @Test
    public void existingHeroesCanBeFoundById() throws Exception {
        Heroes heroes = setupNewHeroes();
        heroesDao.add(heroes);
       Heroes foundHeroes = heroesDao.findById(heroes.getId());
        assertEquals(heroes, foundHeroes);
    }

    @Test
    public void addedHeroesAreReturnedFromgetAll() throws Exception {
        Heroes heroes = setupNewHeroes();
        heroesDao.add(heroes);
        assertEquals(1, heroesDao.getAll().size());
    }

    @Test
    public void noHeroessReturnsEmptyList() throws Exception {
        assertEquals(0, heroesDao.getAll().size());
    }

    @Test
    public void updateChangesHeroesPowers() throws Exception {
        String initialPowers = "speed";
        Heroes heroes = setupNewHeroes();
        heroesDao.add(heroes);

        heroesDao.update(heroes.getId(), "flash", 8, "flight", "water", 1);
        Heroes updatedHeroes = heroesDao.findById(heroes.getId());
        assertNotEquals(initialPowers, updatedHeroes.getPowers());
    }
    @Test
    public void updateChangesHeroesWeakness() throws Exception {
        String initialWeakness = "water";
        Heroes heroes = setupNewHeroes();
        heroesDao.add(heroes);

        heroesDao.update(heroes.getId(), "flash", 8, "flight", "gravity", 1);
        Heroes updatedHeroes = heroesDao.findById(heroes.getId());
        assertNotEquals(initialWeakness, updatedHeroes.getWeakness());
    }

    @Test
    public void deleteByIdDeletesCorrectHeroes() throws Exception {
        Heroes heroes = setupNewHeroes();
        heroesDao.add(heroes);
        heroesDao.deleteById(heroes.getId());
        assertEquals(0, heroesDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Heroes heroes = setupNewHeroes();
        Heroes otherHeroes = new Heroes("", 1, "","", 2);
        heroesDao.add(heroes);
        heroesDao.add(otherHeroes);
        int daoSize = heroesDao.getAll().size();
        heroesDao.clearAllHeroes();
        assertTrue(daoSize > 0 && daoSize > heroesDao.getAll().size());
    }

    @Test
    public void squadIdIsReturnedCorrectly() throws Exception {
        Heroes heroes = setupNewHeroes();
        int originalSquadId = heroes.getSquadId();
        heroesDao.add(heroes);
        assertEquals(originalSquadId, heroesDao.findById(heroes.getId()).getSquadId());
    }
    public Heroes setupNewHeroes(){
        return new Heroes("",3, "","", 4);
    }
}