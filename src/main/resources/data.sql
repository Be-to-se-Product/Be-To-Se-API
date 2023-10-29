-- Inserir Usuario
INSERT INTO usuario (id, email, senha, tipo_usuario)
VALUES (1, 'pedro@pedro.com', '$2a$10$DDyTCVL1Aen6/gJfTfQlD.69pYMngIAP3bYFF.rPVHM9Dligjs3SW', 'COMERCIANTE');


INSERT INTO endereco (id, cep, rua, bairro, numero, geolocalizacaox, geolocalizacaoy)
VALUES (1, '12345-678', 'Rua Comerciante', 'Bairro Comerciante', 123, 0.0, 0.0);

INSERT INTO comerciante (id, cnpj, nome, data_criacao, data_ultimo_acesso, razao_social, fk_usuario, fk_endereco, is_ativo)
VALUES (1, '12345678901234', 'Nome Comerciante', '2023-10-25', '2023-10-25', 'Razao Social', 1, 1, true);

INSERT INTO tag (id, descricao)
VALUES (1, 'NomeTag');

INSERT INTO estabelecimento (
    nome, segmento, data_criacao, telefone_contato, enquadramento_juridico,
    referencia_instagram, referencia_facebook, email_contato, is_ativo,
    fk_comerciante, fk_endereco
) VALUES (
    'Estabelecimento nome', 'Roupas', '2023-10-23', 'SampleTelefone', 'SampleEnquadramento',
    'SampleInstagram', 'SampleFacebook', 'sample@email.com', true,
    1, 1
);
INSERT INTO secao (
    descricao, fk_estabelecimento
) VALUES (
    'Bebidas', 1
);
INSERT INTO metodo_pagamento (descricao) VALUES ('Cartão de Crédito');
INSERT INTO metodo_pagamento (descricao) VALUES ('Dinheiro');
INSERT INTO metodo_pagamento (descricao) VALUES ('Transferência Bancária');
