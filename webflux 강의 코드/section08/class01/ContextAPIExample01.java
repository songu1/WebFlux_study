package com.itvillage.section08.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * Context API 중에서 write API 예제 코드
 * - Context.of(...) 사용
 */
public class ContextAPIExample01 {
    public static void main(String[] args) {
        String key1 = "id";
        String key2 = "name";
        Mono<String> mono =
                Mono.deferContextual(ctx ->     // 데이터 소스레벨에서 contextview에 접근 가능
                        Mono.just("ID: " + " " + ctx.get(key1) + ", " + "Name: " + ctx.get(key2))
                )
                .publishOn(Schedulers.parallel())       // reactor 시퀀스는 2개의 스레드에서 동작
                .contextWrite(Context.of(key1, "itVillage", key2, "Kevin"));    // context에 데이터를 key value 형태로 씀


        mono.subscribe(data -> Logger.onNext(data));

        TimeUtils.sleep(100L);
    }
}
