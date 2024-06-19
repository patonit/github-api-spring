package com.githubapi.domain.repository;

import com.githubapi.domain.model.Repo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface RepoRepository extends Repository<Repo, Long> {

    Repo save(Repo repo);

    List<Repo> findAll(Pageable pageable);

    Optional<Repo> findById(Long id);

    @Modifying
    @Query("UPDATE Repo r SET r.name = :#{#repo.name}, r.owner = :#{#repo.owner} WHERE r.id = :id")
    void updateRepoById(Long id, Repo repo);

    void deleteAll();

    void deleteById(Long id);
}
