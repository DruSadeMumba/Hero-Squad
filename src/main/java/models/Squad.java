package models;

import java.util.Objects;

public class Squad {
    private String name;
    private String cause;
    private int maxSize;
    private int id;

    public Squad(String name, String cause, int maxSize) {
        this.cause = cause;
        this.name = name;
        this.maxSize = maxSize;
    }

    public String getName() {
        return name;
    }
    public String getCause() {
        return cause;
    }
    public int getMaxSize() {
        return maxSize;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Squad)) return false;
        Squad squad = (Squad) o;
        return id == squad.id && Objects.equals(name, squad.name)
                && Objects.equals(cause, squad.cause)
                && Objects.equals(maxSize, squad.maxSize) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cause, maxSize);
    }
}
