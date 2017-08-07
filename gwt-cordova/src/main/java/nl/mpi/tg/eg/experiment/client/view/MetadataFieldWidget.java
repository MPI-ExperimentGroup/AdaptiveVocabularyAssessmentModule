/*
 * Copyright (C) 2017 Max Planck Institute for Psycholinguistics, Nijmegen
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
package nl.mpi.tg.eg.experiment.client.view;

import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TextBoxBase;
import nl.mpi.tg.eg.experiment.client.exception.MetadataFieldException;
import nl.mpi.tg.eg.experiment.client.model.MetadataField;
import nl.mpi.tg.eg.experiment.client.model.StimulusFreeText;

/**
 * @since Aug 2, 2017 3:41:23 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class MetadataFieldWidget implements StimulusFreeText {

    final private MetadataField metadataField;
    final private String initialValue;
    final private FocusWidget focusWidget;
    final private Label label;

    public MetadataFieldWidget(MetadataField metadataField, String initialValue) {
        this.metadataField = metadataField;
        this.initialValue = initialValue;
        if (metadataField.isDate()) {
            label = new Label(metadataField.getFieldLabel());
//            final DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd/MM/yyyy");
//            final DateBox dateBox = new DateBox();
//            dateBox.getDatePicker().setYearAndMonthDropdownVisible(true);
//            dateBox.setFormat(new DateBox.DefaultFormat(dateFormat)); 
            final DateOfBirthField dateOfBirthField = new DateOfBirthField();
            focusWidget = dateOfBirthField.getTextBox();
            if (initialValue != null) {
                dateOfBirthField.setDate(initialValue);
            }

        } else if (metadataField.isCheckBox()) {
            label = new Label();
            focusWidget = new CheckBox(metadataField.getFieldLabel());
            ((CheckBox) focusWidget).setValue((initialValue == null) ? false : Boolean.parseBoolean(initialValue));
        } else if (metadataField.isListBox()) {
            label = new Label(metadataField.getFieldLabel());
            focusWidget = new ListBox();
            int selectedIndex = 0;
            int itemCounter = 0;
            ((ListBox) focusWidget).addItem(""); // make sure there is an empty item at the top of the list
            for (String listItem : metadataField.getListValues()) {
                if (!listItem.isEmpty()) { // allow only one empty item in this list
                    ((ListBox) focusWidget).addItem(listItem);
                    itemCounter++;
                }
                if (initialValue != null && initialValue.equals(listItem)) {
                    selectedIndex = itemCounter;
                }
            }
            ((ListBox) focusWidget).setSelectedIndex(selectedIndex);
        } else {
            label = new Label(metadataField.getFieldLabel());
            if (metadataField.isMultiLine()) {
                focusWidget = new TextArea();
            } else {
                focusWidget = new TextBox();
            }
            ((TextBoxBase) focusWidget).setText((initialValue == null) ? "" : initialValue);
            ((TextBoxBase) focusWidget).addFocusHandler(new FocusHandler() {

                @Override
                public void onFocus(FocusEvent event) {
//                        addKeyboardPadding();
//                scrollToPosition(label.getAbsoluteTop());
                }
            });
        }
        focusWidget.setStylePrimaryName("metadataOK");
    }

    public FocusWidget getFocusWidget() {
        return focusWidget;
    }

    public Label getLabel() {
        return label;
    }

    public void setValue(String fieldValue) {
        if (focusWidget instanceof CheckBox) {
            ((CheckBox) focusWidget).setValue(Boolean.valueOf(fieldValue));
        } else if (focusWidget instanceof ListBox) {
            for (int itemIndex = 0; itemIndex < ((ListBox) focusWidget).getItemCount(); itemIndex++) {
                if (((ListBox) focusWidget).getValue(itemIndex).equals(fieldValue)) {
                    ((ListBox) focusWidget).setSelectedIndex(itemIndex);
                }
            }
        } else if (focusWidget instanceof TextBoxBase) {
            ((TextBoxBase) focusWidget).setValue(fieldValue);
        }
    }

    @Override
    public String getValue() {
        if (focusWidget instanceof CheckBox) {
            return Boolean.toString(((CheckBox) focusWidget).getValue());
        } else if (focusWidget instanceof ListBox) {
            return ((ListBox) focusWidget).getSelectedValue();
        } else if (focusWidget instanceof TextBoxBase) {
            return ((TextBoxBase) focusWidget).getValue();
        } else {
            throw new UnsupportedOperationException("Unexpected type for: " + focusWidget.getClass());
        }
    }

    @Override
    public boolean isValid() {
        try {
            metadataField.validateValue(getValue());
            return true;
        } catch (MetadataFieldException exception) {
            return false;
        }
    }

    @Override
    public String getPostName() {
        return metadataField.getPostName();
    }
}
