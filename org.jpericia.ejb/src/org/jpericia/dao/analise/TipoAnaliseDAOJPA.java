package org.jpericia.dao.analise;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jpericia.common.entity.AbstractEntity;
import org.jpericia.common.entity.analise.TipoAnalise;
import org.jpericia.common.util.to.CriterioPesquisaTO;
import org.jpericia.common.util.to.PaginaTO;
import org.jpericia.dao.AbstractDAO;
import org.jpericia.dao.PersistenceUtil;
import org.jpericia.ejb.exception.DAOException;


/**
 * @author Marlus Cadanus da Costa
 * 
 * Classe que implementa o padrão
 * J2EE Data Access Object
 */

@SuppressWarnings("unchecked")
public class TipoAnaliseDAOJPA extends AbstractDAO implements TipoAnaliseDAO {
	
	private static Logger logger = Logger.getLogger(TipoAnaliseDAOJPA.class); 
	
	//@Override
	public void inserir(TipoAnalise tipoAnalise) throws DAOException {
		logger.debug("Entrou ClienteDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			manager.persist(tipoAnalise);
			manager.flush();
		}catch(Exception e){
			throw new DAOException("Erro salvar Cliente", e);
		}
	}
	
	public void atualizar(TipoAnalise tipoAnalise) throws DAOException {
		logger.debug("Entrou ClienteDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			TipoAnalise tipoAnaliseManaged = manager.merge(tipoAnalise);
			
			//manager.refresh(tipoAnaliseManaged);
			manager.persist(tipoAnaliseManaged);
			manager.flush();
		}catch(Exception e){
			throw new DAOException("Erro salvar Cliente", e);
		}
	}
	
	public void remover(TipoAnalise tipoAnalise) throws DAOException {
		logger.debug("Entrou ClienteDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			TipoAnalise tipoAnaliseManaged = manager.merge(tipoAnalise);
			
			manager.remove(tipoAnaliseManaged);
			manager.flush();
		}catch(Exception e){
			throw new DAOException("Erro salvar Cliente", e);
		}
	}
	
	public ArrayList<AbstractEntity> pesquisar() throws DAOException {
		logger.debug("Entrou TipoAnaliseDAOJPA");
		EntityManager manager = null;
		try{
			manager = PersistenceUtil.currentEntityManager();
			StringBuffer queryStr = new StringBuffer();
			
	        queryStr.append("from TipoAnalise ta order by nome");
	        Query query = manager.createQuery(queryStr.toString());
	        
	        ArrayList result = (ArrayList)query.getResultList();
	        return result;
	        
		}catch(Exception e){
			throw new DAOException("Erro salvar Cliente", e);
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
