package com.weather.the.what.whattheweather.s3.controller;

import com.weather.the.what.whattheweather.s3.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class S3Controller {
  private final S3Service s3Service;

  /**
   * S3 이미지 업로드 테스트를 위한 컨트롤러
   */
  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      String fileUrl = s3Service.uploadImage(file);
      return new ResponseEntity<>(fileUrl, HttpStatus.OK);
    } catch (IOException e) {
      return new ResponseEntity<>("Failed to upload file", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
