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
        String originalFileName = "image";  // Base filename
        String fileExtension = ".jpg";      // Assuming JPG for simplicity
        String fileName = originalFileName + fileExtension;
        String filePath = "images/" + fileName; // Initial path within the repo
        String sha = null; // To hold the file SHA if it exists
        int fileCounter = 1; // To create image1, image2, etc.

        byte[] fileContent;
        try {
            fileContent = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read file content.";
        }

        String base64Content = Base64.getEncoder().encodeToString(fileContent);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("Content-Type", "application/json");

        // Check for an available filename
        boolean fileExists = true;
        while (fileExists) {
            try {
                ResponseEntity<String> getFileResponse = restTemplate.exchange(apiUrl + "/" + filePath, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                if (getFileResponse.getStatusCode() == HttpStatus.OK) {
                    // File exists, so increment the filename
                    fileCounter++;
                    fileName = originalFileName + fileCounter + fileExtension;
                    filePath = "images/" + fileName;
                }
            } catch (Exception e) {
                // If an exception occurs (like a 404 Not Found), the file doesn't exist, so we can use this name
                fileExists = false;
            }
        }

        // Prepare the JSON payload
        String json = "{\"message\":\"Upload image " + fileName + "\",\"content\":\"" + base64Content + "\"}";

        // Send the PUT request to upload the file
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        String url = apiUrl + "/" + filePath;

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
    
    @Override
    public String uploadDocument(MultipartFile file) {
        // Extract the original file extension
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            return "Invalid file.";
        }
        
        // Get the file extension from the original filename
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String baseFileName = "document";  // Base filename for your documents

        String fileName = baseFileName + fileExtension;
        String filePath = "documents/" + fileName; // Path within the repo (changed to 'documents')
        int fileCounter = 1; // To create document1, document2, etc.

        byte[] fileContent;
        try {
            fileContent = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to read file content.";
        }

        String base64Content = Base64.getEncoder().encodeToString(fileContent);

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        headers.set("Accept", "application/vnd.github.v3+json");
        headers.set("Content-Type", "application/json");

        // Check for an available filename
        boolean fileExists = true;
        while (fileExists) {
            try {
                ResponseEntity<String> getFileResponse = restTemplate.exchange(apiUrl + "/" + filePath, HttpMethod.GET, new HttpEntity<>(headers), String.class);
                if (getFileResponse.getStatusCode() == HttpStatus.OK) {
                    // File exists, so increment the filename
                    fileCounter++;
                    fileName = baseFileName + fileCounter + fileExtension;
                    filePath = "documents/" + fileName;
                }
            } catch (Exception e) {
                // If an exception occurs (like a 404 Not Found), the file doesn't exist, so we can use this name
                fileExists = false;
            }
        }

        // Prepare the JSON payload
        String json = "{\"message\":\"Upload document " + fileName + "\",\"content\":\"" + base64Content + "\"}";

        // Send the PUT request to upload the file
        HttpEntity<String> requestEntity = new HttpEntity<>(json, headers);
        String url = apiUrl + "/" + filePath;

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);
            if (response.getStatusCode() == HttpStatus.CREATED) {
                String fileUrl = "https://github.com/PrathapShanmugam3/imageStore/raw/main/" + filePath;
                return "Document uploaded successfully. File URL: " + fileUrl;
            } else {
                return "Failed to upload document. Status code: " + response.getStatusCode() + ". Response: " + response.getBody();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while uploading the document.";
        }
    }

}
