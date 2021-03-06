/*
 * Copyright (C) 2013-2017 Intel Corporation
 *
 * This Program is subject to the terms of the Eclipse Public License, v. 1.0.
 * If a copy of the license was not distributed with this file,
 * you can obtain one at <http://www.eclipse.org/legal/epl-v10.html>
 *
 * SPDX-License-Identifier: EPL-1.0
 */
package com.intel.tools.fdk.graphframework.figure.node;

import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.intel.tools.fdk.graphframework.figure.IGraphFigure;
import com.intel.tools.fdk.graphframework.graph.IGroup;
import com.intel.tools.fdk.graphframework.graph.Style.IStyleListener;
import com.intel.tools.utils.IntelPalette;

public class GroupBodyFigure extends RoundedRectangle implements IGraphFigure, IStyleListener {

    private static final int LINE_WIDTH = 2;

    /** The graph group this figure represents */
    private final IGroup group;

    private final RectangleFigure selection = new RectangleFigure();

    /**
     * Creates a new {@link GroupBodyFigure}
     *
     * @param group
     *            the group this figure represents
     */
    public GroupBodyFigure(final IGroup group) {
        this.group = group;

        setFill(true);
        setAntialias(1);
        setLineWidth(LINE_WIDTH);
        setLineStyle(SWT.LINE_DASHDOT);
        setBackgroundColor(group.getStyle().getBackground());
        setForegroundColor(group.getStyle().getForeground());

        selection.setAlpha(128);
        selection.setFill(true);
        selection.setOutline(true);
        selection.setLineWidth(0);
        selection.setForegroundColor(IntelPalette.INTEL_BLUE);
        selection.setBackgroundColor(IntelPalette.LIGHT_BLUE);
        selection.setVisible(false);

        add(selection);
        group.getStyle().addListener(this);
    }

    @Override
    public void setBounds(final Rectangle rect) {
        super.setBounds(rect);
        final Rectangle inclusiveBounds = new Rectangle(bounds);
        inclusiveBounds.width += LINE_WIDTH / 2;
        inclusiveBounds.height += LINE_WIDTH / 2;
        selection.setBounds(inclusiveBounds);

    }

    @Override
    public void select() {
        selection.setVisible(true);
    }

    @Override
    public void unselect() {
        selection.setVisible(false);
    }

    /**
     * @return the graph element associated to this figure
     */
    public IGroup getGroup() {
        return group;
    }

    @Override
    public void foregroundUpdated(final Color color) {
        setForegroundColor(group.getStyle().getForeground());
        invalidate();
    }

    @Override
    public void backgroundUpdated(final Color color) {
        setBackgroundColor(group.getStyle().getBackground());
        invalidate();
    }

}
