package com.neo9.tests.common.services;

import com.neo9.tests.common.services.impl.FizzBuzzServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
public class FizzBuzzTest {

	public FizzBuzzService fizzBuzzService = new FizzBuzzServiceImpl();

	@Test
	public void test_negative() {
		// given
		final String res = fizzBuzzService.checkFizzBuzz(-42).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.NOT_INCLUDED);
	}

	@Test
	public void test_too_high() {
		// given
		final String res = fizzBuzzService.checkFizzBuzz(20042).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.NOT_INCLUDED);
	}

	@Test
	public void test_is_divisible_3() {
		// given
		String res = fizzBuzzService.checkFizzBuzz(3).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.FIZZ);
		// given
		res = fizzBuzzService.checkFizzBuzz(21).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.FIZZ);
		// given
		res = fizzBuzzService.checkFizzBuzz(27).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.FIZZ);
	}

	@Test
	public void test_is_divisible_5() {
		// given
		String res = fizzBuzzService.checkFizzBuzz(5).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.BUZZ);
		// given
		res = fizzBuzzService.checkFizzBuzz(10).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.BUZZ);
	}

	@Test
	public void test_is_divisible_3_and_5() {
		// given
		String res = fizzBuzzService.checkFizzBuzz(30).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.FIZZ_BUZZ);
		// given
		res = fizzBuzzService.checkFizzBuzz(15).block();
		// assert
		assertNotNull(res);
		assertEquals(res, FizzBuzzService.FIZZ_BUZZ);
	}

	@Test
	public void test_is_not_divisible_3_and_5() {
		// given
		String res = fizzBuzzService.checkFizzBuzz(13).block();
		// assert
		assertNotNull(res);
		assertEquals(res, "13");
		// given
		res = fizzBuzzService.checkFizzBuzz(76).block();
		// assert
		assertNotNull(res);
		assertEquals(res, "76");
	}
}
