## 🛒  API — Simulador de Marketplace

### 📌 Visão Geral

Esta API permite que usuários cadastrem produtos, abasteçam o estoque, criem pedidos, adicionem ou removam itens, finalizem pedidos e atualizem seus saldos com base nas compras realizadas.

---

📥 1. Clonar os projetos necessários
Sua aplicação depende de duas APIs que serão executadas juntas via Docker:
🔹 API Principal (Marketplace)
git clone https://github.com/seu-usuario/marketplace.git
cd marketplace


🔹 API de Autenticação (JWT)
Em paralelo, clone também a API de autenticação:
git clone https://github.com/cauelima1/RegisterAndLoginSystem-javaSpringJWT.git


Essa API será responsável por autenticar os usuários e deve estar configurada para subir junto com o docker-compose.




## 🔐 Autenticação

> Para simplificação, esta versão não inclui autenticação. Em versões futuras, recomenda-se usar JWT ou OAuth2.
> Para o uso correto desta aplicação, é necessário efetuar um registro de usuário e um login, através dessa api: 
https://github.com/cauelima1/RegisterAndLoginSystem-javaSpringJWT.git



Perfeito, Cauê! Com base nas suas classes `Controller`, aqui está a documentação atualizada dos **endpoints da sua API de marketplace**, organizada por recurso e refletindo fielmente a estrutura dos seus métodos:

---

## 📘 Documentação da API — Simulador de Marketplace

### 🔐 Autenticação
> A autenticação é baseada no login do usuário, obtido via `clientService.getLogin()`.

---

## 👤 Cliente

### `GET /client`
Retorna o nome do usuário autenticado.

**Resposta:**
```text
Authenticated user: nomeDoUsuario
```

### `POST /client/deposit/{value}`
Realiza depósito no saldo do cliente. Se for o primeiro acesso, cria o cliente com saldo inicial.

**Parâmetro:**
- `value` (Double): valor a ser depositado

**Resposta:**
```json
{
  "name": "caue",
  "balance": 1000.0
}
```

---

## 📦 Produtos e Estoque

### `POST /market/addProduct`
Cadastra um novo produto.

**Body (ProductDTO):**
```json
{
  "name": "Fone Bluetooth",
  "description": "Fone sem fio com cancelamento de ruído",
  "price": 199.90,
  "quantity": 10
}
```

### `GET /market/products`
Lista todos os produtos disponíveis.

### `DELETE /market/deleteProduct/{idProduct}`
Remove um produto do sistema.

**Parâmetro:**
- `idProduct` (Long): ID do produto

### `DELETE /market/removeQuantity/{idProduct}`
Remove uma quantidade específica de um produto.

**Body (QuantDeleteDTO):**
```json
{
  "quantity": 3
}
```

---

## 🛍️ Pedidos

### `POST /order/{idProduct}`
Adiciona um item ao pedido do cliente.

**Parâmetro:**
- `idProduct` (Long): ID do produto

**Body (OrderDtoRequest):**
```json
{
  "quantity": 2
}
```

### `GET /order/all`
Lista todos os pedidos do cliente autenticado.

### `DELETE /order/{orderId}`
Cancela um pedido em aberto.

**Parâmetro:**
- `orderId` (Long): ID do pedido

### `DELETE /order/item/{orderId}`
Remove um item específico de um pedido.

**Body (ItemDeleteDTORequest):**
```json
{
  "productId": 1
}
```

---

## ✅ Finalizar Pedido

### `GET /order/finishOrder/{orderId}`
Finaliza o pedido. O sistema:
- Verifica saldo e estoque
- Calcula valor total
- Atualiza saldo do cliente
- Deduz estoque dos produtos

**Resposta:**
```json
{
  "deliveryId": 123,
  "status": "finalizado",
  "totalValue": 399.80
}
```

---

## 🚚 Entregas

### `GET /delivery`
Lista todas as entregas registradas.

---

✅ Tecnologias Utilizadas

Java 17+ 
Spring Boot
Spring Data JPA
Hibernate
PostgreSQL / H2
Docker
Docker Compose
Maven
Lombok
Java Servlet API
JWT (JSON Web Token)
Swagger 




