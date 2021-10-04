package com.contributionplatform.ajoapp.repositories;

import com.contributionplatform.ajoapp.models.Requests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestsRepository extends JpaRepository<Requests, Long> {
}
