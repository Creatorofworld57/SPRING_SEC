package ex.springsecurity_1805.Repositories;

import ex.springsecurity_1805.Models.Audio;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
@Transactional
@Repository
public interface AudioRepository extends JpaRepository<Audio,Long> {

    Optional<Audio> findAudioById(Long id);
    @Query("SELECT e.name FROM Audio e ORDER BY e.id ASC ")
    List<String> findAllByOrderByIdAsc();
    List<Audio> findByNameContainingIgnoreCase(String title); //здесь идет поиск названий которые содержат введенную подстроку
    Optional<Audio> findAudioByName(String name);
    // Замените String на соответствующий т
}
