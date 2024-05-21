package com.itvillage.section07.class00;

import com.itvillage.utils.Logger;
import com.itvillage.utils.TimeUtils;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * - parallel()만 사용할 경우에는 병렬로 작업을 수행하지 않는다.
 * - **** runOn()을 사용해서 Scheduler를 할당해주어야 병렬로 작업을 수행한다. ****
 *
 * cpu에서 지원하는 논리적인 코어와 연관 ( cpu코어 , 논리프로세서(논리 코어))
 * Scheduler : 논리 프로세서 수와 연관됨
 * 회사 pc : cpu core 10, 논리 코어(논리 프로세서) 16
 *
 */
public class ParallelExample02 {
    public static void main(String[] args) {
        Flux.fromArray(new Integer[]{1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29, 31})
                .parallel()
                .runOn(Schedulers.parallel())   // parallel()과 runOn을 같이 묶어야 사용 가능
                .subscribe(Logger::onNext);

        TimeUtils.sleep(100L);
    }
}
