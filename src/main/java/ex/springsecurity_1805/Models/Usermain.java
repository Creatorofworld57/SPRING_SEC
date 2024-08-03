package ex.springsecurity_1805.Models;


import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="Users_In_System")
public class Usermain {


    @Column(unique = true)
    @JsonView(Views.Public.class)
    private String name;
    private String password;
    @JsonView(Views.Public.class)
    private String roles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long previewImageId;
    @JsonView(Views.Public.class)
    private Date created;
    @JsonView(Views.Public.class)
    private Date updated;




    public void addImgToProduct(Img img){
        img.setUser(this);
        
    }

}
