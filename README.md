# microsservicos-projeto

Como rodar:

- Criar 3 bancos de dados mysql com os nomes fornecedor, transportador, loja de acordo com o que está especificado no repositório: https://github.com/lucasouza555/microsservicos-configs 

- Rodar os projetos auth, config-server, eureka-server, zuul primeiro, depois rodar o loja, transportador, fornecedor

- Os principais endpoints estão no arquivo endpoints_postman.json basta importá-los no Postman

- O sistema gira em torno desses endpoints "Realiza compra", "Reprocessa compra" e "Cancela compra".

O endpoint "Realiza compra" e "Reprocessa compra" recebem o mesmo json no corpo da requisição que é basicamente um array de itens, 
onde o id desses itens é o id da tabela produto do banco fornecedor.

Rodar esses inserts no banco fornecedor para poder realizar as compras:

insert into fornecedor(endereco, estado, nome)
values('Rua Vergueiro Lima, 2289, Vila das Flores', 'SP','Corrêa informática'),
      ('Rua Marechal Teodoro, 487, Tijuca', 'RJ','Santana móveis'),
      ('Rua Antoino de freitas, 1635, Buscardi', 'MG','Mario eletrodomésticos');

insert into produto(descricao, fornecedor_id, nome, preco)
values ('Mousepad 20cm x 40cm Mazor', 1, 'Mousepad Mazor', 10.90),
       ('Teclado mecânico modelo americano Lonevideas', 1, 'Teclado Lonevideas', 229.99),
       ('Sofá Samarino, 3 lugares, retrátil e reclinável', 2, 'Sofá Samarino', 789.90),
       ('Mesa de jantar Lavett com vidro 210cm', 2, 'Mesa Lavett', 1259.90),
       ('Geladeira/Refrigerador Top freezer BrasTop', 3, 'Geladeira Brastop', 2419.99),
       ('Fogão 5 bocas bivolt Doke', 3,'Fogão Doke', 1349.90);

Obs: Não necessariamente precisam ser esses dados, mas precisa cadastrar uns registros na tabela produto e fornecedor para a compra funcionar certinho.
