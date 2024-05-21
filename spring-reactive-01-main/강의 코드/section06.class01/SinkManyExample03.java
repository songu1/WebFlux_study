package com.itvillage.section06.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.Many 예제
 *  - replay()를 사용하여 이미 emit된 데이터 중에서 특정 개수의 최신 데이터만 전달하는 예제
 */
public class SinkManyExample03 {
    public static void main(String[] args) {
        // 구독 이후, emit 된 데이터 중에서 최신 데이터 2개만 replay 한다.
        Sinks.Many<Integer> replaySink = Sinks.many().replay().limit(2);        // MulticastReplaySpec을 return
        Flux<Integer> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replaySink.emitNext(2, FAIL_FAST);
        replaySink.emitNext(3, FAIL_FAST);

        // limt 숫자만큼 가장 최근에 emit된 데이터를 받을 수 있음
        fluxView.subscribe(data -> Logger.onNext("Subscriber1", data));     // subscriber가 구독하는 시점에 가장 최근에 emit한 데이터를 전달받음
        fluxView.subscribe(data -> Logger.onNext("Subscriber2", data));
    }
}
