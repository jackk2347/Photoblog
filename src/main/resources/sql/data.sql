INSERT INTO users(username, password, description, tel, email) VALUES ('admin', '{noop}adminpw', 'This is admin', '99999999', 'admin@hkmu.edu.hk');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_USER');
INSERT INTO user_roles(username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO users(username, password, description, tel, email) VALUES ('admin1', '{noop}adminpw', 'This is admin1', '12345678', 'admin1@hkmu.edu.hk');
INSERT INTO user_roles(username, role) VALUES ('admin1', 'ROLE_ADMIN');
INSERT INTO users(username, password, description, tel, email) VALUES ('user', '{noop}userpw', 'This is user', '24681022', 'user@hkmu.edu.hk');
INSERT INTO user_roles(username, role) VALUES ('user', 'ROLE_USER');
