INSERT INTO usuarios(nome_de_usuario, senha) VALUES('cliente', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq');
INSERT INTO usuarios(nome_de_usuario, senha) VALUES('admin', '$2a$10$sFKmbxbG4ryhwPNx/l3pgOJSt.fW1z6YcUnuE2X8APA/Z3NI/oSpq');

INSERT INTO perfis(id, nome) VALUES(1, 'ROLE_CLIENTE');
INSERT INTO perfis(id, nome) VALUES(2, 'ROLE_ADMIN');

INSERT INTO usuarios_perfis(id_usuario, id_perfil) VALUES('cliente', 1);
INSERT INTO usuarios_perfis(id_usuario, id_perfil) VALUES('admin', 2);