/*******************************************************************************
 * Copyright (c) 2004 Elias Volanakis.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Elias Volanakis - initial API and implementation
 * IBM Corporation
 *******************************************************************************/
package org.jpericia.editor.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.jpericia.editor.model.EllipticalShape;
import org.jpericia.editor.model.ModelElement;
import org.jpericia.editor.model.RectangularShape;
import org.jpericia.editor.model.Shape;
import org.jpericia.editor.model.ShapesDiagram;
import org.jpericia.editor.model.commands.ShapeCreateCommand;
import org.jpericia.editor.model.commands.ShapeSetConstraintCommand;

/**
 * EditPart for the a ShapesDiagram instance.
 * <p>
 * This edit part server as the main diagram container, the white area where
 * everything else is in. Also responsible for the container's layout (the way
 * the container rearanges is contents) and the container's capabilities (edit
 * policies).
 * </p>
 * <p>
 * This edit part must implement the PropertyChangeListener interface, so it can
 * be notified of property changes in the corresponding model element.
 * </p>
 * 
 * @author Elias Volanakis
 */
class DiagramEditPart extends AbstractGraphicalEditPart implements
		PropertyChangeListener
{

	/**
	 * Upon activation, attach to the model element as a property change
	 * listener.
	 */
	public void activate()
	{
		if (!isActive())
		{
			super.activate();
			((ModelElement) getModel()).addPropertyChangeListener(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	protected void createEditPolicies()
	{
		// disallows the removal of this edit part from its parent
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
		// handles constraint changes (e.g. moving and/or resizing) of model
		// elements
		// and creation of new model elements
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new ShapesXYLayoutEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure()
	{
		Figure f = new FreeformLayer();
		f.setBorder(new MarginBorder(3));
		f.setLayoutManager(new FreeformLayout());
		return f;
	}

	/**
	 * Upon deactivation, detach from the model element as a property change
	 * listener.
	 */
	public void deactivate()
	{
		if (isActive())
		{
			super.deactivate();
			((ModelElement) getModel()).removePropertyChangeListener(this);
		}
	}

	private ShapesDiagram getCastedModel()
	{
		return (ShapesDiagram) getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	protected List getModelChildren()
	{
		return getCastedModel().getChildren(); // return a list of shapes
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt)
	{
		String prop = evt.getPropertyName();
		// these properties are fired when Shapes are added into or removed from
		// the ShapeDiagram instance and must cause a call of refreshChildren()
		// to update the diagram's contents.
		if (ShapesDiagram.CHILD_ADDED_PROP.equals(prop)
				|| ShapesDiagram.CHILD_REMOVED_PROP.equals(prop))
		{
			refreshChildren();
		}
	}

	/**
	 * EditPolicy for the Figure used by this edit part. Children of
	 * XYLayoutEditPolicy can be used in Figures with XYLayout.
	 * 
	 * @author Elias Volanakis
	 */
	private class ShapesXYLayoutEditPolicy extends XYLayoutEditPolicy
	{

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createAddCommand(org.eclipse.gef.EditPart,
		 *      java.lang.Object)
		 */
		protected Command createAddCommand(EditPart child, Object constraint)
		{
			// not used in this example
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.requests.ChangeBoundsRequest,
		 *      org.eclipse.gef.EditPart, java.lang.Object)
		 */
		protected Command createChangeConstraintCommand(
				ChangeBoundsRequest request, EditPart child, Object constraint)
		{
			if (child instanceof ShapeEditPart
					&& constraint instanceof Rectangle)
			{
				// return a command that can move and/or resize a Shape
				return new ShapeSetConstraintCommand((Shape) child.getModel(),
						request, (Rectangle) constraint);
			}
			return super.createChangeConstraintCommand(request, child,
					constraint);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.ConstrainedLayoutEditPolicy#createChangeConstraintCommand(org.eclipse.gef.EditPart,
		 *      java.lang.Object)
		 */
		protected Command createChangeConstraintCommand(EditPart child,
				Object constraint)
		{
			// not used in this example
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
		 */
		protected Command getCreateCommand(CreateRequest request)
		{
			Object childClass = request.getNewObjectType();
			if (childClass == EllipticalShape.class
					|| childClass == RectangularShape.class)
			{
				// return a command that can add a Shape to a ShapesDiagram
				return new ShapeCreateCommand(DiagramEditPart.this
						.getCastedModel(), request);
			}
			return null;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.gef.editpolicies.LayoutEditPolicy#getDeleteDependantCommand(org.eclipse.gef.Request)
		 */
		protected Command getDeleteDependantCommand(Request request)
		{
			// not used in this example
			return null;
		}
	}

}