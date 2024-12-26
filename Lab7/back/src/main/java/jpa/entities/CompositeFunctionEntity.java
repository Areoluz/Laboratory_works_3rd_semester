package jpa.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "composite_func")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompositeFunctionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "inner_func")
    private String inner;
    @Column(nullable = false, name = "outer_func")
    private String outer;
}
