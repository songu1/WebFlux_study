package com.itvillage.section07.class02;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.single()을 적용 후,
 * 첫번째 Schedulers.single()에서 할당 된 쓰레드를 재사용 한다.
 *
 * 호출할 때마다 매번 새로운 스레드 하나를 생성
 */
public class SchedulersSingleExample02 {
    public static void main(String[] args) {

        // new-single-1로 작업 처리
        doTask("task1")
                .subscribe(Logger::onNext);

        // new-single-2로 작업 처리
        doTask("task2")
                .subscribe(Logger::onNext);


        TimeUtils.sleep(200L);
    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .doOnNext(data -> Logger.doOnNext(taskName, "fromArray", data))
                .publishOn(Schedulers.newSingle("new-single", true))    // 스레드 이름 지정 가능, daemon 스레드 지정 가능
                .filter(data -> data > 3)
                .doOnNext(data -> Logger.doOnNext(taskName, "filter", data))
                .map(data -> data * 10)
                .doOnNext(data -> Logger.doOnNext(taskName, "map", data));
    }
}
