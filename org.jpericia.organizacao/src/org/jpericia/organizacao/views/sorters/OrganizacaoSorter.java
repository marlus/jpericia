package org.jpericia.organizacao.views.sorters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.jpericia.common.entity.organizacao.Organizacao;

public class OrganizacaoSorter extends ViewerSorter
{

	public static final String CODIGO = "codigo"; //$NON-NLS-1$

	public static final String NOME = "nome"; //$NON-NLS-1$

	private String column = null;

	private int dir = SWT.DOWN;

	/**
	 * @param column
	 * @param dir
	 */
	public OrganizacaoSorter(String column, int dir)
	{
		super();
		this.column = column;
		this.dir = dir;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.ViewerComparator#compare(org.eclipse.jface.viewers.Viewer,
	 *      java.lang.Object, java.lang.Object)
	 */
	public int compare(Viewer viewer, Object e1, Object e2)
	{
		int returnValue = 0;
		if (this.column == CODIGO)
		{
			returnValue = ((Organizacao) e1).getCodigo().compareTo(
					((Organizacao) e2).getCodigo());
		}
		if (this.column == NOME)
		{
			returnValue = ((Organizacao) e1).getNome().compareTo(
					((Organizacao) e2).getNome());
		}
		if (this.dir == SWT.DOWN)
		{
			returnValue = returnValue * -1;
		}
		return returnValue;
	}
}
