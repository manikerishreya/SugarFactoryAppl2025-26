package com.project.repository;

import com.project.entity.VillageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VillageRepo extends JpaRepository<VillageEntity,Integer> {
    VillageEntity findByVillageName(String village);
}
