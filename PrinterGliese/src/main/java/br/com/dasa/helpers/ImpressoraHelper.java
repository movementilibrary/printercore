package br.com.dasa.helpers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.springframework.stereotype.Component;

@Component
public class ImpressoraHelper {

	public List<String> getImpressoras() {
		List<PrintService> lista = Arrays.asList(PrintServiceLookup.lookupPrintServices(null, null));
		return lista.stream().map(PrintService::getName).collect(Collectors.toList());

	}
}
