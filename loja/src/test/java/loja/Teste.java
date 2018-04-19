package loja;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.math.BigDecimal;

import java.util.Date;

import  org.junit.Test ;

import br.unibh.loja.entidades.Categoria;
import br.unibh.loja.entidades.Cliente;
import br.unibh.loja.entidades.Produto;

public class Teste {
	
	@Test
	public  void  testCreateObject () {
		Cliente cl = new Cliente (3L, "Luma", "LumaL", "*****", "LumaLi", "999.999.999-00", "1111-1111", "abcd@efgh.com.br", new Date(), new Date());
		assertEquals(cl.getId(),new Long(3));
		assertEquals(cl.getNome(),"Luma");
		assertEquals(cl.getLogin(),"LumaL");
		assertEquals(cl.getSenha(),"*****");
		assertEquals(cl.getPerfil(),"LumaLi");
		assertEquals(cl.getCpf(),"999.999.999-00");
		assertEquals(cl.getTelefone(),"1111-1111");
		assertEquals(cl.getEmail(),"abcd@efgh.com.br");
		assertEquals(cl.getDataNascimento(), new Date());
		assertEquals(cl.getDataCadastro(), new Date());
		
		Categoria cat = new Categoria( 1L , "Elétrico");
		assertEquals (cat . getId (), new  Long ( 1 ));
		assertEquals (cat . getDescricao (), "Elétrico" );
		
		Produto prod = new Produto (1L, "Celular", "Elétrico", cat, new BigDecimal(10.00), "Motorola");
		assertEquals(prod.getId(),new Long(1));
		assertEquals(prod.getNome(),"Celular");
		assertEquals(prod.getDescricao(),"Elétrico");
		assertEquals(prod.getPreco(), new BigDecimal(10.00));
		assertEquals(prod.getFabricante(),"Motorola");
		
	}
	
	@Test
	public void testCompareOnject () {
		Cliente cl = new Cliente (3L, "Luma", "LumaL", "*****", "LumaLi", "999.999.999-00", "1111-1111", "abcd@efgh.com.br", new Date(), new Date());
		Cliente c2 = new Cliente (4L, "Luma", "LumaL", "*****", "LumaLi", "999.999.999-00", "1111-1111", "abcd@efgh.com.br", new Date(), new Date());
		assertNotEquals (cl,c2);
		
		Categoria cat1 = new Categoria(1L, "Elétrico");
		Categoria cat2 = new Categoria(3L, "Elétrico");
		assertNotEquals (cat1,cat2);
		
		Produto prod = new Produto (1L, "Celular", "Elétrico", cat1, new BigDecimal(10.00), "Motorola");
		Produto prod1 = new Produto (2L, "Celular", "Elétrico", cat1, new BigDecimal(10.00), "Motorola");
		assertNotEquals (prod,prod1);
		

		
	}
	
	@Test
	public  void  testGenerateHash () {
		Cliente cl = new Cliente (3L, "Luma", "LumaL", "*****", "LumaLi", "999.999.999-00", "1111-1111", "abcd@efgh.com.br", new Date(), new Date());
		Cliente c1 = new Cliente (3L, "Luma", "LumaL", "*****", "LumaLi", "999.999.999-00", "1111-1111", "abcd@efgh.com.br", new Date(), new Date());
		assertEquals (cl . hashCode(), c1 . hashCode ());
		Cliente c2 = new Cliente (4L, "Luma", "LumaL", "*****", "LumaLi", "999.999.999-00", "1111-1111", "abcd@efgh.com.br", new Date(), new Date());
		assertNotEquals (cl . hashCode(), c2 . hashCode ());
		
		Categoria cat1 = new Categoria(1L, "Elétrico");
		Categoria cat2 = new Categoria(1L, "Elétrico");
		assertEquals (cat1 . hashCode(), cat2 . hashCode ());
		Categoria cat3 = new Categoria(2L, "Elétrico");
		assertNotEquals (cat1 . hashCode(), cat3 . hashCode ());
		
		Produto prod = new Produto (1L, "Celular", "Elétrico", cat1, new BigDecimal(10.00), "Motorola");
		Produto prod1 = new Produto (1L, "Celular", "Elétrico", cat1, new BigDecimal(10.00), "Motorola");
		assertEquals (prod . hashCode(), prod1 . hashCode ());
		Produto prod3 = new Produto (2L, "Celular", "Elétrico", cat1, new BigDecimal(10.00), "Motorola");
		assertNotEquals (prod3 . hashCode(), prod . hashCode ());
		
	}
	
	@Test
	public  void  testPrintObject () {
		Cliente cl = new Cliente (3L, "Luma", "LumaL", "*****", "LumaLi", "999.999.999-00", "1111-1111", "abcd@efgh.com.br", null, null);
		System.out.println(cl);
		assertEquals (cl . toString (), "Cliente [id=3, nome=Luma, login=LumaL, senha=*****, perfil=LumaLi, cpf=999.999.999-00, telefone=1111-1111, email=abcd@efgh.com.br, dataNascimento=null, dataCadastro=null]");
		
		Categoria cat1 = new Categoria ( 1L , "Elétrico");
		System.out.println(cat1);
		assertEquals (cat1 . toString (), "Categoria [id=1, descricao=Elétrico]" );
		
		Produto prod = new Produto (1L, "Celular", "Elétrico", cat1, new BigDecimal(10.00), "Motorola");
		System.out.println(prod);
		assertEquals (prod . toString (), "Produto [id=1, nome=Celular, descricao=Elétrico, preco=10, fabricante=Motorola]" );

	}

}
