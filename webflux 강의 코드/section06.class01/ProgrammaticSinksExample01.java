package com.itvillage.section06.class01;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.stream.IntStream;

/**
 * Sinks를 사용하는 예제
 *  - Publisher의 데이터 생성을 멀티 쓰레드에서 진행해도 Thread safe 하다.
 *
 *  sink 객체를 이용해서 operator 외부, 내부와 상관없이 데이터를 emit 가능
 */
@Slf4j
public class ProgrammaticSinksExample01 {
    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;

        Sinks.Many<String> unicastSink = Sinks.many().unicast().onBackpressureBuffer(); // 데이터 emit시 sink 객체 생성
        Flux<String> fluxView = unicastSink.asFlux();       // asFlux : 구독과 연결을 위해 flux로 변환
        IntStream
                .range(1, tasks)
                .forEach(n -> {
                    try {
                        new Thread(() -> {
                            unicastSink.emitNext(doTask(n), Sinks.EmitFailureHandler.FAIL_FAST);    // 데이터 emit
                            // 멀티쓰레드 환경에서 에러 발생 시 EmitFailureHandler제공하여 빠르게 실패 가능
                        }).start();
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {}
                });

        fluxView    // 변환된 flux를 가지고 subscribe 메소드를 호출해서 구독
//                .publishOn(Schedulers.parallel())
//                .map(result -> result + " success!")
//                .doOnNext(n -> log.info("# map(): {}", n))
//                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(200L);
    }

    private static String doTask(int taskNumber) {
        // now tasking.
        // complete to task.
        return "task " + taskNumber + " result";
    }
}
