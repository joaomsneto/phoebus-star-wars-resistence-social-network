# Documentação API Star Wars Resistence Social Network

## Endpoints

#### Criar um novo rebelde

##### Requisição
+ Parâmetros
    + nome: `"Teste"` - String
    + idade: `18` - Inteiro
    + genero: `"m"` - Opções possíveis: `"m" ou "f"`
    + latitude: `23.45` - Double
    + longitude: `42.23` - Double
    + nomeBase: `"Tatooine"` - String
    + itens: `[1, 2, 3, 4]` - Opções possíveis: verifique na listagem de itens
```
POST /rebelde
```

##### Resposta
* Código: 200

#### Listar os itens disponiveis

##### Requisição
```
GET /item
```

##### Resposta
* Código: 200
* Conteúdo: `[{
                 "id": 1,
                 "nome": "Arma",
                 "valor": 4
             }]`

#### Atualizar a localização de um rebelde

##### Requisição
+ Parâmetros
    + latitude: `213.11` - Double
    + longitude: `98.24` - Double
    + nomeBase: `"Hoth"` - String
```
PATCH /rebelde/{id}
```

##### Resposta
* Código: 200

#### Delatar um rebelde como traidor

##### Requisição

```
POST /rebelde/{delatorId}/delatar/{traidorId}
```

##### Resposta
* Código: 200
* Conteúdo: `{
                 "response": "Traidor delatado com sucesso"
             }`
             
#### Negociar itens de um rebelde com outro rebelde

##### Requisição
+ Parâmetros
    + itens_negociante: `[2,4]` - Opções possíveis: verifique na listagem de itens
    + itens_cliente: `[1,3]` - Opções possíveis: verifique na listagem de itens
```
POST /rebelde/{negocianteId}/trocar/{clienteId}
```

##### Resposta
* Código: 200
* Conteúdo: `{
                 "response": "Negociação efetuada com sucesso"
             }`

#### Porcentagem de traidores

##### Requisição

```
GET /relatorio/traidores
```

##### Resposta
* Código: 200
* Conteúdo: `{
                 "response": 50
             }`
             
#### Porcentagem de rebeldes

##### Requisição

```
GET /relatorio/rebeldes
```

##### Resposta
* Código: 200
* Conteúdo: `{
                 "response": 50
             }`
             
#### Quantidade média de cada tipo de recurso por rebelde

##### Requisição

```
GET /relatorio/media_itens_rebeldes
```

##### Resposta
* Código: 200
* Conteúdo: `{
                 "response": [[
        "Arma",
        0.5
    ],
    [
        "Munição",
        0.5
    ],
    [
        "Comida",
        2.25
    ]]
             }`
#### Quantidade de pontos perdidos devido a traidores

##### Requisição

```
GET /relatorio/pontos_perdidos_traidores
```

##### Resposta
* Código: 200
* Conteúdo: `{
                 "response": 45
             }`
