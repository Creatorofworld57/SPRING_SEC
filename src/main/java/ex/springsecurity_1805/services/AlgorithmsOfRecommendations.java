package ex.springsecurity_1805.services;
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class AlgorithmsOfRecommendations {

    // Преобразование аудиоданных с помощью быстрого преобразования Фурье (FFT)
    public static double[] getFrequencyData(byte[] songBytes) {
        int n = songBytes.length;

        // Преобразуем байтовый массив в массив вещественных чисел
        double[] songData = new double[n];
        for (int i = 0; i < n; i++) {
            songData[i] = songBytes[i];
        }

        // Применяем преобразование Фурье
        DoubleFFT_1D fft = new DoubleFFT_1D(n);
        fft.realForward(songData); // Прямое преобразование

        return songData; // Возвращаем спектральные данные
    }

    // Сравнение двух песен через спектральные данные
    public static double compareSongs(byte[] song1, byte[] song2) {
        double[] freqData1 = getFrequencyData(song1);
        double[] freqData2 = getFrequencyData(song2);

        // Вычисляем евклидово расстояние между спектральными характеристиками
        double distance = 0.0;
        for (int i = 0; i < freqData1.length; i++) {
            distance += Math.pow(freqData1[i] - freqData2[i], 2);
        }

        return Math.sqrt(distance); // Чем меньше расстояние, тем более похожи песни
    }

    public static void main(String[] args) {
        byte[] song1 = {/* байты песни 1 */};
        byte[] song2 = {/* байты песни 2 */};

        double similarity = compareSongs(song1, song2);

        if (similarity < 1000) { // Задайте пороговое значение для определения схожести
            System.out.println("Песни похожи.");
        } else {
            System.out.println("Песни разные.");
        }
    }
}
