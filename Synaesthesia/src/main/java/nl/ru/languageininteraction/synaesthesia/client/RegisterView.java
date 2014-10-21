/*
 * Copyright (C) 2014 Language In Interaction
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.ru.languageininteraction.synaesthesia.client;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import java.util.Map;

/**
 * @since Oct 21, 2014 5:15:19 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class RegisterView extends SimpleView {

    final VerticalPanel outerPanel;

    public RegisterView() {
        outerPanel = new VerticalPanel();
        setContent(outerPanel);
    }

    protected void addText(String textString) {
        HTML html = new HTML(textString);
        outerPanel.add(html);
    }

    protected void addMetadata(Map<String, String> metadataValues) {
        Grid grid = new Grid(metadataValues.size(), 2);
        int rowCount = 0;
        for (String valueName : metadataValues.keySet()) {
            grid.setWidget(rowCount, 0, new HTML(valueName));
            grid.setWidget(rowCount, 1, new HTML(metadataValues.get(valueName)));
            rowCount++;
        }
        outerPanel.add(grid);
    }
}
