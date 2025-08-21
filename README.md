Claro, Cauê! Aqui está um modelo de README para o seu sistema de login com Java Spring Security e JWT, já contextualizado como parte da aplicação marketplace que você está desenvolvendo:

---

## 🔐 Sistema de Login com Spring Security + JWT

Este projeto implementa um sistema de autenticação e autorização utilizando **Java Spring Boot**, **Spring Security** e **JSON Web Tokens (JWT)**. Ele foi desenvolvido como **módulo de segurança** para a aplicação principal de marketplace, disponível no repositório [marketplace-with-security](https://github.com/cauelima1/marketplace-with-security.git) 🚀.

---

### 📦 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Maven
- JPA / Hibernate
- Banco de dados (MySQL ou H2)
- Lombok

---

### 📁 Estrutura do Projeto

```bash
src/
├── main/
│   ├── java/
│   │   └── com.example.security/
│   │       ├── config/          # Configurações de segurança
│   │       ├── controller/      # Endpoints de autenticação
│   │       ├── model/          # Entidades JPA
│   │       ├── repository/      # Repositórios
│   │       ├── service/         # Lógica de autenticação
│   │       └── SecurityApplication.java
│   └── resources/
│       └── application.properties
```

---

### 🔑 Funcionalidades

- Registro de usuários com criptografia de senha
- Autenticação via JWT
- Refresh Token (opcional)
- Proteção de endpoints com filtros personalizados
- Integração com o módulo marketplace via API REST

---

### 🚀 Como Executar

1. Clone o repositório:

```bash
git clone https://github.com/cauelima1/register-LoginSystem-javaSpringJWT.git
```

2. Configure o banco de dados em `application.properties`

3. Execute a aplicação:

```bash -> Dentro da raiz do projeto onde está localizado o pom, dê o comando Maven no prompt
mvn spring-boot:run
```

4. Teste os endpoints:

- `POST /auth/register` → Criação de usuário
- `POST /auth/login` → Retorna JWT

---

### 🔗 Integração com o Marketplace

Este sistema de login foi projetado para ser integrado diretamente ao projeto [marketplace-with-security](https://github.com/cauelima1/marketplace-with-security.git), oferecendo autenticação robusta e escalável para usuários e administradores da plataforma.

---

### 📄 Licença

Este projeto está sob a licença MIT. Sinta-se livre para usar, modificar e contribuir!

