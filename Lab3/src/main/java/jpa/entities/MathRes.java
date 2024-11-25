package jpa.entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "MathFunc")
public class MathRes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Double x;

    @Column(nullable = false)
    private Double y;

    @Column(nullable = false)
    private Long hash;

    public MathRes() {
    }

    public MathRes(Double x, Double y, Long hash) {
        this.x = x;
        this.y = y;
        this.hash = hash;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Long getHash() {
        return hash;
    }

    public void setHash(Long hash) {
        this.hash = hash;
    }
}
