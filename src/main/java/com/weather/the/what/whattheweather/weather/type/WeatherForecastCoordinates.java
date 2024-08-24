package com.weather.the.what.whattheweather.weather.type;

import com.weather.the.what.whattheweather.weather.dto.WeatherCoordinates;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WeatherForecastCoordinates {

    서울_종로구("서울특별시", "종로구", 60, 127),
    서울_중구("서울특별시", "중구", 60, 127),
    서울_용산구("서울특별시", "용산구", 60, 126),
    서울_성동구("서울특별시", "성동구", 61, 127),
    서울_광진구("서울특별시", "광진구", 62, 126),
    서울_동대문구("서울특별시", "동대문구", 61, 127),
    서울_중랑구("서울특별시", "중랑구", 62, 128),
    서울_성북구("서울특별시", "성북구", 61, 127),
    서울_강북구("서울특별시", "강북구", 61, 128),
    서울_도봉구("서울특별시", "도봉구", 61, 129),
    서울_노원구("서울특별시", "노원구", 61, 129),
    서울_은평구("서울특별시", "은평구", 59, 127),
    서울_서대문구("서울특별시", "서대문구", 59, 127),
    서울_마포구("서울특별시", "마포구", 59, 127),
    서울_양천구("서울특별시", "양천구", 57, 126),
    서울_강서구("서울특별시", "강서구", 58, 126),
    서울_구로구("서울특별시", "구로구", 58, 125),
    서울_금천구("서울특별시", "금천구", 59, 124),
    서울_영등포구("서울특별시", "영등포구", 58, 126),
    서울_동작구("서울특별시", "동작구", 59, 125),
    서울_관악구("서울특별시", "관악구", 59, 125),
    서울_서초구("서울특별시", "서초구", 61, 125),
    서울_강남구("서울특별시", "강남구", 61, 126),
    서울_송파구("서울특별시", "송파구", 62, 125),
    서울_강동구("서울특별시", "강동구", 63, 126);

    private final String si;
    private final String gu;
    private final int nx;
    private final int ny;

    public static WeatherCoordinates getCoordinates(String si, String gu) {
        return Arrays.stream(values())
                .filter(value -> value.si.equals(si) && value.gu.equals(gu))
                .map(value -> new WeatherCoordinates(value.nx, value.ny))
                .findAny()
                .orElseThrow();
    }
}
