package com.egj.recruitment.dao;

import com.egj.recruitment.dto.JobPostDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Erikson Matondang on 7/19/2017.
 */
@Repository
public interface JobPostDAO {
    /**
     * Method to get list of active job post
     * @return active job postings
     */
     List<JobPostDTO> getActiveJobPostings();
}
