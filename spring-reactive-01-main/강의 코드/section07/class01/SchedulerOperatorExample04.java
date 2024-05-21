package com.itvillage.section07.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * subscribeOn()은 구독 직후에 실행 될 쓰레드를 지정한다.
 * 즉, 원본 Publisher의 실행 쓰레드를 subscribeOn()에서 지정한 쓰레드로 바꾼다.
 */
public class SchedulerOperatorExample04 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[] {1, 3, 5, 7})      // 다른 스레드에서 실행
                .subscribeOn(Schedulers.boundedElastic())       // 구독시점에 실행되는 스레드를 바꿈
                .doOnNext(data -> Logger.doOnNext("fromArray", data))
                // 스레드 추가 지정 작업이 없으므로 subscribeOn에서 지정한 operator가 자동으로 지정됨
                .filter(data -> data > 3)
                .doOnNext(data -> Logger.doOnNext("filter", data))
                .map(data -> data * 10)
                .doOnNext(data -> Logger.doOnNext("map", data))
                .subscribe(Logger::onNext);

        TimeUtils.sleep(500L);
    }
}
