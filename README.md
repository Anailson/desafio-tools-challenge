# ToolsChallenge - API de Pagamentos 💳

API REST desenvolvida em Java com Spring Boot para processamento de pagamentos com cartão de crédito.

---

##  Sobre o Projeto

Desafio técnico proposto pela **Tools**, onde o objetivo é implementar uma API de Pagamentos para um banco na área de cartões de crédito.

A API permite realizar pagamentos, consultar transações e realizar estornos, seguindo os conceitos REST com protocolo HTTP padrão.

---

##  Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 4.1.0**
- **Spring Data JPA**
- **Oracle Database 23c**
- **Hibernate 7**
- **Lombok**
- **JUnit 5**
- **Mockito**
- **Maven**

---

##  Arquitetura

O projeto segue os princípios de **Clean Architecture**, separando as responsabilidades em camadas:

```
src/
├── adapters/
│   ├── controller/       # Controllers REST
│   └── repository/       # Interface do repositório (porta)
├── application/
│   └── usecase/          # Regras de negócio
├── domain/
│   ├── enums/            # StatusTransacao, TipoPagamento
│   └── model/            # Entidades de domínio
└── infrastructure/
    └── persistence/
        ├── entity/       # Entidades JPA
        ├── mapper/       # Conversão Entity <-> Domain
        └── repository/   # Implementação do repositório
```

---
##  Documentação da API

A documentação interativa está disponível via Swagger UI após subir a aplicação:

 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## ⚙ Pré-requisitos

- Java 21+
- Maven 3.8+
- Docker e Docker Compose

---

##  Banco de Dados com Docker

O projeto utiliza **Oracle Database 23c** via Docker. Para subir o banco:

```bash
docker-compose up -d
```

O `docker-compose.yml` já está configurado na raiz do projeto:

```yaml
services:
  tools-challenge-db:
    image: gvenzl/oracle-free:23-slim
    container_name: tools-challenge-db
    ports:
      - "1521:1521"
    environment:
      ORACLE_PASSWORD: oracle
      APP_USER: app
      APP_USER_PASSWORD: app
    volumes:
      - oracle-data:/opt/oracle/oradata
    restart: unless-stopped

volumes:
  oracle-data:
```

### 🔌 Conexão no DBeaver

| Campo | Valor |
|---|---|
| Host | `localhost` |
| Porta | `1521` |
| Database | `FREEPDB1` |
| Usuário | `app` |
| Senha | `app` |


## ▶️ Como Executar

```bash
# Clone o repositório
git clone https://github.com/seu-usuario/ToolsChallenge.git

# Entre na pasta
cd ToolsChallenge

# Execute com Maven
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

---

##  Endpoints

### 💰 Realizar Pagamento
```http
POST /pagamentos
```

**Request:**
```json
{
  "transacao": {
    "cartao": "4444********1234",
    "id": "100023568900001",
    "descricao": {
      "valor": "500.50",
      "dataHora": "01/05/2021 18:30:00",
      "estabelecimento": "PetShop Mundo cão"
    },
    "formaPagamento": {
      "tipo": "AVISTA",
      "parcelas": "1"
    }
  }
}
```

**Response `201 Created`:**
```json
{
  "transacao": {
    "cartao": "4444********1234",
    "id": "100023568900001",
    "descricao": {
      "valor": "500.50",
      "dataHora": "01/05/2021 18:30:00",
      "estabelecimento": "PetShop Mundo cão",
      "nsu": "1234567890",
      "codigoAutorizacao": "147258369",
      "status": "AUTORIZADO"
    },
    "formaPagamento": {
      "tipo": "AVISTA",
      "parcelas": "1"
    }
  }
}
```

---

###  Consultar Todos os Pagamentos
```http
GET /pagamentos
```
**Response `200 OK`:** Lista de transações.

---

###  Consultar Pagamento por ID
```http  http://localhost:8080/pagamentos/100023568900005
GET /pagamentos/{id}
```
**Response `200 OK`:** Transação encontrada.

---

### Realizar Estorno
```http
PUT /pagamentos/{id}/estorno
```

**Response `200 OK`:**
```json
{
  "transacao": {
    "cartao": "4444********1234",
    "id": "100023568900001",
    "descricao": {
      "valor": "500.50",
      "dataHora": "01/05/2021 18:30:00",
      "estabelecimento": "PetShop Mundo cão",
      "nsu": "1234567890",
      "codigoAutorizacao": "147258369",
      "status": "CANCELADO"
    },
    "formaPagamento": {
      "tipo": "AVISTA",
      "parcelas": "1"
    }
  }
}
```

---

##  Regras de Negócio

| Regra | Descrição |
|---|---|
| ID único | Cada transação deve ter um ID único |
| Status | `AUTORIZADO`, `NEGADO`, `CANCELADO` |
| Tipo pagamento | `AVISTA`, `PARCELADO_LOJA`, `PARCELADO_EMISSOR` |
| À vista | Deve ter exatamente 1 parcela |
| Parcelado | Deve ter mais de 1 parcela |
| Estorno | Apenas transações `AUTORIZADO` podem ser estornadas |
| Estorno duplo | Transação já `CANCELADO` não pode ser estornada novamente |

---

##  Testes

Os testes unitários cobrem os **Use Cases** com Mockito, validando as regras de negócio de forma isolada.

```bash
# Executar os testes
mvn test
```

**Classes testadas:**
- `RealizarPagamentoUseCaseTest`
- `BuscarPagamentoUseCaseTest`
- `EstornarPagamentoUseCaseTest`

---

##  Melhorias Futuras

- Implementação de tratamento global de exceções utilizando `@ControllerAdvice`.
- Validação das requisições com Bean Validation (`@Valid`, `@NotNull`, `@Positive`, etc.).
- Implementação de testes de integração para validar o fluxo completo da API.
- Gerenciamento de migrações do banco de dados com Flyway.
- Adição de logs estruturados para facilitar monitoramento e auditoria.
- Coleta de métricas da aplicação utilizando Spring Boot Actuator, Micrometer e integração com Prometheus/Grafana para monitoramento em tempo real.

##  Autor

**Anailson**  
[GitHub](https://github.com/Anailson) • [LinkedIn](https://www.linkedin.com/in/anailson-ribeiro-257a0229/)
