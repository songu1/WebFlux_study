package com.itvillage.section08.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * ContextView API 예제 코드
 *
 */
public class ContextAPIExample03 {
    public static void main(String[] args) {
        String key1 = "id";
        String key2 = "name";

        Mono.deferContextual(ctx ->
                Mono.just("ID: " + " " + ctx.get(key1) + ", "
                        + "Name: " + ctx.get(key2) + ", "
                        + "Job: " + ctx.getOrDefault("job", "Software Engineer"))       // key에 해당하는 값이 없을 때 입력한 default값을 읽어올 수 있음
        )
        .publishOn(Schedulers.parallel())
        .contextWrite(Context.of(key1, "itVillage", key2, "Kevin")) // 2개의 key value 쌍의 데이터
        .subscribe(Logger::onNext);

        TimeUtils.sleep(100L);
    }
}
