package ex.springsecurity_1805.Models;


import jakarta.persistence.ElementCollection;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class updateModel {
    private String name;
    private String password;
    private String tele;
    private String git;


}
