package br.com.dasa.helpers;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

public class DadosImpressaoHelperTest {

	@Test
	public void testValidarDadosImpressoraVazio() {
		try {

			DadosImpressaoHelper impressaoHelper = new DadosImpressaoHelper("C://gliese//teste//impressora-vazio.properties",
					new FileHelper());
			Assert.assertFalse(impressaoHelper.validarDadosImpressoraPreenchidos());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testValidarDadosImpressoraPreenchidos() {
		try {

			DadosImpressaoHelper impressaoHelper = new DadosImpressaoHelper("C://gliese//teste//impressora.properties",
					new FileHelper());
			Assert.assertTrue(impressaoHelper.validarDadosImpressoraPreenchidos());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testDiretorioInexistente() {
		try {
			DadosImpressaoHelper impressaoHelper = new DadosImpressaoHelper("C://gliese_teste//impressora.properties",
					new FileHelper());
			Assert.assertFalse(impressaoHelper.validarDadosImpressoraPreenchidos());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
