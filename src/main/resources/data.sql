-- Inserir Usuario
INSERT INTO usuario (id, email, senha, tipo_usuario)
VALUES (1, 'pedro@pedro.com', '$2a$10$DDyTCVL1Aen6/gJfTfQlD.69pYMngIAP3bYFF.rPVHM9Dligjs3SW', 'CONSUMIDOR');


INSERT INTO endereco (id, cep, rua, bairro, numero, geolocalizacaox, geolocalizacaoy)
VALUES (1, '12345-678', 'Rua Comerciante', 'Bairro Comerciante', 123, 0.0, 0.0);

INSERT INTO comerciante (id, cnpj, nome, data_criacao, data_ultimo_acesso, razao_social, fk_usuario, fk_endereco, is_ativo)
VALUES (1, '12345678901234', 'Nome Comerciante', '2023-10-25', '2023-10-25', 'Razao Social', 1, 1, true);

-- Inserir Tag
INSERT INTO tag (id, descricao)
VALUES (1, 'NomeTag');

INSERT INTO estabelecimento (
    nome, segmento, data_criacao, telefone_contato, enquadramento_juridico,
    referencia_instagram, referencia_facebook, email_contato, is_ativo,
    fk_comerciante, fk_endereco
) VALUES (
    'SampleNome', 'SampleSegmento', '2023-10-23', 'SampleTelefone', 'SampleEnquadramento',
    'SampleInstagram', 'SampleFacebook', 'sample@email.com', true,
    1, 1 -- Assuming fk_comerciante and fk_endereco have a sample value of 1. Adjust accordingly.
);
INSERT INTO secao (
    descricao, fk_estabelecimento
) VALUES (
    'SampleDescricao', 1 -- Assuming fk_estabelecimento has a sample value of 1. Adjust accordingly.
);
