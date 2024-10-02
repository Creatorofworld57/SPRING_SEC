package ex.springsecurity_1805.Controllers;

import ex.springsecurity_1805.Models.Audio;
import ex.springsecurity_1805.Repositories.AudioRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class RestControllerForAudioTest {
    @Mock
    private AudioRepository audioRepository;

    @InjectMocks
    RestControllerForAudio restControllerForAudio;

    @Test
    public void getAudioName(){
        var names = new Audio();
        names.setName("Soda Luv Дождь");
        doReturn(names).when(audioRepository).findAll();

        var responseEntity= this.restControllerForAudio.audioName(152L);

        assertNotNull(responseEntity);


    }
}