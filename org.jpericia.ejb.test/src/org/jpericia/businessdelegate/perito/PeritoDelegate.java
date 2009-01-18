package org.jpericia.businessdelegate.perito;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jpericia.businessdelegate.AbstractBusinessDelegate;
import org.jpericia.common.ejb.exception.SessionFacadeException;
import org.jpericia.common.entity.AbstractEntity;
import org.jpericia.common.entity.perito.Perito;
import org.jpericia.common.sessionfacade.perito.PeritoSessionFacadeRemote;
import org.jpericia.exception.BusinessDelegateException;
import org.jpericia.exception.ServiceLocatorException;
import org.jpericia.util.ServiceLocator;


/**
 * @author Marlus Cadanus da Costa
 */

@SuppressWarnings("all")
public class PeritoDelegate extends AbstractBusinessDelegate {

	private static Logger logger = Logger.getLogger(PeritoDelegate.class);
	
	/**
	 * Inserir Perito
	 * @throws BusinessDelegateException
	 */
	public void inserir(Perito Perito) throws BusinessDelegateException {
		logger.debug("Inicio " + this.getClass().getName() + ".inserir()");
		try {
			this.getFacade().inserir(Perito);
			logger.debug("Fim " + this.getClass().getName() + ".inserir()");
		} catch (SessionFacadeException sfe) {
			logger.error(this.getClass().getName() + ".teste()");
			throw new BusinessDelegateException("Erro salvando Organizacao", sfe);
		}
	}
	
	/**
	 * Atualizar Perito
	 * @throws BusinessDelegateException
	 */
	public void atualizar(Perito Perito) throws BusinessDelegateException {
		logger.debug("Inicio " + this.getClass().getName() + ".inserir()");
		try {
			this.getFacade().atualizar(Perito);
			logger.debug("Fim " + this.getClass().getName() + ".inserir()");
		} catch (SessionFacadeException sfe) {
			logger.error(this.getClass().getName() + ".teste()");
			throw new BusinessDelegateException("Erro salvando Organizacao", sfe);
		}
	}	

	/**
	 * Remover Perito
	 * @throws BusinessDelegateException
	 */
	public void remover(Perito Perito) throws BusinessDelegateException {
		logger.debug("Inicio " + this.getClass().getName() + ".remover()");
		try {
			this.getFacade().remover(Perito);
			logger.debug("Fim " + this.getClass().getName() + ".remover()");
		} catch (SessionFacadeException sfe) {
			logger.error(this.getClass().getName() + ".teste()");
			throw new BusinessDelegateException("Erro salvando Organizacao", sfe);
		}
	}	
	
	/**
	 * Consulta de Organizacoes
	 * @throws BusinessDelegateException
	 */
	public ArrayList<AbstractEntity> pesquisar() throws BusinessDelegateException {
		logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
		ArrayList<AbstractEntity> retorno = null;
		try {
			retorno = this.getFacade().pesquisar();
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
		} catch (SessionFacadeException sfe) {
			logger.error(this.getClass().getName() + ".teste()");
			throw new BusinessDelegateException("Erro consultando Organizacao", sfe);
		}
		return retorno;
	}
	
	/**
	 * Obtem stub do session facade
	 * @return
	 * @throws BusinessDelegateException
	 */
	private PeritoSessionFacadeRemote getFacade() throws BusinessDelegateException{
		try {
			return (PeritoSessionFacadeRemote) ServiceLocator.getInstance().getHome(PeritoSessionFacadeRemote.class, "PeritoSessionFacade");
		} catch (ServiceLocatorException e) {
			throw new BusinessDelegateException("Erro obtendo facade", e);
		}
	}

}
