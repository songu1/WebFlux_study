package com.itvillage.section07.class02;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * Schedulers.newBoundedElastic()을 적용
 *
 * new : reactor의 디폴트 인스턴스가 아닌 새로운 스케줄러 인스턴스를 생성
 * newBoundedElastic()으로 호출한 스레드는 Daemon Thread가 아닌 User Thread => 일정 시간동안 종료되지 않음
 */
public class SchedulersNewBoundedElasticExample01 {
    public static void main(String[] args) {
        // threadCap : 생성할 스레드 개수, queuedTaskCap : 큐에서 대기할 수 있는 작업의 개수 , name :스레드 이름
        Scheduler scheduler = Schedulers.newBoundedElastic(2, 2, "I/O-Thread");
        Mono<Integer> mono =
                    Mono
                        .just(1)
                        .subscribeOn(scheduler);

        Logger.info("# Start");

        mono.subscribe(data -> {        // 스레드1
            Logger.onNext("subscribe 1 doing", data);
            TimeUtils.sleep(3000L);
            Logger.onNext("subscribe 1 done", data);
        });

        mono.subscribe(data -> {        // 스레드2
            Logger.onNext("subscribe 2 doing", data);
            TimeUtils.sleep(3000L);
            Logger.onNext("subscribe 2 done", data);
        });

        // 이미 2개의 메소드가 실행되므로 큐에서 대기하게됨
        mono.subscribe(data -> {        // 스레드1-큐1
            Logger.onNext("subscribe 3 doing", data);
        });

        mono.subscribe(data -> {        // 스레드2-큐1
            Logger.onNext("subscribe 4 doing", data);
        });

        mono.subscribe(data -> {        // 스레드1-큐2
            Logger.onNext("subscribe 5 doing", data);
        });

        mono.subscribe(data -> {        // 스레드2-큐2
            Logger.onNext("subscribe 6 doing", data);
        });

        // 한번 더 호출하면 Exception 발생(스레드, 큐가 모두 가득 참)

        // 스레드를 즉시 종료하고 싶다면 아래 코드
//        TimeUtils.sleep(4000L);
//        scheduler.dispose();
    }
}
