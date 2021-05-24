package com.kamila.food.core.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated // Para @NotNull ser verificado ao inicializar
@Getter
@Setter
@Component
@ConfigurationProperties("food.email")
public class EmailProperties {

    @NotNull
    private String remetente;

}
