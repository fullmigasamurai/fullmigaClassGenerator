package aplication;

import domain.classeMinuscula.models.ClasseMaiuscula;
import domain.classeMinuscula.repositories.ClasseMaiusculaRepository;
import br.edu.ufgd.infra.datasource.DataSource;
import br.edu.ufgd.infra.datasource.DataSourcePlay;
import br.edu.ufgd.infra.result.HttpResult;
import br.edu.ufgd.infra.validation.ValidationInterface;
import br.edu.ufgd.infra.validation.ValidationPlay;
import domain.usuario.repositories.UsuarioRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.classeMinuscula.ClasseMaiusculaFacade;
import application.classeMinuscula.ClasseMaiusculaForm;
import play.test.UnitTest;

public class ClasseMaiusculaServiceTest extends UnitTest {

	private DataSource dataSource = new DataSourcePlay();
	private HttpResult result = new HttpResult();
	private ValidationInterface validation = new ValidationPlay();

	private ClasseMaiusculaFacade service;

	public ClasseMaiusculaServiceTest() {
		service = new ClasseMaiusculaFacade(dataSource, result, validation);
	}

	void limparTabela() {
		dataSource.begin();
		dataSource.getEntityManager().createNativeQuery("DELETE FROM projetos.classe_underline");
		dataSource.commit();
	}

	@Before
	public void before() {
		limparTabela();
	}

	@After
	public void after() {
		limparTabela();
	}

	@Test
	public void deveSalvarNovaClasseMaiusculaCorretamente() {

		ClasseMaiusculaForm dto = new ClasseMaiusculaForm();
		Integer isUsuario = 1;
		dto.descricao = "Teste";
		dto.idUsuarioCadastro = isUsuario;

		DataSourcePlay ds = new DataSourcePlay();
		UsuarioRepository repositorio = new UsuarioRepository(ds);
		Boolean existeUser = repositorio.existeUsuario(isUsuario);

		service.criarNovaClasseMaiuscula(dto);

		if (existeUser){
			assertEquals(201, result.getHttpCode());
		} else {
			assertEquals(500, result.getHttpCode());
		}
	}

	@Test
	public void deveAlterarClasseMaiusculaCorretamente() {

		DataSourcePlay ds = new DataSourcePlay();
		ds.begin();
		ClasseMaiusculaRepository repositorio = new ClasseMaiusculaRepository(ds);
		ClasseMaiuscula classeMinuscula = new ClasseMaiuscula("teste", 1);

		repositorio.salvar(classeMinuscula);
		ds.commit();

		ClasseMaiuscula classeMinusculaInserido = repositorio.buscarClasseMaiusculaPorId(classeMinuscula.getId());
		Integer classeMinusculaId = classeMinusculaInserido.getId();

		ClasseMaiusculaForm dto = new ClasseMaiusculaForm();

		dto.descricao = "nova descricao";

		service.alterarClasseMaiuscula(classeMinusculaId, dto);

		System.out.println(result);

		assertEquals(200, result.getHttpCode());
	}

	@Test
	public void deveRemoverClasseMaiusculaCorretamente() {

		DataSourcePlay ds = new DataSourcePlay();
		ClasseMaiusculaRepository repositorio = new ClasseMaiusculaRepository(ds);

		ClasseMaiuscula classeMinuscula = new ClasseMaiuscula("Teste", 1);
		repositorio.salvar(classeMinuscula);
		ds.commit();

		ClasseMaiuscula classeMinusculaInserido = repositorio.buscarClasseMaiusculaPorId(classeMinuscula.getId());
		Integer classeMinusculaId = classeMinusculaInserido.getId();

		service.removerClasseMaiuscula(classeMinusculaId);

		assertEquals(200, result.getHttpCode());
	}


}
