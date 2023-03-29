CREATE TABLE IF NOT EXISTS items (
   id SERIAL PRIMARY KEY,
   name TEXT,
   created TIMESTAMP
);

COMMENT ON TABLE items IS 'Заявки';
COMMENT ON COLUMN items.id IS 'Идентификатор заявки';
COMMENT ON COLUMN items.name IS 'Наименование заявки';