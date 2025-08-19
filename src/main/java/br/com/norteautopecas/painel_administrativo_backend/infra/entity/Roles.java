package br.com.norteautopecas.painel_administrativo_backend.infra.entity;

public enum Roles {
    USER("ROLE_USER"),

    ADMIN("ROLE_ADMIN"),

    USER_COMPRAS("ROLE_USER_COMPRAS"),

    USER_VENDAS("ROLE_USER_VENDAS"),

    USER_FINANCEIRO("ROLE_USER_FINANCEIRO"),

    USER_ESTOQUE("ROLE_USER_ESTOQUE"),

    USER_TI("ROLE_USER_TI"),

    USER_GERENTE("ROLE_USER_GERENTE"),

    USER_DIRETOR("ROLE_USER_DIRETOR"),

    USER_SUPORTE("ROLE_USER_SUPORTE"),

    ADMIN_COMPRAS("ROLE_ADMIN_COMPRAS"),

    ADMIN_VENDAS("ROLE_ADMIN_VENDAS"),

    ADMIN_FINANCEIRO("ROLE_ADMIN_FINANCEIRO"),

    ADMIN_ESTOQUE("ROLE_ADMIN_ESTOQUE"),

    ADMIN_TI("ROLE_ADMIN_TI"),

    ADMIN_GERENTE("ROLE_ADMIN_GERENTE"),

    ADMIN_DIRETOR("ROLE_ADMIN_DIRETOR"),

    ADMIN_SUPORTE("ROLE_ADMIN_SUPORTE"),

    REGIONAL("ROLE_REGIONAL"),

    GERENTE("ROLE_GERENTE");


    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
