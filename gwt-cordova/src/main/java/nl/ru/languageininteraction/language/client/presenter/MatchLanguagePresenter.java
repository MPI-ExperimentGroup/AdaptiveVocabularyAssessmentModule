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
package nl.ru.languageininteraction.language.client.presenter;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import nl.ru.languageininteraction.language.client.exception.AudioException;
import nl.ru.languageininteraction.language.client.listener.AppEventListner;
import nl.ru.languageininteraction.language.client.listener.PresenterEventListner;
import nl.ru.languageininteraction.language.client.service.AudioPlayer;
import nl.ru.languageininteraction.language.client.view.MatchLanguageView;

/**
 * @since Nov 26, 2014 4:12:27 PM (creation date)
 * @author Peter Withers <p.withers@psych.ru.nl>
 */
public class MatchLanguagePresenter extends AbstractPresenter implements Presenter {

    final AudioPlayer audioPlayer;

    public MatchLanguagePresenter(RootLayoutPanel widgetTag, final AudioPlayer audioPlayer) throws AudioException {
        super(widgetTag, new MatchLanguageView(audioPlayer));
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void setTitle(final PresenterEventListner titleBarListner) {
        simpleView.addTitle(messages.mapScreenTitle(), new PresenterEventListner() {

            @Override
            public String getLabel() {
                return titleBarListner.getLabel();
            }

            @Override
            public void eventFired(Button button) {
                audioPlayer.stopAll();
                titleBarListner.eventFired(button);
            }
        });
    }

    @Override
    public void setContent(AppEventListner appEventListner) {
        ((MatchLanguageView) simpleView).setupScreen();
    }
}
