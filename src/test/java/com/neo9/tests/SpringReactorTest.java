package com.neo9.tests;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class SpringReactorTest {
	@Test
	public void test_flux() {
		// given
		final Flux<Integer> fluxPublisher = Flux.just(1, 2, 3, 5, 8, 13);
		// assert
		StepVerifier.create(fluxPublisher)
						.expectNext(1, 2, 3, 5, 8, 13)
						.expectComplete()
						.verify();
	}

	@Test
	public void test_flux_expect_next_count() {
		// given
		final Flux<Integer> fluxPublisher = Flux.just(1, 2, 3, 5, 8, 13);
		// assert
		StepVerifier.create(fluxPublisher)
						.expectNext(1)
						.expectNext(2)
						.expectNext(3)
						.expectNextCount(3)
						.expectComplete()
						.verify();
	}
}
