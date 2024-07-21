package se.magnus.util.reactor;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class ReactorTests {

  @Test
  void testFlux() {

    List<Integer> list = new ArrayList<>();

    Flux.just(1, 2, 3, 4)
      .filter(n -> n % 2 == 0)
      .map(n -> n * 2)
      .log()
      .subscribe(n -> list.add(n));

    assertThat(list).containsExactly(4, 8);

    // 정수 1,2,3,4를 인자로 정적 헬퍼 메서드인 Flux.just() 를 호출해 스트림을 시작
    // 짝수 필터
    // 값 변환
    // 스트림 데이터 로그 기록
    // 스트림 항목 모아서 List에 넣는다.
  }

  @Test
  void testFluxBlocking() {

    List<Integer> list = Flux.just(1, 2, 3, 4)
      .filter(n -> n % 2 == 0)
      .map(n -> n * 2)
      .log()
      .collectList().block();
    // block 메서드를 호출 해 처리가 끝날 때까지 기다리는 구독자를 등록
    assertThat(list).containsExactly(4, 8);
  }

  /**
   * 보통 직접 스트림 처리를 시작하진 않으며 처리 방법만 정의한다.
   * 스트림 처리를 시작하는 것은 인프라 컴포넌트가 담당한다.
   * 예를 들면,
   * 스프링 웹플럭스가 수신한 HTTP 요청에 대한 응답으로 스트림 처리를 시작한다.
   * 블로킹 코드로 리액티브 스트림의 응압을 처리하는 예외적인 경우도 있는데
   * 이럴 때는 블로킹 코드가 Flux나 Mono 객체의 block() 메서드를 호출하는 방식으로 결과는 받는다.
   */
}
