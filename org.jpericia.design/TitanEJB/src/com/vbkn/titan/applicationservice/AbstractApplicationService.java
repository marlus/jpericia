package com.vbkn.titan.applicationservice;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.log4j.Logger;

import com.vbkn.titan.dao.PersistenceUtil;

/**
 * @author Valter Bruno Konrad Neto Classe base para todos as classes que
 *         implementam o padr�o J2EE Application Service
 * 
 */
@SuppressWarnings("all")
public abstract class AbstractApplicationService {

	private static Logger logger = Logger.getLogger(AbstractApplicationService.class);

	/**
	 * Atributo <code>manager</code> utilizado para gerenciar o EntityManager
	 * para transa��o.
	 */

	private EntityManager manager;

	/**
	 * Atributo <code>transaction</code> utilizado para gerenciar a transa��o
	 */
	private EntityTransaction transaction;

	protected final void beginEntityManager() {
		logger.debug("Inicio " + this.getClass().getName() + ".beginEntityManager()");
		manager = PersistenceUtil.currentEntityManager();
		logger.debug("Fim " + this.getClass().getName() + ".beginEntityManager()");
	}

	/**
	 * Iniciar uma Transa��o.
	 */
	protected final void beginTransaction() {
		logger.debug("Inicio " + this.getClass().getName() + ".beginTransaction()");
		transaction = PersistenceUtil.currentEntityManager().getTransaction();
		transaction.begin();
		logger.debug("Fim " + this.getClass().getName() + ".beginTransaction()");
	}

	/**
	 * Commit de uma Transa��o.
	 */
	protected final void commitTransaction() {
		logger.debug("Inicio " + this.getClass().getName() + ".commitTransaction()");
		if (transaction.isActive()) {
			transaction.commit();
		} else {
			logger.debug(this.getClass().getName() + "Transaction n�o Ativa");
		}
		logger.debug("Fim " + this.getClass().getName() + ".commitTransaction()");
	}

	/**
	 * Realizar rollback de uma Transa��o.
	 * 
	 * @param nomeMetodo
	 *            
	 */
	protected final void rollbackTransaction(final String nomeMetodo) {
		try {
			if (transaction.isActive()) {
				logger.debug(this.getClass().getName() + "." + nomeMetodo + "()  - rollbackTransaction");
				transaction.rollback();
			}
		} catch (final Throwable e) {
			logger.error(this.getClass().getName() + ".rollbackTransaction()");
		}
	}

	protected final void closeSession() {
		logger.debug("Inicio " + this.getClass().getName() + ".closeSession()");
		try {
			PersistenceUtil.closeEntityManager();
		} catch (Exception e) {
			logger.error(this.getClass().getName() + ".closeSession()");
		} catch (Throwable e) {
			logger.error(this.getClass().getName() + ".closeSession()");
		} finally {
			logger.debug("Fim " + this.getClass().getName() + ".closeSession()");
		}

	}

}
