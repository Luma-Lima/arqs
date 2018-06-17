package br.unibh.loja.negocio;

	import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
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
import br.unibh.loja.entidades.Produto;
import br.unibh.loja.util.Resources;

	@RunWith(Arquillian.class)
	@FixMethodOrder(MethodSorters.NAME_ASCENDING)
	
	
	public class TesteServicoProduto {

		@Deployment
		public static Archive<?> createTestArchive() {
			// Cria o pacote que vai ser instalado no Wildfly para realizacao dos testes
			return ShrinkWrap.create(WebArchive.class, "testeloja.war")
					.addClasses(Categoria.class, Produto.class, Resources.class, DAO.class, ServicoCategoria.class,
							ServicoProduto.class)
					.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
					.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		}

		// Realiza as injecoes com CDI
		@Inject
		private Logger log;

		@Inject
		private ServicoProduto sp;
		
		@Inject
		private ServicoCategoria scc;
		
		@Test
		public void inserirCategoria() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Categoria o = new Categoria( 4L, "Analógico");
			scc.insert(o);
			Categoria aux = (Categoria) scc.findByName("Analógico").get(0);
			assertNotNull(aux);
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Test
		public void teste01_inserirSemErro() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Categoria aux1 = (Categoria) scc.findByName("Analógico").get(0);
			assertNotNull(aux1);
			Produto o = new Produto (1L, "Celular", "Elétrico", aux1 , new BigDecimal(10.00), "Motorola");
			sp.insert(o);
			Produto aux = (Produto) sp.findByName("Celular").get(0);
			assertNotNull(aux);
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Test
		public void teste02_inserirComErro() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			try {
				Categoria aux1 = (Categoria) scc.findByName("Analógico").get(0);
				assertNotNull(aux1);
				Produto o = new Produto (1L, "Celular@", "Elétrico", aux1, new BigDecimal(10.00), "Motorola");
				sp.insert(o);
			} catch (Exception e) {
				assertTrue(checkString(e, "Caracteres permitidos: letras, espaços, ponto e aspas simples"));
			}
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Test
		public void teste03_atualizar() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Produto o = (Produto) sp.findByName("Celular").get(0);
			o.setNome("Celular Oi");
			sp.update(o);
			Produto aux = (Produto) sp.findByName("Celular Oi").get(0);
			assertNotNull(aux);
			log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		}

		@Test
		public void teste04_excluir() throws Exception {
			log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
			Produto o = (Produto) sp.findByName("Celular Oi").get(0);
			sp.delete(o);
			assertEquals(0, sp.findByName("Celular Oi").size());
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



