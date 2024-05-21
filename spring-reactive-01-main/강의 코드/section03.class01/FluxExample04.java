package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * 여러개의 Flux를 연결해서 하나의 Flux로 결합하는 예제
 */
public class FluxExample04 {
    public static void main(String[] args) {
        Flux.concat(            // concat opearator의 파라미터로 입력된 publisher들을 연결해주는 operator
                Flux.just("Venus"),     // flux, mono 모두 사용 가능
                Flux.just("Earth"),
                Flux.just("Mars"))  // 다운스트림쪽으로 차례로 emit됨
            .collectList()          // 없다면 concat안의 파라미터들이 각각 출력됨 / 있으면 하나의 리스트안에 각각의 데이터가 담겨서 리스트가 전달됨
            .subscribe(planetList -> Logger.info("# Solar System: {}", planetList));
    }
}
