-- Dados para a tabela 'usuario'
INSERT INTO usuario (email, senha, tipo_usuario) VALUES
('comerciante1@email.com', 'senha123', 'COMERCIANTE'),
('consumidor1@email.com', 'senha456', 'CONSUMIDOR');

-- Dados para a tabela 'endereco'
INSERT INTO endereco (geolocalizacaox, geolocalizacaoy, numero, bairro, cep, rua) VALUES
(-23.550520, -46.633307, 123, 'Centro', '01000-000', 'Rua Principal'),
(-23.590629, -46.657353, 456, 'Vila Oliveira', '02000-000', 'Rua Secundária');

-- Dados para a tabela 'metodo_pagamento'


-- Dados para a tabela 'comerciante'
INSERT INTO comerciante (data_criacao, data_ultimo_acesso, is_ativo, fk_endereco, fk_usuario, cnpj, nome, razao_social) VALUES
('2022-05-15', '2023-02-20', true, 1, 1, '12345678901234', 'Mercado Bom Preço', 'Razão Social Ltda');


-- Dados para a tabela 'consumidor'
INSERT INTO consumidor (data_nascimento, data_ultima_compra, is_ativo, data_criacao, fk_usuario, celular, cpf, genero, nome) VALUES
('1990-03-12', '2023-01-20',  true, '2022-01-01 10:00:00', 2, '987654321', '12345678901', 'Feminino', 'Maria Silva');

-- Dados para a tabela 'estabelecimento'
INSERT INTO estabelecimento (data_criacao, is_ativo, fk_comerciante, fk_endereco, email_contato, enquadramento_juridico, nome, referencia_facebook, referencia_instagram, segmento, telefone_contato) VALUES
('2022-04-01', true, 1, 1, 'contato@mercadobompreco.com', 'MEI', 'Mercado Bom Preço', 'fb.com/mercadobompreco', '@mercadobompreco', 'Mercado', '(11) 1234-5678');

INSERT INTO metodo_pagamento (descricao) VALUES
('Cartão de Crédito'),
('Dinheiro'),
('Pix');

-- Dados para a tabela 'tag'
INSERT INTO tag (descricao) VALUES
('Orgânico'),
('Integral'),
('Promoção');

-- Dados para a tabela 'secao'
INSERT INTO secao (fk_estabelecimento, descricao) VALUES
(1, 'Açougue'),
(1, 'Padaria');

-- Dados para a tabela 'imagem'
INSERT INTO imagem (data_criacao, fk_estabelecimento, fk_produto, nome_imagem, nome_referencia) VALUES
('2022-05-01', 1, NULL, 'logo_mercado.jpg', 'Logo Mercado Bom Preço'),
('2022-07-01', 1, NULL, 'logo_padaria.jpg', 'Logo Padaria Sabor Artesanal');

-- Dados para a tabela 'secao'


-- Dados para a tabela 'produto'
INSERT INTO produto (is_ativo, is_promocao_ativa, preco, preco_oferta, qtd_vendido, fk_secao, categoria, codigo_barras, codigo_sku, descricao, nome) VALUES
(true, false, 5.99, NULL, 100, 1, 'Alimentos', '1234567890123', 'PROD001', 'Arroz Integral 1kg', 'Arroz Integral'),
(true, true, 8.99, 6.99, 50, 2, 'Padaria', '9876543210987', 'PROD002', 'Pão Francês', 'Pão Francês 1 unidade'),
(true, false, 2.50, NULL, 80, 1, 'Alimentos', '8765432109876', 'PROD003', 'Feijão Preto 500g', 'Feijão Preto');

-- Dados para a tabela 'produto_tag'
INSERT INTO produto_tag (fk_produto, fk_tag) VALUES
(1, 1),
(2, 2),
(3, 1);

-- Dados para a tabela 'avaliacao'
INSERT INTO avaliacao (data_criacao, qtd_estrela, data_atualizacao, fk_consumidor, fk_produto, comentario) VALUES
('2023-01-01', 4, '2023-01-02 15:30:00', 1, 1, 'Ótimo produto! Recomendo'),
('2023-01-05', 5, '2023-01-06 12:45:00', 1, 2, 'Excelente atendimento');

-- Dados para a tabela 'metodo_pagamento_aceito'
INSERT INTO metodo_pagamento_aceito (fk_estabelecimento, fk_metodo_pagamento) VALUES
(1, 1),
(1, 2),
(1, 3);

-- Dados para a tabela 'interesse'
INSERT INTO interesse (nivel_interesse, fk_consumidor, fk_tag) VALUES
(3, 1, 1),
(2, 1, 2);

INSERT INTO pedido (is_pagamento_online, data_hora_pedido, data_hora_retirada, fk_metodo_aceito, nf, status_descricao) VALUES
(true, '2023-03-01 11:30:00', '2023-03-01 16:00:00', 1, '12345', 'Aguardando Retirada'),
(false, '2023-03-02 09:45:00', '2023-03-02 14:30:00', 2, '67890', 'Em Preparo');

-- Dados para a tabela 'transacao'
INSERT INTO transacao (is_estornado, taxa, valor, fk_pedido) VALUES
(false, 0.05, 30.00, 1),
(false, 0.03, 15.00, 2);

-- Dados para a tabela 'carrinho'
INSERT INTO carrinho (quantidade, data_hora_alocacao, fk_consumidor, fk_produto) VALUES
(2, '2023-02-10 08:30:00', 1, 1),
(1, '2023-02-11 14:00:00', 1, 3);

-- Dados para a tabela 'pedido'


-- Dados para a tabela 'item_venda'
INSERT INTO item_venda (is_promocao_ativa, quantidade, fk_consumidor, fk_pedido, fk_produto) VALUES
(true, 2, 1, 1, 1),
(false, 1, 1, 2, 3);