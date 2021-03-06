package com.wdrshadow.essentialinfo.module.message;

import com.google.inject.Inject;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;

import java.util.Collection;
import java.util.Objects;

public class Message {
    // class for Server
    private final ProxyServer proxyServer;
    private TextComponent textComponent;

    // connect the module to the plugin and server
    @Inject
    public Message(ProxyServer proxyServer){
        this.proxyServer = proxyServer;
    }

    // listener of player chat
    @Subscribe(order = PostOrder.EARLY)
    public void onPlayerChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        broadcast(player, message);
    }

    // broadcast the message
    private void broadcast(Player player, String message){
        // Audience message
        String sendMessage = "<"+player.getUsername()+"> "+ message;
        this.textComponent = Component.text(sendMessage);
        // get players on the proxy exclude those who send message
        Collection<Player> players = proxyServer.getAllPlayers();
        // send message to other players
        for(Player p: players){
            if(!Objects.equals(p.getUsername(), player.getUsername())){
                String playerSever = player.getCurrentServer().get().getServer().getServerInfo().getName();
                String pServer = p.getCurrentServer().get().getServer().getServerInfo().getName();
                if(!Objects.equals(playerSever, pServer)){
                    p.sendMessage(textComponent);
                }
            }
        }
    }
}



