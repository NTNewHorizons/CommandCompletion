package com.bufka.commandcompletion.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;

import com.bufka.commandcompletion.CommonProxy;
import com.bufka.commandcompletion.client.gui.chat.ChatScreen;
import com.bufka.commandcompletion.client.gui.chat.IModChat;
import com.bufka.commandcompletion.client.gui.chat.MPChatScreen;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;

public class ClientProxy extends CommonProxy {

    @Override
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        GuiScreen screen = event.gui;

        if (screen instanceof GuiSleepMP && !(screen instanceof IModChat)) {
            event.gui = new MPChatScreen();
        } else if (screen instanceof GuiChat && !(screen instanceof IModChat)) {
            GuiChat chat = (GuiChat) screen;
            String initialText = "";
            try {
                initialText = ReflectionHelper.getPrivateValue(GuiChat.class, chat, new String[] { "defaultInputFieldText", "field_146410_g" });
            } catch (Exception e) {
                // ignore, fall back to empty
            }
            event.gui = new ChatScreen(initialText);
        }
    }
}
