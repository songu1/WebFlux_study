package com.itvillage.section03.class01;

import com.itvillage.utils.Logger;
import reactor.core.publisher.Flux;

/**
 * Flux 에서의 Operator 체인 사용 예제
 */
public class FluxExample02 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[]{3, 6, 7, 9})       // fromArray : 데이터 소스로 배열을 전달받고 전달받은 배열의 원소들을 각각 다운스트림쪽으로 emit
                .filter(num -> num > 6)         // emit된 값은 필터링됨 : 7,9만 map operator로 전달됨
                .map(num -> num * 2)            // 계산 값을 subscriber에 전달
                .subscribe(multiply -> Logger.info("# multiply: {}", multiply));
    }
}
