package com.codenjoy.dojo.football.model;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2016 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.codenjoy.dojo.football.model.Level;
import com.codenjoy.dojo.football.model.LevelImpl;
import com.codenjoy.dojo.football.model.Football;
import com.codenjoy.dojo.football.model.Single;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.EventListener;
import com.codenjoy.dojo.services.PrinterFactory;
import com.codenjoy.dojo.services.PrinterFactoryImpl;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * User: sanja
 * Date: 19.12.13
 * Time: 5:22
 */
public class SingleTest {

    private EventListener listener1;
    private EventListener listener2;
    private EventListener listener3;
    private Single game1;
    private Single game2;
    private Single game3;
    private Dice dice;

    // появляется другие игроки, игра становится мультипользовательской
    @Before
    public void setup() {
        Level level = new LevelImpl(
                "☼☼┴┴☼☼" +
                "☼    ☼" +
                "☼  ∙ ☼" +
                "☼    ☼" +
                "☼    ☼" +
                "☼☼┬┬☼☼");

        dice = mock(Dice.class);
        Football Sample = new Football(level, dice);
        PrinterFactory factory = new PrinterFactoryImpl();

        listener1 = mock(EventListener.class);
        game1 = new Single(Sample, listener1, factory);

        listener2 = mock(EventListener.class);
        game2 = new Single(Sample, listener2, factory);

        listener3 = mock(EventListener.class);
        game3 = new Single(Sample, listener3, factory);

        dice(1, 1);
        game1.newGame();

        dice(2, 4);
        game2.newGame();

        dice(3, 1);
        game3.newGame();
    }

    private void dice(int x, int y) {
        when(dice.next(anyInt())).thenReturn(x, y);
    }

    private void asrtFl1(String expected) {
        assertEquals(expected, game1.getBoardAsString());
    }

    private void asrtFl2(String expected) {
        assertEquals(expected, game2.getBoardAsString());
    }

    private void asrtFl3(String expected) {
        assertEquals(expected, game3.getBoardAsString());
    }

    // рисуем несколько игроков
    @Test
    public void shouldPrint() {
        asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼  ∙ ☼\n" +
                "☼    ☼\n" +
                "☼☺ ♦ ☼\n" +
                "☼☼==☼☼\n");

        asrtFl2(
                "☼☼==☼☼\n" +
                "☼ ☺  ☼\n" +
                "☼  ∙ ☼\n" +
                "☼    ☼\n" +
                "☼♣ ♣ ☼\n" +
                "☼☼⌂⌂☼☼\n");
        
        asrtFl3(
                "☼☼⌂⌂☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼  ∙ ☼\n" +
                "☼    ☼\n" +
                "☼♦ ☺ ☼\n" +
                "☼☼==☼☼\n");
        
        game3.getJoystick().up();
        game3.tick();
        game3.getJoystick().up();
        game3.tick();
        asrtFl3(
        		"☼☼⌂⌂☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼  ☻ ☼\n" +
                "☼    ☼\n" +
                "☼♦   ☼\n" +
                "☼☼==☼☼\n");
        game3.getJoystick().act(Actions.HIT_DOWN.getValue());
        game3.getJoystick().down();
        game3.tick();
        
