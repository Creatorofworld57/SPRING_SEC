package ex.springsecurity_1805.Algorithms;

import org.springframework.stereotype.Component;
import smile.classification.RandomForest;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.data.formula.Formula;
import smile.data.vector.IntVector;
import smile.data.type.StructType;
import smile.data.vector.DoubleVector;


public class RecommendationModel {
    private RandomForest model;
    private StructType schema;

    public void trainModel(double[][] features, int[] labels) {
        // Проверка на допустимые входные размеры данных
        if (features == null || labels == null) {
            throw new IllegalArgumentException("Features and labels must not be null.");
        }
        if (features.length != labels.length) {
            throw new IllegalArgumentException("Number of feature vectors and labels must be the same.");
        }
        if (features.length == 0) {
            throw new IllegalArgumentException("Features and labels must not be empty.");
        }

        // Создание DataFrame из массива features и labels
        DataFrame data = DataFrame.of(features, "feature");
        data = data.merge(IntVector.of("label", labels));

        // Создание формулы для RandomForest
        Formula formula = Formula.lhs("label");

        // Обучение модели случайного леса с использованием DataFrame
        model = RandomForest.fit(formula, data); // 100 деревьев


    }

    public int predict(double[] features) {
        if (model == null) {
            throw new IllegalStateException("Model has not been trained. Call trainModel() first.");
        }
        if (features == null || features.length == 0) {
            throw new IllegalArgumentException("Features for prediction must not be null or empty.");
        }

        // Создание Tuple с использованием схемы
        Tuple x = new Tuple() {
            @Override
            public StructType schema() {
                return null;
            }

            @Override
            public Object get(int i) {
                return null;
            }
        };

        // Прогнозирование с использованием модели
        return model.predict(x);// возвращает 0 или 1
    }
}
