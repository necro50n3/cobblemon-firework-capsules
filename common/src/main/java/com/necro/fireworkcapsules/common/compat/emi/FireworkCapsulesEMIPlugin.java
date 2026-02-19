package com.necro.fireworkcapsules.common.compat.emi;

import com.necro.fireworkcapsules.common.item.FireworkCapsuleItems;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.stack.Comparison;
import dev.emi.emi.api.stack.EmiStack;

@EmiEntrypoint
public class FireworkCapsulesEMIPlugin implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        EmiStack blank = EmiStack.of(FireworkCapsuleItems.STICKER.value()).comparison(Comparison.compareComponents());
        registry.addEmiStackAfter(EmiStack.of(FireworkCapsuleItems.STICKER.value().getDefaultInstance()), blank);
        registry.removeEmiStacks(blank);
    }
}
