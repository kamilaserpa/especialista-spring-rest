package com.kamila.food.di.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 *  Tudo em aplicatino.properties que começa por esse prefixo é atribuido para os atributos dessa classe
 */
@Component
@ConfigurationProperties("notificador.email")
public class NotificadorProperties {

	/*
	 * Host do servidor de email
	 */
	private String hostServidor;
	
	/*
	 * Porta do servidor de email.
	 * Se não configurada em application.properties o valor padrão da propriedade será 25
	 */
	private Integer portaServidor = 25;

	public String getHostServidor() {
		return hostServidor;
	}

	public void setHostServidor(String hostServidor) {
		this.hostServidor = hostServidor;
	}

	public Integer getPortaServidor() {
		return portaServidor;
	}

	public void setPortaServidor(Integer portaServidor) {
		this.portaServidor = portaServidor;
	}

}
