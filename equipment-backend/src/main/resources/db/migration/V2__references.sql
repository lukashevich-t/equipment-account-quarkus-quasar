INSERT INTO `equipment_state`
VALUES (1, 'Новый'),
       (2, 'В эксплуатации'),
       (3, 'В резерве'),
       (4, 'В ремонте'),
       (5, 'Списан'),
       (6, 'state0.6415836732604231'),
       (7, 'state 0.30081789597396824'),
       (8, 'ttt'),
       (9, 'yyy');
INSERT INTO `equipment_type`
VALUES (7, 'Notebook'),
       (9, 'type 0.13426956922414446'),
       (8, 'type0.5021845515835421'),
       (3, 'МФУ'),
       (2, 'Ноутбук'),
       (1, 'Системный блок');
INSERT INTO `responsible_person`
VALUES (1, 'Коско Александр Николаевич'),
       (2, 'Бурая Наталья Владимировна'),
       (3, 'Лукашевич Тимофей Викентьевич'),
       (4, 'person0.9312200988162168'),
       (5, 'person0.8335635554693495');

INSERT INTO `action`
VALUES (0, 'Нет действия'),
       (1, 'Создание'),
       (2, 'Изменение'),
       (3, 'Обслуживание'),
       (4, 'Списание');
INSERT INTO `equipment`
VALUES (0x2CC70FEA0F0720489D59643F70FE0572, 2, 4, 2, 'Comment 0.4079442887152863', '0.3268372732069208', '2021-01-01',
        'serial0.24091852119561696'),
       (0x31323300000000000000000000000000, 1, 1, 1, 'comment1', 'inv1', '2022-08-04', 'serial1'),
       (0x31323400000000000000000000000000, 2, 2, 2, 'comment2', 'inv2', '2022-08-04', 'serial2'),
       (0x3D975515B9ED4C44A7328A4944906C1A, 1, 1, 1, 'Comment 0.8753092258167107', '0.5448845352387712', NULL,
        'serial0.6887578006370947'),
       (0x8618D95303DF2F4B8F13E4C5B3A5C865, 1, 1, 1, 'Comment 0.1624616671784156', '0.7467416400324167', NULL,
        'serial0.12009278214293895'),
       (0xF082A39E2303F241BDAE2A5E6D9B69E1, 3, 3, 1, 'comment11', 'инве11', NULL, NULL),
       (0xF082A39E2303F241BDAE2A5E6D9B69E2, 3, 3, 1, 'comment11', 'инве12', '2021-12-26', '121212');

INSERT INTO `role`
VALUES (1, 'user'),
       (2, 'odmin');
INSERT INTO `user`
VALUES (1, 'tim', 'CN=tim,OU=UIST,O=BTO,L=Minsk,C=BY', 'tim', 0x01, '5ee0edb9e2229c0838f1959779f1949031de0123'),
       (2, 'kan', 'CN=kan,OU=UIST,O=BTO,L=Minsk,C=BY', 'kan', 0x01, '5ee0edb9e2229c0838f1959779f1949031de0123');
INSERT INTO `user_role`
VALUES (1, 1),
       (1, 2);
