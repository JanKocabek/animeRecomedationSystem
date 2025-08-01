package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Long> getUserWithAnime(Long aniId) {
        return userRepository.retrieveDistinctUserIdsByAnimeSorted(aniId, PageRequest.of(0, 5)).getContent();
    }

}
