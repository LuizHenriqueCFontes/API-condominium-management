# 🏢 Condominium Management — Backend

API REST para gerenciamento de condomínios residenciais, desenvolvida com Java e Spring Boot. O sistema permite organizar condomínios, blocos, unidades habitacionais, moradores e usuários, com autenticação e controle de acesso.

## 📋 Sobre o projeto

O **Condominium Management** é um projeto acadêmico desenvolvido com o objetivo de aplicar conceitos de desenvolvimento backend, construção de APIs REST, persistência de dados relacionais, autenticação e organização de aplicações Java.

A aplicação utiliza uma arquitetura organizada por módulos, separando as responsabilidades de autenticação, regras de negócio e acesso aos dados.

## 🚀 Tecnologias utilizadas

- **Java** — linguagem de programação.
- **Spring Boot** — estrutura principal da aplicação.
- **Spring Security** — autenticação e segurança.
- **JWT (JSON Web Token)** — autenticação baseada em tokens.
- **Spring Data JPA** — persistência e acesso aos dados.
- **Hibernate** — implementação de ORM.
- **MySQL** — banco de dados relacional.
- **Flyway** — versionamento e gerenciamento das migrations do banco.
- **Maven** — gerenciamento de dependências e build.
- **Lombok** — redução de código repetitivo.

## 🏗️ Arquitetura e módulos

A API é organizada em módulos de domínio, facilitando a manutenção e a evolução do sistema.

| Módulo | Responsabilidade |
|---|---|
| `auth` | Cadastro, autenticação e validação de credenciais. |
| `condominiums` | Gerenciamento dos condomínios. |
| `blocks` | Gerenciamento dos blocos vinculados aos condomínios. |
| `units` | Gerenciamento das unidades habitacionais. |
| `residents` | Gerenciamento dos moradores e seus vínculos com as unidades. |

A estrutura interna utiliza componentes de serviços, repositórios, entidades e DTOs, conforme as responsabilidades de cada módulo.

### 🔐 Autenticação e segurança

A aplicação utiliza Spring Security integrado à autenticação baseada em JWT.

O fluxo de autenticação contempla:

1. Recebimento das credenciais de acesso.
2. Autenticação das credenciais por meio do Spring Security.
3. Utilização de senhas protegidas por hash com BCrypt.
4. Geração de um token JWT após a autenticação bem-sucedida.
5. Validação da autenticação para acesso aos recursos protegidos.

A entidade `User` implementa a interface `UserDetails`, integrando os dados do usuário ao mecanismo de autenticação do Spring Security. As permissões são associadas a uma entidade `Role`.

## 🗄️ Banco de dados

O projeto utiliza MySQL para persistência dos dados e Spring Data JPA com Hibernate para o mapeamento objeto-relacional.

O Flyway é utilizado para versionar as alterações do esquema do banco de dados por meio de migrations, permitindo manter a estrutura da base organizada entre diferentes ambientes.

## ⚙️ Como executar o projeto

### Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- Java JDK compatível com a versão configurada no projeto.
- Maven, ou utilize o Maven Wrapper disponibilizado no repositório.
- MySQL Server.
- Git.

### 1. Clone o repositório

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre no diretório do projeto:

```bash
cd condominium-management
```

### 2. Configure o banco de dados

Crie um banco de dados MySQL para a aplicação.

```sql
CREATE DATABASE condominium_management;
```

Configure as credenciais de acesso e a URL de conexão no arquivo `application.properties` ou `application.yml`, conforme a configuração utilizada pelo projeto.

Exemplo de configuração:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

Defina as variáveis de ambiente de acordo com o seu ambiente de execução.

> **Importante:** ajuste a URL de conexão para incluir o endereço, a porta e o nome do banco de dados. Não versione credenciais ou outros segredos no repositório.

### 3. Execute a aplicação

Caso o projeto possua Maven Wrapper no Windows:

```bash
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Alternativamente, com Maven instalado:

```bash
mvn spring-boot:run
```

Após a inicialização, a API estará disponível no endereço e na porta configurados na aplicação.

## 🧪 Testes

Os testes automatizados podem ser executados com Maven:

```bash
mvn test
```

Ou, utilizando o Maven Wrapper no Windows:

```bash
.\mvnw.cmd test
```

Os testes permitem verificar o comportamento dos componentes da aplicação e reduzir regressões durante a manutenção do código.

## 🛠️ Boas práticas aplicadas

- Separação de responsabilidades entre serviços, repositórios e controladores.
- Utilização de DTOs para estruturar os dados de entrada e saída da API.
- Persistência de dados com JPA e Hibernate.
- Gerenciamento versionado do esquema do banco de dados com Flyway.
- Proteção de senhas utilizando BCrypt.
- Autenticação baseada em JWT com Spring Security.
- Gerenciamento de dependências por meio do Maven.
- Organização modular para facilitar manutenção e evolução.

## 🎯 Objetivos de aprendizagem

Este projeto foi desenvolvido para consolidar conhecimentos em:

- Desenvolvimento de APIs REST com Java e Spring Boot.
- Modelagem de entidades e relacionamentos em bancos relacionais.
- Autenticação e autorização com Spring Security.
- Utilização de JWT para autenticação.
- Gerenciamento de migrations com Flyway.
- Organização de código e separação de responsabilidades.
- Testes automatizados e manutenção de aplicações backend.

## 👨‍💻 Autor

**Luiz Henrique Carvalho Fontes**

Projeto acadêmico de gerenciamento de condomínios, desenvolvido com foco em backend Java e desenvolvimento de APIs REST.

--
## Orientador
Professor Hudson Neves
