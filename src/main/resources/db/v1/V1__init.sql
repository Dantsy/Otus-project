CREATE TABLE users
(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
);

CREATE TABLE otus-project
(
    id          serial PRIMARY KEY,
    title       text                     NOT NULL,
    description text,
    status      ENUM('New', 'In progress', 'Completed') NOT NULL DEFAULT 'New',
    due_date    timestamp with time zone,
    assigned_to uuid                     REFERENCES users (id) ON DELETE SET NULL,
    created_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_otus-project_status ON taskmanager (status);
CREATE INDEX idx_otus-project_due_date ON taskmanager (due_date);
CREATE INDEX idx_otus-project_assigned_to ON taskmanager (assigned_to);

CREATE
OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at
= CURRENT_TIMESTAMP;
RETURN NEW;
END;
$$
language 'plsql';

CREATE TRIGGER update_otus-project_updated_at
    BEFORE UPDATE
    ON taskmanager
    FOR EACH ROW EXECUTE PROCEDURE update_updated_at_column();