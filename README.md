# ProxWork

## Descrição do Projeto
O trabalho final do curso Jovem Programador, idealizado e criado durante o mesmo. Trata-se de um MVP de um sistema onde prestadores de serviço podem se cadastrar e ser encontrados por pessoas os procuram, direcionado para trabalhadores mais modestos. Esse MVP conta com a possibilidade de prestadores e clientes criarem suas contas, autenticação de login, pesquisa e filtragem de prestadores.

## Tecnologias Utilizadas
- HTML5
- CSS3
- TypesScript
- React
- Java
- SpringBoot
- PostgreSQL

## Como Executar
- Requisitos:
  - Node.js: v20.20.0
  - npm: 11.10.0
  - PostgreSql: 17.4
  - Vscode
- Passo-a-passo
  - Após extrair a pasta:
  - Configure o banco de dados
    - Acesse o pgAdmin (PostgreSQL)
    - Crie um banco com o nome `dbproxwork`
  - Execute o back-end
    - Abra a pasta `proxwork` com o vscode
    - Acesse o arquivo `application.properties`
    - Troque o texto `[SUA-SENHA]` pela sua senha do PostgreSQL
    - Execute o seguinte comando no terminal: `.\mvnw spring-boot:run`
    - Troque:
      - ```
          spring.sql.init.mode=always
          # spring.sql.init.mode=never
        ```
    - Por:
      - ```
          # spring.sql.init.mode=always
          spring.sql.init.mode=never
        ```
    - Deixe o projeto rodando
  - Execute o front-end
    - Em uma nova nova janela do Vscode abra a pasta `Acoes`
    - Digite o seguinte comando e aguarde a instalação: `npm install`
    - Execute o comando: `npm run dev`
  - Acesse o link indicado
