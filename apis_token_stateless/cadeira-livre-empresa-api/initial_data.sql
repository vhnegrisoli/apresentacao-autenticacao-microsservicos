INSERT INTO public.permissao (id,descricao,permissao) VALUES
(1,'Administrador','ADMIN')
,(2,'Proprietário','PROPRIETARIO')
,(3,'Sócio','SOCIO')
,(4,'Gerente','GERENTE')
,(5,'Funcionário','FUNCIONARIO')
;

INSERT INTO public.usuario (id,cpf,data_cadastro,data_nascimento,email,nome,senha,sexo,situacao,token_notificacao,ultimo_acesso) VALUES
(2,'234.305.090-25','2020-07-02 21:18:47.377','1998-03-31','proprietario1@gmail.com','Proprietario 1','$2a$10$7WktO6b67Or4HmCrY0R3deDxWYjJXZyHdy4rXr4D9ivkOtcZB7csC','MASCULINO','ATIVO',NULL,'2020-07-02 21:18:47.377')
,(3,'468.395.250-58','2020-07-02 21:19:25.115','1998-03-31','proprietario2@gmail.com','Proprietario 2','$2a$10$32QNFxFmj8l6paQQtavGO.bVydvD/hAemCLkTwHTo6eo03EhofpzG','MASCULINO','ATIVO',NULL,'2020-07-02 21:19:25.115')
,(1,'103.324.589-54','2020-07-02 20:44:01.860','1998-03-31','victorhugonegrisoli.ccs@gmail.com','Victor Hugo Negrisoli','$2a$10$uOy6Cs2KXHXR.ZO0KyGh1eupbj1q40vWss2Al2m8tM/R5ZCQZv4p6','MASCULINO','ATIVO',NULL,'2020-07-02 21:52:57.181')
,(8,'817.571.400-00','2020-07-02 21:59:11.596','1998-03-31','socio2@gmail.com','Sócio 2','$2a$10$cFq2l1RZTD3hfa14cm45qegqwsSxMqrzqyO4kAi5whUsjVrPunxYK','MASCULINO','ATIVO',NULL,'2020-07-02 21:59:11.596')
,(6,'590.845.540-78','2020-07-02 21:42:49.060','2000-12-14','socio1@gmail.com','Sócio 1 Update','$2a$10$P4/oIdDL.GuPBBp8PD2HWuWQ.fFlxBstD4yZmApPUTspGS7Rchsm2','MASCULINO','ATIVO',NULL,'2020-07-02 22:37:55.554')
;

INSERT INTO public.usuario_permissao (fk_usuario,fk_permissao) VALUES
(1,1)
,(2,2)
,(3,2)
,(6,3)
,(8,3)
;

INSERT INTO public.empresa (id,cnpj,data_cadastro,nome,razao_social,situacao,tipo_empresa) VALUES
(4,'26.343.835/0001-38','2020-07-02 21:31:37.316','Empresa 01 Edicao','Empresa 01','ATIVA','SALAO')
,(7,'49.579.794/0001-89','2020-07-02 21:56:54.802','Empresa 02','Empresa 02','ATIVA','SALAO')
;

INSERT INTO public.empresa_socios (fk_empresa,fk_usuario) VALUES
(7,3)
,(4,2)
,(4,6)
,(7,8)
;