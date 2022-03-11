package pl.bartoszsredzinski.sudokuapp;

import java.io.Serializable;

/**
 * Class description
 *
 * @author Bartosz Średziński
 * created on 11.03.2022
 */
public class LeaderboardEntity implements Comparable<LeaderboardEntity>, Serializable{
    private Integer id;
    private final String name;
    private final Integer score;

    public LeaderboardEntity(int id, String name, int score){
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public Integer getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public Integer getScore(){
        return score;
    }

    public void setId(Integer id){
        this.id = id;
    }

    @Override
    public int compareTo(LeaderboardEntity e){
        return this.getScore().compareTo(e.getScore());
    }
}
