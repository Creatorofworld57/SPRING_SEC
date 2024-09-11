package ex.springsecurity_1805.Models;

import jakarta.persistence.*;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;


@Data
@Entity
@Table(name = "audio")
public class Audio {
    @Id
    @GeneratedValue(generator = "custom-generator")
    @GenericGenerator(name = "custom-generator", type = ex.springsecurity_1805.Models.CustomIdGenerator.class)
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
