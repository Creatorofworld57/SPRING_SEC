package ex.springsecurity_1805.services;

import ex.springsecurity_1805.Repositories.UserRepository;
import ex.springsecurity_1805.Models.Usermain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service

public class MyUserDetailsService  implements UserDetailsService {
@Autowired
   private UserRepository repository;
  @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usermain> user = repository.findByName(username);
        return user.map(UserDEtailsService::new)
                .orElseThrow(()->new UsernameNotFoundException(STR."\{username}not found"));
    }
}
