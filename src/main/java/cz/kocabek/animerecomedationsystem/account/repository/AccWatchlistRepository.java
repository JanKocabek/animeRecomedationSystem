package cz.kocabek.animerecomedationsystem.account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import cz.kocabek.animerecomedationsystem.account.dto.AccWatchlistDto;
import cz.kocabek.animerecomedationsystem.account.dto.AccWatchlistShowDto;
import cz.kocabek.animerecomedationsystem.account.entity.AccWatchlist;
import cz.kocabek.animerecomedationsystem.account.entity.AccWatchlistId;

public interface AccWatchlistRepository extends JpaRepository<AccWatchlist, AccWatchlistId> {

    @Query("""
            select new cz.kocabek.animerecomedationsystem.account.dto.AccWatchlistDto( a.id.animeId,a.inWatchlist)
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

    /*TODO choose fields and create dto for it

     */
    @Query("""
            select new cz.kocabek.animerecomedationsystem.account.dto.AccWatchlistShowDto(a.id.animeId,a.anime.name,a.anime.englishName,a.anime.score,a.anime.imageURL)
                        from AccWatchlist a
                        where a.id.accId =:accId
                        and a.inWatchlist=true
            """)
    List<AccWatchlistShowDto> getActiveWatchlistByAccountId(@NonNull @Param("accId") Integer accId);
}