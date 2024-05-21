package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Mono;

/**
 * Mono 기본 개념 예제
 *  - 1개의 데이터를 생성해서 emit한다.
 */
public class MonoExample01 {
    public static void main(String[] args) {    // publisher가 데이터 생성, subscriber가 데이터를 전달받아 처리
        Mono.just("Hello Reactor!")     // mono의 just를 사용해서 단 1개의 데이터를 다운스트림쪽으로 내보냄 : 업스트림
                .subscribe(data -> Logger.info("# emitted data: {}", data));    // subscriber가 데이터를 전달받아 처리 : 다운스트림(최종 소비자)

        // upstream에서 emit한 데이터는 subscribe메소드의 1번째 파라미터로 입력한 lambda표현식으로 전달됨
        // mono의 just operator를 통해 onNextSignal에 전달할 데이터를 실어서 보냄
    }
}
