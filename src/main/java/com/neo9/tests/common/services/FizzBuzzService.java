package com.neo9.tests.common.services;

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
public interface FizzBuzzService {
	String FIZZ_BUZZ = "Fizzbuzz";
	String FIZZ = "Fizz";
	String BUZZ = "Buzz";
	String NOT_INCLUDED = "NOT_INCLUDED";

	Mono<String> checkFizzBuzz(Integer in);
}
