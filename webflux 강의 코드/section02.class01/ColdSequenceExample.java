package com.itvillage.section04.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

import java.util.Arrays;

public class ColdSequenceExample {
    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"))       // 데이터 소스를 fromIterable로 emit받음
                .map(String::toLowerCase);      // map operator로 전달받아 변환

        // 구독이 발생할때마다 타임라인이 하나씩 생성
        coldFlux.subscribe(country -> Logger.info("# Subscriber1: {}", country));
        Logger.info("-------------------------");
        coldFlux.subscribe(country -> Logger.info("# Subscriber2: {}", country));
    }
}
