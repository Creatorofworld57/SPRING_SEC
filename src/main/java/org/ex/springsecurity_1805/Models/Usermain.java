package ex.springsecurity_1805.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="UsersInSystem")
public class Usermain {


    @Column(unique = true)
    private String name;
    private String password;
    private String roles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long previewImageId;

    public void addImgToProduct(Img img){
        img.setUser(this);
        
    }

}
