-- ======================================================
-- V2 – Seeds iniciais: trilhas, fases, quiz, medalhas
-- ======================================================

-- Trails
INSERT INTO trails (id, title, description, category, order_index, active) VALUES
('tr-hidr-0001-0001-000000000001', 'Hidratação Saudável',    'Aprenda sobre a importância de se manter hidratado diariamente.', 'HIDRATACAO',   1, TRUE),
('tr-alim-0002-0002-000000000002', 'Alimentação Saudável',   'Descubra como uma alimentação equilibrada transforma sua saúde.',  'ALIMENTACAO',  2, TRUE),
('tr-sono-0003-0003-000000000003', 'Qualidade do Sono',      'Entenda como o sono impacta sua saúde física e mental.',          'SONO',         3, TRUE),
('tr-ment-0004-0004-000000000004', 'Saúde Mental',           'Estratégias e conhecimentos para cuidar da sua mente.',           'SAUDE_MENTAL', 4, TRUE),
('tr-exer-0005-0005-000000000005', 'Exercícios Físicos',     'Como incorporar atividade física na sua rotina de forma segura.', 'EXERCICIO',    5, TRUE);

-- Phases – Trail Hidratação
INSERT INTO phases (id, trail_id, title, content, xp_reward, order_index) VALUES
('ph-hidr-001', 'tr-hidr-0001-0001-000000000001',
 'Por que se hidratar?',
 'A água representa cerca de 60% do corpo humano e é essencial para todas as funções vitais, incluindo regulação da temperatura, transporte de nutrientes e eliminação de toxinas. A desidratação, mesmo leve (1-2%), já causa queda de concentração, fadiga e dores de cabeça. Recomenda-se ingerir em média 2 litros de água por dia, podendo variar conforme peso, atividade física e clima.',
 15, 1),
('ph-hidr-002', 'tr-hidr-0001-0001-000000000001',
 'Sinais de desidratação',
 'Sede intensa, urina escura, boca seca, tontura e cansaço são sinais de alerta. Para monitorar sua hidratação, observe a cor da urina: amarelo pálido indica boa hidratação, enquanto amarelo escuro indica necessidade de beber mais água. Frutas e vegetais com alto teor de água, como melancia, pepino e laranja, também contribuem para a hidratação diária.',
 15, 2);

-- Phase – Trail Alimentação
INSERT INTO phases (id, trail_id, title, content, xp_reward, order_index) VALUES
('ph-alim-001', 'tr-alim-0002-0002-000000000002',
 'Grupos alimentares',
 'Uma alimentação saudável é baseada na variedade e equilíbrio. Os principais grupos são: carboidratos (fonte de energia), proteínas (construção muscular), gorduras boas (funções hormonais), vitaminas e minerais (imunidade). O Guia Alimentar para a População Brasileira recomenda priorizar alimentos in natura e minimamente processados, reduzindo ultraprocessados.',
 15, 1);

-- Quiz – Phase Hidratação 1
INSERT INTO quizzes (id, phase_id, title, xp_reward) VALUES
('qz-hidr-001', 'ph-hidr-001', 'Quiz: Fundamentos da Hidratação', 25);

INSERT INTO questions (id, quiz_id, statement, option_a, option_b, option_c, option_d, correct_option, order_index) VALUES
('qq-h001-1', 'qz-hidr-001',
 'Qual percentual aproximado do corpo humano adulto é composto por água?',
 '30%', '45%', '60%', '80%', 'C', 1),
('qq-h001-2', 'qz-hidr-001',
 'Qual é a quantidade mínima diária de água recomendada para um adulto médio?',
 '500 ml', '1 litro', '2 litros', '4 litros', 'C', 2),
('qq-h001-3', 'qz-hidr-001',
 'Qual cor de urina indica boa hidratação?',
 'Amarelo escuro', 'Amarelo pálido', 'Laranja', 'Incolor total', 'B', 3),
('qq-h001-4', 'qz-hidr-001',
 'Qual dos sintomas abaixo NÃO está associado à desidratação leve?',
 'Fadiga', 'Dor de cabeça', 'Febre alta', 'Dificuldade de concentração', 'C', 4),
('qq-h001-5', 'qz-hidr-001',
 'Qual alimento possui alto teor de água e contribui para a hidratação?',
 'Pão branco', 'Queijo parmesão', 'Pepino', 'Carne seca', 'C', 5);

-- Badges
INSERT INTO badges (id, title, description, criteria) VALUES
('bg-0001', 'Primeira Fase',        'Completou sua primeira fase no aplicativo.',                     'Completar 1 fase'),
('bg-0002', 'Hidratado!',           'Registrou consumo de água por 7 dias consecutivos.',             'Registrar água por 7 dias'),
('bg-0003', 'Quiz Master',          'Acertou 100% de um quiz.',                                      'Score perfeito em quiz'),
('bg-0004', 'Nível 5',              'Alcançou o nível 5.',                                           'Atingir level >= 5'),
('bg-0005', 'Trilha Completa',      'Concluiu todas as fases de uma trilha.',                         'Completar trilha inteira'),
('bg-0006', 'Dedicado',             'Completou 10 fases no total.',                                  'Completar 10 fases'),
('bg-0007', '2 Litros por Dia',     'Atingiu sua meta diária de hidratação por 3 dias seguidos.',     'Meta de água 3 dias'),
('bg-0008', 'Explorador',           'Iniciou fases em pelo menos 3 trilhas diferentes.',              'Iniciar 3 trilhas');
