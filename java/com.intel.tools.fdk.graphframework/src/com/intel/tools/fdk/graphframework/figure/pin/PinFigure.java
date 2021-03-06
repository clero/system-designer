/*
 * Copyright (C) 2013-2017 Intel Corporation
 *
 * This Program is subject to the terms of the Eclipse Public License, v. 1.0.
 * If a copy of the license was not distributed with this file,
 * you can obtain one at <http://www.eclipse.org/legal/epl-v10.html>
 *
 * SPDX-License-Identifier: EPL-1.0
 */
package com.intel.tools.fdk.graphframework.figure.pin;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Color;

import com.intel.tools.fdk.graphframework.figure.IGraphFigure;
import com.intel.tools.fdk.graphframework.figure.ghost.GhostPinFigure;
import com.intel.tools.fdk.graphframework.graph.IPin;
import com.intel.tools.fdk.graphframework.graph.Style.IStyleListener;
import com.intel.tools.utils.IntelPalette;

/** Abstract class representing a node I/O */
public abstract class PinFigure<IOType extends IPin> extends GhostPinFigure implements IGraphFigure, IStyleListener {

    private final IOType pin;

    private final RectangleFigure selection = new RectangleFigure();

    private final boolean debug = false;

    /**
     * @param pin
     *            the pin graph element represented by this figure
     */
    public PinFigure(final IOType pin) {
        this.pin = pin;
        setColor(pin.getStyle().getForeground());

        selection.setAlpha(128);
        selection.setFill(true);
        selection.setOutline(true);
        selection.setLineWidth(0);
        selection.setForegroundColor(IntelPalette.INTEL_BLUE);
        selection.setBackgroundColor(IntelPalette.LIGHT_BLUE);
        selection.setVisible(false);
        selection.setBounds(getBounds());

        if (debug) {
            showPixelGrid(getArrow());
        }

        add(selection);
        this.pin.getStyle().addListener(this);
    }

    private void showPixelGrid(final IFigure figure) {
        for (int i = 0; i < figure.getBounds().width - 1; i++) {
            for (int j = 0; j < figure.getBounds().height - 1; j++) {
                final RectangleFigure r = new RectangleFigure();
                r.setBounds(new Rectangle(i, j, 2, 2));
                r.setFill(false);
                r.setOutline(true);
                r.setLineWidth(0);
                r.setForegroundColor(IntelPalette.RED);
                add(r);
            }
        }
    }

    @Override
    public void setAlpha(final int value) {
        getArrow().setAlpha(value);
        getConnector().setAlpha(value);
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
     * @return the pin graph element represented by this figure
     */
    public IOType getPin() {
        return pin;
    }

    @Override
    public void foregroundUpdated(final Color color) {
        setColor(pin.getStyle().getForeground());
        invalidate();
    }

}
