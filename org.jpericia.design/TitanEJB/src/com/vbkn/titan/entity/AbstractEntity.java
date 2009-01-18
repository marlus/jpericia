package com.vbkn.titan.entity;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;

/**
 * @author Valter Bruno Konrad Neto
 */

public abstract class AbstractEntity implements Serializable {
	
	private static Logger logger = Logger.getLogger(AbstractEntity.class);
	
	/**
	 * Retorna a representa��o de String do Objeto.
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("all")
	public String toString() {
		Method [] methods = getClass().getMethods();
		StringBuffer sb = new StringBuffer();
		Object obj = null;
		String methodName = null;
		String msgError = "N�o foi poss�vel realizar o m�todo toString da Entidade : " + getClass().getName();
		sb.append("[");
		for (int i = 0; i < methods.length; i++) {							
			Method method = methods[i];
			Class<?> [] parameters = method.getParameterTypes();
			methodName = method.getName();    
			if (methodName.startsWith("get") && !methodName.equals("getClass") && parameters.length == 0) {
				try {
					obj = method.invoke(this, null);
				} catch (IllegalArgumentException e) {
					logger.error(msgError, e);
				} catch (IllegalAccessException e) {
					logger.error(msgError, e);
				} catch (InvocationTargetException e) {
					logger.error(msgError, e);
				}
				sb.append(method.getName().substring(3)).append("=");
				sb.append(obj).append(", ");
			} 
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

}
