
    -- Dados para a tabela 'usuario'
-- Dados para a tabela 'usuario'
INSERT INTO usuario (email, senha, tipo_usuario) VALUES
('comerciante1@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'COMERCIANTE'),
('consumidor1@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'CONSUMIDOR'),
('comerciante2@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'COMERCIANTE'),
('consumidor2@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'CONSUMIDOR'),
('comerciante3@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'COMERCIANTE'),
('consumidor3@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'CONSUMIDOR'),
('comerciante4@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'COMERCIANTE'),
('consumidor4@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'CONSUMIDOR'),
('comerciante5@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'COMERCIANTE'),
('consumidor5@email.com', '$2a$12$I/JR6Xqs1cPFyy./BHPIJe0I7mBNpCHbhXoGueCkIMZN1jYihQFxq', 'CONSUMIDOR');


    -- Dados para a tabela 'endereco'
    INSERT INTO endereco (geolocalizacaox, geolocalizacaoy, numero, bairro, cep, rua) VALUES
    (-23.550520, -46.633307, 123, 'Centro', '01000-000', 'Rua Principal'),
    (-23.590629, -46.657353, 456, 'Vila Oliveira', '02000-000', 'Rua Secundária'),
    (-23.560123, -46.610987, 789, 'Jardins', '03000-000', 'Avenida Principal'),
    (-23.578901, -46.625432, 321, 'Mooca', '04000-000', 'Avenida Secundária'),
    (-23.545678, -46.665432, 555, 'Pinheiros', '05000-000', 'Travessa Principal'),
    (-23.590123, -46.612345, 777, 'Itaim Bibi', '06000-000', 'Travessa Secundária'),
    (-23.520987, -46.678901, 999, 'Brooklin', '07000-000', 'Praça Principal'),
    (-23.540321, -46.655432, 111, 'Perdizes', '08000-000', 'Praça Secundária'),
    (-23.510987, -46.645678, 222, 'Santana', '09000-000', 'Alameda Principal'),
    (-23.580321, -46.632109, 333, 'Liberdade', '10000-000', 'Alameda Secundária');

    -- Dados para a tabela 'metodo_pagamento'
    INSERT INTO metodo_pagamento (descricao) VALUES
    ('Cartão de Crédito'),
    ('Dinheiro'),
    ('Pix'),
    ('Boleto'),
    ('Transferência Bancária'),
    ('PicPay'),
    ('PayPal'),
    ('Vale Alimentação'),
    ('Vale Refeição'),
    ('Cesta Básica');

    -- Dados para a tabela 'comerciante'
    INSERT INTO comerciante (data_criacao, data_ultimo_acesso, is_ativo, fk_endereco, fk_usuario, cnpj, nome, razao_social) VALUES
    ('2022-05-15', '2023-02-20', true, 1, 1, '12345678901234', 'Mercado Bom Preço', 'Razão Social Ltda'),
    ('2022-06-20', '2023-03-25', true, 3, 3, '56789012345678', 'Padaria Sabor Artesanal', 'Sabor e Arte Ltda'),
    ('2022-07-10', '2023-04-30', true, 5, 5, '90123456789012', 'Açougue da Esquina', 'Carnes de Qualidade Ltda'),
    ('2022-08-05', '2023-05-15', true, 7, 7, '34567890123456', 'Loja de Conveniência', 'Comodidade Rápida Ltda'),
    ('2022-09-01', '2023-06-10', true, 9, 9, '67890123456789', 'Supermercado Mega', 'Variedade em Produtos Ltda');

    -- Dados para a tabela 'consumidor'
    INSERT INTO consumidor (data_nascimento, data_ultima_compra, is_ativo, data_criacao, fk_usuario, celular, cpf, genero, nome) VALUES
    ('1990-03-12', '2023-01-20',  true, '2022-01-01 10:00:00', 2, '987654321', '12345678901', 'Feminino', 'Maria Silva'),
    ('1985-06-25', '2023-02-15',  true, '2022-02-01 14:30:00', 4, '123456789', '98765432109', 'Masculino', 'João Oliveira'),
    ('1992-11-18', '2023-03-10',  true, '2022-03-01 09:15:00', 6, '987654321', '34567890123', 'Feminino', 'Ana Santos'),
    ('1980-09-30', '2023-04-05',  true, '2022-04-01 18:45:00', 8, '123456789', '56789012345', 'Masculino', 'Carlos Lima'),
    ('1988-04-15', '2023-05-20',  true, '2022-05-01 12:00:00', 10, '987654321', '90123456789', 'Feminino', 'Rafaela Silva');

    -- Dados para a tabela 'estabelecimento'
    INSERT INTO estabelecimento (data_criacao, is_ativo, fk_comerciante, fk_endereco, email_contato, enquadramento_juridico, nome, referencia_facebook, referencia_instagram, segmento, telefone_contato) VALUES
    ('2022-04-01', true, 1, 1, 'contato@mercadobompreco.com', 'MEI', 'Mercado Bom Preço', 'fb.com/mercadobompreco', '@mercadobompreco', 'Mercado', '(11) 1234-5678'),
    ('2022-06-01', true, 3, 3, 'contato@padariasaborartesanal.com', 'MEI', 'Padaria Sabor Artesanal', 'fb.com/padariasaborartesanal', '@padariasaborartesanal', 'Padaria', '(11) 9876-5432'),
    ('2022-08-01', true, 5, 5, 'contato@acouguedaesquina.com', 'MEI', 'Açougue da Esquina', 'fb.com/acouguedaesquina', '@acouguedaesquina', 'Açougue', '(11) 5678-9012'),
    ('2022-10-01', true, 4, 7, 'contato@lojaconveniencia.com', 'MEI', 'Loja de Conveniência', 'fb.com/lojaconveniencia', '@lojaconveniencia', 'Conveniência', '(11) 2345-6789'),
    ('2023-01-01', true, 2, 9, 'contato@supermercadomega.com', 'MEI', 'Supermercado Mega', 'fb.com/supermercadomega', '@supermercadomega', 'Supermercado', '(11) 8901-2345');


-- Segunda-feira
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('12:00:00', '10:00:00', 1, 'Segunda-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('13:30:00', '11:00:00', 2, 'Segunda-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('15:00:00', '12:30:00', 3, 'Segunda-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('17:00:00', '14:00:00', 4, 'Segunda-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('18:30:00', '15:30:00', 5, 'Segunda-feira');


-- Terça-feira
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('12:30:00', '09:00:00', 1, 'Terça-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('14:00:00', '10:30:00', 2, 'Terça-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('16:00:00', '12:00:00', 3, 'Terça-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('18:00:00', '14:30:00', 4, 'Terça-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('19:30:00', '16:00:00', 5, 'Terça-feira');


-- Quarta-feira
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('11:00:00', '09:30:00', 1, 'Quarta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('13:00:00', '10:30:00', 2, 'Quarta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('15:00:00', '12:00:00', 3, 'Quarta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('17:00:00', '14:30:00', 4, 'Quarta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('18:30:00', '16:00:00', 5, 'Quarta-feira');


-- Quinta-feira
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('12:30:00', '09:00:00', 1, 'Quinta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('14:00:00', '10:30:00', 2, 'Quinta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('16:00:00', '12:00:00', 3, 'Quinta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('18:00:00', '14:30:00', 4, 'Quinta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('19:30:00', '16:00:00', 5, 'Quinta-feira');


-- Sexta-feira
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('11:00:00', '09:30:00', 1, 'Sexta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('13:00:00', '10:30:00', 2, 'Sexta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('15:00:00', '12:00:00', 3, 'Sexta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('17:00:00', '14:30:00', 4, 'Sexta-feira');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('18:30:00', '16:00:00', 5, 'Sexta-feira');


-- Sábado
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('12:30:00', '09:00:00', 1, 'Sábado');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('14:00:00', '10:30:00', 2, 'Sábado');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('16:00:00', '12:00:00', 3, 'Sábado');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('18:00:00', '14:30:00', 4, 'Sábado');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('19:30:00', '16:00:00', 5, 'Sábado');


-- Domingo
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('11:00:00', '09:30:00', 1, 'Domingo');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('13:00:00', '10:30:00', 2, 'Domingo');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('15:00:00', '12:00:00', 3, 'Domingo');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('17:00:00', '14:30:00', 4, 'Domingo');
insert into agenda (horario_fim, horario_inicio, fk_estabelecimento, dia) values ('18:30:00', '16:00:00', 5, 'Domingo');



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
