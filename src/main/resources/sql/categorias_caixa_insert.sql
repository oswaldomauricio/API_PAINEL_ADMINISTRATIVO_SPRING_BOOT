-- =============================================================================
-- Script de inicialização das Categorias do Caixa
-- Execute este script depois que a aplicação criar a tabela categorias_caixa
-- =============================================================================

-- Limpa registros existentes (opcional - comente se não quiser apagar)
-- DELETE FROM categorias_caixa;

-- Insere as categorias padrão
INSERT INTO categorias_caixa (nome, ativo, created_at)
VALUES
    ('CTE', true, NOW()),
    ('TN', true, NOW()),
    ('DEVOLUCAO', true, NOW()),
    ('DEPOSITO PROSSEGUR', true, NOW()),
    ('RECEBIMENTO DE DUPLICATA', true, NOW()),
    ('OUTROS', true, NOW())
ON DUPLICATE KEY UPDATE updated_at = NOW();

-- Você pode inserir novas categorias a qualquer momento com:
-- INSERT INTO categorias_caixa (nome, ativo, created_at) VALUES ('NOVA CATEGORIA', true, NOW());

-- Para desativar uma categoria sem apagar:
-- UPDATE categorias_caixa SET ativo = false, updated_at = NOW() WHERE nome = 'CATEGORIA_A_DESATIVAR';
