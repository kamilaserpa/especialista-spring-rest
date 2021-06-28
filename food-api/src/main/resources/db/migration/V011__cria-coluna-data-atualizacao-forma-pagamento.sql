alter table tb_forma_pagamento add data_atualizacao datetime null;
update tb_forma_pagamento set data_atualizacao = utc_timestamp;
alter table tb_forma_pagamento modify data_atualizacao datetime not null;