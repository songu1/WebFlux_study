package com.itvillage.section05.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * Unbounded request 일 경우, Downstream 에 Backpressure Buffer DROP_LATEST 전략을 적용하는 예제
 *  - Downstream 으로 전달 할 데이터가 버퍼에 가득 찰 경우,
 *    버퍼 안에 있는 데이터 중에서 가장 최근에(나중에) 버퍼로 들어온 데이터부터 Drop 시키는 전략
 */
public class BackpressureStrategyBufferDropLatestExample {
    public static void main(String[] args) {
        Flux
            .interval(Duration.ofMillis(300L))  // 0.3초마다 데이터 EMIT
            .doOnNext(data -> Logger.info("# emitted by original Flux: {}", data))
            .onBackpressureBuffer(2,    // 버퍼 사이즈
                    dropped -> Logger.info("# Overflow & dropped: {}", dropped),    // overflow 발생 시 drop된 데이터
                    BufferOverflowStrategy.DROP_LATEST)
            .doOnNext(data -> Logger.info("# emitted by Buffer: {}", data))     //버퍼에서 emit되는 데이터
            .publishOn(Schedulers.parallel(), false, 1)     // prefetch : 추가되는 스레드에서 사용하는 일종의 버퍼 (버퍼 전략을 조금 더 쉽게 확인하기 위해 1로 지정)
            .subscribe(data -> {
                    TimeUtils.sleep(1000L);     // publisher에서 emit하는 속도보다 subcriber의 처리 속도가 느리게 지정
                    Logger.onNext(data);
                },
                error -> Logger.onError(error));

        TimeUtils.sleep(3000L);
    }
}
