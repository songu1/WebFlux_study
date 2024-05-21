package com.itvillage.section05.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * Subscriber가 처리 가능한 만큼의 request 개수를 조절하는 Backpressure 예제
 */
public class BackpressureExample01 {
    public static void main(String[] args) {
        Flux.range(1, 5)    // range : 다운스트림쪽으로 emit
            .doOnNext(Logger::doOnNext) // 업스트림에서 emit한 데이터를 출력
            .doOnRequest(Logger::doOnRequest)   // subscriber에서 요청한 데이터 개수를 출력
            .subscribe(new BaseSubscriber<Integer>() {  // 요청 데이터 개수 지정
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }   // 데이터 요청 개수를 1로 지정(limit)

                @Override
                protected void hookOnNext(Integer value) {  // emit된 데이터를 전달받음
                    TimeUtils.sleep(2000L);     // 2초의 delay 타임(데이터 처리에 2초가 걸린다고 가정)
                    Logger.onNext(value);
                    request(1); // 데이터 처리가 끝나면 publisher에 데이터 요청
                }
            });
    }
}
