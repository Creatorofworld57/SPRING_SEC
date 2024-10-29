package ex.springsecurity_1805.Algorithms;
import java.util.ArrayList;
import java.util.List;



// Пример подготовки данных для обучения
public class DataPreparation {
    public static double[][] prepareFeatureMatrix(List<Track> tracks) {
        double[][] features = new double[tracks.size()][];
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            double[] featureVector = new double[track.getSpectralData().length + 2];
            System.arraycopy(track.getSpectralData(), 0, featureVector, 0, track.getSpectralData().length);
            featureVector[track.getSpectralData().length] = track.getGenre().hashCode(); // закодированный жанр
            featureVector[track.getSpectralData().length + 1] = track.getDuration();
            features[i] = featureVector;
        }
        return features;
    }

    public static int[] prepareLabels(List<Track> tracks) {
        int[] labels = new int[tracks.size()];
        for (int i = 0; i < tracks.size(); i++) {
            labels[i] = tracks.get(i).getLiked();
        }
        return labels;
    }
}
