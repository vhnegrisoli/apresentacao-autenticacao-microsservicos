INSERT INTO empresa (id,cpf_cnpj,data_cadastro,nome,razao_social,situacao,tipo_empresa) VALUES
  (4,'26.343.835/0001-38','2020-07-02 21:31:37.316','Empresa 01 Edicao','Empresa 01','ATIVA','SALAO'),
  (7,'49.579.794/0001-89','2020-07-02 21:56:54.802','Empresa 02','Empresa 02','ATIVA','SALAO');

INSERT INTO empresa_socios (fk_empresa,fk_usuario) VALUES
  (7,3),
  (4,2),
  (4,6),
  (7,8);