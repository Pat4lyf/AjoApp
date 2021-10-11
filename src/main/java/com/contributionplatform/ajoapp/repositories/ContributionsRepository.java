package com.contributionplatform.ajoapp.repositories;

import com.contributionplatform.ajoapp.models.Contributions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionsRepository extends JpaRepository<Contributions, Long> {
}
