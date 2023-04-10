<body>
  <h1>Gerenciamento de clientes e endereços</h1>

  <p>Este projeto de API tem como objetivo gerenciar informações de clientes e endereços utilizando as tecnologias Java, JUnit, Spring Framework e H2.</p>

<h2>Observações</h2>
 <p>Foi adicionado ao projeto o arquivo Teste api desafio.postman_collection.json contendo os testes de todas as funções da classe controller</p>
 	<p>Optei por não criar o metodo de atualizar o endereço do cliente, ao inves disso fazer o processo de excluir o endereço e criar um novo</p>
 	<p>Na função updateCliente() da classe controller decidi não utilizar a anotação @Valid para caso queira atualizar somente um campo do cliente não precisar preencher todos os outros campos,na função  a validação será feita na função usada da classe clienteService</p>
	<p>A função getClienteEndereco(@PathVariable Long clienteId, @PathVariable Long enderecoId) da classe controller busca no banco de dados um endereco com id informado associado ao cliente, caso não haja, retorna o erro EndrecoNotFound ou ClienteNotFound se não tiver nenhum cliente com o id passado.
 </p>

  <h2>Funcionalidades</h2>

  <p>A API oferece as seguintes funcionalidades:</p>
	<ul>
    <li>Cadastro de clientes</li>
    <li>Cadastro de endereços para um cliente</li>
    <li>Consulta de clientes por id</li>
    <li>Consulta de endereços por id</li>
    <li>Consulta de todos os endereços de um cliente</li>
    <li>Consulta de todos os endereços no geral</li>
    <li>Consulta de todos os clientes</li>
    <li>Atualização de endereco principal</li>
		<li>Atualização de data de nascimento, nome ou endreço de clientes(pode ser atualizado um ou mais campos usando a mesma função)</li>
		<li>Exclusão de clientes</li>
		<li>Exclusão de endereços(pelo id do cliente+id do enredereço ou so com o id do endereco)</li>
  </ul>
	
<h2>Tecnologias utilizadas</h2>

  <p>As principais tecnologias utilizadas neste projeto foram:</p>

  <ul>
    <li>Java</li>
	<li>JUnit</li>
    <li>Spring Framework</li>
    <li>H2 database</li>
  </ul>
  
<h2>Executando o projeto</h2>

  <p>Para executar o projeto, siga os seguintes passos:</p>

  <ol>
    <li>Clone o repositório: <code>git clone https://github.com/arthur-j1/desafio-artornattus.git</code></li>
    <li>Acesse o diretório do projeto: <code>cd desafio-artornattus</code></li>
    <li>Abra o projeto em sua IDE de preferência</li>
    <li>Execute a classe <code>DesafioArtornattusApplication</code> para iniciar a aplicação</li>
		<li>Caso queira executar os testes de reposta das url's basta instalar o postman na sua máquina e importar 
			o arquivo Teste api desafio.postman_collection.json no postman</li>
  </ol>

  <h2>Rotas</h2>
	<table>
		<thead>
			<tr>
				<th>Método</th>
				<th>Rota</th>
				<th>Descrição</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>GET</td>
				<td>/clientes</td>
				<td>Retorna a lista de todos os clientes cadastrados.</td>
			</tr>
			<tr>
				<td>GET</td>
				<td>/enderecos</td>
				<td>Retorna a lista de todos os endereços cadastrados.</td>
			</tr>
			<tr>
				<td>GET</td>
				<td>/cliente/{id}</td>
				<td>Retorna um cliente específico por ID.</td>
			</tr>
			<tr>
				<td>GET</td>
				<td>/cliente/{id}/enderecos</td>
				<td>Retorna a lista de endereços de um cliente específico.</td>
			</tr>
			<tr>
				<td>GET</td>
				<td>/endereco/{id}</td>
				<td>Retorna um endereço específico por ID.</td>
			</tr>
			<tr>
				<td>GET</td>
				<td>/{idCliente}/buscar-endereco/{idEndereço}</td>
				<td>Retorna um endereço específico por ID dentro dos endereços do cliente, caso não exista endereço ou cliente com esse ID é retornada uma exceção personalizada.</td>
			</tr>
			<tr>
				<td>GET</td>
				<td>/{idCliente}/endereco/principal</td>
				<td>Retorna o endereço principal do cliente dono do ID.</td>
			</tr>
			<tr>
				<td>POST</td>
				<td>/</td>
				<td>Cria um novo cliente (path: localhost:8080/).</td>
			</tr>
			<tr>
				<td>POST</td>
				<td>/{idCliente}/endereco</td>
				<td>Cria um novo endereco relacionado ao cliente dono do ID.</td>
			</tr>
			<tr>
				<td>POST</td>
				<td>/{idCliente}/endereco-principal/{idEndereço}</td>
				<td>Busca cliente e endereco pelo ID de ambos, e define o endereço principal do cliente como o endereço do id passado como parametro, caso não exista o ID do endereco na lista de endereços do cliente retorna uma excecao personalizada, impedindo que o cliente altere informações de outro cliente.</td>
			</tr>
			<tr>
				<td>PUT</td>
				<td>/clientes/{id}</td>
				<td>Atualiza as informações de um cliente específico, usando como parametros as informações passadas no corpo da requisição(pode ser atualizado um ou mais endereço usando a mesma função)</td>
    </tr>
			<td>DELETE</td>
				<td>/{id}</td>
				<td>Deleta um cliente específico</td>
    </tr>
	</tr>
			<td>DELETE</td>
				<td>/enderecos/{id}</td>
				<td>Deleta um endereço específico</td>
    </tr>
		</tr>
			<td>DELETE</td>
				<td>/{idCliente}/delete-endereco/{idEndereço}</td>
				<td>Deleta o endereço específico apenas se ele estiver na lista de endereços do cliente passado como ID</td>
    </tr>
