package com.DATA.DataCodeAnalysing.ServiceImpl;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.client.RestTemplate;

import com.DATA.DataCodeAnalysing.Service.GitHubService;

@Service
public class GitHubServiceImpl implements GitHubService {

    @Value("${github.token}")
    private String githubToken;

    @Value("${github.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public GitHubServiceImpl() {
        // Initialize RestTemplate
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String uploadImage(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String filePath = "images/" + fileName; // Path within the repo

        byte[] fileContent;
        try {
            fileContent = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read file content.";
        }

        String base64Content = Base64.getEncoder().encodeToString(fileContent);

        String json = "{\"message\":\"Upload image " + fileName + "\",\"content\":\"" + base64Content + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("Content-Type", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        String url = apiUrl + "/" + filePath;

        System.out.println("Uploading to URL: " + url); // Debugging line

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                String fileUrl = "https://github.com/PrathapShanmugam3/imageStore/raw/main/" + filePath;
                return "Image uploaded successfully. File URL: " + fileUrl;
            } else {
                return "Failed to upload image. Status code: " + response.getStatusCode() + ". Response: " + response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while uploading the image.";
        }
    }
}
