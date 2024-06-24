package ex.springsecurity_1805.Repositories;

import ex.springsecurity_1805.Models.Audio;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Transactional
@Repository
public interface AudioRepository extends JpaRepository<Audio,Long> {
    Optional<Audio> findByName(String name);
    Optional<Audio> findAudioById(Long id);
}