        asrtFl3(
                "☼☼⌂⌂☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼    ☼\n" +
                "☼  ☻ ☼\n" +
                "☼♦   ☼\n" +
                "☼☼==☼☼\n");
        
    }

    // Каждый игрок может упраыляться за тик игры независимо
    @Test
    public void shouldJoystick() {
        
        game1.getJoystick().up();
        game2.getJoystick().down();
        game3.getJoystick().right();

        game1.tick();

        asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼    ☼\n" +
                "☼ ♣∙ ☼\n" +
                "☼☺   ☼\n" +
                "☼   ♦☼\n" +
                "☼☼==☼☼\n");
    }

    // игроков можно удалять из игры
    @Test
    public void shouldRemove() {
        game3.destroy();

        game1.tick();

        asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼  ∙ ☼\n" +
                "☼    ☼\n" +
                "☼☺   ☼\n" +
                "☼☼==☼☼\n");
    }

    // игрок может пробросить мяч через другого
    @Test
    public void shouldPassBallThroughPlayer() {
    	
    	asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼  ∙ ☼\n" +
                "☼    ☼\n" +
                "☼☺ ♦ ☼\n" +
                "☼☼==☼☼\n");
    	
    	game2.getJoystick().down();
    	game3.getJoystick().up();
    	game1.tick();
    	game2.getJoystick().down();
    	game3.getJoystick().up();
    	game1.tick();
    	game2.getJoystick().right();
    	game1.tick();
    	
    	asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼    ☼\n" +
                "☼  ♥ ☼\n" +
                "☼  ♣ ☼\n" +
                "☼☺   ☼\n" +
                "☼☼==☼☼\n");
    	
    	game3.getJoystick().act(Actions.HIT_DOWN.getValue());
    	game1.tick();
    	
    	asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼    ☼\n" +
                "☼  ♦ ☼\n" +
                "☼  ♠ ☼\n" +
                "☼☺   ☼\n" +
                "☼☼==☼☼\n");
    	
    	game1.tick();
    	
    	asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼    ☼\n" +
                "☼  ♦ ☼\n" +
                "☼  ♣ ☼\n" +
                "☼☺ * ☼\n" +
                "☼☼==☼☼\n");
    	
    }

    // другой игрок может остановить мяч
    @Test
    public void shouldStopBall() {
    	game2.getJoystick().down();
    	game3.getJoystick().up();
    	game1.tick();
    	game2.getJoystick().down();
    	game3.getJoystick().up();
    	game1.tick();
    	game2.getJoystick().right();
    	game1.tick();
    	
    	asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼    ☼\n" +
                "☼  ♥ ☼\n" +
                "☼  ♣ ☼\n" +
                "☼☺   ☼\n" +
                "☼☼==☼☼\n");
    	
    	game3.getJoystick().act(Actions.HIT_DOWN.getValue());
    	game1.tick();
    	
    	asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼    ☼\n" +
                "☼  ♦ ☼\n" +
                "☼  ♠ ☼\n" +
                "☼☺   ☼\n" +
                "☼☼==☼☼\n");
    	
    	game2.getJoystick().act(Actions.STOP_BALL.getValue());
    	game1.tick();
    	
    	asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼    ☼\n" +
                "☼  ♦ ☼\n" +
                "☼  ♠ ☼\n" +
                "☼☺   ☼\n" +
                "☼☼==☼☼\n");
    }
    
    // игрок не может пойи на другого игрока
    @Test
    public void shouldCantGoOnHero() {
        game1.getJoystick().right();
        game3.getJoystick().left();

        game1.tick();

        asrtFl1("☼☼⌂⌂☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼  ∙ ☼\n" +
                "☼    ☼\n" +
                "☼ ☺♦ ☼\n" +
                "☼☼==☼☼\n");
    }
    
    // счет начисляется
    @Test
    public void scoreMultiplayerTest() {
    	
    	game3.getJoystick().up();
    	game1.tick();
    	game3.getJoystick().up();
    	game1.tick();
    	
    	game3.getJoystick().act(Actions.HIT_UP.getValue());
    	game1.tick();
    	game1.tick();
    	
    	asrtFl1("☼☼⌂x☼☼\n" +
                "☼ ♣  ☼\n" +
                "☼  ♦ ☼\n" +
                "☼    ☼\n" +
                "☼☺   ☼\n" +
                "☼☼==☼☼\n");
    	
    	assertEquals(0, game1.getPlayer().getScore());
    	assertEquals(0, game2.getPlayer().getScore());
    	assertEquals(0, game3.getPlayer().getScore());
    	
    	game1.tick();
    	
    	assertEquals(1, game1.getPlayer().getScore());
    	assertEquals(0, game2.getPlayer().getScore());
    	assertEquals(1, game3.getPlayer().getScore());
    	
    	assertEquals(true, game1.isGameOver());
    	assertEquals(true, game2.isGameOver());
    	assertEquals(true, game3.isGameOver());
    }
}
