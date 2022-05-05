

package dao;

import models.Heroes;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class SqlHeroesDaoTest {

    String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
    Sql2o sql2o = new Sql2o(connectionString, "", "");
    private SqlHeroesDao heroesDao = new SqlHeroesDao(sql2o);
    private Connection conn = sql2o.open();

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
        heroesDao.addHeroes(heroes);
        assertNotEquals(originalHeroesId, heroes.getId());
    }

    @Test
    public void existingHeroesCanBeFoundById() throws Exception {
        Heroes heroes = setupNewHeroes();
        heroesDao.addHeroes(heroes);
        Heroes foundHeroes = heroesDao.findById(heroes.getId());
        assertEquals(heroes.getId(), foundHeroes.getId());
    }

    @Test
    public void addedHeroesAreReturnedFromgetAll() throws Exception {
        Heroes heroes = setupNewHeroes();
        heroesDao.addHeroes(heroes);
        assertEquals(4, heroesDao.getAll().size());
    }

    @Test
    public void noHeroessReturnsEmptyList() throws Exception {
        assertEquals(4, heroesDao.getAll().size());
    }

    @Test
    public void updateChangesHeroesPowers() throws Exception {
        String initialPowers = "speed";
        Heroes heroes = setupNewHeroes();
        heroesDao.addHeroes(heroes);

        heroesDao.update(heroes.getId(), "flash", 8, "flight", "water", 1);
        Heroes updatedHeroes = heroesDao.findById(heroes.getId());
        assertNotEquals(initialPowers, updatedHeroes.getPowers());
    }
    @Test
    public void updateChangesHeroesWeakness() throws Exception {
        String initialWeakness = "water";
        Heroes heroes = setupNewHeroes();
        heroesDao.addHeroes(heroes);

        heroesDao.update(heroes.getId(), "flash", 8, "flight", "gravity", 1);
        Heroes updatedHeroes = heroesDao.findById(heroes.getId());
        assertNotEquals(initialWeakness, updatedHeroes.getWeakness());
    }

    @Test
    public void deleteByIdDeletesCorrectHeroes() throws Exception {
        Heroes heroes = setupNewHeroes();
        heroesDao.addHeroes(heroes);
        heroesDao.deleteById(heroes.getId());
        assertEquals(0, heroesDao.getAll().size());
    }

    @Test
    public void clearAllClearsAll() throws Exception {
        Heroes heroes = setupNewHeroes();
        Heroes otherHeroes = new Heroes("", 1, "","", 2);
        heroesDao.addHeroes(heroes);
        heroesDao.addHeroes(otherHeroes);
        int daoSize = heroesDao.getAll().size();
        heroesDao.clearAllHeroes();
        assertTrue(daoSize > 0 && daoSize > heroesDao.getAll().size());
    }

    @Test
    public void squadIdIsReturnedCorrectly() throws Exception {
        Heroes heroes = setupNewHeroes();
        int originalSquadId = heroes.getSquadId();
        heroesDao.addHeroes(heroes);
        assertEquals(originalSquadId, heroesDao.findById(heroes.getId()).getSquadId());
    }
    public Heroes setupNewHeroes(){
        return new Heroes(" dru",3, "high intellect","darkness", 4);
    }
}

