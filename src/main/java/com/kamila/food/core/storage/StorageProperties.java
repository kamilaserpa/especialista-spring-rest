package com.kamila.food.core.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Getter
@Setter
@Component
@ConfigurationProperties("food.storage")
public class StorageProperties {

    private Local local = new Local();
    private S3 s3 = new S3();

    @Getter
    @Setter
    public class Local {

        private Path diretorioFotos;

    }

    @Getter
    @Setter
    public class S3 {

        private String idChaveAcesso;
        private String chaveAcessoSecreta;
        private String bucket;
        private String regiao;
        private String diretorioFotos;

    }

}
