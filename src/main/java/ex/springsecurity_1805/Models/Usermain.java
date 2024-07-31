package ex.springsecurity_1805.Models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="Users_In_System")
public class Usermain {


    @Column(unique = true)
    private String name;
    private String password;
    private String roles;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long previewImageId;

    private Date created;

    private Date updated;




    public void addImgToProduct(Img img){
        img.setUser(this);
        
    }

}
