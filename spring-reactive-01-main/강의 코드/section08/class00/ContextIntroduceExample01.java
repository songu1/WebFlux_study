package com.itvillage.section08.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * Context 개념 설명 예제 코드
 *  - contextWrite()으로 Context에 값을 쓸 수 있고, ContextView.get()을 통해서 Context에 저장된 값을 read 할 수 있다.
 *  - ContextView는 deferContextual() 또는 transformDeferredContextual()을 통해 제공된다.
 */
public class ContextIntroduceExample01 {
    public static void main(String[] args) {
        String key = "message";     // key는 message
        // deferContextual : 데이터 소스 레벨에서 context view에 엑세스 할 수 있는 operator
        Mono<String> mono = Mono.deferContextual(ctx ->     // ctx : 저장된 상태정보를 읽어오기 위한 전용 view인 context view의 객체
                        Mono.just("Hello" + " " + ctx.get(key)).doOnNext(Logger::doOnNext)  // context view에 해당되는 값을 읽어오고 있음
                )
                .subscribeOn(Schedulers.boundedElastic())   // 업스트림 publisher가 데이터를 emit하는 스레드를 추가적으로 지정
                .publishOn(Schedulers.parallel())           // 다운스트림 스레드를 추가적으로 지정
                .transformDeferredContextual((mono2, ctx) -> mono2.map(data -> data + " " + ctx.get(key)))  // context에 저장된 값을 읽어올 수 있음
                // 1번째 파라미터 : mono를 이용해 다운스트림쪽으로 데이터를 emit / 2번째 파라미터 : context에서 읽어온 key에 해당되는 값을 가져옴
                .contextWrite(context -> context.put(key, "Reactor"));      // context의 put 메소드를 이용해서 key, value 형태로 context에 저장 (java의 map과 비슷)

        // "Hello Reactor"라는 문자열을 다운스트림 쪽으로 emit
        // transformDefaultContextual operator 내부에서 emit된 문자열을 전달받고(Hello Reactor) context에 저장된 key에 해당하는 값(Reactor)
        // => Hello Reactor Reactor 출력

        // 데이터를 emit하는 쪽의 스레드와 emit된 데이터를 전달받는 transformDeferredContextual의 스레드는 다른 스레드
        // 실행 스레드가 다르지만 reactor에서의 context를 이용하면 context 내부에 저장된 상태값을 쉽게 공유해서 사용 가능

        mono.subscribe(data -> Logger.onNext(data));

        TimeUtils.sleep(100L);
    }
}
