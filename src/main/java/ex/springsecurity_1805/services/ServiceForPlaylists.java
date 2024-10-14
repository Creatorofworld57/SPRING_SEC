package ex.springsecurity_1805.services;

import ex.springsecurity_1805.Models.AudioShort;
import ex.springsecurity_1805.Models.Playlist;
import ex.springsecurity_1805.Repositories.AudioRepository;
import ex.springsecurity_1805.Repositories.PlaylistRepository;
import ex.springsecurity_1805.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ServiceForPlaylists {
    private final AudioRepository audioRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;

    public List<AudioShort> likedAudios(UserDetails userDetails) {
        Optional<Playlist> playlistOptional = playlistRepository.findByName(userRepository.findByName(userDetails.getUsername()).get().getId().toString());
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            List<Long> ids = playlist.getTracks();
            List<AudioShort> audios = new ArrayList<>();
            System.out.println(ids);

            List<Object[]> namesAndIds = audioRepository.findTrackNamesById(ids);
            System.out.println(namesAndIds);

            // Создаем карту для сопоставления id с AudioShort
            Map<Long, AudioShort> audioMap = new HashMap<>();
            for (Object[] result : namesAndIds) {
                if (result[0] instanceof Long) {
                    audioMap.put((Long) result[0], new AudioShort((Long) result[0], (String) result[1]));
                }
            }

            // Итерируем по ids, чтобы сохранить порядок
            for (Long id : ids) {
                AudioShort audioShort = audioMap.get(id);
                if (audioShort != null) {
                    audios.add(audioShort);
                }
            }

            System.out.println(audios);

            return audios;
        } else {
            return null;
        }
    }


}
