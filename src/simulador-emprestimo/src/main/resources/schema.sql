drop table if exists cliente;
drop table if exists taxa_juros;

create table cliente (
    
    id int primary key auto_increment,
    cpf varchar(11) not null unique,
    nome varchar(100) not null,
    data_nascimento date not null
);

create table taxa_juros (
    id int primary key auto_increment,
    idade_inicial int not null,
    idade_final int not null,
    taxa_anual decimal(5, 2) not null
);
