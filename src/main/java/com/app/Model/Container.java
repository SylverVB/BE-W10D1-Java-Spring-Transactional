package com.app.Model;

import lombok.*;
import jakarta.persistence.*;

/**
 * The @Entity is provided by Spring Data to convert this class into an ORM entity with a relationship to the
 * database. All other annotations have been provided by Lombok.
 */
@Entity
@Data
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    // @Column annotations aren't necessary. All fields will be made columns by default.
    public String contents;
    public double weight;

    public Container(String contents, double weight) {
        this.contents = contents;
        this.weight = weight;
    }
}