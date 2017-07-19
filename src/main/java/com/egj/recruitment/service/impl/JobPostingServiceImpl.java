package com.egj.recruitment.service.impl;

import com.egj.recruitment.dao.JobPostDAO;
import com.egj.recruitment.dto.JobPostDTO;
import com.egj.recruitment.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Erikson Matondang on 7/19/2017.
 */
@Service
public class JobPostingServiceImpl implements JobPostingService {

    @Autowired
    private JobPostDAO jobPostDAO;

    public List<JobPostDTO> getActiveJobPostings() {
       return  jobPostDAO.getActiveJobPostings();
    }
}
