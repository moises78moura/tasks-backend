package br.ce.wcaquino.taskbackend.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;

public class DateUtilsTest {
	
	
	@Test
	public void deveRetornarTrueParaDatasFuturas() {
		LocalDate data = LocalDate.of(2030, 01, 01);
		assertTrue(DateUtils.isEqualOrFutureDate(data));
	}
	
	@Test
	public void deveRetornarFalseParaDatasPassadas() {
		LocalDate data = LocalDate.of(2010, 01, 01);
		assertFalse(DateUtils.isEqualOrFutureDate(data));
	}
	
	@Test
	public void deveRetornarTrueParaDatasPresente() {
		LocalDate data = LocalDate.now();
		assertTrue(DateUtils.isEqualOrFutureDate(data));
	}

}
