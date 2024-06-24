package com.kosa.realestate.favorites.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kosa.realestate.favorites.entity.Favorite;

@Repository
public interface FavoriteRepostiory extends JpaRepository<Favorite, Long> {

  Optional<Favorite> findByFavoriteId(Long favoriteId);
}
