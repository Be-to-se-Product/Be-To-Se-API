

SET character_set_client = utf8;
SET character_set_connection = utf8;
SET character_set_results = utf8;
SET collation_connection = utf8_general_ci;

use easyfind;
    create table if not exists agenda  (
            horario_fim time(6),
            horario_inicio time(6),
            fk_estabelecimento bigint,
            id bigint not null auto_increment,
            dia varchar(255),
            primary key (id)
        );
    
        create table if not exists avaliacao  (
            data_criacao date,
            qtd_estrela integer,
            data_atualizacao datetime(6),
            fk_consumidor bigint,
            fk_produto bigint,
            id bigint not null auto_increment,
            comentario varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists carrinho  (
            quantidade integer,
            data_hora_alocacao datetime(6),
            fk_consumidor bigint,
            fk_produto bigint,
            id bigint not null auto_increment,
            primary key (id)
        ) 
    ;
    
        create table if not exists comerciante  (
            data_criacao date,
            data_ultimo_acesso date,
            is_ativo bit,
            fk_endereco bigint,
            fk_usuario bigint,
            id bigint not null auto_increment,
            cnpj varchar(255),
            nome varchar(255),
            razao_social varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists consumidor  (
            data_nascimento date,
            data_ultima_compra date,
            fk_imagem integer,
            is_ativo bit,
            data_criacao datetime(6),
            fk_usuario bigint,
            id bigint not null auto_increment,
            celular varchar(255),
            cpf varchar(255),
            genero varchar(255),
            nome varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists endereco  (
            geolocalizacaox float(53) not null,
            geolocalizacaoy float(53) not null,
            id bigint not null auto_increment,
            bairro varchar(255),
            cep varchar(255),
            numero varchar(255),
            rua varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists estabelecimento  (
            data_criacao date,
            is_ativo bit,
            fk_comerciante bigint,
            fk_endereco bigint,
            id bigint not null auto_increment,
            email_contato varchar(255),
            enquadramento_juridico varchar(255),
            nome varchar(255),
            referencia_facebook varchar(255),
            referencia_instagram varchar(255),
            segmento varchar(255),
            telefone_contato varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists imagem  (
            id integer not null auto_increment,
            data_criacao datetime(6),
            fk_estabelecimento bigint,
            fk_produto bigint,
            nome_imagem varchar(255),
            nome_referencia varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists interesse  (
            nivel_interesse integer,
            fk_consumidor bigint,
            fk_tag bigint,
            id bigint not null auto_increment,
            primary key (id)
        ) 
    ;
    
        create table if not exists item_venda  (
            is_promocao_ativa bit not null,
            quantidade integer,
            fk_consumidor bigint,
            fk_pedido bigint,
            fk_produto bigint,
            id bigint not null auto_increment,
            primary key (id)
        ) 
    ;
    
        create table if not exists metodo_pagamento  (
            id bigint not null auto_increment,
            descricao varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists metodo_pagamento_aceito  (
            is_ativo bit,
            fk_estabelecimento bigint,
            fk_metodo_pagamento bigint,
            id bigint not null auto_increment,
            primary key (id)
        ) 
    ;
    
        create table if not exists pedido  (
            is_pagamento_online bit,
            data_hora_pedido datetime(6),
            data_hora_retirada datetime(6),
            fk_metodo_aceito bigint,
            id bigint not null auto_increment,
            nf varchar(255),
            status_descricao enum ('AGUARDANDO_RETIRADA','CANCELADO','ENTREGUE','PENDENTE','PREPARO'),
            primary key (id)
        ) 
    ;
    
        create table if not exists produto  (
            is_ativo bit,
            is_deleted bit,
            is_promocao_ativa bit,
            preco float(53),
            preco_oferta float(53),
            qtd_vendido integer,
            taxa_compra float(53),
            fk_secao bigint,
            id bigint not null auto_increment,
            categoria varchar(255),
            codigo_barras varchar(255),
            codigo_sku varchar(255),
            descricao varchar(255),
            nome varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists produto_tag  (
            fk_produto bigint,
            fk_tag bigint,
            id bigint not null auto_increment,
            primary key (id)
        ) 
    ;
    
        create table if not exists secao  (
            fk_estabelecimento bigint,
            id bigint not null auto_increment,
            descricao varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists tag  (
            id bigint not null auto_increment,
            descricao varchar(255),
            primary key (id)
        ) 
    ;
    
        create table if not exists transacao  (
            data_transacao date,
            is_estornado bit not null,
            taxa float(53),
            valor float(53),
            fk_pedido bigint,
            id bigint not null auto_increment,
            primary key (id)
        ) 
    ;
    
        create table if not exists usuario  (
            id bigint not null auto_increment,
            email varchar(255),
            senha varchar(255),
            tipo_usuario enum ('COMERCIANTE','CONSUMIDOR'),
            primary key (id)
        ) 
    ;
    
        alter table comerciante 
        add constraint UK_28kjsv50c31qe2ip4w2xhun5v unique (fk_endereco)
    ;
    
        alter table comerciante 
        add constraint UK_1yrwvypert71ymdwd3upbobpi unique (fk_usuario)
    ;
    
        alter table consumidor 
        add constraint UK_ja2ots1o4qteblikfke9g0vlv unique (fk_imagem)
    ;
    
        alter table consumidor 
        add constraint UK_8fww58s59imyxpsk25mp0e40o unique (fk_usuario)
    ;
    
        alter table agenda 
        add constraint FKo6ilpjgnpcbyd5oqio9ann5rc 
        foreign key (fk_estabelecimento) 
        references estabelecimento (id)
    ;
    
        alter table avaliacao 
        add constraint FK1brquxxtum8ndyt80wijuwiyk 
        foreign key (fk_consumidor) 
        references consumidor (id)
    ;
    
        alter table avaliacao 
        add constraint FK2843gy1jqoshwbhx4kj9ikt6g 
        foreign key (fk_produto) 
        references produto (id)
    ;
    
        alter table carrinho 
        add constraint FKbd08wu39l3l9jh0hgeam7xy49 
        foreign key (fk_consumidor) 
        references consumidor (id)
    ;
    
        alter table carrinho 
        add constraint FKogmev991ik8tp5vj7ncyrty78 
        foreign key (fk_produto) 
        references produto (id)
    ;
    
        alter table comerciante 
        add constraint FKmeiea0wvf828spvkid2gffyrf 
        foreign key (fk_endereco) 
        references endereco (id)
    ;
    
        alter table comerciante 
        add constraint FKql0ktbuelbogblg8vyf3ka49r 
        foreign key (fk_usuario) 
        references usuario (id)
    ;
    
        alter table consumidor 
        add constraint FKl20togr8sc2d64n7tsmo2yoex 
        foreign key (fk_imagem) 
        references imagem (id)
    ;
    
        alter table consumidor 
        add constraint FK5c981l9hyi819wxnqasuanp5u 
        foreign key (fk_usuario) 
        references usuario (id)
    ;
    
        alter table estabelecimento 
        add constraint FK29hr1l555hppgavdv2iw23e3w 
        foreign key (fk_comerciante) 
        references comerciante (id)
    ;
    
        alter table estabelecimento 
        add constraint FKgl4xhu2rpfwqytf8v8ayqrg22 
        foreign key (fk_endereco) 
        references endereco (id)
    ;
    
        alter table imagem 
        add constraint FK2hwtngho9egog3yapgp6e63dl 
        foreign key (fk_estabelecimento) 
        references estabelecimento (id)
    ;
    
        alter table imagem 
        add constraint FKelba3wgi9199pgc9877yw3npf 
        foreign key (fk_produto) 
        references produto (id)
    ;
    
        alter table interesse 
        add constraint FKs2y0wlac20rtoj7qv4ffqni54 
        foreign key (fk_consumidor) 
        references consumidor (id)
    ;
    
        alter table interesse 
        add constraint FKmqdmbk5hcn0xbvo597g5xnuje 
        foreign key (fk_tag) 
        references tag (id)
    ;
    
        alter table item_venda 
        add constraint FKyosjrcc93imxj8fqbw78vdsc 
        foreign key (fk_consumidor) 
        references consumidor (id)
    ;
    
        alter table item_venda 
        add constraint FKg8vtxgjmp4l1ospao1q9ekgvs 
        foreign key (fk_pedido) 
        references pedido (id)
    ;
    
        alter table item_venda 
        add constraint FKn3vu0bg46q0w0jkgnk51tpnyq 
        foreign key (fk_produto) 
        references produto (id)
    ;
    
        alter table metodo_pagamento_aceito 
        add constraint FK9viq6w7i667gjw2q21u8484ud 
        foreign key (fk_estabelecimento) 
        references estabelecimento (id)
    ;
    
        alter table metodo_pagamento_aceito 
        add constraint FK14aju01j51df7a7h85nwnjlgj 
        foreign key (fk_metodo_pagamento) 
        references metodo_pagamento (id)
    ;
    
        alter table pedido 
        add constraint FKreb1ymqtm5jqn9t1a114n3bnt 
        foreign key (fk_metodo_aceito) 
        references metodo_pagamento_aceito (id)
    ;
    
        alter table produto 
        add constraint FKey4fkkgc3hd0le4mjfb2xwsw9 
        foreign key (fk_secao) 
        references secao (id)
    ;
    
        alter table produto_tag 
        add constraint FK36f8ktuj1p3lqajnvkfka64xy 
        foreign key (fk_produto) 
        references produto (id)
    ;
    
        alter table produto_tag 
        add constraint FKmoigyjpjyfjbll87csgsbcolt 
        foreign key (fk_tag) 
        references tag (id)
    ;
    
        alter table secao 
        add constraint FK5ri0bn0328ch597u284dlwnf3 
        foreign key (fk_estabelecimento) 
        references estabelecimento (id)
    ;
    
        alter table transacao 
        add constraint FK1gg4qs8qm1h29py3pactvi7ud 
        foreign key (fk_pedido) 
        references pedido (id);







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
        (-23.550520, -46.633307, 123, 'Centro', '09531080', 'Rua Principal'),
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
        ('Cartão de Débito'),
        ('PicPay'),
        ('PayPal'),
        ('Vale Alimentação'),
        ('Vale Refeição'),
        ('Bitcoin');

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


        -- Dados para a tabela 'metodo_pagamento_aceito'
        INSERT INTO metodo_pagamento_aceito (fk_estabelecimento, fk_metodo_pagamento,is_ativo) VALUES
        (1, 1,true),
        (1, 2,true),
        (1, 3,true),
        (2, 4,true),
        (2, 5,true),
        (2, 6,true),
        (3, 7,true),
        (3, 8,true),
        (3, 9,true),
        (4, 10,true);

        -- Dados para a tabela 'tag'
        INSERT INTO tag (descricao) VALUES
        ('Orgânico'),
        ('Integral'),
        ('Promoção'),
        ('Sem Glúten'),
        ('Sem Lactose'),
        ('Vegano'),
        ('Low Carb'),
        ('Fitness'),
        ('Gourmet'),
        ('Artesanal');

        -- Dados para a tabela 'secao'
        INSERT INTO secao (fk_estabelecimento, descricao) VALUES
        (1, 'Açougue'),
        (1, 'Padaria'),
        (2, 'Açougue'),
        (2, 'Padaria'),
        (3, 'Açougue'),
        (3, 'Padaria'),
        (4, 'Conveniência'),
        (4, 'Mercado'),
        (5, 'Hortifruti'),
        (5, 'Congelados');

        -- Dados para a tabela 'produto'
        INSERT INTO produto (is_ativo, is_promocao_ativa, preco, preco_oferta, qtd_vendido, fk_secao, categoria, codigo_barras, codigo_sku, descricao, nome) VALUES
        (true, false, 5.99, NULL, 100, 1, 'Alimentos', '1234567890123', 'PROD001', 'Arroz Integral 1kg', 'Arroz Integral'),
        (true, true, 8.99, 6.99, 50, 2, 'Padaria', '9876543210987', 'PROD002', 'Pão Francês', 'Pão Francês 1 unidade'),
        (true, false, 2.50, NULL, 80, 1, 'Alimentos', '8765432109876', 'PROD003', 'Feijão Preto 500g', 'Feijão Preto'),
        (true, true, 15.99, 12.99, 30, 4, 'Padaria', '3456789012345', 'PROD004', 'Bolo de Chocolate', 'Bolo de Chocolate 1kg'),
        (true, false, 3.50, NULL, 60, 5, 'Hortifruti', '2345678901234', 'PROD005', 'Maçã Fuji', 'Maçã Fuji 1kg'),
        (true, true, 7.99, 5.99, 45, 6, 'Congelados', '6543210987654', 'PROD006', 'Pizza Margherita', 'Pizza Margherita 400g'),
        (true, false, 9.50, NULL, 70, 7, 'Conveniência', '7890123456789', 'PROD007', 'Água Mineral 1L', 'Água Mineral'),
        (true, true, 4.99, 3.99, 55, 8, 'Mercado', '3210987654321', 'PROD008', 'Sabonete Líquido 250ml', 'Sabonete Líquido'),
        (true, false, 6.50, NULL, 40, 9, 'Supermercado', '0123456789012', 'PROD009', 'Detergente 500ml', 'Detergente'),
        (true, true, 11.99, 9.99, 25, 10, 'Gourmet', '9876543210123', 'PROD010', 'Azeite Extra Virgem 500ml', 'Azeite Extra Virgem');

        -- Dados para a tabela 'imagem'
        INSERT INTO imagem (data_criacao, fk_estabelecimento, fk_produto, nome_imagem, nome_referencia) VALUES
        ('2022-05-01', 1, NULL, 'logo_mercado.jpg', 'Logo Mercado Bom Preço'),
        ('2022-07-01', 1, NULL, 'logo_padaria.jpg', 'Logo Padaria Sabor Artesanal'),
        ('2022-08-15', 3, NULL, 'logo_acougue.jpg', 'Logo Açougue da Esquina'),
        ('2022-09-10', 3, NULL, 'logo_padaria_acougue.jpg', 'Logo Padaria e Açougue da Esquina'),
        ('2022-11-01', 5, NULL, 'logo_conveniencia.jpg', 'Logo Loja de Conveniência'),
        ('2023-01-15', 5, NULL, 'logo_supermercado.jpg', 'Logo Supermercado Mega');

        -- Dados para a tabela 'produto_tag'
        INSERT INTO produto_tag (fk_produto, fk_tag) VALUES
        (1, 1),
        (2, 2),
        (3, 1),
        (4, 3),
        (5, 4),
        (6, 5),
        (7, 6),
        (8, 7),
        (9, 8),
        (10, 9);

        -- Dados para a tabela 'avaliacao'
        INSERT INTO avaliacao (data_criacao, qtd_estrela, data_atualizacao, fk_consumidor, fk_produto, comentario) VALUES
        ('2023-01-01', 4, '2023-01-02 15:30:00', 1, 1, 'Ótimo produto! Recomendo'),
        ('2023-01-05', 5, '2023-01-06 12:45:00', 1, 2, 'Excelente atendimento'),
        ('2023-02-10', 3, '2023-02-12 09:00:00', 2, 3, 'Bom custo-benefício'),
        ('2023-02-15', 5, '2023-02-18 14:20:00', 2, 4, 'O bolo é incrível!'),
        ('2023-03-05', 4, '2023-03-08 11:10:00', 3, 5, 'Maçãs fresquinhas!'),
        ('2023-03-12', 5, '2023-03-15 16:45:00', 3, 6, 'A pizza é deliciosa'),
        ('2023-04-01', 3, '2023-04-03 08:30:00', 4, 7, 'Água de qualidade'),
        ('2023-04-05', 4, '2023-04-08 14:15:00', 4, 8, 'Cheirinho maravilhoso'),
        ('2023-04-20', 5, '2023-04-22 10:45:00', 5, 9, 'Detergente eficiente'),
        ('2023-05-02', 4, '2023-05-05 13:00:00', 5, 10, 'Azeite de alta qualidade');

        -- Dados para a tabela 'interesse'
        INSERT INTO interesse (nivel_interesse, fk_consumidor, fk_tag) VALUES
        (3, 1, 1),
        (2, 1, 2),
        (4, 2, 3),
        (5, 2, 4),
        (3, 3, 5),
        (4, 3, 6),
        (2, 4, 7),
        (5, 4, 8),
        (3, 5, 9),
        (4, 5, 10);

        -- Dados para a tabela 'pedido'
        INSERT INTO pedido (is_pagamento_online, data_hora_pedido, data_hora_retirada, fk_metodo_aceito, nf, status_descricao) VALUES
        (true, '2023-03-01 11:30:00', '2023-03-01 16:00:00', 1, '12345', 'ENTREGUE'),
        (false, '2023-03-02 09:45:00', '2023-03-02 14:30:00', 2, '67890', 'AGUARDANDO_RETIRADA'),
        (true, '2023-03-05 14:00:00', '2023-03-05 18:30:00', 3, '11111', 'PENDENTE'),
        (false, '2023-03-08 10:30:00', '2023-03-08 15:15:00', 4, '22222', 'PREPARO'),
        (true, '2023-03-12 12:45:00', '2023-03-12 17:00:00', 5, '33333', 'CANCELADO'),
        (false, '2023-03-15 09:00:00', '2023-03-15 13:45:00', 6, '44444', 'ENTREGUE'),
        (true, '2023-03-18 16:30:00', '2023-03-18 20:45:00', 7, '55555', 'AGUARDANDO_RETIRADA'),
        (false, '2023-03-22 11:15:00', '2023-03-22 15:30:00', 8, '66666', 'PENDENTE'),
        (true, '2023-03-25 13:45:00', '2023-03-25 18:00:00', 9, '77777', 'ENTREGUE'),
        (false, '2023-03-28 08:30:00', '2023-03-28 12:45:00', 10, '88888', 'CANCELADO');

        -- Dados para a tabela 'transacao'
        INSERT INTO transacao (is_estornado, taxa, valor, fk_pedido, data_transacao) VALUES
        (false, 0.05, 30.00, 1, '2023-03-01'),
        (false, 0.03, 15.00, 2, '2023-03-02'),
        (false, 0.08, 40.00, 3, '2023-03-05'),
        (false, 0.02, 25.00, 4, '2023-03-08'),
        (false, 0.07, 35.00, 5, '2023-03-12'),
        (false, 0.04, 20.00, 6, '2023-03-15'),
        (false, 0.06, 30.00, 7, '2023-03-18'),
        (false, 0.01, 10.00, 8, '2023-03-22'),
        (false, 0.09, 45.00, 9, '2023-03-25'),
        (false, 0.03, 15.00, 10, '2023-03-28');

        -- Dados para a tabela 'carrinho'
        INSERT INTO carrinho (quantidade, data_hora_alocacao, fk_consumidor, fk_produto) VALUES
        (2, '2023-02-10 08:30:00', 1, 1),
        (1, '2023-02-11 14:00:00', 1, 3),
        (3, '2023-02-15 09:30:00', 2, 5),
        (2, '2023-02-18 16:00:00', 2, 7),
        (1, '2023-02-22 11:45:00', 3, 9),
        (4, '2023-02-25 13:15:00', 3, 2),
        (3, '2023-03-01 10:00:00', 4, 4),
        (2, '2023-03-04 15:30:00', 4, 6),
        (1, '2023-03-08 09:15:00', 5, 8),
        (4, '2023-03-11 12:45:00', 5, 10);

        -- Dados para a tabela 'item_venda'
        INSERT INTO item_venda (is_promocao_ativa, quantidade, fk_consumidor, fk_pedido, fk_produto) VALUES
        (true, 2, 1, 1, 1),
        (false, 1, 1, 2, 3),
        (true, 3, 2, 3, 5),
        (false, 2, 2, 4, 7),
        (true, 1, 3, 5, 9),
        (false, 4, 3, 6, 2),
        (true, 3, 4, 7, 4),
        (false, 2, 4, 8, 6),
        (true, 1, 5, 9, 8),
        (false, 4, 5, 10, 10);

        INSERT INTO pedido (is_pagamento_online, data_hora_pedido, data_hora_retirada, fk_metodo_aceito, nf, status_descricao) VALUES
            (true, '2023-03-09 12:30:00', '2023-03-09 17:00:00', 1, '99999', 'CANCELADO'),
            (false, '2023-03-13 09:45:00', '2023-03-13 14:30:00', 2, '101010', 'PENDENTE'),
            (true, '2023-03-17 14:00:00', '2023-03-17 18:30:00', 3, '111111', 'CANCELADO'),
            (false, '2023-03-21 10:30:00', '2023-03-21 15:15:00', 1, '121212', 'PENDENTE'),
            (true, '2023-03-25 12:45:00', '2023-03-25 17:00:00', 1, '131313', 'CANCELADO'),
            (false, '2023-03-29 09:00:00', '2023-03-29 13:45:00', 2, '141414', 'PENDENTE'),
            (true, '2023-04-02 16:30:00', '2023-04-02 20:45:00', 2, '151515', 'CANCELADO'),
            (false, '2023-04-06 11:15:00', '2023-04-06 15:30:00', 3, '161616', 'PENDENTE'),
            (true, '2023-04-10 13:45:00', '2023-04-10 18:00:00', 2, '171717', 'CANCELADO'),
            (false, '2023-04-14 08:30:00', '2023-04-14 12:45:00', 1, '181818', 'PENDENTE');

        INSERT INTO transacao (is_estornado, taxa, valor, fk_pedido, data_transacao) VALUES
            (false, 0.05, 30.00, 11, '2023-03-09'),
                (false, 0.03, 15.00, 12, '2023-03-13'),
                (false, 0.08, 40.00, 13, '2023-03-17'),
                (false, 0.02, 25.00, 14, '2023-03-21'),
                (false, 0.07, 35.00, 15, '2023-03-25'),
                (false, 0.04, 20.00, 16, '2023-03-29'),
                (false, 0.06, 30.00, 17, '2023-04-02'),
                (false, 0.01, 10.00, 18, '2023-04-06'),
                (false, 0.09, 45.00, 19, '2023-04-10'),
                (false, 0.03, 15.00, 20, '2023-04-14');

        INSERT INTO item_venda (is_promocao_ativa, quantidade, fk_consumidor, fk_pedido, fk_produto) VALUES
                (true, 2, 1, 11, 1),
                (false, 1, 1, 12, 3),
                (true, 3, 2, 13, 2),
                (false, 2, 2, 14, 3),
                (true, 1, 3, 15, 2),
                (false, 4, 3, 16, 3),
                (true, 3, 4, 17, 1),
                (false, 2, 4, 18, 2),
                (true, 1, 5, 19, 3),
                (false, 4, 5, 20, 3);