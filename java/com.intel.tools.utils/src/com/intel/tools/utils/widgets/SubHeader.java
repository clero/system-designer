/*
 * Copyright (C) 2013-2017 Intel Corporation
 *
 * This Program is subject to the terms of the Eclipse Public License, v. 1.0.
 * If a copy of the license was not distributed with this file,
 * you can obtain one at <http://www.eclipse.org/legal/epl-v10.html>
 *
 * SPDX-License-Identifier: EPL-1.0
 */

package com.intel.tools.utils.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.wb.swt.SWTResourceManager;

import com.intel.tools.utils.IntelPalette;

/**
 * This class presents a large white on blue label, to serve as a header for any part.
 */
public class SubHeader extends Canvas {

    private static final int DEFAULT_PADDING = 8;
    private static final Color ACCENT_COLOR = IntelPalette.ORANGE;

    private String titleText;
    private final List<Control> buttonList = new ArrayList<>();

    public SubHeader(final Composite parent) {
        super(parent, SWT.NONE);

        Font font = SWTResourceManager.getFont("Intel Clear", 10, SWT.BOLD);
        if (!font.getFontData()[0].getName().equals("Intel Clear")) {
            font = SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD);
        }
        setFont(font);

        setForeground(IntelPalette.BLACK);

        addPaintListener(new PaintListener() {
            @Override
            public void paintControl(final PaintEvent pe) {
                paint(pe);
            }
        });
    }

    public static final Color getAccentColor() {
        return ACCENT_COLOR;
    }

    private void paint(final PaintEvent e) {
        final GC gc = e.gc;

        paintBackground(gc);

        if (titleText != null) {
            drawText(gc);
        }
    }

    private void paintBackground(final GC gc) {
        final Rectangle rect = getClientArea();
        gc.setBackground(ACCENT_COLOR);

        // We draw an outline of 2 px height at 4 pixels of the bottom of the widget
        rect.y = rect.height - 6;
        rect.height = 2;
        // rect.y
        gc.fillRectangle(rect);
    }

    private void drawText(final GC gc) {
        final Rectangle rect = getClientArea();
        int x, y;

        gc.setForeground(getForeground());
        gc.setTextAntialias(SWT.ON);

        gc.setFont(getFont());
        final Point textSize = gc.textExtent(titleText);
        y = (int) Math.floor((rect.height - textSize.y) / 2) - 3;
        x = 0;
        gc.drawText(titleText, x, y, true);

        int buttonIndex = 0;
        final int buttonSize = (int) (rect.height * 0.6);
        final int buttonMargin = (rect.height - buttonSize) / 2;
        for (final Control button : buttonList) {
            button.setBounds(rect.width - ((buttonSize + buttonMargin) * (buttonIndex + 1)),
                    (rect.height - buttonSize) / 2, buttonSize,
                    buttonSize);
            buttonIndex++;
        }

    }

    @Override
    public Point computeSize(final int wHint, final int hHint, final boolean changed) {

        int width, height;

        final GC gc = new GC(this);
        gc.setFont(getFont());
        final Point textSize = gc.textExtent(titleText);
        width = textSize.x;
        height = textSize.y + DEFAULT_PADDING * 2;

        if (wHint != SWT.DEFAULT) {
            width = wHint;
        }
        if (hHint != SWT.DEFAULT) {
            height = hHint;
        }

        return new Point(width, height);
    }

    public void addButton(final Control control) {
        buttonList.add(control);
    }

    public String getTitleText() {
        checkWidget();
        return titleText;
    }

    public void setText(final String text) {
        checkWidget();
        this.titleText = text;
        redraw();
    }

    @Override
    public void setEnabled(final boolean state) {
        super.setEnabled(state);
        redraw();
    }
}
