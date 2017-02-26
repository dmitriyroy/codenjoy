package com.codenjoy.dojo.services;

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


public class PlayerGame implements Tickable {

    private Player player;
    private Game game;
    private PlayerController controller;
    private Tickable lazyJoystick;

    public PlayerGame(Player player, Game game, PlayerController controller, Tickable lazyJoystick) {
        this.player = player;
        this.game = game;
        this.controller = controller;
        this.lazyJoystick = lazyJoystick;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == NullPlayerGame.INSTANCE && (o != NullPlayer.INSTANCE && o != NullPlayerGame.INSTANCE)) return false;

        if (o instanceof Player) {
            Player p = (Player)o;

            return p.equals(player);
        }

        if (o instanceof PlayerGame) {
            PlayerGame pg = (PlayerGame)o;

            return pg.player.equals(player);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return player.hashCode();
    }

    public void remove() {
        controller.unregisterPlayerTransport(player);
        game.destroy();
    }

    public Player getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public PlayerController getController() {
        return controller;
    }

    @Override
    public String toString() {
        return String.format("PlayerGame[player=%s, game=%s, controller=%s]",
                player,
                game.getClass().getSimpleName(),
                controller.getClass().getSimpleName());
    }

    @Override
    public void tick() {
        lazyJoystick.tick();
    }
}
