package com.egj.recruitment.service;

import com.egj.recruitment.dto.JobPostDTO;

import java.util.List;

/**
 * Created by Erikson Matondang on 7/19/2017.
 */
public interface JobPostingService {
    /**
     * Method to get list of active job postings
     * @return active job posting
     */
    List<JobPostDTO> getActiveJobPostings();
}
