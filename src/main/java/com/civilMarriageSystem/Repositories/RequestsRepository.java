package com.civilMarriageSystem.Repositories;

import com.civilMarriageSystem.Domain.Requests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestsRepository extends JpaRepository<Requests, Integer> {
}
