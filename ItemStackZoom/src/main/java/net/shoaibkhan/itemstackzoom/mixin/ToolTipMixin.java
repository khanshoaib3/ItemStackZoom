package net.shoaibkhan.itemstackzoom.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

@Mixin(InGameHud.class)
public class ToolTipMixin {
  @SuppressWarnings("static-access")
  @Inject(at = @At("TAIL"), method = "renderTooltip(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/item/ItemStack;II)V")
  private void init(MatrixStack matrices, ItemStack stack, int x, int y, CallbackInfo info) {
    RenderSystem.enableBlend();
    MinecraftClient client = MinecraftClient.getInstance();
    client.currentScreen.setZOffset(200);
    client.getItemRenderer().zOffset = 200.0F;
    matrices.push();
    matrices.scale(1, 1, 1);
    client.inGameHud.setZOffset(200);
    int xPos = (int) (client.getWindow().getScaledWidth() * 0.25);
    int yPos = (int) (client.getWindow().getScaledHeight() / 2);
    if (client.options.fullscreen)
      client.inGameHud.fill(matrices, xPos - 80, yPos - 75, xPos - 10, yPos - 5, -267386864);
    else
      client.inGameHud.fill(matrices, xPos - 80, yPos - 90, xPos - 10, yPos - 15, -267386864);
    RenderSystem.translatef(0.0F, 0.0F, 32.0F);
    RenderSystem.scalef(3.8f, 3.8f, 1f);
    if (client.options.fullscreen)
      client.getItemRenderer().renderInGuiWithOverrides(stack, xPos / 10, yPos / 7);
    else
      client.getItemRenderer().renderInGuiWithOverrides(stack, xPos / 10, yPos / 10);
    RenderSystem.scalef(1.0f, 1.0f, 1.0f);
    client.currentScreen.setZOffset(0);
    client.getItemRenderer().zOffset = 0.0F;
    client.inGameHud.setZOffset(0);
    matrices.pop();
  }
}
