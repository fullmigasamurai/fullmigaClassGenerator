package domain.classeMinuscula;

import br.edu.ufgd.infra.datasource.DataSourcePlay;
import domain.classeMinuscula.models.ClasseMaiuscula;
import domain.classeMinuscula.repositories.ClasseMaiusculaRepository;
import org.junit.Test;
import play.db.jpa.JPA;
import play.test.UnitTest;
import java.util.List;

/**
 *
 * @author Iel Rebeque
 */
public class ClasseMaiusculaRepositoryTest extends UnitTest {

	@Test
	public void deveBuscarTodasAsClassePluralNoBanco(){
		DataSourcePlay ds = new DataSourcePlay();
		ClasseMaiusculaRepository repositorio = new ClasseMaiusculaRepository(ds);

		List<ClasseMaiuscula> ClassePluralEsperado =  JPA.em().createNativeQuery("SELECT * FROM projetos.classe_underline", ClasseMaiuscula.class).getResultList();
		List<ClasseMaiuscula> ClassePlural = repositorio.buscarTodasClassePlural();

		assertEquals(ClassePluralEsperado, ClassePlural);
	}

	@Test
	public void deveBuscarApenasUmaClasseMaiusculaNoBanco(){
		DataSourcePlay ds = new DataSourcePlay();
		ClasseMaiusculaRepository repositorio = new ClasseMaiusculaRepository(ds);
		int id = 2;
		ClasseMaiuscula ClasseMaiuscula = repositorio.buscarClasseMaiusculaPorId(id);
		boolean esperado = ClasseMaiuscula != null ? true : false;
		boolean recebido;

		Boolean exists = (Boolean) ( JPA.em().createNativeQuery("SELECT EXISTS (SELECT * FROM projetos.classe_underline WHERE id = :id) ")
		.setParameter("id", id)
		.getSingleResult());

		if (exists) {
			ClasseMaiuscula ClasseMaiusculaEsperada =  (ClasseMaiuscula) JPA.em().createNativeQuery("SELECT * FROM projetos.classe_underline WHERE id = :id  ", ClasseMaiuscula.class)
			.setParameter("id", id)
			.getSingleResult();
		
		recebido = ClasseMaiuscula.equals(ClasseMaiusculaEsperada);
		}
		else {
			recebido = false;
		}
			
			assertEquals(esperado, recebido);


	}
	
}
