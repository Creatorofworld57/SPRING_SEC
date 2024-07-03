package ex.springsecurity_1805.Models;

import jakarta.persistence.*;

import lombok.Data;



@Data
@Entity
@Table(name = "audio")
public class Audio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] buffer;
    private String name;
    private String ContentType;
    private Long Size;


    public Audio() {

    }
}
