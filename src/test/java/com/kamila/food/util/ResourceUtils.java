package com.kamila.food.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

/**
 * Classe utilitária para extrair o conteúdo de arquivo json
 * @author kamila.serpa
 *
 */
public class ResourceUtils {

	public static String getContentFromResource(String resourceName) {
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
			
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
