package com.nova.armordurability;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public final class NovaArmorDurabilityClient implements ClientModInitializer {
    private static final int PANEL_BG = 0x990B1020;
    private static final int TRACK = 0xFF303747;
    private static final int GREEN = 0xFF35D07F;
    private static final int YELLOW = 0xFFFFD34E;
    private static final int RED = 0xFFFF5555;
    private static final int WHITE = 0xFFFFFFFF;

    private static final EquipmentSlot[] SLOTS = {
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET
    };

    private static final String[] LABELS = {
            "Helmet", "Chestplate", "Leggings", "Boots"
    };

    @Override
    public void onInitializeClient() {
        // Fabric's HUD callback is deprecated in newer API releases, but remains
        // available for Minecraft 1.21.11. It is sufficient for this small overlay.
        HudRenderCallback.EVENT.register(this::renderHud);
    }

    private void renderHud(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        int x = 8;
        int y = 8;
        int panelWidth = 126;
        int rowHeight = 24;
        int panelHeight = 12 + rowHeight * SLOTS.length;

        context.fill(x, y, x + panelWidth, y + panelHeight, PANEL_BG);
        context.drawTextWithShadow(client.textRenderer, Text.literal("NOVA ARMOR"), x + 7, y + 5, WHITE);

        for (int i = 0; i < SLOTS.length; i++) {
            ItemStack stack = client.player.getEquippedStack(SLOTS[i]);
            int rowY = y + 18 + i * rowHeight;
            String label = LABELS[i];

            if (stack.isEmpty() || !stack.isDamageable()) {
                context.drawTextWithShadow(client.textRenderer, Text.literal(label + "  --"), x + 7, rowY, 0xFF9AA4B2);
                continue;
            }

            int maxDamage = stack.getMaxDamage();
            int remaining = Math.max(0, maxDamage - stack.getDamage());
            int percent = Math.round((remaining * 100.0f) / maxDamage);
            int color = percent >= 60 ? GREEN : (percent >= 25 ? YELLOW : RED);

            context.drawTextWithShadow(client.textRenderer,
                    Text.literal(label + "  " + percent + "%"), x + 7, rowY, color);

            int barX = x + 7;
            int barY = rowY + 11;
            int barWidth = 111;
            int fillWidth = Math.round(barWidth * (percent / 100.0f));
            context.fill(barX, barY, barX + barWidth, barY + 4, TRACK);
            if (fillWidth > 0) {
                context.fill(barX, barY, barX + fillWidth, barY + 4, color);
            }
        }
    }
}
