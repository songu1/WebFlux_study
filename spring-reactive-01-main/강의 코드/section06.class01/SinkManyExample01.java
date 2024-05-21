package com.itvillage.section06.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.Many 예제
 *  - unicast()를 사용해서 단 하나의 Subscriber에게만 데이터를 emit하는 예제
 *
 *  flux에 해당
 */
public class SinkManyExample01 {
    public static void main(String[] args) {
        // 브로드캐스트 : 네트워크에 연결된 모든 시스템이 정보를 전달받음
        // 유니캐스트 : 하나의 특정 시스템만 정보를 전달받음
        // 멀티캐스트 : 일부 시스템들만 정보를 전달받음

        // 단 하나의 Subscriber에게만 데이터를 emit할 수 있다.
        Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();    // subscriber 1개에게만 데이터를 emit
        Flux<Integer> fluxView = unicastSink.asFlux();  // unicastSink를 이용해서 flux로 변환

        // 2건의 데이터를 emit
        unicastSink.emitNext(1, FAIL_FAST);
        unicastSink.emitNext(2, FAIL_FAST);

        // 구독이 발생
        fluxView.subscribe(data -> Logger.onNext("Subscriber1", data));     // 숫자 3을 전달받음

        unicastSink.emitNext(3, FAIL_FAST);     //

        // TODO 주석 전, 후 비교해서 보여 줄 것.
        // 주석처리 : 첫번째 subscriber가 1,2,3을 전달받음
        // 주석처리x : UnicastProcessor allows only a single Subscriber => 유니캐스트 subscriber는 1개의 데이터만 전달받을 수 있음
        fluxView.subscribe(data -> Logger.onNext("Subscriber2", data));
    }
}
