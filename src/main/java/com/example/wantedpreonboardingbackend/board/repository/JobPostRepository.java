package com.example.wantedpreonboardingbackend.board.repository;

import com.example.wantedpreonboardingbackend.board.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    boolean existsByCompanyNameAndPosition(String companyName, String position);

}
