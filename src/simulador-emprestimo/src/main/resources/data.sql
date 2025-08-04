insert into cliente (cpf, nome, data_nascimento)
values
('12345678901', 'Horrander Barbosa', '2002-05-15'), 
('98765432100', 'Maria da Silva', '1985-10-20'),
('11223344556', 'João Pereira', '1990-03-30'),
('99887766554', 'Ana Costa', '1975-07-25'),
('55667788990', 'Carlos Souza', '1980-12-05'),
('44332211000', 'Fernanda Lima', '1995-08-10'),
('66778899001', 'Roberto Santos', '1988-11-15'),
('33445566778', 'Patrícia Oliveira', '1992-04-20'),
('77889900112', 'Lucas Almeida', '2000-01-01'),
('88990011223', 'Juliana Rocha', '1983-09-30'),
('12312312345', 'Eduardo Martins', '1998-06-15'),
('45645645678', 'Sofia Ferreira', '1991-02-28'),
('78978978901', 'Ricardo Gomes', '1987-12-10'),
('32132132109', 'Camila Costa', '1994-05-05'),
('65465465432', 'André Silva', '1982-11-20'),
('98798798765', 'Larissa Santos', '1996-03-15'),
('22233344455', 'Tatiane Almeida', '1993-10-25'),
('33344455566', 'Gustavo Lima', '1986-08-05'),
('44455566677', 'Bruna Oliveira', '1999-04-20'),
('55566677788', 'Marcos Pereira', '1984-01-15'),
('66677788899', 'Aline Costa', '1997-09-10'),
('77788899900', 'Thiago Santos', '1990-12-30'),
('88899900011', 'Vanessa Rocha', '1981-06-05'),
('99900011122', 'Rafael Martins', '1995-03-20'),
('00011122233', 'Isabela Ferreira', '1988-08-15'),
('11122233344', 'Leonardo Gomes', '1992-11-25');

insert into taxa_juros (idade_inicial, idade_final, taxa_anual)
values
(0, 25, 5.0),
(26, 40, 3.0),
(41, 60, 2.0),
(61, 99, 4.0);
