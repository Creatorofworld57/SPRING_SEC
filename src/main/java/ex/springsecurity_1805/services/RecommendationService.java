package ex.springsecurity_1805.services;
import ex.springsecurity_1805.Algorithms.DataPreparation;
import ex.springsecurity_1805.Algorithms.RecommendationModel;
import ex.springsecurity_1805.Algorithms.Track;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class RecommendationService {
    /*  private final RecommendationModel recommendationModel;



    public void train(List<Track> tracks) {
        double[][] features = DataPreparation.prepareFeatureMatrix(tracks);
        int[] labels = DataPreparation.prepareLabels(tracks);
        recommendationModel.trainModel(features, labels);
    }

    public List<Track> recommendTracks(List<Track> candidateTracks) {
        List<Track> recommendations = new ArrayList<>();
        for (Track track : candidateTracks) {
            double[] features = new double[track.getSpectralData().length + 2];
            System.arraycopy(track.getSpectralData(), 0, features, 0, track.getSpectralData().length);
            features[track.getSpectralData().length] = track.getGenre().hashCode();
            features[track.getSpectralData().length + 1] = track.getDuration();

            if (recommendationModel.predict(features) == 1) {
                recommendations.add(track);
            }
        }
        return recommendations;
    }*/
}
