package br.unibh.loja.negocio;

	import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import br.unibh.loja.entidades.Categoria;
import br.unibh.loja.util.Resources;

	@RunWith(Arquillian.class)
	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	
	
	public class TesteServicoCategoria {

		@Deployment
		public static Archive<?> createTestArchive() {
			// Cria o pacote que vai ser instalado no Wildfly para realizacao dos testes
			return ShrinkWrap.create(WebArchive.class, "testeloja.war")
					.addClasses(Categoria.class, Resources.class, DAO.class,
							ServicoCategoria.class)
					.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
					.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		}

		// Realiza as injecoes com CDI
		@Inject
		private Logger log;

		@Inject
		private ServicoCategoria scc;

		@Test
		public void teste01_inserirSemErro() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Categoria o = new Categoria( 2L, "Elétrico");
			scc.insert(o);
			Categoria aux = (Categoria) scc.findByName("Elétrico").get(0);
			assertNotNull(aux);
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Test
		public void teste02_inserirComErro() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			try {
				Categoria o = new Categoria( 1L , "Elétrico@");
				scc.insert(o);
			} catch (Exception e) {
				assertTrue(checkString(e, "Caracteres permitidos: letras, espaços, ponto e aspas simples"));
			}
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Test
		public void teste03_atualizar() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Categoria o = (Categoria) scc.findByName("Elétrico").get(0);
			o.setDescricao("Digital");
			scc.update(o);
			Categoria aux = (Categoria) scc.findByName("Digital").get(0);
			assertNotNull(aux);
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Test
		public void teste04_excluir() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Categoria o = (Categoria) scc.findByName("Digital").get(0);
			scc.delete(o);
			assertEquals(0, scc.findByName("Digital").size());
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		private boolean checkString(Throwable e, String str) {
			if (e.getMessage().contains(str)) {
				return true;
			} else if (e.getCause() != null) {
				return checkString(e.getCause(), str);
			}
			return false;
		}
	}



