# JunoChallenge

Esse é o desafio que aplicamos para candidatos Android!

Você deve elaborar um projeto Android que faça buscas de repositórios no Github com as seguintes especificações:

- Exibir um campo de busca para que o usuário digite o termo a ser buscado;
- Executar as consultas à API do Github no seguinte endpoint: https://api.github.com/search/repositories?q=[termo]
- Exibir os resultados da busca em forma de listagem (resumidamente);
- Ao abrir um item do resultado da busca, exibir mais informações daquele item;
- Persistir os resultados localmente a fim de que sejam acessíveis offline;
- Implementar testes unitários.

## Bônus (Opcional)

Implementar paginação (parâmetros page e per_page, ex:
https://api.github.com/search/repositories?q=android&per_page=100&page=2

- Você é livre para decidir o layout, mas ele deve ser responsivo.
- Linguagem: Kotlin ou Java

## Entrega
- Ao final, versionar o código no GitHub (ou na plataforma de sua preferência) e nos enviar o link por aqui.

# Implementação

Eis, passo-a-passo, as 24 tarefas que implementei até ter o aplicativo finalizado:

- Criar o repositório no Github.
- Incluir dependências no Gradle: Dagger2, Retrofit, RecyclerView, dentre outras.
- Personalizar arquivo gradle.properties.
- Implantar padrão MVP.
- Importar arquivos para fazer chamada básica de Retrofit.

- Adicionar Interceptors de Retrofit: Cache, NetworkInterceptor e OfflineInterceptor.
- Adicionar Timber.
- Testar chamada de Retrofit básica usando API do Github.
- Mudar ícone do aplicativo.
- Criar testes instrumentados usando Espresso.

- Criar testes instrumentados usando MockWebServer.
- Implantar RxJava nas chamadas de Retrofit.
- Inserir resultado do Retrofit em um RecyclerView.
- Adicionar NavigationUtils.
- Adicionar Loading antes de retornar dados.

- Adicionar CardView aos Adapters.
- Atualizar método de checar se há Internet.
- Implementar SearchView na Toolbar.
- Criar tela de Detalhes.
- Criar placeholders para resposta vazia do Retrofit, e para quando não houver conexão.

- Setar cor do cursor do SearchView para cor Branca.
- Implementar onSavedInstanceState.
- Criar layout Landscape da tela de Detalhes.
- Persistir os resultados localmente.
