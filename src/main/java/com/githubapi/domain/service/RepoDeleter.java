package com.githubapi.domain.service;

import com.githubapi.domain.repository.RepoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class RepoDeleter {
    private final RepoRepository repoRepository;

    public RepoDeleter(RepoRepository repoRepository) {
        this.repoRepository = repoRepository;
    }

    public void deleteAllRepos() {
        repoRepository.deleteAll();
    }

    public void deleteRepoById(Long id) {
        repoRepository.deleteById(id);
    }
}
