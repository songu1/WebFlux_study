package com.itvillage.section06.class01;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.stream.IntStream;

/**
 * create() Operator를 사용하는 예제
 *  - 일반적으로 Publisher의 데이터 생성을 단일 쓰레드에서 진행한다. 멀티 쓰레드에서도 가능
 *  - 데이터 emit은 create Operator 내부에서 가능.
 *  - Backpressure 적용 가능
 *
 *  create operator 내에서만 flux sink를 이용해서 데이터 emit 가능
 */
@Slf4j
public class ProgrammaticCreateExample01 {
    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;
        Flux
                .create((FluxSink<String> sink) -> {    // fluxsink : 데이터를 동기적, 비동기적으로 emit하는 역할
                    IntStream       // 자바의 stream api를 이용하여 1부터 5까지 생성(6보다 작은 숫자)
                            .range(1, tasks)
                            .forEach(n -> sink.next(doTask(n)));    // 데이터 emit
                })
//                .subscribeOn(Schedulers.boundedElastic())
//                .doOnNext(n -> log.info("# create(): {}", n))
//                .publishOn(Schedulers.parallel())
//                .map(result -> result + " success!")
//                .doOnNext(n -> log.info("# map(): {}", n))
//                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    private static String doTask(int taskNumber) {      // 호출될때마다 작업 넘버에 대한 물자열 출력
        // now tasking.
        // complete to task.
        return "task " + taskNumber + " result";
    }
}
