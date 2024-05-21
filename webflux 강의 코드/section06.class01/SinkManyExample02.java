package com.itvillage.section06.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.Many 예제
 *  - multicast()를 사용해서 하나 이상의 Subscriber에게 데이터를 emit하는 예제
 */
public class SinkManyExample02 {
    public static void main(String[] args) {
        // 하나 이상의 Subscriber에게 데이터를 emit할 수 있다.
        Sinks.Many<Integer> multicastSink = Sinks.many().multicast().onBackpressureBuffer();    // 하나 이상의 subscriber에게 데이터를 emit
        Flux<Integer> fluxView = multicastSink.asFlux();

        multicastSink.emitNext(1, FAIL_FAST);
        multicastSink.emitNext(2, FAIL_FAST);


        fluxView.subscribe(data -> Logger.onNext("Subscriber1", data));     // 1,2 전달받음

        fluxView.subscribe(data -> Logger.onNext("Subscriber2", data));     // 1,2 전달받지않음

        // multicast spec에서는 hot sequence 동작 방식중에서 첫번재 구독이 발생하는 시점에 데이터 emit이 시작되는 warm-up 방식으로 동작


        multicastSink.emitNext(3, FAIL_FAST);       // 3번째 데이터가 emit된 시점에 1번째 subscriber와 2번째 subscriber가 각각 숫자 3을 전달 받음
    }
}
