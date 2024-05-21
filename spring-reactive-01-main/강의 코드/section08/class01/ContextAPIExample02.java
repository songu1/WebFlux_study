package com.itvillage.section08.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.context.Context;

/**
 * Context API 예제 코드
 *  - pullAll(ContextView) API 사용
 */
public class ContextAPIExample02 {
    public static void main(String[] args) {
        String key1 = "id";
        String key2 = "name";
        String key3 = "country";

        Mono.deferContextual(ctx ->
                        Mono.just("ID: " + " " + ctx.get(key1) + ", " + "Name: " + ctx.get(key2) +
                                ", " + "Country: " + ctx.get(key3))
        )
        .publishOn(Schedulers.parallel())       // 스레드 2개에서 동작(메인스레드, parallel)
        .contextWrite(context -> context.putAll(Context.of(key2, "Kevin", key3, "Korea").readOnly()))   // 두번째 : 기존의 컨텍스트 데이터를 합쳐서 새로운 컨텍스트를 리턴 (contextview객체이므로 readonly 추가)
        .contextWrite(context -> context.put(key1, "itVillage"))    // 첫번째
        .subscribe(Logger::onNext);

        // contextWriter operator를 이용해 데이터를 context에 저장
        // defer Contextual

        TimeUtils.sleep(100L);
    }
}
