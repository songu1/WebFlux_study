package com.itvillage.section04.class01;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

public class HotSequenceExample {
    public static void main(String[] args) {
        Flux<String> concertFlux =
                Flux.fromStream(Stream.of("Singer A", "Singer B", "Singer C", "Singer D", "Singer E"))      // fromStream으로 java의 stream을 원본 데이터 소스로 전달받음
                        .delayElements(Duration.ofSeconds(1)).share();  //  share() 원본 Flux를 여러 Subscriber가 공유한다. (cold sequence를 hot sequence로 변환해주는 operator)
                        // delayElements를 이용해 1초에 한번씩 데이터를 emit

        concertFlux.subscribe(singer -> Logger.info("# Subscriber1 is watching {}'s song.", singer));

        TimeUtils.sleep(2500);

        concertFlux.subscribe(singer -> Logger.info("# Subscriber2 is watching {}'s song.", singer));   // 1초에 한번씩 emit, 2.5초의 delay : Singer C부터 전달받음

        TimeUtils.sleep(3000);  // share 메소드 사용 시 메인 스레드가 아닌 다른 스레드가 하나 생김 => 메인 스레드가 먼저 종료되지 않도록 코드의 실행결과를 모두 확인하기 위한 용도
    }
}
