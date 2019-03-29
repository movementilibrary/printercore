package br.com.dasa.helpers;

import java.net.InetAddress;
import java.net.NetworkInterface;

import org.springframework.stereotype.Component;

import br.com.dasa.exceptions.SOException;

@Component
public class SOHelper {


	public String getMacAddress() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new SOException(e, "Erro ao recuperar MACADDRESS");

		}
	}
}
