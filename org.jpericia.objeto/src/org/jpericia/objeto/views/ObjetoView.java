package org.jpericia.objeto.views;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;
import org.jpericia.common.entity.objeto.Objeto;
import org.jpericia.core.exception.BusinessDelegateException;
import org.jpericia.core.ui.preferences.PropertyChangeEvents;
import org.jpericia.objeto.ObjetoPlugin;
import org.jpericia.objeto.actions.ObjetoEditarAction;
import org.jpericia.objeto.actions.ObjetoRemoverAction;
import org.jpericia.objeto.businessdelegate.ObjetoDelegate;
import org.jpericia.objeto.messages.Messages;
import org.jpericia.objeto.views.listeners.ObjetoListener;
import org.jpericia.objeto.views.sorters.ObjetoSorter;

public class ObjetoView extends ViewPart implements PropertyChangeListener
{
	public static final String VIEW_ID = "org.jpericia.objeto.views.objetoView";
	
	private Objeto objeto;
	
	private ObjetoRemoverAction removerAction;
	
	private ObjetoEditarAction editarAction;

	private TableViewer viewer;

	private Text tituloText;

	ViewerFilter filter = new ViewerFilter()
	{
		public boolean select(Viewer viewer1, Object parentElement,
				Object element)
		{
			return (ObjetoView.this.tituloText.getText().length() == 0 || ((Objeto) element)
					.getTitulo()
					.matches(
							replaceWildCard(ObjetoView.this.tituloText.getText())));
		}
	};

	static String replaceWildCard(String value)
	{
		return value.replaceAll("\\*", "(\\\\s*?\\\\S)*?"); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider
	{
		public void inputChanged(Viewer v, Object oldInput, Object newInput)
		{
			// do nothing
		}

		public void dispose()
		{
			// do nothing
		}

		public Object[] getElements(Object parent)
		{
			return ((ObjetoListener) parent).toArray(new Objeto[0]);
		}
	}

	class ViewLabelProvider extends LabelProvider implements
			ITableLabelProvider
	{
		public String getColumnText(Object obj, int index)
		{
			String returnValue = null;
			switch (index)
			{
			case 0:
				returnValue = ((Objeto) obj).getCodigo().toString();
				break;
			case 1:
				returnValue = ((Objeto) obj).getTitulo();
				break;
			default:
				break;
			}
			return returnValue;
		}

		public Image getColumnImage(Object obj, int index)
		{
			return null;
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent)
	{
		parent.setLayout(new GridLayout(2, false));
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		parent.setLayoutData(gd);

		GridData gdSpan = new GridData();
		gdSpan.horizontalSpan = 2;
		
		Label infoLabel = new Label(parent, SWT.NONE);
		infoLabel.setLayoutData(gdSpan);
		infoLabel.setText(Messages.infoObjeto);
		
		Label titulo = new Label(parent, SWT.NONE);
		titulo.setText(Messages.titulo);
		gd = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		gd.widthHint = 100;

		titulo.setLayoutData(gd);
		this.tituloText = new Text(parent, SWT.BORDER);
		gd = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		gd.widthHint = 200;
		this.tituloText.setLayoutData(gd);

		Composite buttonComposite = new Composite(parent, SWT.NONE);
		buttonComposite.setLayout(new GridLayout(2, false));
		gd = new GridData(SWT.RIGHT, SWT.BEGINNING, true, false);
		gd.horizontalSpan = 2;
		buttonComposite.setLayoutData(gd);

		Button applyButton = new Button(buttonComposite, SWT.PUSH);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		applyButton.setText(Messages.pesquisar);
		applyButton.setLayoutData(gd);
		applyButton.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				try
				{
					ObjetoView.this.viewer.setInput(ObjetoDelegate.getInstance().pesquisar());
				}
				catch(BusinessDelegateException e)
				{
					//Trata erro
				}
				
				ObjetoView.this.viewer.addFilter(ObjetoView.this.filter);
			}
		});

		Button clearButton = new Button(buttonComposite, SWT.PUSH);
		gd = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		clearButton.setText(Messages.todos);
		clearButton.setLayoutData(gd);
		clearButton.addListener(SWT.Selection, new Listener()
		{
			public void handleEvent(Event event)
			{
				ObjetoView.this.viewer.removeFilter(ObjetoView.this.filter);
			}
		});

