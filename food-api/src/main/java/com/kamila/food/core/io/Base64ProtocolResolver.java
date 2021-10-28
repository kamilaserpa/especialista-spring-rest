package com.kamila.food.core.io;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.Base64;

public class Base64ProtocolResolver implements ProtocolResolver,
        ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public Resource resolve(String location, ResourceLoader resourceLoader) {
        if (location.startsWith("base64:")) {
            // Binário que representa arquivo jks
            byte[] decodedResource = Base64.getDecoder().decode(location.substring(7));
            return new ByteArrayResource(decodedResource);
        }

        return null;
    }

    // Reage a eventos da aplicação
    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {
        event.getApplicationContext().addProtocolResolver(this);
    }

}
