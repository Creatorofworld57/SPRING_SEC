package ex.springsecurity_1805.Controllers;


import ex.springsecurity_1805.Models.Audio;
import ex.springsecurity_1805.Repositories.AudioRepository;



import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

@EnableWebMvc
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestControllerForAudio {
    private final AudioRepository audioRepository;

    @Transactional
    @GetMapping("/audio/{id}")
    public ResponseEntity<?> getAudio(@PathVariable Long id) {

        Audio audio = audioRepository.getReferenceById(id);

        return ResponseEntity.ok()
                .header("Name", audio.getName())
                .contentType(MediaType.valueOf(audio.getContentType()))
                .contentLength(audio.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(audio.getBuffer())));

    }

    @PostMapping("/audio")
    public void audioSend(@RequestParam("file") MultipartFile file) throws IOException {
        Audio audio = new Audio();

        StringBuilder str = new StringBuilder(Objects.requireNonNull(file.getOriginalFilename()));
        str.delete(str.indexOf("."),str.lastIndexOf("3"));

        audio.setBuffer(file.getBytes());
        audio.setName(str.toString());

        audio.setContentType(file.getContentType());
        audio.setSize(file.getSize());
        audioRepository.save(audio);
    }
    @GetMapping("/audioName/{id}")
    public String audioName(@PathVariable Long id){
        if(audioRepository.findAudioById(id).isPresent())
            return audioRepository.findAudioById(id).get().getName();
        else
            return "Track";
    }
    @GetMapping("/audioCount")
    public String audioCount() {
        long counter = audioRepository.count();
        return Long.toString(counter);

    }

}
