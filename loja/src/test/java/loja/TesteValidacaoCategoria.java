package loja;


import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import br.unibh.loja.entidades.Categoria;
import junit.framework.Assert;


@SuppressWarnings("deprecation")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteValidacaoCategoria {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testeValidacaoCategoria1() {
		Categoria cat = new Categoria( 1L , "Elétrico");
		System.out.println(cat);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(cat);
		for (ConstraintViolation<Categoria> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(0, constraintViolations.size());
	}
	
	@Test
	public void testeValidacaoCategoria2() {
		Categoria cat = new Categoria( 1L , "@Elétrico");
		System.out.println(cat);
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(cat);
		for (ConstraintViolation<Categoria> c : constraintViolations) {
			System.out.println(" Erro de Validacao: " + c.getMessage());
		}
		Assert.assertEquals(1, constraintViolations.size());
	}

}

