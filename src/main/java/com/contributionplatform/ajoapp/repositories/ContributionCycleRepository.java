package com.contributionplatform.ajoapp.repositories;

import com.contributionplatform.ajoapp.models.ContributionCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContributionCycleRepository extends JpaRepository<ContributionCycle, Long> {
}
