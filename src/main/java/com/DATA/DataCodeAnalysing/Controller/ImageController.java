package com.DATA.DataCodeAnalysing.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.DATA.DataCodeAnalysing.Service.GitHubService;

@RestController
@RequestMapping("/images")
public class ImageController {
	@Autowired
    private GitHubService gitHubService;

    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return gitHubService.uploadImage(file);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

}
