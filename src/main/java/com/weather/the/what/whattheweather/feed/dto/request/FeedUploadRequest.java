package com.weather.the.what.whattheweather.feed.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@ToString
public class FeedUploadRequest {
  private String city; //시
  private String district; //구
  private String memberId; //회원아이디
  private MultipartFile file; //이미지파일
}
