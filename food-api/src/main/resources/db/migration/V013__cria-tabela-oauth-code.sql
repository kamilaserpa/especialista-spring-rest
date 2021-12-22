-- Tabela para persistir code do Authorization Code Grant Type de forma acessível para todos os containers
create table oauth_code (
    code varchar(256),
    authentication blob
);
