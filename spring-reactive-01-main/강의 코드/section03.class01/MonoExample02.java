package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;

/**
 * Mono 기본 개념 예제
 *  - 원본 데이터의 emit 없이 onComplete signal 만 emit 한다.
 */
public class MonoExample02 {
    public static void main(String[] args) {        // mono의 empty operator를 이용해서 실제 데이터 전달없이 onComplete signal만 전송
        Mono.empty()
                .subscribe(
                        data -> Logger.info("# emitted data: {}", data),    // 상위 업스트림으로부터 emit된 데이터를 전달받음
                        error -> {},                                             // 업스트림에서 에러가 발생할 경우 에러를 전달받음 (onError 시그널)
                        () -> Logger.info("# emitted onComplete signal")    // 상위 업스트림에서 모든 데이터를 emit한 후 업스트림으로부터 onComplete 시그널을 전달받는 부분
                );

        // subscriber쪽에 따로 전달된 데이터는 없고 onComplete signal을 전송했으므로 onComplete을 전달받는 람다 표현식이 실행됨
    }
}
