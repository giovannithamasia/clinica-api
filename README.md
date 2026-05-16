# API de Agendamento de Consultas 🏥

Uma API REST construída com Java e Spring Boot para gerenciar o cadastro de pacientes e o agendamento de consultas médicas. O sistema fornece operações CRUD completas com validação de dados e tratamento de erros, simulando o backend de um sistema de clínica real.

## 🚀 Tecnologias Utilizadas
- Java
- Spring Boot (Web, Data JPA)
- Jakarta Validation (para validação de dados de entrada)
- Maven (Gerenciamento de dependências)
- MySQL (banco de dados relacional)

## 🛠️ Arquitetura e Padrões
O projeto foi estruturado seguindo o padrão de camadas para garantir a separação de responsabilidades:

- **Controllers:** Camada de entrada que gerencia as requisições HTTP (GET, POST, PUT, DELETE).
- **Services:** Camada onde a lógica de negócio, regras e validações residem.
- **Repositories:** Comunicação direta com o banco de dados utilizando Spring Data JPA.
- **DTOs (Data Transfer Objects):** Padronização do tráfego de dados, garantindo segurança ao não expor diretamente as entidades do banco.
- **Entities:** Representação das tabelas do banco de dados com os devidos relacionamentos (Many-to-One entre Consulta e Paciente).
- **Exceptions:** Exceções customizadas para cada regra de negócio violada.

## ✨ Principais Funcionalidades
- **Gestão de Pacientes:** Cadastro, busca por e-mail, atualização e exclusão de pacientes.
- **Agendamento de Consultas:** Criação de consultas com tipo (ONLINE ou PRESENCIAL), status e vínculo com paciente.
- **Cancelamento de Consulta:** Rota dedicada para cancelar uma consulta pelo ID.
- **Validação de Regras de Negócio:**
    - Impede o cadastro de pacientes com e-mails duplicados.
    - Impede a exclusão de pacientes que possuem consultas vinculadas.
    - Impede conflitos de agendamento na mesma data.
    - Valida a existência do paciente antes de criar uma consulta.
- **Segurança de Credenciais:** As credenciais do banco de dados são lidas via variáveis de ambiente (`${DATABASE_USERNAME}` e `${DATABASE_PASSWORD}`), sem nenhuma senha exposta no código.
- **Respostas HTTP Padronizadas:** Uso correto dos status codes semânticos (200 OK, 201 Created, 404 Not Found, 409 Conflict).

## 🛣️ Endpoints da API

### 👤 Pacientes
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | `/paciente` | Cadastra um novo paciente |
| GET | `/paciente` | Lista todos os pacientes |
| GET | `/paciente/{email}` | Busca um paciente pelo e-mail |
| PUT | `/paciente/{email}` | Atualiza um paciente |
| DELETE | `/paciente/{email}` | Exclui um paciente |

### 📅 Consultas
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | `/consulta` | Agenda uma nova consulta |
| GET | `/consultas` | Lista todas as consultas |
| PUT | `/consulta/{id}` | Atualiza uma consulta |
| DELETE | `/consulta/{id}` | Exclui uma consulta |
| POST | `/consulta/cancelar/{id}` | Cancela uma consulta |

#### Valores aceitos:
- **Tipo:** `ONLINE` · `PRESENCIAL`
- **Status:** `AGENDADA` · `EM_ANDAMENTO` · `CONCLUIDA` · `CANCELADA`

## 🛡️ Exemplo de Corpo das Requisições

**Criar paciente:**
```json
{
  "nome": "João da Silva",
  "email": "joao.silva@email.com"
}
```

**Agendar consulta:**
```json
{
  "titulo": "Consulta de Rotina",
  "dataConsulta": "2026-06-10",
  "status": "AGENDADA",
  "tipo": "ONLINE",
  "emailPaciente": "joao.silva@email.com"
}
```

## 🗄️ Estrutura do banco de dados

**paciente**
- `id` (PK)
- `nome`
- `email` (UNIQUE)

**consulta**
- `id` (PK)
- `titulo`
- `data_consulta`
- `status`
- `tipo`
- `id_paciente` (FK → paciente.id)

## ⚙️ Pré-requisitos e Configuração

Este projeto utiliza o banco de dados **MySQL**. Antes de executar, certifique-se de:

1. Ter o MySQL rodando localmente na porta padrão (`3306`).
2. Criar um banco de dados vazio chamado `clinica_api`.
3. Configurar as seguintes **variáveis de ambiente** no seu sistema operacional ou na sua IDE de preferência:

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `DATABASE_USERNAME` | Seu usuário do MySQL | `root` |
| `DATABASE_PASSWORD` | Sua senha do MySQL | `sua_senha_aqui` |

*Nota: As tabelas (`paciente` e `consulta`) serão criadas automaticamente pelo Hibernate assim que a aplicação for iniciada pela primeira vez.*

## 🛠️ Como Executar

- Clonar o repositório git:
```bash
git clone https://github.com/giovannithamasia/clinica-api.git
```
- Construir o projeto:
```
$ ./mvnw clean package
```
- Executar a aplicação:
```
$ java -jar target/clinicaApi-0.0.1-SNAPSHOT.jar
```

A API poderá ser acessada em [localhost:8080](http://localhost:8080)

## 📁 Estrutura do Projeto

```text
src/main/java/com/senai/clinicaApi/
├── controllers/       # Endpoints da API
├── services/          # Regras de negócio
├── repositories/      # Acesso ao banco de dados
├── entities/          # Entidades JPA
├── dtos/              # Objetos de transferência de dados
└── exceptions/        # Exceções customizadas
```

## 👨‍💻 Autores

- **[Giovanni Thamasia](https://github.com/giovannithamasia)**
- **[Cleiton Bertoldi](https://github.com/cleiton0223)**
