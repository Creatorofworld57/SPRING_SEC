package ex.springsecurity_1805.Algorithms;

import ex.springsecurity_1805.Models.Audio;
import org.jtransforms.fft.DoubleFFT_1D;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpectralAnalysis {

    public double[] SpectralAnalys(Audio audio) throws UnsupportedAudioFileException, IOException {
        // Загрузка аудио данных и спектральный анализ
        double[] audioData = loadAudioData(audio.getBuffer());
        if (audioData == null || audioData.length == 0) {
            throw new IOException("Аудиоданные пусты или не были загружены.");
        }

        // Выполнение FFT
        DoubleFFT_1D fft = new DoubleFFT_1D(audioData.length);
        fft.realForward(audioData);

        return audioData;
    }

    private double[] loadAudioData(byte[] buffer) throws IOException, UnsupportedAudioFileException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer)) {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bais);

            // Проверяем и преобразуем аудиопоток в PCM_SIGNED
            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat pcmFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16, // 16 бит на сэмпл
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, // размер кадра в байтах (16-бит стерео = 2 байта на канал)
                    baseFormat.getSampleRate(),
                    false // Little-endian
            );
            AudioInputStream pcmAudioStream = AudioSystem.getAudioInputStream(pcmFormat, audioStream);

            // Чтение PCM данных
            List<Double> audioDataList = new ArrayList<>();
            byte[] bufferBytes = new byte[4096];
            int bytesRead;
            while ((bytesRead = pcmAudioStream.read(bufferBytes)) != -1) {
                for (int i = 0; i < bytesRead - 1; i += 2) {
                    int sample = (bufferBytes[i + 1] << 8) | (bufferBytes[i] & 0xff);
                    audioDataList.add(sample / 32768.0); // нормализация до диапазона [-1, 1]
                }
            }

            // Преобразуем List<Double> в double[]
            double[] audioData = new double[audioDataList.size()];
            for (int i = 0; i < audioDataList.size(); i++) {
                audioData[i] = audioDataList.get(i);
            }

            return audioData;
        } catch (UnsupportedAudioFileException e) {
            throw new UnsupportedAudioFileException("Формат файла не поддерживается: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Ошибка при чтении аудиофайла: " + e.getMessage());
        }
    }
}
