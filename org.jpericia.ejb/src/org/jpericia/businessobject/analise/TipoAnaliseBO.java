package org.jpericia.businessobject.analise;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jpericia.businessobject.AbstractBusinessObject;
import org.jpericia.common.entity.AbstractEntity;
import org.jpericia.common.entity.analise.TipoAnalise;
import org.jpericia.common.util.to.CriterioPesquisaTO;
import org.jpericia.common.util.to.PaginaTO;
import org.jpericia.dao.JPericiaDAOFactory;
import org.jpericia.dao.analise.TipoAnaliseDAO;
import org.jpericia.ejb.exception.ApplicationServiceException;
import org.jpericia.ejb.exception.BusinessObjectException;
import org.jpericia.ejb.exception.DAOException;
import org.jpericia.ejb.exception.JPericiaDAOFactoryException;


public class TipoAnaliseBO extends AbstractBusinessObject {
	
	private static Logger logger = Logger.getLogger(TipoAnaliseBO.class);
	
	private static TipoAnaliseBO me;

	private TipoAnaliseBO() {
	}

	private static synchronized void loadInstance() {
		if (me == null) {
			me = new TipoAnaliseBO();
		}
	}

	/**
	 * @return TipoAnalise
	 */
	public static TipoAnaliseBO getInstance() {
		if (me == null) {
			loadInstance();
		}
		return me;
	}
	
	/**
	 * Inserir
	 * @throws ApplicationServiceException
	 */
	public void inserir(TipoAnalise funcaoPerito) throws BusinessObjectException{
		try{
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			JPericiaDAOFactory factory = this.getDaoFactory();
			TipoAnaliseDAO funcaoPeritoDAO = (TipoAnaliseDAO)factory.getDao(TipoAnaliseDAO.class);
			funcaoPeritoDAO.inserir(funcaoPerito);
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
		}catch (JPericiaDAOFactoryException tdfe) {
			logger.fatal(tdfe.getMessage(), tdfe);
			throw new BusinessObjectException("Erro salvando Cliente", tdfe);
		}catch (DAOException de){
			logger.fatal(de.getMessage(), de);
			throw new BusinessObjectException("Erro salvando Cliente", de);
		}
	}
	
	/**
	 * Atualizar
	 * @throws ApplicationServiceException
	 */
	public void atualizar(TipoAnalise funcaoPerito) throws BusinessObjectException{
		try{
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			JPericiaDAOFactory factory = this.getDaoFactory();
			TipoAnaliseDAO funcaoPeritoDAO = (TipoAnaliseDAO)factory.getDao(TipoAnaliseDAO.class);
			funcaoPeritoDAO.atualizar(funcaoPerito);
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
		}catch (JPericiaDAOFactoryException tdfe) {
			logger.fatal(tdfe.getMessage(), tdfe);
			throw new BusinessObjectException("Erro salvando Cliente", tdfe);
		}catch (DAOException de){
			logger.fatal(de.getMessage(), de);
			throw new BusinessObjectException("Erro salvando Cliente", de);
		}
	}
	
	/**
	 * Remover
	 * @throws ApplicationServiceException
	 */
	public void remover(TipoAnalise funcaoPerito) throws BusinessObjectException{
		try{
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			JPericiaDAOFactory factory = this.getDaoFactory();
			TipoAnaliseDAO funcaoPeritoDAO = (TipoAnaliseDAO)factory.getDao(TipoAnaliseDAO.class);
			funcaoPeritoDAO.remover(funcaoPerito);
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
		}catch (JPericiaDAOFactoryException tdfe) {
			logger.fatal(tdfe.getMessage(), tdfe);
			throw new BusinessObjectException("Erro salvando Cliente", tdfe);
		}catch (DAOException de){
			logger.fatal(de.getMessage(), de);
			throw new BusinessObjectException("Erro salvando Cliente", de);
		}
	}
	
	/**
	 * Pesquisar
	 * @throws ApplicationServiceException
	 */
	public ArrayList<AbstractEntity> pesquisar() throws BusinessObjectException{
		try{
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			JPericiaDAOFactory factory = this.getDaoFactory();
			TipoAnaliseDAO funcaoPeritoDAO = (TipoAnaliseDAO)factory.getDao(TipoAnaliseDAO.class);
			ArrayList<AbstractEntity> pesquisa = funcaoPeritoDAO.pesquisar();
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
			return pesquisa;
		}catch (JPericiaDAOFactoryException tdfe) {
			logger.fatal(tdfe.getMessage(), tdfe);
			throw new BusinessObjectException("Erro salvando Cliente", tdfe);
		}catch (DAOException de){
			logger.fatal(de.getMessage(), de);
			throw new BusinessObjectException("Erro salvando Cliente", de);
		}
	}
	
	/**
	 * Consultar
	 * @throws ApplicationServiceException
	 */
	public PaginaTO consultar(CriterioPesquisaTO criterios) throws BusinessObjectException{
		PaginaTO retorno = null;
		try{
			logger.debug("Inicio " + this.getClass().getName() + ".consultar()");
			JPericiaDAOFactory factory = this.getDaoFactory();
			TipoAnaliseDAO funcaoPeritoDAO = (TipoAnaliseDAO)factory.getDao(TipoAnaliseDAO.class);
			retorno = funcaoPeritoDAO.consultar(criterios);
			logger.debug("Fim " + this.getClass().getName() + ".consultar()");
		}catch (JPericiaDAOFactoryException tdfe) {
			logger.fatal(tdfe.getMessage(), tdfe);
			throw new BusinessObjectException("Erro consultando Cliente", tdfe);
		}catch (DAOException de){
			logger.fatal(de.getMessage(), de);
			throw new BusinessObjectException("Erro consultando Cliente", de);
		}
		return retorno;
	}

}
