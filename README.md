# MyAddressBook Front-end

## :book: Resumo do Projeto
Esse é o projeto de uma Agenda, na qual você pode gerenciar os seus contatos e, futuramente, poderá anotar afazares do dia para não esquecer.

## :bulb: Funcionalidades
* Buscar todos os contatos cadastrados
* Buscar contatos por nome
* Cadastar novos contatos em sua lista
* Editar dados de um contato da sua lista
* Remover um contato da sua lista

## :toolbox: Tecnologias

- `HTML`
- `CSS`
- `BootStrap`
- `Vue JS 3`
- `Spectre CSS`
- `PrimeVue`

## Project setup
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
```

### Lints and fixes files
```
npm run lint
```

# MyAddressBook Back-end

## :bulb: Funcionalidades
### Lista de Contatos
* Busca paginada de todos os contatos cadastrados: GET - /contacts
* Busca paginada de contatos por nome: GET - /contacts?name=nome-desejado
* Cadastar novos contatos em sua lista: POST - /contacts
  ```json
    {
        "name" : "Nome",
        "email" : "email@email.com",
        "phoneNumber" : "8191234-5678"
    }
  ```
* Editar dados de um contato da sua lista por id: PUT - /contacts/{id}
  ```json
    {
        "name" : "Nome",
        "email" : "email@email.com",
        "phoneNumber" : "8191234-5678"
    }
  ```
* Remover um contato da sua lista por id: DELETE - /contacts/{id}

### Anotações de afazeres do dia
* Busca paginada de todas as anotações do dia vigente: GET - /notes
* Busca paginada de anotações por data: GET - /notes?date=2023-01-18
* Cadastar novas anotações em sua agenda: POST - /notes
  ```json
    {
        "text" : "Fazer feira",
        "date" : "2023-01-19"
    }
  ```
* Editar dados de uma anotação da sua agenda por id: PUT - /notes/{id}
  ```json
    {
        "text" : "Fazer feira",
        "date" : "2023-01-19"
    }
  ```
* Remover uma anotação da sua agenda por id: DELETE - /notes/{id}

## :toolbox: Tecnologias

- `Intellij`
- `Java 17`
- `Maven`
- `Spring Boot`
- `Spring Data JPA`
- `Spring Validation`
- `Swagger`
- `Banco de dados H2`
- `MySQL`
- `Mockito`
- `JUnit5`
- `Testes de unidade`
- `Testes de integração`
