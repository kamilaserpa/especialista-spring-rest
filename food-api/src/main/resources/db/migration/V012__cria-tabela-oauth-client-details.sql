# Cria tabela utilizada pelo Oauth Security com dados de clientes

create table oauth_client_details (
  client_id varchar(255) comment 'Id de clientes registrados',
  resource_ids varchar(256) comment 'Resource Servers que esse cliente pode acessar (ex: food-web). Nova stack do SpringSecurity não tem suporte.',
  client_secret varchar(256) comment 'Password de clientes (BCrypt)',
  scope varchar(256) comment 'Ações permitidas, separadas por vírgula, sem espaço entre elas',
  authorized_grant_types varchar(256) comment 'Forma como os usuários podem fazer login em um cliente específico',
  web_server_redirect_uri varchar(256) comment 'URI de redirecionamento do cliente após login em alguns fluxos (ex. fluxo authorization-code)',
  authorities varchar(256) comment 'Indica quais funções são permitidas com determinado cliente',
  access_token_validity integer comment 'Validade do token de um cliente (em segundos)',
  refresh_token_validity integer comment 'Validade do Refresh Token',
  additional_information varchar(4096),
  autoapprove varchar(256) comment 'Se "true" aprova automaticamente os escopos salvos no banco, não acessando tela de aprovação dos escopos (após login em fluxo authorization-code p.ex.)',

  primary key (client_id)
) engine=innodb default charset=utf8;
