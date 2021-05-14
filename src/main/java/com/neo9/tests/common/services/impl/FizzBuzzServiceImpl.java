package com.neo9.tests.common.services.impl;

import com.neo9.tests.common.services.FizzBuzzService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * TDD Examples FizzBuzz
 *
 * INPUT : for a given {@code Integer} between 1 and 10000 included :
 *
 * If number is divisible by 3 : we should return Fizz
 * If number is divisible by 5 : we should return Buzz
 * If number is divisible by 3 and by 5 : we should return Fizzbuzz
 * else : we should return the given number
 *
 * If number exceeds range, we should return "NOT_INCLUDED"
 */
@Service
@NoArgsConstructor
public class FizzBuzzServiceImpl implements FizzBuzzService {
	@Override
	public Mono<String> checkFizzBuzz(Integer in) {
		if (in < 1 || in > 10000) {
			return Mono.just(FizzBuzzService.NOT_INCLUDED);
		}
		if (in % 5 == 0 && in % 3 == 0) {
			return Mono.just(FizzBuzzService.FIZZ_BUZZ);
		}
		if (in % 3 == 0) {
			return Mono.just(FizzBuzzService.FIZZ);
		}
		if (in % 5 == 0) {
			return Mono.just(FizzBuzzService.BUZZ);
		}
		return Mono.just(in.toString());
	}
}
