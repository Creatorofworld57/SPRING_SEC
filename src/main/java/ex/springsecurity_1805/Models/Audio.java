package ex.springsecurity_1805.Models;

import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(Views.Public.class)
    private String name;
    private String ContentType;
    @JsonView(Views.Public.class)
    private Long Size;
    @JsonView(Views.Public.class)
    private String Author;
    @JsonView(Views.Public.class)
    private String image;


    public Audio() {

    }
}
