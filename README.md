API REST para gerenciamento de produtos em estoque, desenvolvida como parte de um teste técnico para vaga de desenvolvedor backend pleno.

## 📋 Funcionalidades

- Cadastro de produtos com nome, quantidade e preço.
- Atualização de dados do produto.
- Remoção de produtos.
- Listagem de todos os produtos.
- Movimentação de produtos
- Consulta de lucro por produto

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database** (ambiente de testes)
- **Maven**
- **Swagger** (documentação)
- **JUnit e Mockito** (testes unitários)

## 🚀 Como executar o projeto

### Pré-requisitos

- Java 17 instalado
- Maven instalado

### Rodando localmente

Clone o repositório:
git clone https://github.com/Janderocha/estoque-api.git
cd estoque-api

./mvnw spring-boot:run
a aplicação estará disponível em http://localhost:8081

Para informação sobre os endpoints:
o Swagger estará disponível em :http://localhost:8081/swagger-ui/index.html
