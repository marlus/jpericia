package org.jpericia.sessionfacade.organizacao;

import static javax.ejb.TransactionAttributeType.NEVER;
import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;
import org.jpericia.applicationservice.organizacao.TituloContatoAS;
import org.jpericia.common.ejb.exception.SessionFacadeException;
import org.jpericia.common.entity.AbstractEntity;
import org.jpericia.common.entity.organizacao.TituloContato;
import org.jpericia.common.sessionfacade.organizacao.TituloContatoSessionFacadeRemote;
import org.jpericia.common.util.to.CriterioPesquisaTO;
import org.jpericia.common.util.to.PaginaTO;
import org.jpericia.ejb.exception.ApplicationServiceException;
import org.jpericia.sessionfacade.AbstractSessionFacade;


/**
 * Classe base para todos as classes que implementam o padrão
 * J2EE Session Facade
 */

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class TituloContatoSessionFacade extends AbstractSessionFacade implements TituloContatoSessionFacadeRemote {
	
	private static Logger logger = Logger.getLogger(TituloContatoSessionFacade.class);
	
	@TransactionAttribute(NEVER)
	public void inserir(TituloContato tituloContato) throws SessionFacadeException {
		try {
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			TituloContatoAS.getInstance().inserir(tituloContato);
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
		} catch (ApplicationServiceException ase) {
			logger.error(this.getClass().getName() + ".salvar()");
			throw new SessionFacadeException("Erro salvando Cliente", ase);
		}
	}
	
	@TransactionAttribute(REQUIRES_NEW)
	public void atualizar(TituloContato tituloContato) throws SessionFacadeException {
		try {
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			TituloContatoAS.getInstance().atualizar(tituloContato);
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
		} catch (ApplicationServiceException ase) {
			logger.error(this.getClass().getName() + ".salvar()");
			throw new SessionFacadeException("Erro salvando Cliente", ase);
		}
	}
	
	public void remover(TituloContato tituloContato) throws SessionFacadeException {
		try {
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			TituloContatoAS.getInstance().remover(tituloContato);
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
		} catch (ApplicationServiceException ase) {
			logger.error(this.getClass().getName() + ".salvar()");
			throw new SessionFacadeException("Erro salvando Cliente", ase);
		}
	}
	
	public ArrayList<AbstractEntity> pesquisar() throws SessionFacadeException {
		try {
			logger.debug("Inicio " + this.getClass().getName() + ".salvar()");
			ArrayList<AbstractEntity> pesquisa = TituloContatoAS.getInstance().pesquisar();
			logger.debug("Fim " + this.getClass().getName() + ".salvar()");
			return pesquisa;
		} catch (ApplicationServiceException ase) {
			logger.error(this.getClass().getName() + ".salvar()");
			throw new SessionFacadeException("Erro salvando Cliente", ase);
		}
	}

	//@Override
	public PaginaTO consultar(CriterioPesquisaTO criterios) throws SessionFacadeException {
		PaginaTO retorno = null;
		try {
			logger.debug("Inicio " + this.getClass().getName() + ".consultar()");
			retorno = TituloContatoAS.getInstance().consultar(criterios);
			logger.debug("Fim " + this.getClass().getName() + ".consultar()");
		} catch (ApplicationServiceException ase) {
			logger.error(this.getClass().getName() + ".consultar()");
			throw new SessionFacadeException("Erro consultando Cliente", ase);
		}
		return retorno;
	}
	
	

}
