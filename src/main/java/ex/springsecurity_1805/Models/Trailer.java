package ex.springsecurity_1805.Models;

import jakarta.persistence.*;
import lombok.Data;
@Table(name="trailers")
@Entity
@Data
public class Trailer {

    byte[] size;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}
