package ex.springsecurity_1805.Controllers;


import com.fasterxml.jackson.annotation.JsonView;
import ex.springsecurity_1805.Models.Audio;
import ex.springsecurity_1805.Models.Img;
import ex.springsecurity_1805.Models.Views;

import ex.springsecurity_1805.Repositories.AudioRepository;
import ex.springsecurity_1805.services.RedisService;
import ex.springsecurity_1805.services.UserDEtailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@EnableWebMvc
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestControllerForAudio {
    private final AudioRepository audioRepository;
    private final RedisService service;


    @GetMapping("/audio/{id}")
    public ResponseEntity<?> getAudio(@PathVariable Long id) {
       Audio audio =  service.getAudioById(id);
         return ResponseEntity.ok()
                .contentType(MediaType.valueOf(audio.getContentType()))
                .contentLength(audio.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(audio.getBuffer())));
    }

    @PostMapping("/audio")
    public void audioSend(@RequestParam("file") MultipartFile file) throws IOException {
        Audio audio = new Audio();

        StringBuilder str = new StringBuilder(Objects.requireNonNull(file.getOriginalFilename()));
        str.delete(str.indexOf("."), str.lastIndexOf("3") + 1);

        audio.setBuffer(file.getBytes());
        audio.setName(str.toString());

        audio.setContentType(file.getContentType());
        audio.setSize(file.getSize());
        audioRepository.save(audio);
        System.out.println(audio.getId());

    }

    @JsonView(Views.Public.class)
    @GetMapping("/audioName/{id}")
    public Audio audioName(@PathVariable Long id) {
       /* Optional<Audio> opt = audioRepository.findAudioById(id);
        return opt.map(audio -> Map.of("name", audio.getName())).orElseGet(() -> Map.of("name", "Track"));*/
        Optional<Audio> opt = audioRepository.findAudioById(id).stream().findFirst();
        return opt.orElse(null);
    }
    @JsonView(Views.AudioImage.class)
    @GetMapping("/audio/image/{name}")
    public Audio getImageById(@PathVariable String name) {
        // Получаем аудио объект по имени
        Optional<Audio> optionalAudio = audioRepository.findTopByNameOrderByIdAsc(name);
        return optionalAudio.orElse(null);
    }



    @GetMapping("/audioInfo/{id}")
    public Audio audioInfo(@PathVariable Long id) {
        Optional<Audio> opt = audioRepository.findAudioById(id);
        return opt.orElse(null);

    }

    //доделать счетчик (сделано)
    @GetMapping("/audioCount")
    public String audioCount() {
        long counter = audioRepository.count();
        return Long.toString(102 + (counter - 1) * 50);

    }

    @CrossOrigin(origins = "https://localhost:3000/audio_playlist", allowCredentials = "true")
    @GetMapping("/playList")
    public List<String> playList(@AuthenticationPrincipal UserDEtailsService user1) {
        return new ArrayList<>(audioRepository.findAllByOrderByIdAsc());

    }
    @GetMapping("/nextAudios")
    public List<?> nextTracks(@RequestParam("id") Long idCurrentTrack,@RequestParam("direction")String direction) {
        if(Objects.equals(direction, "forward"))
            return audioRepository.findNextFiveTracks(idCurrentTrack);
        else if (Objects.equals(direction,"reverse"))
            return audioRepository.findPreviousFiveTracks(idCurrentTrack);
        else
            return List.of("Укажите направление треков");
    }

}
