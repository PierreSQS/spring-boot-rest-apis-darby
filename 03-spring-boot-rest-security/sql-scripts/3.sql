-- Drop tables if they exist
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

--
-- Table structure for table `system_users`
--

CREATE TABLE system_users (
  user_id VARCHAR(50) NOT NULL,
  password CHAR(68) NOT NULL,
  enabled BOOLEAN NOT NULL,
  PRIMARY KEY (user_id)
);

--
-- Inserting data for table `system_users`
--
-- The passwords are encrypted using BCrypt
-- A generation tool is available at: https://www.luv2code.com/generate-bcrypt-password
-- Default passwords here are: fun123
--

INSERT INTO system_users (user_id, password, enabled)
VALUES
('john', '{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q', TRUE),
('mary', '{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q', TRUE),
('susan', '{bcrypt}$2a$10$qeS0HEh7urweMojsnwNAR.vcXJeXR1UcMRZ2WcGQl9YeuspUdgF.q', TRUE);

--
-- Table structure for table `roles`
--

CREATE TABLE roles (
  user_id VARCHAR(50) NOT NULL,
  role VARCHAR(50) NOT NULL,
  UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES system_users (user_id)
);

--
-- Inserting data for table `roles`
--

INSERT INTO roles (user_id, role)
VALUES
('john', 'ROLE_EMPLOYEE'),
('mary', 'ROLE_EMPLOYEE'),
('mary', 'ROLE_MANAGER'),
('susan', 'ROLE_EMPLOYEE'),
('susan', 'ROLE_MANAGER'),
('susan', 'ROLE_ADMIN');