package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

/**
 * Mono 활용 예제
 *  - worldtimeapi.org Open API를 이용해서 서울의 현재 시간을 조회한다.
 */
public class MonoExample03 {    // mono를 사용해서 http request를 보낸 후 response를 전달받아 처리하는 예제 코드
    public static void main(String[] args) {
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // mono는 http request를 처리하기에 적합한 publisher
        Mono.just(
                restTemplate.exchange(worldTimeUri, HttpMethod.GET, new HttpEntity<String>(headers), String.class)  // WorldTimeAPI쪽의 Response가 mono의 데이터 소스가 됨
        )   // Response를 just를 사용하여 emit
                .map(response -> {      // response를 전달받아 가공
                    // json data를 parsing해서 daytime이라는 값만 추출
                    DocumentContext jsonContext = JsonPath.parse(response.getBody());
                    String dateTime = jsonContext.read("$.datetime");
                    return dateTime;    // map operator에서 return된 값은 subscriber쪽에 전달됨
                })
                .subscribe(
                        data -> Logger.info("# emitted data: " + data),
                        error -> {
                            Logger.onError(error);
                        },
                        () -> Logger.info("# emitted onComplete signal")
                );

    }
}
