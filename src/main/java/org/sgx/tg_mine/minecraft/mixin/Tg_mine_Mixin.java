package org.sgx.tg_mine.minecraft.mixin;

import com.mojang.authlib.GameProfile;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.SendResponse;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SentMessage;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.sgx.tg_mine.minecraft.telegram.Telegram_bot_pengrad;

import java.io.IOException;

@Mixin(ServerPlayerEntity.class)
public abstract class Tg_mine_Mixin extends PlayerEntity {

    public Tg_mine_Mixin(World world, BlockPos pos, float yaw, GameProfile gameProfile) {
        super(world, pos, yaw, gameProfile);
    }

    @Inject(method = "sendChatMessage", at = @At("TAIL"))
    private void injected(SentMessage message, boolean filterMaskEnabled, MessageType.Parameters params, CallbackInfo ci) {
        String text = message.getContent().getString();
        String name = this.getName().getString();
        String final_text = String.format("<i><b>%s:</b></i> %s", name, text);
        SendMessage request = new SendMessage(Telegram_bot_pengrad.chatId, final_text).parseMode(ParseMode.HTML).disableWebPagePreview(true);
        Telegram_bot_pengrad.bot.execute(request);
    }
}
