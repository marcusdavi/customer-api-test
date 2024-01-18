# Customer Service

### Requisitos

1. JDK 11
1. Maven 3

### Como Rodar
1. Clone o projeto
2. Importe como Maven Project na sua IDE favorita
3. Faça requisiçoes aos endpoints via swagger, browser (gets) ou algum aplicativo que faça todos os tipos de requisições (postman, insomnia etc)

### Swagger
Foi criada uma página no swagger para melhorar a documentação, bem como facilitar os testes sem necessidade de uma ferramenta a parte. Após rodar a aplicação acesse: 
http://localhost:8080/swagger-ui.html

### Endpoints
* Apesar de ter disponibilizado o swagger, também detalho aqui um pouco sobre os endpoints disponibilizados

1. Listar Todos os clientes (Consulta paginada)
    * Foi criada uma consulta podendo ser parametrizada a paginação e a ordenação. Se nada for informado a página é a primeira (valor 0) e possui tamanho 10. A ordenação default é pelo campo name em direção ascendente.

    * Exemplo com paginação: 
        * GET http://localhost:8080/customers?pageNumber=0&pageSize=10
    * Exemplo com paginação e ordenação pelo nome na direção descendente:
        * GET http://localhost:8080/customers?pageNumber=0&pageSize=10&sort=name,desc


2. Consultar Clientes pelo id(GET)
    * GET http://localhost:8080/customers/{id}

        * onde {id} é o identificador do cliente

3. Consultar Clientes pelos campos(GET)

    * Foi criada apenas um único endpoint para a busca por nome, email e/ou genero. A consulta foi paginada. Para nome e email o filtro verifica se parte do nome ou email está presente no banco de dados. Já o gênero tem que ser exatamente igual.

    * Para buscar por nome: 
        * GET http://localhost:8080/customers/search?name=value

    * Para buscar por email:
        * GET http://localhost:8080/customers/search?gender=value
    
    * Para buscar por gênero:
        * GET http://localhost:8080/customers/search?gender=value

    * Para buscar por 2 ou mais campos, basta separá-los por &, exemplo:

        * Nome, email e gênero: 
            * GET http://localhost:8080/customers/search?name=value1&gender=value2&email=value3
        * Nome e gênero:
            * GET http://localhost:8080/customers/search?name=value1&gender=value2

4. Criar um novo Cliente
    * POST http://localhost:8080/customers/

        * Request Body
            ```
            {
            "email": "joao@gmail.com",
            "gender": "M",
            "name": "Joao"
            }
            ```
        * Exemplo: Response Body
            * Status: 201 (Created)
            ```
                {
                "id" 6,
                "email": "joao@gmail.com",
                "gender": "M",
                "name": "Joao"
                }
            ```

5. Editar novo Cliente
    * PUT http://localhost:8080/customers/6

        * Request Body
            ```
            {
              "email": "joao@gmail.com",
              "gender": "M",
              "name": "Joao Santos"
            }
            ```
        * Exemplo: Response Body
            * Status: 200 (Ok)
            ```
            {
              "id" 6,
              "email": "joao@gmail.com",
              "gender": "M",
              "name": "Joao Santos"
            }
            ```

6. Deletar Cliente
    * DELETE http://localhost:8080/customers/6
        * Response: 204 (No Content)

7. Consultar Cliente por Uf e Localidade
    * Para buscar por UF: 
        * GET http://localhost:8080/customers/search-address?uf=value

    * Para buscar por email:
        * GET http://localhost:8080/customers/search-address?localidade=value
    
    * Para buscar por gênero:
        * GET http://localhost:8080/customers/search-address?uf=value&localidade=value

8. Adicionar Endereços para o cliente
    * O endpoint foi criado para que seja passado uma lista de números de cep como entrada. A partir daí se todos os ceps forem válidos o cliente terá todos os endereços acrescentados ao seu cadastro.

    * POST http://localhost:8080/addresses/{customerId}, onde customerID é o id do customer que terá os endereços adicionados.

    * Request Body
        ```
        { 
            "ceps": [45345000, 41830260]
        }
        ```
                      
### Observações
* Response Body dos serviços
    * Como foi criado um swagger, então não coloquei neste documento
* Validações gerais
    * Gênero M ou F - Validador próprio criado
    * Email - email bem formado
    * Nome - Tamanho mínimo 2
    * Verificação se o Cliente existe ou não
    * Verificação se o Endereço existe ou não
    * Se um dos ceps informados não possuir endereço nenhum endereço da requisição é cadastrado para o Cliente.
* Tratamento das Exceções
    * Foi criado um ControlerAdvice para tratar as exceções. Foram criados duas exceções personalizadas e um tratamento específico para a exceção na validação dos campos dos requests.
* Testes Unitários
    * Foram criados testes unitários para alguns cenários do CustomerService
* Sobre as consultas
    * Não foi criado endpoint para cada campo a ser consultado. Existe um endpoint que pode ser consultado nome, email e/ou gênero.
        * Usei criteria nesta primeira consulta. Facilita quando o número de campos pode crescer e ficar muito grande
    * Um segundo endpoint foi criado para consultar por UF e Localidade
        * Como eram só dois campos fiz uma verificação no service para indicar qual consulta será feita ao banco
* Não foram implementadas autenticação e autorização para esta API

