INSERT INTO permissao (id,descricao,permissao) VALUES
  (1,'Administrador','ADMIN'),
  (2,'Proprietário','PROPRIETARIO'),
  (3,'Sócio','SOCIO'),
  (4,'Funcionário','FUNCIONARIO');

INSERT INTO usuario (id,cpf,data_cadastro,data_nascimento,email,nome,senha,sexo,situacao,token_notificacao,ultimo_acesso) VALUES
  (1,'103.324.589-54','2020-07-02 20:44:01.860','1998-03-31','victorhugonegrisoli.ccs@gmail.com','Victor Hugo Negrisoli','$2a$10$uOy6Cs2KXHXR.ZO0KyGh1eupbj1q40vWss2Al2m8tM/R5ZCQZv4p6','MASCULINO','ATIVO','123456','2020-07-02 21:52:57.181'),
  (2,'234.305.090-25','2020-07-02 21:18:47.377','1998-03-31','proprietario1@gmail.com','Proprietario 1','$2a$10$7WktO6b67Or4HmCrY0R3deDxWYjJXZyHdy4rXr4D9ivkOtcZB7csC','MASCULINO','ATIVO',NULL,'2020-07-02 21:18:47.377'),
  (3,'468.395.250-58','2020-07-02 21:19:25.115','1998-03-31','proprietario2@gmail.com','Proprietario 2','$2a$10$32QNFxFmj8l6paQQtavGO.bVydvD/hAemCLkTwHTo6eo03EhofpzG','MASCULINO','ATIVO',NULL,'2020-07-02 21:19:25.115'),
  (6,'590.845.540-78','2020-07-02 21:42:49.060','2000-12-14','socio1@gmail.com','Sócio 1 Update','$2a$10$FxBiaXeA0cTn3qy6mq9KJO4cjAO7ueL.ieCCnc7uf1M8K9G3pK5Za','MASCULINO','ATIVO',NULL,'2020-09-02 09:26:27.203'),
  (8,'817.571.400-00','2020-07-02 21:59:11.596','1998-03-31','socio2@gmail.com','Sócio 2','$2a$10$cFq2l1RZTD3hfa14cm45qegqwsSxMqrzqyO4kAi5whUsjVrPunxYK','MASCULINO','ATIVO',NULL,'2020-07-02 21:59:11.596'),
  (10,'192.393.640-99','2020-07-06 22:38:58.573','1998-03-31','funcionario1@gmail.com','Funcionário 1','$2a$10$lSCn3kc0Q9BidAARevjfweQ.rFDKHrLSWCobEIt7gRDPobD0B.KJG','MASCULINO','INATIVO',NULL,'2020-07-06 22:45:55.458'),
  (11,'218.112.450-72','2020-07-06 22:38:58.573','1998-03-31','proprietariosempermissao@gmail.com','Proprietario Sem Permissao','$2a$10$lSCn3kc0Q9BidAARevjfweQ.rFDKHrLSWCobEIt7gRDPobD0B.KJG','MASCULINO','INATIVO',NULL,'2020-07-06 22:45:55.458'),
  (12,'313.777.560-41','2020-07-06 22:38:58.573','1998-03-31','sociosempermissao@gmail.com','Socio Sem Permissao','$2a$10$lSCn3kc0Q9BidAARevjfweQ.rFDKHrLSWCobEIt7gRDPobD0B.KJG','MASCULINO','INATIVO',NULL,'2020-07-06 22:45:55.458'),
  (13,'289.993.010-95','2020-07-06 22:41:11.598','1998-03-31','funcionario2@gmail.com','Funcionário 2','$2a$10$gZpNr7M4lzXuUSfp3bc9R.V/oEvxyaiRAgNZ1eo0ULPt510Tz/cdS','MASCULINO','ATIVO',NULL,'2020-07-06 22:41:11.598'),
  (14,'202.431.510-04','2020-07-06 22:41:11.598','1998-03-31','funcionariosempermissao@gmail.com','Funcionário Sem Permissao','$2a$10$gZpNr7M4lzXuUSfp3bc9R.V/oEvxyaiRAgNZ1eo0ULPt510Tz/cdS','MASCULINO','ATIVO',NULL,'2020-07-06 22:41:11.598');

INSERT INTO usuario_permissao (fk_usuario,fk_permissao) VALUES
  (1,1),
  (2,2),
  (3,2),
  (6,3),
  (8,3),
  (10,4),
  (11,2),
  (12,3),
  (13,4),
  (14,4);