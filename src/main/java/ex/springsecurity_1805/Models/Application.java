package ex.springsecurity_1805.Models;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

@AllArgsConstructor
@NoArgsConstructor
public class Application {
    private int id;
    private String name;
    private String author;
    private String version;


}
