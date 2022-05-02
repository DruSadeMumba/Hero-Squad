package models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Heroes {
    private String name;
    private int age;
    private String powers;
    private String weakness;
    private boolean completes;
    private LocalDateTime joinedAt;
    private int id;
    private int squadId;

    public Heroes(String name, int age, String powers, String weakness, int squadId){
        this.name = name;
        this.age = age;
        this.powers =powers;
        this.weakness = weakness;
        this.completes = false;
        this.joinedAt = LocalDateTime.now();
        this.squadId = squadId;
    }
    public int getSquadId() {
        return squadId;
    }
    public void setSquadIdId(int squadId) {this.squadId = squadId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Heroes)) return false;
        Heroes heroes = (Heroes) o;
        return getCompletes() == heroes.getCompletes() && getId() == heroes.getId() && Objects.equals(getPowers(), heroes.getPowers());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getPowers(), getWeakness(),  getCompletes(), getId());
    }
    public void setPowers(String powers) {
        this.powers = powers;
    }
    public void setWeakness(String weakness) {
        this.weakness = weakness;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCompletes(boolean completes) {
        this.completes = completes;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getPowers() {
        return powers;
    }
    public String getWeakness() {
        return weakness;
    }
    public int getAge() {
        return age;
    }
    public String getName() {return name;}
    public boolean getCompletes(){return this.completes;}
    public LocalDateTime getJoinedAt() {return joinedAt;}
    public int getId() {
        return id;
    }
}
