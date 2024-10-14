package ex.springsecurity_1805.Controllers;

import com.fasterxml.jackson.annotation.JsonView;
import ex.springsecurity_1805.Models.*;
import ex.springsecurity_1805.Repositories.AudioRepository;
import ex.springsecurity_1805.Repositories.PlaylistRepository;
import ex.springsecurity_1805.Repositories.UserRepository;
import ex.springsecurity_1805.services.ServiceForPlaylists;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ControllerForPlaylists {
    private final AudioRepository audioRepository;
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private final ServiceForPlaylists service;


    @PostMapping("/likedTrack")
    public String likedTracks(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("track") Long idCurrentTrack) {
        // Находим пользователя один раз
        Usermain user = userRepository.findByName(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Проверяем, есть ли плейлист для данного пользователя
        Playlist playlist = playlistRepository.findByName(user.getId().toString())
                .orElseGet(() -> {
                    // Если плейлиста нет, создаем новый
                    Playlist newPlaylist = new Playlist();
                    newPlaylist.setUser(user);
                    newPlaylist.setName(user.getId().toString());
                    newPlaylist.setTracks(new ArrayList<>());
                    return newPlaylist;
                });

        // Добавляем трек в плейлист

        playlist.getTracks().add(idCurrentTrack);

        // Сохраняем плейлист (новый или обновленный)
        playlistRepository.save(playlist);

        return "Success";
    }

    @DeleteMapping("/likedTrackDelete")
    public String deleteLikedTracks(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("track") Long idCurrentTrack) {
        Usermain user = userRepository.findByName(userDetails.getUsername()).get();
        // Проверяем, есть ли плейлист для данного пользователя
        Playlist playlist = playlistRepository.findByName(user.getId().toString()).get();

        // Добавляем трек в плейлист

        playlist.getTracks().remove(idCurrentTrack);

        // Сохраняем плейлист (новый или обновленный)
        playlistRepository.save(playlist);

        return "Success deleting";
    }

    @GetMapping("/likeOrNo")
    public boolean likeOrNo(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("track") Long idCurrentTrack) {

        Usermain user = userRepository.findByName(userDetails.getUsername()).get();

        // Проверяем, есть ли плейлист для данного пользователя
        Optional<Playlist> playlist = playlistRepository.findByName(user.getId().toString());
        return playlist.map(value -> value.getTracks().contains(idCurrentTrack)).orElse(false);
    }

    @GetMapping("/likedPlaylist/names")
    public List<AudioShort> likedPlaylistNames(@AuthenticationPrincipal UserDetails userDetails) {
        return service.likedAudios(userDetails);
    }

    @GetMapping("/likedPlaylist/images/{id}")
    public ResponseEntity<?> likedPlaylistImages(@PathVariable Long id) {
        byte[] massa = audioRepository.findTrackImagesById(id);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/jpeg"))
                .contentLength(massa.length)
                .body(new InputStreamResource(new ByteArrayInputStream(massa)));
    }


}


