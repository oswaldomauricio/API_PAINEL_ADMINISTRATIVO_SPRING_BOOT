-- =============================================================================
-- MIGRAÇÃO DA TABELA Caixa
-- Execute este script no banco PAINEL_ADMINISTRATIVO_DEV
-- =============================================================================

-- 1. Adiciona a coluna data_insercao (DATE) — data lógica do lançamento
ALTER TABLE Caixa
    ADD COLUMN data_insercao DATE NULL AFTER created_at;

-- 2. Preenche data_insercao com a data de created_at nos registros existentes
UPDATE Caixa
SET data_insercao = DATE(created_at)
WHERE data_insercao IS NULL;

-- 3. Torna a coluna NOT NULL após preencher os dados existentes
ALTER TABLE Caixa
    MODIFY COLUMN data_insercao DATE NOT NULL;

-- 4. Adiciona a coluna id_categoria (FK para a nova tabela categorias_caixa)
ALTER TABLE Caixa
    ADD COLUMN id_categoria BIGINT NULL AFTER data_insercao;

-- 5. Garante que a tabela categorias_caixa já existe antes (criada pelo Hibernate)
--    Preenche id_categoria nos registros existentes com base na coluna antiga 'categoria'
--    Ajuste os nomes conforme os dados existentes no seu banco.
UPDATE Caixa c
JOIN categorias_caixa cc ON UPPER(cc.nome) = UPPER(c.categoria)
SET c.id_categoria = cc.id
WHERE c.id_categoria IS NULL;

-- 6. Para registros que ainda não tiveram categoria mapeada, atribui "OUTROS"
UPDATE Caixa c
JOIN categorias_caixa cc ON cc.nome = 'OUTROS'
SET c.id_categoria = cc.id
WHERE c.id_categoria IS NULL;

-- 7. Torna id_categoria NOT NULL e adiciona FK
ALTER TABLE Caixa
    MODIFY COLUMN id_categoria BIGINT NOT NULL;

ALTER TABLE Caixa
    ADD CONSTRAINT fk_caixa_categoria
    FOREIGN KEY (id_categoria) REFERENCES categorias_caixa(id);

-- 8. (Opcional) Remove a coluna antiga 'categoria' (ENUM) após validar os dados
-- ALTER TABLE Caixa DROP COLUMN categoria;

-- =============================================================================
-- VERIFICAÇÃO
-- =============================================================================
-- SELECT id, data_insercao, id_categoria, categoria FROM Caixa LIMIT 10;
