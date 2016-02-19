alter table corporativo.documento add column qtd_paginas number(5, 0);

update corporativo.documento set qtd_paginas = 1;