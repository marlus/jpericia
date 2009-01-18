package org.jpericia.dao.pericia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jpericia.common.entity.AbstractEntity;
import org.jpericia.common.entity.pericia.Anexo;
import org.jpericia.common.entity.pericia.Pericia;
import org.jpericia.common.util.to.CriterioPesquisaTO;
import org.jpericia.common.util.to.PaginaTO;
import org.jpericia.dao.AbstractDAO;
import org.jpericia.dao.PersistenceUtil;
import org.jpericia.ejb.exception.DAOException;


/**
 * Classe que implementa o padrão
 * J2EE Data Access Object
 */

@SuppressWarnings("unchecked")
public class AnexoDAOJPA extends AbstractDAO implements AnexoDAO {
	
	private static Logger logger = Logger.getLogger(AnexoDAOJPA.class);

	//@Override
	public void inserir(Anexo anexo) throws DAOException {
		logger.debug("Entrou ClienteDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			manager.persist(anexo);
			manager.flush();
		}catch(Exception e){
			throw new DAOException("Erro salvar Cliente", e);
		}
	}
	
	public void atualizar(Anexo anexo) throws DAOException {
		logger.debug("Entrou ClienteDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			Anexo anexoManaged = manager.merge(anexo);
			
			manager.persist(anexoManaged);
			manager.flush();
		}catch(Exception e){
			throw new DAOException("Erro salvar Cliente", e);
		}
	}
	
	public void remover(Anexo anexo) throws DAOException {
		logger.debug("Entrou ClienteDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			Anexo anexoManaged = manager.merge(anexo);
			
			manager.remove(anexoManaged);
			manager.flush();
		}catch(Exception e){
			throw new DAOException("Erro salvar Cliente", e);
		}
	}
	
	public ArrayList<AbstractEntity> pesquisar() throws DAOException {
		logger.debug("Entrou AnexoDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			StringBuffer queryStr = new StringBuffer();
			
	        queryStr.append("from Anexo p order by titulo");
	        Query query = manager.createQuery(queryStr.toString());
	        
	        ArrayList result = (ArrayList)query.getResultList();
	        return result;
	        
		}catch(Exception e){
			throw new DAOException("Erro salvar TituloPeritoDAOJPA", e);
		}
	}

	//@Override
	public PaginaTO consultar(CriterioPesquisaTO criterios) throws DAOException {
		logger.debug("Entrou ClienteDAOJPA");
		EntityManager manager = null;
		PaginaTO retorno = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			StringBuffer queryStr = new StringBuffer();
			
	        queryStr.append("from Cliente c order by ");
	
	        switch (criterios.getOrdenarPor())
	        {
	            case CriterioPesquisaTO.ORDENAR_POR_CODIGO:
	                queryStr.append(" c.codigo ");
	                break;
	            case CriterioPesquisaTO.ORDENAR_POR_NOME:
	                queryStr.append(" c.nome ");
	                break;
	            default:
	            	queryStr.append(" c.codigo ");
	        }
	        
	        switch (criterios.getOrdem())
	        {
	            case CriterioPesquisaTO.ORDEM_CRESCENTE:
	                queryStr.append(" asc ");
	                break;
	            case CriterioPesquisaTO.ORDEM_DECRESCENTE:
	                queryStr.append(" desc ");
	                break;
	            default:
	            	queryStr.append(" asc ");
	        }
			
	        Query query = manager.createQuery(queryStr.toString());
			
			query.setFirstResult(criterios.getQtdeRegistrosPorPagina() * (criterios.getPagina() - 1));
	        query.setMaxResults(criterios.getQtdeRegistrosPorPagina());
	        
	        List result = query.getResultList();
	        
	        Long qtdeRegistros = (Long) manager.createQuery("select count(*) from Cliente c").getResultList().iterator().next();
			
			retorno = new PaginaTO(criterios);
			retorno.setRegistros(result);
			retorno.setTotalRegistros(qtdeRegistros);
		
		}catch(Exception e){
			throw new DAOException("Erro consultando clientes", e);
		}
		return retorno;
	}
	
	
}
