package domain.classeMinuscula;

import domain.classeMinuscula.models.ClasseMaiuscula;
import br.edu.ufgd.infra.validation.ValidationInterface;
import br.edu.ufgd.infra.validation.ValidationPlay;
import org.junit.Test;
import play.data.validation.Error;
import play.data.validation.Validation;
import play.test.UnitTest;
import javax.validation.ValidationException;
import java.util.List;

/**
 *
 * @author Iel Rebeque
 */
public class ClasseMaiusculaTest extends UnitTest {

	@Test
	public void deveValidarCamposVaziosDeClasseMaiuscula() {

		ClasseMaiuscula ClasseMaiuscula = new ClasseMaiuscula(null, null);

		try {
			ValidationInterface validation = new ValidationPlay();
			validation.valid("ClasseMaiuscula", ClasseMaiuscula).throwIfErrors();

			fail("Deveria atirar erro!");

		} catch (ValidationException e) {
			assertEquals(3, Validation.errors().size());

			List<Error> errors = Validation.errors();
			assertEquals("ClasseMaiuscula.descricao", errors.get(0).getKey());
			assertEquals("O campo descricao é obrigatório!", errors.get(0).message());
			
			assertEquals("ClasseMaiuscula.idUsuarioCadastro", errors.get(1).getKey());
			assertEquals("O campo idUsuarioCadastro é obrigatório!", errors.get(1).message());
			
			assertEquals("ClasseMaiuscula", errors.get(2).getKey());
			assertEquals("Erro: Há campos inválidos no formulário", errors.get(2).message());
		}
	}

	@Test
	public void deveValidarIdUsuarioCadastroVazioEmClasseMaiuscula() {

		ClasseMaiuscula ClasseMaiuscula = new ClasseMaiuscula("Descricao", null);

		try {
			ValidationInterface validation = new ValidationPlay();
			validation.valid("ClasseMaiuscula", ClasseMaiuscula).throwIfErrors();

			fail("Deveria atirar erro!");

		} catch (ValidationException e) {
			assertEquals(2, Validation.errors().size());

			List<Error> errors = Validation.errors();
			
			assertEquals("ClasseMaiuscula.idUsuarioCadastro", errors.get(0).getKey());
			assertEquals("O campo idUsuarioCadastro é obrigatório!", errors.get(0).message());
			
			assertEquals("ClasseMaiuscula", errors.get(1).getKey());
			assertEquals("Erro: Há campos inválidos no formulário", errors.get(1).message());
		}

	}


}
