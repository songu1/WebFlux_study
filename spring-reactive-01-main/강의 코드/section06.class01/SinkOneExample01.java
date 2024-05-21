package com.itvillage.section06.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

/**
 * Sinks.One 예제
 *  - 한 건의 데이터만 emit 하는 예제
 */
public class SinkOneExample01 {
    public static void main(String[] args) {
        // emit 된 데이터 중에서 단 하나의 데이터만 Subscriber에게 전달한다. 나머지 데이터는 Drop 됨.
        Sinks.One<String> sinkOne = Sinks.one();    // sinksOne 인터페이스의 구현 객체
        // One 인터페이스 : truemitvalue, emitvalue(tryemitvalue 다시 이용, failure handler 지정 가능)
        Mono<String> mono = sinkOne.asMono();

        sinkOne.emitValue("Hello Reactor", FAIL_FAST);  // emitValue(emit할 데이터, failure handler)
        // FAIL_FAST : Sinks 안에 내부적으로 정의된 handler(false return => 프로세스 종료 / true => 재시도)

        mono.subscribe(data -> Logger.onNext("Subscriber1 ", data));
        mono.subscribe(data -> Logger.onNext("Subscriber2 ", data));

        // Sinks.One의 구현 객체 얻은 부분 : 데이터를 emit하므로 publisher의 역할
        // sinksOne.asMono() : subscribe 메소드를 호출해서 구독 -> sinksOne에서 subscriber의 역할
    }
}
