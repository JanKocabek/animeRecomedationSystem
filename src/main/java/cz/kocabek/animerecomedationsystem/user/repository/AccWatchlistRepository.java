package cz.kocabek.animerecomedationsystem.user.repository;

import cz.kocabek.animerecomedationsystem.user.dto.AccWatchlistDto;
import cz.kocabek.animerecomedationsystem.user.entity.AccWatchlist;
import cz.kocabek.animerecomedationsystem.user.entity.AccWatchlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AccWatchlistRepository extends JpaRepository<AccWatchlist, AccWatchlistId> {

    @Query("""
            select new cz.kocabek.animerecomedationsystem.user.dto.AccWatchlistDto( a.id.animeId,a.inWatchlist)
                        from AccWatchlist a where a.id.accId=?1
            """
    )
    List<AccWatchlistDto> getWatchlistByAccId(@NonNull Integer accId);

    @Transactional
    @Modifying
    @Query("""
            update AccWatchlist a set a.inWatchlist = :isInWatchlist
                    where a.id.animeId= :animeId
                    and a.id.accId= :accId
            """)
    int updateWatchlistStatus(@NonNull @Param("animeId") Long animeId, @NonNull @Param("accId") Integer accId, @Param("isInWatchlist") boolean isInWatchlist);


}