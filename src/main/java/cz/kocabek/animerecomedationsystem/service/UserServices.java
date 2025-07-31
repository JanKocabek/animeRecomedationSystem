package cz.kocabek.animerecomedationsystem.service;

import cz.kocabek.animerecomedationsystem.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public Iterable<UserDto> getAllUsersWhoRatedGiven(Long id) {
//        List<User> users = new ArrayList<>();
//        try {
//            users = userRepository.findByUsersAnimeScores_Anime_Id(id);
//        } catch (Exception e) {
//            e.printStackTrace(System.err);
//        }
//
//
//        if (users.isEmpty()) {
//            return null;
//        }
//        final var batch = users.subList(0, Math.min(users.size(), 5));
//        List<UserDto> uDTO = new ArrayList<>();
//        for (User user : batch) {
//            Set<UsersAnimeScoreDto> uasDTO = new LinkedHashSet<>();
//            for (UsersAnimeScore uas : user.getUsersAnimeScores()) {
//                Anime a = uas.getAnime();
//                AnimeDto aDTO = new AnimeDto(a.getName());
//                UsersAnimeScoreDto UsersAnimeScoreDto = new UsersAnimeScoreDto(aDTO, uas.getRating());
//                uasDTO.add(UsersAnimeScoreDto);
//            }
//            //uDTO.add(new UserDto(user.getId(), uasDTO));
//        }
//        return uDTO;
//    }


    public List<Long> getUserWithAnime(Long aniId) {
        return userRepository.findUserIdDistinctByUsersAnimeScores_Id_UserId(aniId);
    }

}
