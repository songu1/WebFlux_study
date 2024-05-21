package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 2개의 Mono를 연결해서 Flux로 변환하는 예제
 */
public class FluxExample03 {
    public static void main(String[] args) {    // mono와 mono를 연결해 새로운 flux를 생성
        Flux<Object> flux =
                Mono.justOrEmpty(null)      // just : null값 포함 불가능 , justOrEmpty : null값 포함 가능
                        .concatWith(Mono.justOrEmpty("Jobs"));      // concatWith operator : mono와 mono를 합쳐주는 operator
        flux.subscribe(data -> Logger.info("# result: {}", data));      // 최종적으로 Jobs만 출력됨
    }
}
