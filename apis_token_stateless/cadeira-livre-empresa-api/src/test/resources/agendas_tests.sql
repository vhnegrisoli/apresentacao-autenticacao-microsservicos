INSERT INTO dia_da_semana (id,dia,dia_nome,dia_codigo) VALUES
  (26,0,'Segunda-feira','SEGUNDA_FEIRA'),
  (27,1,'Terça-feira','TERCA_FEIRA'),
  (28,2,'Quarta-feira','QUARTA_FEIRA'),
  (29,3,'Quinta-feira','QUINTA_FEIRA'),
  (30,4,'Sexta-feira','SEXTA_FEIRA'),
  (31,5,'Sábado','SABADO'),
  (25,6,'Domingo','DOMINGO');

INSERT INTO horario (id,fk_empresa,horario,fk_dia_da_semana) VALUES
  (36,4,'17:30:00',26),
  (40,4,'14:30:00',25),
  (41,7,'13:30:00',25),
  (42,7,'11:30:00',26),
  (43,7,'05:30:00',27);

INSERT INTO servico (id, descricao, fk_empresa, preco) VALUES
  (1, 'Corte exclusivo', 4, 15.90),
  (2, 'Corte de cabelo', 7, 15.90),
  (3, 'Corte e lavagem', 4, 15.90),
  (4, 'Lavagem', 4, 15.90),
  (5, 'Corte e barba', 7, 15.90),
  (6, 'Barba', 7, 15.90),
  (7, 'Corte, barba e lavagem', 4, 15.90);

INSERT INTO agenda (id, fk_usuario, fk_empresa, fk_horario, horario_agendamento, situacao, tipo_agenda,
                    data_cadastro, desconto, cliente_id, cliente_nome, cliente_email, cliente_cpf, total_desconto, total_pagamento, total_servico, minutos_disponiveis) VALUES
  (1, null, 4, 36, '17:30:00', 'RESERVA', 'HORARIO_MARCADO', '2020-07-02 20:44:01.860', null, 1, 'Cleinte 01', 'cliente1@gmail.com', '096.283.520-08', 0.0, 0.0, 0.0, 30),
  (2, null, 4, 40, '14:30:00', 'RESERVA', 'HORARIO_MARCADO', '2020-07-02 20:44:01.860', null, 2, 'Cleinte 02', 'cliente2@gmail.com', '710.378.970-30', 0.0, 0.0, 0.0, 30),
  (3, null, 7, 41, '14:30:00', 'RESERVA', 'HORARIO_MARCADO', '2020-07-02 20:44:01.860', null, 3, 'Cleinte 03', 'cliente3@gmail.com', '294.129.200-40', 0.0, 0.0, 0.0, 30),
  (4, null, 7, 42, '14:30:00', 'RESERVA', 'HORARIO_MARCADO', '2020-07-02 20:44:01.860', null, 4, 'Cleinte 04', 'cliente4@gmail.com', '315.076.610-96', 0.0, 0.0, 0.0, 30),
  (5, null, 4, 36, '17:30:00', 'DISPONIVEL', 'CADEIRA_LIVRE', '2020-07-02 20:44:01.860', null, null, null, null, null, 0.0, 0.0, 0.0, 30),
  (6, null, 7, 41, '14:30:00', 'DISPONIVEL', 'CADEIRA_LIVRE', '2020-07-02 20:44:01.860', null, null, null, null, null, 0.0, 0.0, 0.0, 30);

INSERT INTO agenda_servicos (fk_agenda, fk_servico) VALUES
  (1, 1),
  (1, 3),
  (2, 3),
  (2, 4),
  (3, 2),
  (3, 5),
  (3, 6),
  (4, 2),
  (5, 4),
  (5, 7),
  (6, 2),
  (6, 6);