		final Table resultTable = new Table(parent, SWT.FULL_SELECTION
				| SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		resultTable.setHeaderVisible(true);
		resultTable.setLinesVisible(true);

		final TableColumn tc0 = new TableColumn(resultTable, SWT.NONE);
		tc0.setText(Messages.codigo);
		tc0.setWidth(150);
		tc0.setMoveable(true);
		
		final TableColumn tc1 = new TableColumn(resultTable, SWT.NONE);
		tc1.setText(Messages.titulo);
		tc1.setWidth(150);
		tc1.setMoveable(true);

		Listener sortListener = new Listener()
		{
			public void handleEvent(Event e)
			{
				// determine new sort column and direction
				TableColumn sortColumn = ObjetoView.this.viewer.getTable()
						.getSortColumn();
				TableColumn currentColumn = (TableColumn) e.widget;
				int dir = ObjetoView.this.viewer.getTable().getSortDirection();
				if (sortColumn == currentColumn)
				{
					dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
				}
				else
				{
					ObjetoView.this.viewer.getTable().setSortColumn(
							currentColumn);
					dir = SWT.UP;
				}
				
				// sort the data based on column and direction
				String sortIdentifier = null;
				if (currentColumn == tc0)
				{
					sortIdentifier = ObjetoSorter.CODIGO;
				}
				if (currentColumn == tc1)
				{
					sortIdentifier = ObjetoSorter.TITULO;
				}
				ObjetoView.this.viewer.getTable().setSortDirection(dir);
				ObjetoView.this.viewer.setSorter(new ObjetoSorter(
						sortIdentifier, dir));
			}
		};

		tc0.addListener(SWT.Selection, sortListener);
		tc1.addListener(SWT.Selection, sortListener);

		try
		{
			this.viewer = new TableViewer(resultTable);
			this.viewer.setContentProvider(new ViewContentProvider());
			this.viewer.setLabelProvider(new ViewLabelProvider());

			gd = new GridData(SWT.FILL, SWT.FILL, true, true);
			gd.horizontalSpan = 2;
			this.viewer.getTable().setLayoutData(gd);

			ObjetoDelegate.getInstance().pesquisar().addPropertyChangeListener(
					this);
		}
		catch (BusinessDelegateException e)
		{
			// TODO tratar erro
		}
		createToolBarButtons();
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(final PropertyChangeEvent evt)
	{
		getViewSite().getShell().getDisplay().asyncExec(new Runnable()
		{
			/*
			 * (non-Javadoc)
			 * 
			 * @see java.lang.Runnable#run()
			 */
			public void run()
			{
				if (evt.getPropertyName().equals(PropertyChangeEvents.ADD))
				{
					ObjetoView.this.viewer.refresh();
				}
				if (evt.getPropertyName().equals(PropertyChangeEvents.CLEAR))
				{
					ObjetoView.this.viewer.refresh();
				}

			}
		});

	}

	public void dispose()
	{
		try
		{
			ObjetoDelegate.getInstance().pesquisar().removePropertyChangeListener(
					this);
		}
		catch (BusinessDelegateException e)
		{
			// TODO tratar erro
		}
		super.dispose();
	}
	
	private void createToolBarButtons()
	{
		// Adiciona acao de remover
		removerAction = new ObjetoRemoverAction(this, "Remover");
		
		ImageDescriptor imgRemover = ImageDescriptor.createFromURL(
                FileLocator.find(ObjetoPlugin.getDefault().getBundle(), 
            new Path("icons/package_delete.png"),null));
		
		ImageDescriptor imgRemoverDisabled = ImageDescriptor.createFromURL(
                FileLocator.find(ObjetoPlugin.getDefault().getBundle(), 
            new Path("icons/package_delete_disabled.png"),null));
		
		removerAction.setImageDescriptor(imgRemover);
		removerAction.setDisabledImageDescriptor(imgRemoverDisabled);
		removerAction.setToolTipText("Remove o objeto selecionada");
		
		// Adiciona acao de editar
		editarAction = new ObjetoEditarAction(this, "Atualizar");
		
		ImageDescriptor imgEditar = ImageDescriptor.createFromURL(
                FileLocator.find(ObjetoPlugin.getDefault().getBundle(), 
            new Path("icons/package_edit.png"),null));
		
		ImageDescriptor imgEditarDisabled = ImageDescriptor.createFromURL(
                FileLocator.find(ObjetoPlugin.getDefault().getBundle(), 
            new Path("icons/package_edit_disabled.png"),null));
		
		editarAction.setImageDescriptor(imgEditar);
		editarAction.setDisabledImageDescriptor(imgEditarDisabled);
		editarAction.setToolTipText("Atualiza o objeto selecionada");
		
		// Adiciona botoes na view
		getViewSite().getActionBars().getToolBarManager().add(removerAction);
		getViewSite().getActionBars().getToolBarManager().add(editarAction);
		removerAction.setEnabled(false);
		editarAction.setEnabled(false);
		viewer.addSelectionChangedListener(new ISelectionChangedListener()
		{
			public void selectionChanged(SelectionChangedEvent event)
			{
				removerAction.setEnabled(!event.getSelection().isEmpty());
				editarAction.setEnabled(!event.getSelection().isEmpty());
				setObjeto((Objeto)((StructuredSelection)event.getSelection()).getFirstElement());
			}
		});
	}

	public Objeto getObjeto()
	{
		return objeto;
	}

	public void setObjeto(Objeto objeto)
	{
		this.objeto = objeto;
	}

	public TableViewer getViewer() {
		return viewer;
	}

	public void setViewer(TableViewer viewer) {
		this.viewer = viewer;
	}
	
}