package ex.springsecurity_1805.services;

import com.github.javafaker.Faker;
import ex.springsecurity_1805.Models.Application;
import ex.springsecurity_1805.Models.Audio;
import ex.springsecurity_1805.Models.Img;
import ex.springsecurity_1805.Repositories.AudioRepository;
import ex.springsecurity_1805.Repositories.ImageRepository;
import ex.springsecurity_1805.Repositories.UserRepository;
import ex.springsecurity_1805.Models.Usermain;
import jakarta.annotation.PostConstruct;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;


@Service
@AllArgsConstructor
public class ServiceApp {
    private List<Application> applicationList;
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;
    private ImageRepository imageRepository;
    private AudioRepository audioRepository;

    @PostConstruct
    public void loadAppInDB() {
        Faker faker = new Faker();
        applicationList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Application.builder()
                        .id(i)
                        .name(faker.app().name())
                        .author(faker.app().author())
                        .version(faker.app().version())
                        .build())
                .toList();
    }

    public List<Application> allApplications() {
        return applicationList;
    }

    public Application applicationById(int id) {

        return applicationList.stream()
                .filter(app -> app.getId() == id)
                .findFirst().orElse(null);
    }

    public Application applicationByName(String name) {
        return applicationList.stream()
                .filter(app -> Objects.equals(app.getName(), name))
                .findFirst().orElse(null);
    }

    public void addUser(HttpServletRequest request, MultipartFile file) throws IOException {
        Img img = new Img();
        Usermain user = new Usermain();
        user.setName(request.getParameter("name"));
        user.setPassword(request.getParameter("password"));
        user.setRoles(request.getParameter("roles"));

        if (file != null && file.getSize() != 0) {
            img = toImgEntity(file);
            img.setPreview(true);
            user.addImgToProduct(img);
        }
        Date date = new Date();
        user.setUpdated(date);
        user.setCreated(date);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Usermain userok = repository.save(user);
        Img img1 = imageRepository.save(img);
        userok.setPreviewImageId(img1.getId());
        repository.save(userok);

    }

@Transactional
    public void updateUser(String name, String password, MultipartFile file, UserDEtailsService model) throws IOException {

        Img img2;
        //переписать

        Long id = repository.findByName(model.getUsername()).get().getId();
        if (file != null && file.getSize() != 0) {
            img2 = toImgEntity(file);
            img2.setPreview(true);
            repository.getReferenceById(id).addImgToProduct(img2);

            Long ImageId = repository.getReferenceById(id).getPreviewImageId();
            imageRepository.getReferenceById(ImageId).setName(img2.getName());
            imageRepository.getReferenceById(ImageId).setOriginalFileName(img2.getOriginalFileName());
            imageRepository.getReferenceById(ImageId).setContentType(img2.getContentType());
            imageRepository.getReferenceById(ImageId).setBytes(img2.getBytes());
            imageRepository.getReferenceById(ImageId).setSize(img2.getSize());

            imageRepository.save(imageRepository.getReferenceById(ImageId));

        }
        if (password != null)
            repository.getReferenceById(id).setPassword(passwordEncoder.encode(password));
        if (name != null)
            repository.getReferenceById(id).setName(name);
        Date data = new Date();
        repository.getReferenceById(id).setUpdated(data);

        repository.save(repository.getReferenceById(id));
    }


    @Transactional
    public void deleteUser(UserDEtailsService model) {
        try {
            Optional<Usermain> userOpt = repository.findByName(model.getUsername());

            if (userOpt.isPresent()) {
                Usermain user = userOpt.get();
                Long previewImageId = user.getPreviewImageId();

                if (previewImageId != null) {
                    Img image = imageRepository.findById(previewImageId)
                            .orElseThrow(() -> new RuntimeException("Image not found"));
                    image.setUser(null);
                    user.setPreviewImageId(null);
                    imageRepository.delete(image);
                }

                repository.delete(user);

            }
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при удалении пользователя: " + ex.getMessage(), ex);
        }
    }

    private Img toImgEntity(MultipartFile file) throws IOException {
        Img img = new Img();
        img.setName(file.getName());
        img.setOriginalFileName(file.getOriginalFilename());
        img.setContentType(file.getContentType());
        img.setSize(file.getSize());
        img.setBytes(file.getBytes());
        return img;

    }

    public void audioKeep(MultipartFile file) throws IOException {
        Audio audio = new Audio();
        audio.setBuffer(file.getBytes());
        audio.setName(file.getOriginalFilename());
        audio.setContentType(file.getContentType());
        audio.setSize(file.getSize());
        audioRepository.save(audio);
    }

}
