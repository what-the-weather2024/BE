package com.weather.the.what.whattheweather.weather.service;

import com.weather.the.what.whattheweather.global.http.WebClientService;
import com.weather.the.what.whattheweather.global.http.dto.weather.WeatherForecastItem;
import com.weather.the.what.whattheweather.global.http.dto.weather.WeatherForecastPayload;
import com.weather.the.what.whattheweather.weather.dto.WeatherCoordinates;
import com.weather.the.what.whattheweather.weather.dto.WeatherResponse;
import com.weather.the.what.whattheweather.weather.type.WeatherForecastCoordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class WeatherService {

    private static final Map<String, String> WEATHER_STATUS = Map.of(
            "SKY-1", "맑음",
            "SKY-3", "구름많음",
            "SKY-4", "흐림",
            "PTY-1", "비",
            "PTY-3", "눈",
            "PTY-4", "소나기"
    );

    private final WebClientService webClientService;

    public WeatherResponse getWeatherForecast(String si, String gu) {
        final String requestUrl = createRequestUrl(WeatherForecastCoordinates.getCoordinates(si, gu));
        final WeatherForecastPayload weatherForecastPayload = webClientService.getDataToServer(requestUrl, WeatherForecastPayload.class);
        final List<WeatherForecastItem> items = weatherForecastPayload.response()
                .body()
                .items()
                .item();
        final WeatherForecastItem weatherForecastItemForStatus = items.stream()
                .filter(item -> item.category().equals("SKY") || (item.category().equals("PTY") && !item.fcstValue().matches("[02]")))
                .sorted(Comparator.comparing(WeatherForecastItem::fcstTime))
                .limit(1)
                .toList()
                .get(0);
        final String key = weatherForecastItemForStatus.category() + "-" + weatherForecastItemForStatus.fcstValue();
        final String weatherStatus = WEATHER_STATUS.getOrDefault(key, "맑음");
        final WeatherForecastItem weatherForecastItemForTemperature = items.stream()
                .filter(item -> item.category().equals("T1H"))
                .sorted(Comparator.comparing(WeatherForecastItem::fcstTime))
                .limit(1)
                .toList()
                .get(0);
        final int temperature = Integer.parseInt(weatherForecastItemForTemperature.fcstValue());
        return WeatherResponse.of(weatherStatus, temperature);
    }

    private String createRequestUrl(WeatherCoordinates weatherCoordinates) {
        return UriComponentsBuilder.fromHttpUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst")
                .queryParam("serviceKey", "71M3UVKqWbnqm9DQvvfMSyQEUVBbhF6blsBZadrx%2BSZ1Rg3%2Be3DBQD8dZS7WbXoGrDr9%2B7501yRtAf2ZkGISdw%3D%3D")
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 10000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .queryParam("base_time", LocalTime.now().format(DateTimeFormatter.ofPattern("HHmm")))
                .queryParam("nx", weatherCoordinates.nx())
                .queryParam("ny", weatherCoordinates.ny())
                .build()
                .toString();
    }
}
