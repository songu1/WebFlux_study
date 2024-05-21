package com.itvillage.section05.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Unbounded request 일 경우, Downstream 에 Backpressure Error 전략을 적용하는 예제
 *  - Downstream 으로 전달 할 데이터가 버퍼에 가득 찰 경우, Exception을 발생 시키는 전략
 */
public class BackpressureStrategyErrorExample {
    public static void main(String[] args) {
        Flux
                .interval(Duration.ofMillis(1L))    // 1ms에 한번씩 숫자 0부터 데이터를 emit
                .onBackpressureError()      // 에러전략 적용
                .doOnNext(Logger::doOnNext)
                .publishOn(Schedulers.parallel())   // 스레드를 추가 (Scheduler : 추가적으로 스레드를 할당)
                .subscribe(data -> {
                        TimeUtils.sleep(5L);    // publisher에서 emit하는 속도보다 subscriber에서 처리하는 속도가 느린 것을 시뮬레이션
                        Logger.onNext(data);
                    },
                    error -> Logger.onError(error));

        TimeUtils.sleep(2000L);
    }

    // 버퍼 가득차면 error
}
