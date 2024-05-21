package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * Flux 기본 예제
 */
public class FluxExample01 {
    public static void main(String[] args) {
        Flux.just(6, 9, 13)     // just 연산자에서 emit한 데이터 소스 6,9,13을 operator로 전달
                .map(num -> num % 2)    // emit된 숫자들을 받아 2로 나누고 2로 나눈 나머지를 다운스트림으로 보냄
                .subscribe(remainder -> Logger.info("# remainder: {}", remainder)); // 다운스트림이 subscriber가 됨(2로 나눈 나머지 값을 출력)
    }
}
