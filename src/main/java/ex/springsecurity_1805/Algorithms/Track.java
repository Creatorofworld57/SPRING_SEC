package ex.springsecurity_1805.Algorithms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Track {
    private double[] spectralData; // Спектральные данные
    private String genre;
    private double duration; // Продолжительность
    private int liked; // 0 - не понравился, 1 - понравился (целевая метка)

    // Конструктор, геттеры и сеттеры
}


