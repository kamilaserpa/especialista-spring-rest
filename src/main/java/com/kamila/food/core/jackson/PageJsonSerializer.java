package com.kamila.food.core.jackson;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent // Componente Spring que fornece implementação de serializador
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

	@Override
	public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		// Inicia objeto Json
		gen.writeStartObject();
		
		// Cria atributo com valor 
		gen.writeObjectField("content", page.getContent());
		gen.writeObjectField("size", page.getSize());
		gen.writeObjectField("totalElements", page.getTotalElements());
		gen.writeObjectField("totalPages", page.getTotalPages());
		gen.writeObjectField("number", page.getNumber());
		
		// Finaliza objeto Json
		gen.writeEndObject();
	}

}
