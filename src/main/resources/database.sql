INSERT INTO mc_user (
    email,
    password
) VALUES ('random.raj93@gbmail.com', 'Test@123');

INSERT INTO mc_user (
    email,
    password
) VALUES ('random.raj93@gbmail.com', '$2b$12$KIXQJQwQb6QwQb6QwQb6QeQb6QwQb6QwQb6QwQb6QwQb6QwQb6Qe'); -- bcrypt hash of 'Test@123'

INSERT INTO mc_user (
    email,
    password
) VALUES ('second-random.user@gmail.com', '$2b$12$JHkQJHkQJHkQJHkQJHkQJHkQJHkQJHkQJHkQJHkQJHkQJHkQJHk'); -- bcrypt hash of 'Testable@123'