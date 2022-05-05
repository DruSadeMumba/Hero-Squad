

package models;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class HeroesTest {
    @Test
    public void NewHeroesObjectGetsCorrectlyCreated_true() throws Exception {
        Heroes heroes = setupNewHeroes();
        assertEquals(true, heroes instanceof Heroes);
    }
    @Test
    public void HeroesInstantiatesWithPowers_true() throws Exception {
        Heroes heroes = setupNewHeroes();
        assertEquals("Super strength", heroes.getPowers());
    }
    public void HeroesInstantiatesWithWeakness_true() throws Exception {
        Heroes heroes = setupNewHeroes();
        assertEquals("Kryptonite", heroes.getWeakness());
    }
    public void HeroesInstantiatesWithAge_true() throws Exception {
        Heroes heroes = setupNewHeroes();
        assertEquals(15, heroes.getAge());
    }
    @Test
    public void isCompletesPropertyIsFalseAfterInstantiation() throws Exception {
        Heroes heroes = setupNewHeroes();
        assertEquals(false, heroes.getCompletes());
    }
    @Test
    public void getJoinedAtInstantiatesWithCurrentTimeToday() throws Exception {
        Heroes heroes = setupNewHeroes();
        assertEquals(LocalDateTime.now().getDayOfWeek(), heroes.getJoinedAt().getDayOfWeek());
    }
    //Heroes method
    public Heroes setupNewHeroes(){
        return new Heroes("Superman", 60, "Super strength", "Kryptonite",  1);
    }
}

