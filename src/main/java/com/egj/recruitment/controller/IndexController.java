package com.egj.recruitment.controller;


import com.egj.recruitment.service.JobPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Erikson Matondang on 7/15/2017.
 */
@Controller
@RequestMapping("/")
public class IndexController extends AbstractController {

    @Autowired
    private JobPostingService jobPostingService;

    @RequestMapping
    public String index(Model model) {
        model.addAttribute("activeJobPostings",jobPostingService.getActiveJobPostings());
        return "index";
    }

}

