INSERT INTO evento (nombre, descripcion, tipo_de_evento)
VALUES ('Obra Romeo y Julieta', 'Comedia romántica clásica', 'OBRA_DE_TEATRO'),
       ('Obra Hamlet', 'Tragedia clásica', 'OBRA_DE_TEATRO'),
       ('Recital Banda XYZ', 'Concierto en estadio local', 'RECITAL'),
       ('Recital Banda ABC', 'Concierto al aire libre', 'RECITAL'),
       ('Charla de IA', 'Conferencia sobre Inteligencia Artificial', 'CHARLA_CONFERENCIA'),
       ('Charla Motivacional', 'Tips de productividad', 'CHARLA_CONFERENCIA');

INSERT INTO funcion (fecha, hora, direccion, altura, localidad, provincia, evento_id)
VALUES ('2025-09-20', '20:00:00', 'Teatro Central', '10', 'Ciudad A', 'Provincia 1', 1),
       ('2025-09-21', '20:00:00', 'Teatro Central', '10', 'Ciudad A', 'Provincia 1', 1),
       ('2025-10-05', '19:30:00', 'Teatro Norte', '15', 'Ciudad B', 'Provincia 2', 2);


INSERT INTO funcion (fecha, hora, direccion, altura, localidad, provincia, evento_id)
VALUES ('2025-09-22', '21:00:00', 'Estadio Municipal', '100', 'Ciudad C', 'Provincia 3', 3),
       ('2025-09-23', '22:00:00', 'Estadio Municipal', '100', 'Ciudad C', 'Provincia 3', 3),
       ('2025-10-10', '20:00:00', 'Parque Central', '5', 'Ciudad D', 'Provincia 4', 4);


INSERT INTO funcion (fecha, hora, direccion, altura, localidad, provincia, evento_id)
VALUES ('2025-10-15', '14:00:00', 'Centro de Convenciones', '200', 'Ciudad E', 'Provincia 5', 5),
       ('2025-10-16', '15:00:00', 'Centro de Convenciones', '200', 'Ciudad E', 'Provincia 5', 5),
       ('2025-11-01', '10:00:00', 'Auditorio Principal', '50', 'Ciudad F', 'Provincia 6', 6);

INSERT INTO funcion (fecha, hora, direccion, altura, localidad, provincia, evento_id)
VALUES ('2025-09-20', '20:00:00', 'Teatro Central', '10', 'Ciudad A', 'Provincia 1', 1),
       ('2025-09-21', '20:00:00', 'Teatro Central', '10', 'Ciudad A', 'Provincia 1', 1),
       ('2025-10-05', '19:30:00', 'Teatro Norte', '15', 'Ciudad B', 'Provincia 2', 2);


INSERT INTO funcion (fecha, hora, direccion, altura, localidad, provincia, evento_id)
VALUES ('2025-09-22', '21:00:00', 'Estadio Municipal', '100', 'Ciudad C', 'Provincia 3', 3),
       ('2025-09-23', '22:00:00', 'Estadio Municipal', '100', 'Ciudad C', 'Provincia 3', 3),
       ('2025-10-10', '20:00:00', 'Parque Central', '5', 'Ciudad D', 'Provincia 4', 4);


INSERT INTO funcion (fecha, hora, direccion, altura, localidad, provincia, evento_id)
VALUES ('2025-10-15', '14:00:00', 'Centro de Convenciones', '200', 'Ciudad E', 'Provincia 5', 5),
       ('2025-10-16', '15:00:00', 'Centro de Convenciones', '200', 'Ciudad E', 'Provincia 5', 5),
       ('2025-11-01', '10:00:00', 'Auditorio Principal', '50', 'Ciudad F', 'Provincia 6', 6);

INSERT INTO disponibilidad (tipo_de_entrada, precio, cupos_totales, cupos_ocupados, funcion_id)
VALUES ('ENTRADA_GENERAL', 1000.00, 200, 0, 1),
       ('ENTRADA_VIP', 2500.00, 50, 0, 1),
       ('ENTRADA_GENERAL', 1000.00, 200, 0, 2),
       ('ENTRADA_VIP', 2500.00, 50, 0, 2),
       ('ENTRADA_GENERAL', 1200.00, 150, 0, 3),
       ('ENTRADA_VIP', 3000.00, 40, 0, 3);


INSERT INTO disponibilidad (tipo_de_entrada, precio, cupos_totales, cupos_ocupados, funcion_id)
VALUES ('CAMPO', 1500.00, 500, 0, 4),
       ('PLATEA', 2500.00, 200, 0, 4),
       ('PALCO', 4000.00, 50, 0, 4),
       ('CAMPO', 1600.00, 500, 0, 5),
       ('PLATEA', 2600.00, 200, 0, 5),
       ('PALCO', 4200.00, 50, 0, 5),
       ('CAMPO', 1800.00, 400, 0, 6),
       ('PLATEA', 2800.00, 150, 0, 6),
       ('PALCO', 4500.00, 30, 0, 6);


INSERT INTO disponibilidad (tipo_de_entrada, precio, cupos_totales, cupos_ocupados, funcion_id)
VALUES ('CON_MEET_AND_GREET', 8000.00, 10, 0, 7),
       ('SIN_MEET_AND_GREET', 2000.00, 100, 0, 7),
       ('CON_MEET_AND_GREET', 8500.00, 8, 0, 8),
       ('SIN_MEET_AND_GREET', 2200.00, 120, 0, 8),
       ('CON_MEET_AND_GREET', 9000.00, 5, 0, 9),
       ('SIN_MEET_AND_GREET', 2500.00, 80, 0, 9);
