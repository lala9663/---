package com.example.wantedpreonboardingbackend.post.repository;

import com.example.wantedpreonboardingbackend.post.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    boolean existsByCompanyNameAndPosition(String companyName, String position);

    List<JobPost> findByCompanyName(String companyName);

    List<JobPost> findByPosition(String position);

}
